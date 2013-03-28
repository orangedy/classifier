package category;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import common.bean.CategoryBean;
import common.bean.Document;
import common.bean.Term;
import common.datasource.DataFormatHelper;
import common.datasource.TrainDataSource;
import common.feature.ITermSelector;
import common.tokenizer.ITokenizer;

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
	private TrainDataSource dataSource;

	public TrainDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(TrainDataSource dataSource) {
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
	
	/**
	 *  分词器
	 */
	private ITokenizer tokenizer;

	public ITokenizer getTokenizer() {
		return tokenizer;
	}

	public void setTokenizer(ITokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}
	
	/**
	 * 特征选择
	 */
	private ITermSelector termSelector;

	public ITermSelector getTermSelector() {
		return termSelector;
	}

	public void setTermSelector(ITermSelector termSelector) {
		this.termSelector = termSelector;
	}

	public AbstractClassifier() {
		super();
	}

	//TODO Term重复的问题
	public void initDocuments(Map<CategoryBean, List<Document>> documentsMap) {
		for (Map.Entry<CategoryBean, List<Document>> entry : documentsMap.entrySet()) {
			for (Document document : entry.getValue()) {
				String[] terms = this.tokenizer.tokenize(document.getContent());
				HashSet<Term> set = new HashSet<Term>();
				for(String termStr : terms){
					Term term = new Term(termStr);
					if(set.contains(term)){
						term.addFrequency();
						set.add(term);
					}
				}
				document.setTerms(set);
			}
		}
	}

	public void excuteTrain() {
		if (this.dataSource != null) {
			categorys = DataFormatHelper.getCategorys(this.dataSource);
			Map<CategoryBean, List<Document>> documentsMap = new HashMap<CategoryBean, List<Document>>();
			for (CategoryBean category : categorys) {
				List<Document> documents = DataFormatHelper.getContentsByCategory(this.dataSource, category);
				documentsMap.put(category, documents);
			}
			initDocuments(documentsMap);
			
		}
	}

	public void excuteEval() {

	}
}
