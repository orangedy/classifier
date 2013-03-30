package common.bean;

public class CategoryBean {

	private static int id = 0;
	
	private int categoryId;

	public int getCategoryId() {
		return categoryId;
	}

	private String categoryName;
	
	public String getCategoryName() {
		return categoryName;
	}
	
//	public void setCategoryName(String categoryName) {
//		this.categoryName = categoryName;
//	}
	
	private int documentNum;
	
	public int getDocumentNum() {
		return documentNum;
	}
	
	public void setDocumentNum(int documentNum) {
		this.documentNum = documentNum;
	}

	public CategoryBean(String categoryName) {
		super();
		this.categoryName = categoryName;
		this.categoryId = CategoryBean.id;
		CategoryBean.id++;
	}
	
}
