package category.processor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import category.AbstractTrainer;

import common.bean.Document;
import common.bean.Term;
import common.tokenizer.ITokenizer;
import common.util.ConfigHelper;
import common.util.FileUtil;

public class TokenizerProcessor extends Processor {

	/**
	 * 分词器
	 */
	private ITokenizer tokenizer;

	public ITokenizer getTokenizer() {
		return tokenizer;
	}

	public void setTokenizer(ITokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	/**
	 * 由于忽略的词性就几种，所有用数组一次查找也比hash算法效率高
	 */
	private String[] ignoreType;

	public String[] getIgnoreType() {
		return ignoreType;
	}

	public void setIgnoreType(String[] ignoreType) {
		this.ignoreType = ignoreType;
	}

	/**
	 * 由于停用词可能会比较多，所有用set查找效率会高些
	 */
	private Set<String> stopWords;

	public Set<String> getStopWords() {
		return stopWords;
	}

	public void setStopWords(Set<String> stopWords) {
		this.stopWords = stopWords;
	}

	// 分词
	// TODO 加上过滤停用词
	@Override
	public void process(Document document) {
		String content = document.getContent();
		String[] words = this.tokenizer.tokenize(content);
		// 释放文档内容，已经不再需要
		document.setContent(null);
		content = null;

		// 可以考虑使用谷歌的guava提高效率，map中使用Integer每次都要创建新的实例(超过-128至127)
		Map<String, Integer> termNum = new HashMap<String, Integer>();
		for (String word : words) {
			if (isWord(word) && !isIgnoreType(word) && !isStopWrod(word)) {
				// 文档内词出现的频率统计
				if (termNum.containsKey(word)) {
					int count = termNum.get(word);
					count++;
					termNum.put(word, count);
				} else {
					termNum.put(word, 1);
				}
			}
		}
		// 去掉重复的词，并统计每个词出现的频率
		for (Map.Entry<String, Integer> entry : termNum.entrySet()) {
			String word = entry.getKey();
			int num = entry.getValue();
			Term term = new Term(word, num);
			document.getTerms().add(term);
		}
	}

	public void init() {
		initIgnoreType();
		initStopWord();
	}

	public void initIgnoreType() {
		File ignoreTypeFile = new File(this.getClass().getResource(ConfigHelper.getConfig().getIgnoreTypePath()).getFile());
		try {
			List<String> ignoreType = FileUtil.readFileByLine(ignoreTypeFile);
			this.ignoreType = ignoreType.toArray(new String[0]);
		} catch (IOException e) {
			// ignore
			this.ignoreType = new String[0];
			e.printStackTrace();
		}
	}

	public void initStopWord() {
		File stopWordFile = new File(this.getClass().getResource(ConfigHelper.getConfig().getStopwordPath()).getFile());
		try {
			List<String> stopWord = FileUtil.readFileByLine(stopWordFile);
			this.stopWords = new HashSet<String>();
			for (String word : stopWord) {
				this.stopWords.add(word);
			}
		} catch (IOException e) {
			// ignore
			this.stopWords = new HashSet<String>();
			e.printStackTrace();
		}
	}

	/**
	 * 判断词是否合法
	 * 
	 * @param word
	 * @return
	 */
	public boolean isWord(String word) {
		if (word != null && !word.trim().equals("") && word.length() >= 2 && !word.trim().equals("&#0;")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否为过滤词类型，判断/后面的类型字母即可
	 * 
	 * @param word
	 * @return
	 */
	public boolean isIgnoreType(String word) {
		String part;
		if (word.indexOf("/") != -1) {
			part = word.substring(word.indexOf("/") + 1);
		} else {
			return false;
		}
		for (int i = 0; i < ignoreType.length; i++) {
			if (part.equalsIgnoreCase(ignoreType[i]))
				return true;
		}
		return false;
	}

	/**
	 * 判断是否为需过滤的停用词，判断/前面的字符是否相等
	 * 
	 * @param word
	 * @return
	 */
	public boolean isStopWrod(String word) {
		if (word.indexOf("/") != -1) {
			word = word.substring(0, word.indexOf("/"));
			if (word.length() < 2 || word.length() > 11)
				return true;
		}
		// for(int i = 0; i < stopWords.length; ++i) {
		// if(word.equalsIgnoreCase(stopWords[i]))
		// return true;
		// }
		if (this.stopWords.contains(word)) {
			return true;
		} else {
			return false;
		}
	}
}
