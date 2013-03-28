package category;

import java.util.ArrayList;
import java.util.List;

import common.bean.CategoryBean;
import common.datasource.TrainDataSource;

import category.processor.Processor;

public abstract class AbstractTrainer extends AbstractClassifier {
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

	public void train() {
		if (this.trainDataSource != null) {
			// this.categorys = this.trainDataSource.getCategorys();

		} else {

		}
	}

	private void initCategorys() {
		String[] categorys = this.trainDataSource.getCategorys();
		if(categorys.length != 0){
			
			List<CategoryBean> categoryBeans = new ArrayList<CategoryBean>();
			for (String category : categorys) {
				CategoryBean categoryBean = new CategoryBean(category);
				categoryBeans.add(categoryBean);
			}
			this.categorys = categoryBeans;
		}else{
			
		}
	}
}
