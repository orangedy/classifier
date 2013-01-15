package common.datasource;

public interface DataSource {

	public String[] getCategorys();
	
	public String[] getContentsByCategory(String category);
	
	public String[] getContents();
}
