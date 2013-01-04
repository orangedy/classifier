package common.bean;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import common.tokenizer.ITokenizer;

import libsvm.svm_node;

public class Document {

	private Logger log = Logger.getLogger(Document.class);

	/**
	 * document所属的类别，类别应该是可以自动识别的，种类不一定
	 */
	private String category;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * document分词后，所得的词的集合
	 */
	private Set<Term> terms = new HashSet<Term>();

	public Set<Term> getTerms() {
		return terms;
	}

	public void setTerms(Set<Term> terms) {
		this.terms = terms;
	}

	/**
	 * 所使用的分词器，可以通过spring配置不同的分词器
	 */
	private ITokenizer tokenizer;

	public ITokenizer getTokenizer() {
		return tokenizer;
	}

	public void setTokenizer(ITokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	/**
	 * document分词前的内容，为一个string，没必要提供set方法
	 */
	private String content;

	public String getContent() {
		return content;
	}

	public Document(String content, String category) {
		super();
		this.content = content;
		this.category = category;
		init();
	}

	private void init() {
		if (this.content == null || this.content.length() == 0) {
			log.error("创建Document时，content不能为空");
		} else if (this.category == null || this.category.length() == 0) {
			log.error("创建Document时，category不能为空");
		} else {
			String[] termsTemp = this.tokenizer.tokenize(this.content);
			for(String sterm : termsTemp){
				if(sterm == null || sterm.length() <= 2){
					continue;
				}else{
					Term term = new Term(sterm);
					if(terms.contains(term)){
						term.addFrequency();
					}else{
						terms.add(term);
					}
				}
			}
		}
	}

	public svm_node[] getSvmNodeArray() {
		// TODO Auto-generated method stub
		return null;
	}

}
