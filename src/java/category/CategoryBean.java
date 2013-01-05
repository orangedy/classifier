package category;

import java.util.List;

import org.apache.log4j.Logger;

import common.bean.Document;

public class CategoryBean {

	private Logger log = Logger.getLogger(CategoryBean.class);
	
	private int categoryId;
	
	private String categoryName;
	
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
