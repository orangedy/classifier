package category;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import common.bean.CategoryBean;
import common.bean.Document;
import common.bean.Statistics;
import common.bean.TermInfo;
import common.datasource.TrainDataSource;
import common.feature.ITermSelector;
import common.util.ConfigHelper;
import common.util.FileUtil;

import category.processor.DocumentToVectorProcessor;
import category.processor.Processor;

public abstract class AbstractTrainer extends AbstractClassifier {

	private static Logger log = Logger.getLogger(AbstractTrainer.class);

	/**
	 * 处理器
	 */
	private List<Processor> processors;

	public List<Processor> getProcessors() {
		return processors;
	}

	public void setProcessors(List<Processor> processors) {
		this.processors = processors;
	}

	/**
	 * 训练数据来源，可为文件或数据库等，只要实现了TrainDataSource接口即可
	 */
	private TrainDataSource trainDataSource;

	public TrainDataSource getTrainDataSource() {
		return trainDataSource;
	}

	public void setTrainDataSource(TrainDataSource trainDataSource) {
		this.trainDataSource = trainDataSource;
	}

	/**
	 * 训练器的类别
	 */
	private List<CategoryBean> categorys;

	public List<CategoryBean> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<CategoryBean> categorys) {
		this.categorys = categorys;
	}

	/**
	 * 待训练的样本
	 */
	private List<Document> documents = new ArrayList<Document>();

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	/**
	 * 统计信息
	 */
	private Statistics statistics;

	public Statistics getStatistics() {
		return statistics;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

//	/**
//	 * 词的统计信息，格式如下： 词 类别1 类别2 类别3... ‘训练’ 2 3 0 ......
//	 */
//	private Map<String, TermInfo> termsInfo = new HashMap<String, TermInfo>();
//
//	public Map<String, TermInfo> getTermsInfo() {
//		return termsInfo;
//	}
//
//	public void setTermsInfo(Map<String, TermInfo> termsInfo) {
//		this.termsInfo = termsInfo;
//	}

	/**
	 * 特征选择算法 默认实现CHI
	 */
	private ITermSelector termSelector;

	public ITermSelector getTermSelector() {
		return termSelector;
	}

	public void setTermSelector(ITermSelector termSelector) {
		this.termSelector = termSelector;
	}

	/**
	 * 文档转化为向量的处理器
	 */
	private DocumentToVectorProcessor documentToVector;

	public DocumentToVectorProcessor getDocumentToVector() {
		return documentToVector;
	}

	public void setDocumentToVector(DocumentToVectorProcessor documentToVector) {
		this.documentToVector = documentToVector;
	}

	public void init() {
		this.trainDataSource.init();
		this.categorys = this.trainDataSource.getCategorys();
		this.statistics.setDocumentNumEachCategory(new int[this.categorys.size()]);
		for (CategoryBean category : this.categorys) {
			int id = category.getCategoryId();
			this.statistics.getDocumentNumEachCategory()[id] = category.getDocumentNum();
		}
		initProcessor();
	}

	public void train() {
		init();
		while (this.trainDataSource.haveNext()) {
			Document document = this.trainDataSource.getNextDocument();
			this.documents.add(document);
			for (Processor processor : this.processors) {
				processor.process(document);
			}
		}
		// 特征提取
		int[] documentNumEachCategory = this.statistics.getDocumentNumEachCategory();
		Map<String, Integer> selectTerms = this.termSelector.selectTerms(this.statistics.getTermEachCategory(),
				documentNumEachCategory);
		this.statistics.setSelectTerms(selectTerms);
		this.documentToVector.init();
		for (Document document : this.documents) {
			this.documentToVector.process(document);
		}
		// 训练
		doTrain();
		saveCategoryInfo();
		saveSelectTerms(selectTerms);

	}

//	public void train() {
//		if (initCategorys()) {
//			initProcessor();
//			while (this.trainDataSource.haveNext()) {
//				Document document = this.trainDataSource.getNextDocument();
//				this.documents.add(document);
//				for (Processor processor : this.processors) {
//					processor.process(document);
//				}
//			}
//			// 特征提取
//			int[] documentNumEachCategory = new int[this.categorys.size()];
//			for (int i = 0; i < documentNumEachCategory.length; i++) {
//				documentNumEachCategory[i] = this.categorys.get(i).getDocumentNum();
//			}
//			Map<String, Integer> selectTerms = this.termSelector.selectTerms(this.termsInfo, documentNumEachCategory);
//			// test
//			for (String term : selectTerms.keySet()) {
//				log.debug("term:" + term);
//			}
//			this.termsInfo = null;
//			this.documentToVector.setSelectTerms(selectTerms);
//			for (Document document : this.documents) {
//				this.documentToVector.process(document);
//			}
//			// 训练
//			doTrain();
//			saveCategoryInfo();
//			saveSelectTerms(selectTerms);
//		}
//	}

	private boolean initCategorys() {
		boolean result = true;
		if (this.trainDataSource != null) {
			this.trainDataSource.init();
			this.categorys = this.trainDataSource.getCategorys();
		} else {
			result = false;
			log.error("trainDataSource is null");
		}
		return result;
	}

	private void initProcessor() {
		for (Processor processor : this.processors) {
			processor.init();
		}
	}

	public void saveCategoryInfo() {
		String path = ConfigHelper.getConfig().getCategoryPath();
		File file = new File(path);
		StringBuilder buffer = new StringBuilder();
		for (CategoryBean category : this.categorys) {
			buffer.append(category.getCategoryId() + " " + category.getCategoryName() + "\n");
		}
		try {
			FileUtil.writeStringToFile(file, buffer.toString(), false, "gb2312");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveSelectTerms(Map<String, Integer> selectTerms) {
		String path = ConfigHelper.getConfig().getTermsPath();
		File file = new File(path);
		StringBuilder buffer = new StringBuilder();
		for (Map.Entry<String, Integer> entry : selectTerms.entrySet()) {
			buffer.append(entry.getKey() + " " + entry.getValue() + "\n");
		}
		try {
			FileUtil.writeStringToFile(file, buffer.toString(), false, "gb2312");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract void doTrain();
}
