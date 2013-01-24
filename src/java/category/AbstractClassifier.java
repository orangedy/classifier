package category;

import java.util.List;
import java.util.Map;

import common.bean.CategoryBean;
import common.bean.Document;
import common.datasource.DataFormatHelper;
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
	
	private Map<String, List<Document>> map;

	public AbstractClassifier() {
		super();
	}
	
	public void excuteTrain(){
		if(this.dataSource != null){
			categorys = DataFormatHelper.getCategorys(this.dataSource);
			for(CategoryBean category : categorys){
				List<Document> documents = DataFormatHelper.getContentsByCategory(this.dataSource, category);
				category.setDocuments(documents);
			}
		}
	}
	
	public void excuteEval(){
		
	}
}
