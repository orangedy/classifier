package common.datasource;

public interface TrainDataSource {

	public String[] getCategorys();
	
	public String[] getContentsByCategory(String category);
	
	public String[] getContents();
}
