package common.datasource;

import java.util.ArrayList;
import java.util.List;

import common.bean.CategoryBean;
import common.bean.Document;

public class DataFormatHelper {

	public static List<CategoryBean> getCategorys(DataSource dataSource){
		String[] categorys = dataSource.getCategorys();
		List<CategoryBean> categoryBeans = new ArrayList<CategoryBean>();
		if(categorys.length != 0){
			for(String category : categorys){
				CategoryBean categoryBean = new CategoryBean(category);
				categoryBeans.add(categoryBean);
			}
		}
		return categoryBeans;
	}
	
	public static List<Document> getContentsByCategory(DataSource dataSource, CategoryBean category){
		String[] contents = dataSource.getContentsByCategory(category.getCategoryName());
		List<Document> documents = new ArrayList<Document>();
		if(contents.length != 0){
			for(String content : contents){
				Document document = new Document(content, category);
				documents.add(document);
			}
		}
		return documents;
	}
	
	public static List<Document> getContents(DataSource dataSource){
		String[] contents = dataSource.getContents();
		List<Document> documents = new ArrayList<Document>();
		if(contents.length != 0){
			for(String content : contents){
				Document document = new Document(content, null);
				documents.add(document);
			}
		}
		return documents;
	}
}
