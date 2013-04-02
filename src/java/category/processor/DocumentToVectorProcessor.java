package category.processor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import category.AbstractTrainer;

import libsvm.svm_node;

import common.bean.Document;
import common.bean.Statistics;
import common.bean.Term;

public class DocumentToVectorProcessor extends Processor implements ApplicationContextAware{
	
	ApplicationContext appCtx;
    public void setApplicationContext(ApplicationContext appCtx) {
        this.appCtx = appCtx;
    }

	/**
	 * 统计信息类
	 */
	private Statistics statistics;
	
	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}


	/**
	 * 选择的特征词
	 */
	private Map<String, Integer> selectTerms;
	
	public Map<String, Integer> getSelectTerms() {
		return selectTerms;
	}

	public void setSelectTerms(Map<String, Integer> selectTerms) {
		this.selectTerms = selectTerms;
	}

	@Override
	public void process(Document document) {
		for(Term term : document.getTerms()){
			if(this.selectTerms.containsKey(term.getTerm())){
				svm_node node = new svm_node();
				node.index = this.selectTerms.get(term.getTerm());
				node.value = term.getFrequency();
				document.addNode(node);
			}
		}
		document.setTerms(null);
	}


	public void init() {
		this.selectTerms = this.statistics.getSelectTerms();
	}

	public void destory(){
		this.selectTerms = null;
		this.statistics = null;
	}
}
