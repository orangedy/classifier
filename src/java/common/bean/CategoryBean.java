package common.bean;

import java.util.List;

import org.apache.log4j.Logger;


public class CategoryBean {

	private static Logger log = Logger.getLogger(CategoryBean.class);
	
	private static int id = 0;
	
	private long categoryId;
	
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

	public CategoryBean(String categoryName) {
		super();
		CategoryBean.id++;
		this.categoryName = categoryName;
		this.categoryId = CategoryBean.id;
	}
	
}
