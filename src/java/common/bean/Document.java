package common.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	
//	/**
//	 * 分词后的词，包括重复的词，没有合并
//	 */
//	private String[] termTemp;
//
//	public String[] getTermTemp() {
//		return termTemp;
//	}
//
//	public void setTermTemp(String[] termTemp) {
//		this.termTemp = termTemp;
//	}

	/**
	 * document分词前的内容，为一个string，set方法可以释放文档的内容
	 */
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 文档转化成向量表示
	 */
	private List<svm_node> vectors = new ArrayList<svm_node>();
	
	public List<svm_node> getVectors() {
		return vectors;
	}

	public void setVectors(List<svm_node> vectors) {
		this.vectors = vectors;
	}

	public Document(String content, CategoryBean category) {
		super();
		this.content = content;
		this.category = category;
	}

	public svm_node[] getSvmNodeArray() {
		int l = this.vectors.size();
		svm_node[] nodes = new svm_node[l];
		return this.vectors.toArray(nodes);
	}

	public void addNode(svm_node node){
		this.vectors.add(node);
	}
}
