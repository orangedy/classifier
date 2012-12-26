package common.bean;

import java.util.ArrayList;
import java.util.List;

public class DocumentHelper {

	public static List<Document> readDocuments(String filePath){
		List<Document> documents = new ArrayList<Document>();
		try{
			String[] lines = readLines(filePath);
			for(int i = lines.length -1; i > 0; i--){
				initDocument(lines[i]);
			}
		} catch(Exception e){
			
		}
		return documents;
	}

	private static void initDocument(String string) {
		// TODO Auto-generated method stub
		
	}

	private static String[] readLines(String filePath) {
		// TODO Auto-generated method stub
		return null;
	}
}
