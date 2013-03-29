package category.processor;

import java.util.HashMap;
import java.util.Map;

import common.bean.Document;
import common.bean.Term;
import common.bean.TermInfo;
import common.feature.ITermSelector;

public class TermSelectorProcessor extends Processor{

	private Map<String, TermInfo> termInfos;
	
	private ITermSelector termSelector;
	
	@Override
	public void process(Document document) {
		
		for(Term term : document.getTerms()){
			
		}
	}

}
