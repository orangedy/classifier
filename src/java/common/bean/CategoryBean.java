package common.bean;

import java.util.List;

import org.apache.log4j.Logger;


public class CategoryBean {

	private Logger log = Logger.getLogger(CategoryBean.class);
	
	private int categoryId;
	
	private String categoryName;
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	private List<Document> documents;

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public CategoryBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
