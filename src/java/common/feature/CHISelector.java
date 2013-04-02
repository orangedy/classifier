package common.feature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;


import common.bean.CategoryBean;
import common.bean.Document;
import common.bean.Term;
import common.bean.TermInfo;

public class CHISelector implements ITermSelector{

	private Logger log = Logger.getLogger(CHISelector.class);
	
	/**
	 * 选择特征词的数量，如果总数少于此数，则返回特征数量为大于minCHIValue的数量
	 */
	private int selectNum = 2000;
	
	public int getSelectNum() {
		return selectNum;
	}

	public void setSelectNum(int selectNum) {
		this.selectNum = selectNum;
	}
	
	/**
	 * 最小的开方检验的值，当开方检验的值为0.455时，相关性的置信度是50%
	 */
//	private double minCHIValue = 0.455;
	private double minCHIValue = 0.1;
	
	public double getMinCHIValue() {
		return minCHIValue;
	}

	public void setMinCHIValue(double minCHIValue) {
		this.minCHIValue = minCHIValue;
	}

	/**
	 * 所有的词算出来的CHI值的集合
	 */
	private Map<Term, Double> termInfo = new HashMap<Term, Double>();	
	
	/**
	 * 参与训练的样本的总数
	 */
	private int trainDocumentsNum;
	
	public int getTrainDocumentsNum() {
		return trainDocumentsNum;
	}

	public void setTrainDocumentsNum(int trainDocumentsNum) {
		this.trainDocumentsNum = trainDocumentsNum;
	}

	/**
	 * 每个类别的文档数
	 */
	private int[] documentNumEachCategory;
	
	public int[] getDocumentNumEachCategory() {
		return documentNumEachCategory;
	}

	public void setDocumentNumEachCategory(int[] documentNumEachCategory) {
		this.documentNumEachCategory = documentNumEachCategory;
	}

	public CHISelector() {
		super();
	}

	public CHISelector(int selectNum) {
		super();
		this.selectNum = selectNum;
	}
	
	public Map<String, Integer> selectTerms(Map<String, TermInfo> termInfo){
//		List<String> selectTerms = new ArrayList<String>();
		Map<String, Integer> selectTermsMap = new HashMap<String, Integer>();
		int index = 0;
		int allDocumentNum = 0;
		int categoryNum = this.documentNumEachCategory.length;
		for(int num : this.documentNumEachCategory){
			allDocumentNum += num;
		}
		for(int i = 0; i < categoryNum; i++){
			int categoryANum = this.documentNumEachCategory[i];
			int nonCategoryANum = allDocumentNum - categoryANum;
			for(Map.Entry<String, TermInfo> entry : termInfo.entrySet()){
				TermInfo termA = entry.getValue();
				int all = 0;
				for(int j = 0; j < categoryNum; j++){
					all += termA.getDocumentFrequency()[j];
				}
				int A = termA.getDocumentFrequency()[i];
				int B = all - A;
				int C = categoryANum - A;
				int D = nonCategoryANum - B;
				double CHIValue = calculateCHI(A, B, C, D);
				termA.setWeight(CHIValue);
			}
			List<String> tempList = selectTopN(termInfo, this.selectNum/categoryNum);
			for(String term : tempList){
				Integer value = selectTermsMap.get(term);
				if(value == null){
					selectTermsMap.put(term, ++index);
				}else{
					
				}
			}
		}
		return selectTermsMap;
	}
	
	public Map<String, Integer> selectTerms(Map<String, TermInfo> termInfo, int[] documentNumEachCategory){
		this.documentNumEachCategory = documentNumEachCategory;
//		List<String> selectTerms = new ArrayList<String>();
		Map<String, Integer> selectTermsMap = new HashMap<String, Integer>();
		int index = 0;
		int allDocumentNum = 0;
		int categoryNum = this.documentNumEachCategory.length;
		for(int num : this.documentNumEachCategory){
			allDocumentNum += num;
		}
		for(int i = 0; i < categoryNum; i++){
			int categoryANum = this.documentNumEachCategory[i];
			int nonCategoryANum = allDocumentNum - categoryANum;
			for(Map.Entry<String, TermInfo> entry : termInfo.entrySet()){
				TermInfo termA = entry.getValue();
				int all = 0;
				for(int j = 0; j < categoryNum; j++){
					all += termA.getDocumentFrequency()[j];
				}
				int A = termA.getDocumentFrequency()[i];
				int B = all - A;
				int C = categoryANum - A;
				int D = nonCategoryANum - B;
				double CHIValue = calculateCHI(A, B, C, D);
				termA.setWeight(CHIValue);
			}
			List<String> tempList = selectTopN(termInfo, this.selectNum/categoryNum);
			for(String term : tempList){
				Integer value = selectTermsMap.get(term);
				if(value == null){
					selectTermsMap.put(term, ++index);
				}else{
					
				}
			}
		}
		return selectTermsMap;
	}


//	@Override
//	public List<Term> selectTerms(List<CategoryBean> categorys) {
//		List<Term> results = new ArrayList<Term>();
//		if(this.selectNum == 0){
//			log.error("请设置选取特征词的数量");
//			return results;
//		}else{
//			
//		}
//		return null;
//	}

//	@Override
//	public List<Term> selectTerms(CategoryBean categoryA, CategoryBean categoryNonA) {
//		List<Term> results = new ArrayList<Term>();
//		if(this.selectNum == 0){
//			log.error("请设置选取特征词的数量");
//			return results;
//		}else{
//			int categoryANum = categoryA.getDocuments().size();
//			int categoryNonANum = categoryNonA.getDocuments().size();
//			Set<Term> allATerms = getAllTerms(categoryA);
//			Set<Term> allNonATerms = getAllTerms(categoryNonA);
//			for(Term term : allATerms){
//				int A = countNum(categoryA.getDocuments(), term);
//				int C = categoryANum - A;
//				int B = 0;
//				int D = 0;
//				if(allNonATerms.contains(term)){
//					B = countNum(categoryNonA.getDocuments(), term);
//					D = categoryNonANum - B;
//					allNonATerms.remove(term);
//				}else{
//					B = 0;
//					D = categoryNonANum;
//				}
//				double CHIValue = calculateCHI(A, B, C, D);
//				this.termInfo.put(term, CHIValue);
//			}
//			for(Term term : allNonATerms){
//				int A = 0;
//				int C = categoryANum;
//				int B = countNum(categoryNonA.getDocuments(), term);
//				int D = categoryNonANum - B;
//				double CHIValue = calculateCHI(A, B, C, D);
//				this.termInfo.put(term, CHIValue);
//			}
//			results = selectTopN(termInfo, this.selectNum);
//		}
//		return results;
//	}

//	public List<Term> selectTopN(Map<Term, Double> termMap, int N) {
//		List<Term> results = new ArrayList<Term>();
//		ArrayList<Map.Entry<Term, Double>> tempList = new ArrayList<Map.Entry<Term, Double>>(termMap.entrySet());
//		Collections.sort(tempList,
//				new Comparator<Map.Entry<Term, Double>>() {
//					public int compare(Map.Entry<Term, Double> o1, Map.Entry<Term, Double> o2) {
//						if(o1.getValue() < o2.getValue()){
//							return -1;
//						}else if(o1.getValue() == o2.getValue()){
//							return 0;
//						}else{
//							return 1;
//						}
//					}
//				});
//		for(int i = 0; i < N; i++){
//			if(tempList.get(i).getValue() > this.minCHIValue){
//				results.add(tempList.get(i).getKey());
//			}else{
//				break;
//			}
//		}
//		return results;
//	}
	
	public List<String> selectTopN(Map<String, TermInfo> termMap, int N) {
		List<String> results = new ArrayList<String>();
		ArrayList<Entry<String, TermInfo>> tempList = new ArrayList<Map.Entry<String, TermInfo>>(termMap.entrySet());
		Collections.sort(tempList,
				new Comparator<Map.Entry<String, TermInfo>>() {
					public int compare(Map.Entry<String, TermInfo> o1, Map.Entry<String, TermInfo> o2) {
						if(o1.getValue().getWeight() < o2.getValue().getWeight()){
							return 1;
						}else if(o1.getValue().getWeight() == o2.getValue().getWeight()){
							return 0;
						}else{
							return -1;
						}
					}
				});
		for(int i = 0; i < N && i < tempList.size(); i++){
			if(tempList.get(i).getValue().getWeight() > this.minCHIValue){
				results.add(tempList.get(i).getKey());
			}else{
				break;
			}
		}
		return results;
	}

	public double calculateCHI(int a, int b, int c, int d) {
		double CHIValue = 0;
		double numerator = (double)(a + b + c + d)*(a*d - b*c)*(a*d - b*c);
		double denominator = (double)(a + c)*(a + b)*(b + d)*(c + d);
		CHIValue = numerator/denominator;
		return CHIValue;
	}

//	public Set<Term> getAllTerms(CategoryBean category){
//		HashSet<Term> temp = new HashSet<Term>();
//		for(Document document : category.getDocuments()){
//			for(Term term : document.getTerms()){
//				if(temp.contains(term)){
//					
//				}else{
//					temp.add(term);
//				}
//			}
//		}
//		return temp;
//	}
//	
//	public int countNum(List<Document> documents, Term term) {
//		int count = 0;
//		for(Document document : documents){
//			if(document.getTerms().contains(term)){
//				count++;
//			}
//		}
//		return count;
//	}

	
}
