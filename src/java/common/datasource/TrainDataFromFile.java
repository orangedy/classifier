package common.datasource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import common.bean.CategoryBean;
import common.bean.Document;
import common.util.FileUtil;

/**
 * 从文件获取输入数据，文件目录为train和eval
 * train下包括positive和negative两个文件夹，分别存放正样本和副样本文件，都以纯文本的方式存储数据，多类别时文件名表示类别名
 * eval下包括positive和negative两个文件夹，分别存放正样本和副样本文件，都以纯文本的方式存储数据，多类别时文件名表示类别名
 * 输出为字符串数组
 * 
 * 是不是效率考虑的太多了？所有文件同时读如内存又怎么样？
 * 
 * @author orangedy
 * 
 */
public class TrainDataFromFile implements TrainDataSource {
	private static Logger log = Logger.getLogger(TrainDataFromFile.class);

	private String trainDataDir;

	private List<CategoryBean> categorys;

	private List<Document> documents;

	public List<CategoryBean> getCategorys() {
		return this.categorys;
	}

	public TrainDataFromFile(String trainDataDir) {
		this.trainDataDir = trainDataDir;
		// init();
	}

	public boolean init() {
		boolean result = true;
		File trainRoot = new File(this.trainDataDir);
		if (trainRoot.isDirectory()) {
			String[] categoryNames = trainRoot.list();
			this.categorys = new ArrayList<CategoryBean>();
			this.documents = new ArrayList<Document>();
			for (String categoryName : categoryNames) {
				CategoryBean category = new CategoryBean(categoryName);
				this.categorys.add(category);
				File classDir = new File(trainDataDir + File.separator + categoryName);
				String[] subFiles = classDir.list();
				int sum = 0;
				for (String subFile : subFiles) {
					File file = new File(this.trainDataDir + File.separator + categoryName + File.separator + subFile);
					if (file.exists()) {
						String content;
						try {
							content = FileUtil.readFile(file);
							Document document = new Document(content, category);
							this.documents.add(document);
							sum++;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				category.setDocumentNum(sum);
			}
		} else {
			result = false;
			log.error("train data dir is not dirctory:" + this.trainDataDir);
		}
		return result;
	}

	/*
	 * 返回下一个文档 当map为空时，没有未处理的document，抛出运行异常
	 * 
	 * @see common.datasource.TrainDataSource#getNextDocument()
	 */
	public Document getNextDocument() {
		Document document = null;
		if (this.documents.isEmpty()) {
			throw new RuntimeException("no more document");
		} else {
			document = this.documents.remove(0);
		}
		return document;
	}

	public boolean haveNext() {
		if (this.documents.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public static void main(String[] args) {
		TrainDataFromFile test = new TrainDataFromFile("E:\\worktemp\\Sample");
		Date start = new Date();
		test.init();
		while (test.haveNext()) {
			Document document = test.getNextDocument();
			System.out.println(document.getCategory().getCategoryName() + " " + document.getContent());
		}
		Date finish = new Date();
		System.out.println(finish.getTime() - start.getTime());
	}
}
