package common.datasource;

import common.bean.Document;

public interface ValidatorDataSource {

	public boolean init();
	
	public boolean havaNext();
	
	public Document getNextDocument();
}
