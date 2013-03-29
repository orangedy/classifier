package category;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import common.bean.CategoryBean;
import common.bean.Document;
import common.datasource.TrainDataSource;

import category.processor.Processor;

public abstract class AbstractTrainer extends AbstractClassifier {
	
	private static Logger log = Logger.getLogger(AbstractTrainer.class);
	
	/**
	 * 处理器
	 */
	private Processor[] processors;

	public Processor[] getProcessors() {
		return processors;
	}

	public void setProcessors(Processor[] processors) {
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
	
	public void train() {
		if(initCategorys()){
			while(this.trainDataSource.haveNext()){
				Document document = this.trainDataSource.getNextDocument();
				this.documents.add(document);
				for(Processor processor : this.processors){
					processor.process(document);
				}
			}
			//特征提取
			
			//训练
		}
	}

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
}
