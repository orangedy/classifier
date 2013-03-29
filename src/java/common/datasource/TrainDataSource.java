package common.datasource;

import java.util.List;

import common.bean.CategoryBean;
import common.bean.Document;

public interface TrainDataSource {

	public void init();
	
	public List<CategoryBean> getCategorys();
	
	public Document getNextDocument();
	
	public boolean haveNext();
}
