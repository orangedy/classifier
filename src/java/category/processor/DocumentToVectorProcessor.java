package category.processor;

import java.util.List;
import java.util.Map;

import category.AbstractTrainer;

import libsvm.svm_node;

import common.bean.Document;
import common.bean.Term;

public class DocumentToVectorProcessor extends Processor{

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


	@Override
	public void init(AbstractTrainer train) {
		
	}

}
