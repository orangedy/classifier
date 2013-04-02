package common.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author orangedy
 * 
 *         统计信息类 包含类别信息，词统计信息，特征词信息等
 * 
 */
public class Statistics {

	/**
	 * 每个类别的训练文档数，按照类别id从小到大
	 */
	private int[] documentNumEachCategory;

	/**
	 * 每个词在各个类别中出现的文档数，按照类别id从小到大
	 */
	private Map<String, TermInfo> termEachCategory = new HashMap<String, TermInfo>();

	/**
	 * 通过特征选择算法选出的特征词
	 */
	private Map<String, Integer> selectTerms = new HashMap<String, Integer>();

	public Statistics() {

	}

	public void distory() {
		this.documentNumEachCategory = null;
		this.termEachCategory = null;
		this.selectTerms = null;
	}

	public int[] getDocumentNumEachCategory() {
		return documentNumEachCategory;
	}

	public void setDocumentNumEachCategory(int[] documentNumEachCategory) {
		this.documentNumEachCategory = documentNumEachCategory;
	}

	public Map<String, TermInfo> getTermEachCategory() {
		return termEachCategory;
	}

	public void setTermEachCategory(Map<String, TermInfo> termEachCategory) {
		this.termEachCategory = termEachCategory;
	}

	public Map<String, Integer> getSelectTerms() {
		return selectTerms;
	}

	public void setSelectTerms(Map<String, Integer> selectTerms) {
		this.selectTerms = selectTerms;
	}

}
