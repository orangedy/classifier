package category.processor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import category.AbstractTrainer;

import common.bean.CategoryBean;
import common.bean.Document;
import common.bean.Statistics;
import common.bean.Term;
import common.bean.TermInfo;
import common.feature.ITermSelector;

public class TermSelectorProcessor extends Processor{

	/**
	 * 统计信息
	 */
	private Statistics statistics;
	
	public Statistics getStatistics() {
		return statistics;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

	private Map<String, TermInfo> termInfos;
	
	/**
	 * 类别数，统计时，需要知道有多少类
	 */
	private int categoryNum;
	
	public int getCategoryNum() {
		return categoryNum;
	}

	public void setCategoryNum(int categoryNum) {
		this.categoryNum = categoryNum;
	}
	
	public void init(){
		this.categoryNum = this.statistics.getDocumentNumEachCategory().length;
		this.termInfos = this.statistics.getTermEachCategory();
	}

	@Override
	public void process(Document document) {
		int categoryId = document.getCategory().getCategoryId();
		for(Term term : document.getTerms()){
			TermInfo termInfo = termInfos.get(term.getTerm());
			if(termInfo == null){
				TermInfo value = new TermInfo();
				value.setDocumentFrequency(new int[this.categoryNum]);
				value.getDocumentFrequency()[categoryId]++;
				termInfos.put(term.getTerm(), value);
			}else{
				termInfo.getDocumentFrequency()[categoryId]++;
			}
		}
	}
	
	public void destory(){
		this.termInfos = null;
		this.statistics = null;
	}

	public static void main(String[] args) {
		TermSelectorProcessor termSelector = new TermSelectorProcessor();
		termSelector.setCategoryNum(3);
		termSelector.termInfos = new HashMap<String, TermInfo>();
		CategoryBean categoryA = new CategoryBean("A");
		Document document = new Document("", categoryA);
		Term term1 = new Term("111");
		Term term2 = new Term("222");
		Set<Term> set = new HashSet<Term>();
		set.add(term1);
		set.add(term2);
		
		document.setTerms(set);
		termSelector.process(document);
		
	}
}
