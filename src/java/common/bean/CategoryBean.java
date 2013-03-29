package common.bean;

import java.util.List;

import org.apache.log4j.Logger;


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

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public CategoryBean(String categoryName) {
		super();
		CategoryBean.id++;
		this.categoryName = categoryName;
		this.categoryId = CategoryBean.id;
	}
	
}
