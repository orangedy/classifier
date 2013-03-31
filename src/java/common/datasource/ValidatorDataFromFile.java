package common.datasource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import common.bean.Document;
import common.util.FileUtil;

/**
 * @author orangedy
 * @version 2013-3-31 下午9:29:55
 */
public class ValidatorDataFromFile implements ValidatorDataSource {
	
	private static Logger log = Logger.getLogger(ValidatorDataFromFile.class);
	
	/**
	 * 待分类的数据的文件目录
	 */
	private String validateDataDir;

	public String getValidateDataDir() {
		return validateDataDir;
	}

	public void setValidateDataDir(String validateDataDir) {
		this.validateDataDir = validateDataDir;
	}
	
	/**
	 * 存放所有待分类的文件的路径
	 */
	private List<String> documentsPath;
	
	/**
	 * 用于缓存，每次读取一定数量的文件放入内存，处理完成后再继续读取
	 */
	private List<Document> documentCache;
	
	/**
	 * 缓存的大小，每次读取这么大小的文件放入内存
	 */
	private int cacheSize = 100;
	
	public int getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public ValidatorDataFromFile(String validateDataDir){
		this.validateDataDir = validateDataDir;
	}

	@Override
	public boolean init() {
		boolean result = true;
		File root = new File(this.validateDataDir);
		if(root.isDirectory()){
			this.documentsPath = new ArrayList<String>();
			String[] subPaths = root.list();
			for(String subPath : subPaths){
				StringBuilder strBuilder = new StringBuilder(this.validateDataDir);
				strBuilder.append(File.separator + subPath);
				this.documentsPath.add(strBuilder.toString());
			}
			this.documentCache = new ArrayList<Document>(this.cacheSize);
		}else{
			result = false;
			log.error("validateDataDir is not directory:" + this.validateDataDir);
		}
		return result;
	}

	@Override
	public boolean havaNext() {
		if(this.documentCache.isEmpty() && this.documentsPath.isEmpty()){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Document getNextDocument() {
		if(this.documentCache.isEmpty()){
			for(int i = 0; i < this.cacheSize; i++){
				if(this.documentsPath.isEmpty()){
					break;
				}else{
					String filePath = this.documentsPath.remove(0);
					File file = new File(filePath);
					String content = FileUtil.readFile(file);
					Document document = new Document(content);
					this.documentCache.add(document);
				}
			}
		}
		Document result = this.documentCache.remove(0);
		return result;
	}

}
