package category;

import java.util.List;

import common.bean.CategoryBean;
import common.datasource.DataSource;

public abstract class AbstractClassifier {

	/**
	 * 是否需要训练
	 */
	private boolean needTrain = true;
	
	public boolean isNeedTrain() {
		return needTrain;
	}

	public void setNeedTrain(boolean needTrain) {
		this.needTrain = needTrain;
	}

	/**
	 * 是否需要分类
	 */
	private boolean needEval = true;

	public boolean isNeedEval() {
		return needEval;
	}

	public void setNeedEval(boolean needEval) {
		this.needEval = needEval;
	}
	
	/**
	 * 文本数据来源
	 */
	private DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * 类别
	 */
	private List<CategoryBean> categorys;

	public List<CategoryBean> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<CategoryBean> categorys) {
		this.categorys = categorys;
	}

	public AbstractClassifier() {
		super();
	}
	
	public void excuteTrain(){
		if(this.dataSource != null){
			String[] categorys = this.dataSource.getCategorys();
			for(String category : categorys){
				CategoryBean categoryBean = new CategoryBean();
				this.categorys.add(categoryBean);
			}
		}
	}
	
	public void excuteEval(){
		
	}
}
