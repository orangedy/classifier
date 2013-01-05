package common.feature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import category.CategoryBean;

import common.bean.Document;
import common.bean.Term;

public class CHISelector implements ITermSelector{

	private Logger log = Logger.getLogger(CHISelector.class);
	
	/**
	 * 选择特征词的数量，如果总数少于此数，则返回特征数量为大于minCHIValue的数量
	 */
	private int selectNum;
	
	public int getSelectNum() {
		return selectNum;
	}

	public void setSelectNum(int selectNum) {
		this.selectNum = selectNum;
	}
	
	/**
	 * 最小的开方检验的值，当开方检验的值为0.455时，相关性的可能性是50%
	 */
	private double minCHIValue = 0.455;
	
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

	public CHISelector() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Term> selectTerms(List<CategoryBean> categorys) {
		List<Term> results = new ArrayList<Term>();
		if(this.selectNum == 0){
			log.error("请设置选取特征词的数量");
			return results;
		}else{
			
		}
		return null;
	}

	@Override
	public List<Term> selectTerms(CategoryBean categoryA, CategoryBean categoryNonA) {
		List<Term> results = new ArrayList<Term>();
		if(this.selectNum == 0){
			log.error("请设置选取特征词的数量");
			return results;
		}else{
			int categoryANum = categoryA.getDocuments().size();
			int categoryNonANum = categoryNonA.getDocuments().size();
			Set<Term> allATerms = getAllTerms(categoryA);
			Set<Term> allNonATerms = getAllTerms(categoryNonA);
			for(Term term : allATerms){
				int A = countNum(categoryA.getDocuments(), term);
				int C = categoryANum - A;
				int B = 0;
				int D = 0;
				if(allNonATerms.contains(term)){
					B = countNum(categoryNonA.getDocuments(), term);
					D = categoryNonANum - B;
					allNonATerms.remove(term);
				}else{
					B = 0;
					D = categoryNonANum;
				}
				double CHIValue = calculateCHI(A, B, C, D);
				this.termInfo.put(term, CHIValue);
			}
			for(Term term : allNonATerms){
				int A = 0;
				int C = categoryANum;
				int B = countNum(categoryNonA.getDocuments(), term);
				int D = categoryNonANum - B;
				double CHIValue = calculateCHI(A, B, C, D);
				this.termInfo.put(term, CHIValue);
			}
			results = selectTopN(termInfo);
		}
		return null;
	}

	public List<Term> selectTopN(Map<Term, Double> termMap) {
		List<Term> results = new ArrayList<Term>();
		int length = termMap.size();
		double[] values = new double[length];
		int position = 0;
		Collections.sort(termMap, new Comparator<Map.Entry<Term, Double>>() {   
            public int compare(Map.Entry<Term, Double> o1, Map.Entry<Term, Double> o2) {   
                return (int) (o2.getValue() - o1.getValue());
            }
		}
		return null;
	}

	public double calculateCHI(int a, int b, int c, int d) {
		return 0;
	}

	public Set<Term> getAllTerms(CategoryBean category){
		HashSet<Term> temp = new HashSet<Term>();
		for(Document document : category.getDocuments()){
			for(Term term : document.getTerms()){
				if(temp.contains(term)){
					
				}else{
					temp.add(term);
				}
			}
		}
		return temp;
	}
	
	public int countNum(List<Document> documents, Term term) {
		int count = 0;
		for(Document document : documents){
			if(document.getTerms().contains(term)){
				count++;
			}
		}
		return count;
	}

	
}
