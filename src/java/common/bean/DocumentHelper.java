package common.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import common.tokenizer.ICTCLASTokenizer;
import common.tokenizer.ITokenizer;
import common.util.FileUtil;

public class DocumentHelper {
	
	private static Logger log = Logger.getLogger(Document.class);
	
	/**
	 * 所使用的分词器，可以通过spring配置不同的分词器
	 */
	private ITokenizer tokenizer = new ICTCLASTokenizer();

	public ITokenizer getTokenizer() {
		return tokenizer;
	}

	public void setTokenizer(ITokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}
	
	public DocumentHelper() {
		super();
	}

//	public static List<Document> readDocumentsFromPath(String filePath, String category){
//		List<Document> documents = new ArrayList<Document>();
//		try{
//			String[] lines = readFiles(filePath);
//			for(String line : lines){
//				if(line == null || line.length() == 0){
//					continue;
//				}else{
//					Document document = initDocument(line, category);
//					documents.add(document);
//				}
//			}
//		} catch(Exception e){
//			log.error("read file failed: " + filePath);
//			e.printStackTrace();
//		}
//		return documents;
//	}

	public Document initDocument(String content, CategoryBean category) {
		if (content == null || content.length() == 0) {
			log.error("创建Document时，content不能为空");
			return null;
		} else if (category == null || category.getCategoryName().length() == 0) {
			log.error("创建Document时，category不能为空");
			return null;
		}else{
			Document document = new Document(content, category);
			String[] termsTemp = this.tokenizer.tokenize(document.getContent());
			for(String sterm : termsTemp){
				if(sterm == null || sterm.length() <= 2){
					continue;
				}else{
					Term term = new Term(sterm);
					if(document.getTerms().contains(term)){
						term.addFrequency();
					}else{
						document.getTerms().add(term);
					}
				}
			}
			return document;
		}
	}

//	public static String[] readFiles(String filePath) {
//		int i = 0;
//		File path = new File(filePath);
//		if(path.exists() && path.listFiles() != null){
//			File[] files = path.listFiles();
//			String[] lines = new String[files.length];
//			for(File file : files){
//				if(file.exists()){
//					String line = FileUtil.reanFile(file);
//					lines[i++] = line;
//				}
//			}
//			return lines;
//		}else{
//			log.error("输入样本路径出错: " + filePath);
//			return new String[0];
//		}
//	}
	
	public static void main(String[] args) {
//		String filePath = "E:/worktemp/Sample/C0000087";
//		String category = "C000007";
//		DocumentHelper.readDocumentsFromPath(filePath, category);
	}
}
