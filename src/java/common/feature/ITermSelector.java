package common.feature;

import java.util.List;


import common.bean.CategoryBean;
import common.bean.Document;
import common.bean.Term;

public interface ITermSelector {

	public List<Term> selectTerms(List<CategoryBean> categorys);
	
	public List<Term> selectTerms(CategoryBean categoryA, CategoryBean categoryNonA);
	
}
