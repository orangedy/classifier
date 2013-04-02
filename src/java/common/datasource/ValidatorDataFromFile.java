package common.datasource;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import common.bean.Document;
import common.util.ConfigHelper;
import common.util.FileUtil;

/**
 * @author orangedy
 * @version 2013-3-31 下午9:29:55
 * 
 *          待分类数据IO类，封装了读取的细节，对外提供init,haveNext,getNextDocument方法
 *          向该类提供待分类数据的文件目录，该目录下直接包含所有待分类的文件，所有待分类文件后缀为.txt
 *          提供缓存读取，每次从硬盘上读取cacheSize个文件进入内存，当处理完成后再读取，不会一次全部读取
 *          也不会一个读取一个，提供并行处理的可能性
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

	public ValidatorDataFromFile(String validateDataDir) {
		this.validateDataDir = validateDataDir;
	}
	
	public ValidatorDataFromFile() {
		
	}

	/*
	 * 该类实例化后，需要调用init方法进行初始化，否则会出错 初始化读取validateDateDir下所有以.txt结尾的文件列表
	 * 
	 * @see common.datasource.ValidatorDataSource#init()
	 */
	@Override
	public boolean init() {
		boolean result = true;
		this.validateDataDir = ConfigHelper.getConfig().getEvalPath();
		File root = new File(this.validateDataDir);
		if (root.isDirectory()) {
			this.documentsPath = new ArrayList<String>();
			// 只接受后缀为.txt的文件
			String[] subPaths = root.list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if (name.endsWith(".txt")) {
						return true;
					} else {
						return false;
					}
				}
			});
			for (String subPath : subPaths) {
				StringBuilder strBuilder = new StringBuilder(this.validateDataDir);
				strBuilder.append(File.separator + subPath);
				this.documentsPath.add(strBuilder.toString());
			}
			this.documentCache = new ArrayList<Document>(this.cacheSize);
		} else {
			result = false;
			log.error("validateDataDir is not directory:" + this.validateDataDir);
		}
		return result;
	}

	/*
	 * 检测是否还有待处理的文档
	 * 
	 * @see common.datasource.ValidatorDataSource#haveNext()
	 */
	@Override
	public boolean haveNext() {
		if (this.documentCache.isEmpty() && this.documentsPath.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * 获取一个待处理的文档 调用前，请调用haveNext方法，判断是否还有待处理的文档，否则可能出错
	 * 
	 * @see common.datasource.ValidatorDataSource#getNextDocument()
	 */
	@Override
	public Document getNextDocument() {
		if (this.documentCache.isEmpty()) {
			for (int i = 0; i < this.cacheSize; i++) {
				if (this.documentsPath.isEmpty()) {
					break;
				} else {
					String filePath = this.documentsPath.remove(0);
					File file = new File(filePath);
					try {
						String content = FileUtil.readFile(file, "gb2312");
						Document document = new Document(content);
						this.documentCache.add(document);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		assert !this.documentsPath.isEmpty() : "please invoke haveNext before invoke getNextDocument";
		Document result = this.documentCache.remove(0);
		return result;
	}
	
	public static void main(String[] args) {
		ValidatorDataFromFile test = new ValidatorDataFromFile("E:\\categoryworktemp\\SogouC.reduced\\Reduced");
		test.init();
		test.getNextDocument();
	}
}
