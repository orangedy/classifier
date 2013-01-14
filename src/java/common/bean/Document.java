package common.bean;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import common.tokenizer.ICTCLASTokenizer;
import common.tokenizer.ITokenizer;

import libsvm.svm_node;

public class Document {

	private Logger log = Logger.getLogger(Document.class);

	/**
	 * document所属的类别，类别应该是可以自动识别的，种类不一定
	 */
	private CategoryBean category;

	public CategoryBean getCategory() {
		return category;
	}

	public void setCategory(CategoryBean category) {
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
	 * document分词前的内容，为一个string，没必要提供set方法
	 */
	private String content;

	public String getContent() {
		return content;
	}

	public Document(String content, CategoryBean category) {
		super();
		this.content = content;
		this.category = category;
	}

	public svm_node[] getSvmNodeArray() {
		return null;
	}

}
