package common.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import common.util.FileUtil;

public class DocumentHelper {
	
	private static Logger log = Logger.getLogger(Document.class);

	public static List<Document> readDocumentsFromPath(String filePath, String category){
		List<Document> documents = new ArrayList<Document>();
		try{
			String[] lines = readFiles(filePath);
			for(String line : lines){
				if(line == null || line.length() == 0){
					continue;
				}else{
					Document document = initDocument(line, category);
					documents.add(document);
				}
			}
		} catch(Exception e){
			log.error("read file failed: " + filePath);
			e.printStackTrace();
		}
		return documents;
	}

	public static Document initDocument(String content, String category) {
		Document document = new Document(content, category);
		return document;
	}

	public static String[] readFiles(String filePath) {
		int i = 0;
		File path = new File(filePath);
		if(path.exists() && path.listFiles() != null){
			File[] files = path.listFiles();
			String[] lines = new String[files.length];
			for(File file : files){
				if(file.exists()){
					String line = FileUtil.reanFile(file);
					lines[i++] = line;
				}
			}
			return lines;
		}else{
			log.error("输入样本路径出错: " + filePath);
			return new String[0];
		}
	}
	
	public static void main(String[] args) {
		String filePath = "E:/worktemp/Sample/C0000087";
		String category = "C000007";
		DocumentHelper.readDocumentsFromPath(filePath, category);
	}
}
