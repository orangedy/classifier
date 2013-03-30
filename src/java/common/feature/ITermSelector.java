package common.feature;

import java.util.List;
import java.util.Map;
import java.util.Set;


import common.bean.CategoryBean;
import common.bean.Document;
import common.bean.Term;
import common.bean.TermInfo;

public interface ITermSelector {

//	public List<Term> selectTerms(List<CategoryBean> categorys);
//	
//	public List<Term> selectTerms(CategoryBean categoryA, CategoryBean categoryNonA);
	
//	public List<String> selectTerms(Map<String, TermInfo> termInfos);
	
	public Map<String, Integer> selectTerms(Map<String, TermInfo> termInfos);
	
	public Map<String, Integer> selectTerms(Map<String, TermInfo> termInfo, int[] documentNumEachCategory);
}
