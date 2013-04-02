package category;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import category.processor.DocumentToVectorProcessor;
import category.processor.Processor;

import common.bean.CategoryBean;
import common.bean.Document;
import common.datasource.ValidatorDataSource;
import common.util.ConfigHelper;
import common.util.FileUtil;

public abstract class AbstractValidator extends AbstractClassifier{

	private static Logger log = Logger.getLogger(AbstractValidator.class);
	
	/**
	 * 待分类数据IO接口
	 */
	private ValidatorDataSource validatorDataSource;

	public ValidatorDataSource getValidatorDataSource() {
		return validatorDataSource;
	}

	public void setValidatorDataSource(ValidatorDataSource validatorDataSource) {
		this.validatorDataSource = validatorDataSource;
	}
	
	/**
	 * 类别信息
	 */
	private Map<Double, String> categoryInfo = new HashMap<Double, String>();
	
	public Map<Double, String> getCategoryInfo() {
		return categoryInfo;
	}

	public void setCategoryInfo(Map<Double, String> categoryInfo) {
		this.categoryInfo = categoryInfo;
	}
	
	/**
	 * 训练结果信息统计
	 */
	private Map<Double, Integer> resultInfo;

	public Map<Double, Integer> getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(Map<Double, Integer> resultInfo) {
		this.resultInfo = resultInfo;
	}

	/**
	 * 特征词信息
	 */
	private Map<String, Integer> termInfo;
	
	public Map<String, Integer> getTermInfo() {
		return termInfo;
	}

	public void setTermInfo(Map<String, Integer> termInfo) {
		this.termInfo = termInfo;
	}
	
	/**
	 * 分词器
	 */
	private Processor tokenizer;

	public Processor getTokenizer() {
		return tokenizer;
	}

	public void setTokenizer(Processor tokenizer) {
		this.tokenizer = tokenizer;
	}
	
	/**
	 * 将文档转化为向量表示
	 */
	private DocumentToVectorProcessor vectorCreator;

	public DocumentToVectorProcessor getVectorCreator() {
		return vectorCreator;
	}

	public void setVectorCreator(DocumentToVectorProcessor vectorCreator) {
		this.vectorCreator = vectorCreator;
	}
	
	public void init(){
		this.validatorDataSource.init();
		initCategoryInfo();
		initTermSelectorInfo();
		initModel();
		this.vectorCreator.setSelectTerms(this.termInfo);
		this.resultInfo = new HashMap<Double, Integer>();
		this.tokenizer.init();
	}
	
	private void initCategoryInfo(){
		String path = ConfigHelper.getConfig().getCategoryPath();
		File file = new File(path);
		try {
			List<String> content = FileUtil.readFileByLine(file);
			for(String line : content){
				String[] categoryInfo = line.split(" ");
				Double value = Double.valueOf(categoryInfo[0]);
				String name = categoryInfo[1];
				this.categoryInfo.put(value, name);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("初始化类别信息失败，请检查result/category.txt文件是否正确");
		}
	}
	
	private void initTermSelectorInfo(){
		String path = ConfigHelper.getConfig().getTermsPath();
		File file = new File(path);
		this.termInfo = new HashMap<String, Integer>();
		try {
			List<String> content = FileUtil.readFileByLine(file);
			for(String line : content){
				String[] termInfo = line.split(" ");
				String name = termInfo[0];
				int index = Integer.valueOf(termInfo[1]);
				this.termInfo.put(name, index);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("初始化特征词信息失败，请检查result/terms.txt文件是否正确");
		}
	}
	
	public void eval(){
		init();
		while(this.validatorDataSource.haveNext()){
			Document document = this.validatorDataSource.getNextDocument();
			this.tokenizer.process(document);
			this.vectorCreator.process(document);
			double result = doEval(document);
			Integer temp = this.resultInfo.get(result);
			if(temp == null){
				this.resultInfo.put(result, 1);
			}else{
				temp++;
				this.resultInfo.put(result, temp);
			}
		}
		for(Map.Entry<Double, Integer> entry : this.resultInfo.entrySet()){
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}
	
	public abstract double doEval(Document document);
	
	public abstract void initModel();
}
