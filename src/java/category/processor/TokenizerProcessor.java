package category.processor;

import java.util.HashMap;
import java.util.Map;

import common.bean.Document;
import common.bean.Term;
import common.tokenizer.ITokenizer;

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

	// 分词
	// TODO 加上过滤停用词
	@Override
	public void process(Document document) {
		String content = document.getContent();
		String[] words = this.tokenizer.tokenize(content);
		//释放文档内容，已经不再需要
		document.setContent(null);
		
		Map<String, Integer> termNum = new HashMap<String, Integer>();
		for (String word : words) {
			// 文档内词出现的频率统计
			if (termNum.containsKey(word)) {
				int count = termNum.get(word);
				count++;
				termNum.put(word, count);
			} else {
				termNum.put(word, 1);
			}
		}
		//去掉重复的词，并统计每个词出现的频率
		for(Map.Entry<String, Integer> entry : termNum.entrySet()){
			String word = entry.getKey();
			int num = entry.getValue();
			Term term = new Term(word, num);
			document.getTerms().add(term);
		}
	}

}
