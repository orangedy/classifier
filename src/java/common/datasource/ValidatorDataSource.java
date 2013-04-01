package common.datasource;

import java.util.List;

import common.bean.CategoryBean;
import common.bean.Document;

public interface ValidatorDataSource {

	public boolean init();
	
	public boolean haveNext();
	
	public Document getNextDocument();
	
}
