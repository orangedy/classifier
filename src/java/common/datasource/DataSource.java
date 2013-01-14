package common.datasource;

public interface DataSource {

	public String[] getCategorys();
	
	public String[] getContentDirByCategory(String category);
}
