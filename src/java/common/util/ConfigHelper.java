package common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;


public class ConfigHelper {
	private static ConfigHelper config = null;
	
	static final Logger logger = Logger.getLogger(ConfigHelper.class);

	// 操作-训练
	private boolean isTrain = false;
	private String trainPath;
	
	// 操作-测试
	private boolean isEval = false;
	private String evalPath;
	
	private String modelPath;
	
	private String termsPath;
	
	private String categoryPath;
	
	private String stopwordPath;
	
	private String ignoreTypePath;
	
	public static ConfigHelper getConfig(){
		if(config == null){
			config = new ConfigHelper();
		}
		return config;
	}

	private ConfigHelper() {
		
		Properties prop = new Properties();
		try {
	        try {
	            InputStreamReader is = new InputStreamReader(new FileInputStream(new File("config.txt")),"utf-8");
	            prop.load(is);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		    
		    isTrain = Boolean.parseBoolean(prop.getProperty("train", "false"));
		    trainPath = prop.getProperty("train_path", "");
		    if(isTrain){
		        if(!new File(trainPath).exists()){
		            logger.error("训练目录不存在#" + trainPath);
		        }
		        System.out.println("trainPath#" + trainPath);
		    }
		    
		    isEval = Boolean.parseBoolean(prop.getProperty("eval", "false"));
		    evalPath = prop.getProperty("eval_path", "");
		    if(isEval){
                if(!new File(evalPath).exists()){
                    logger.error("测试目录不存在#" + evalPath);
                }
                System.out.println("evalPath#" + evalPath);
            }
		    
		    if(!isTrain && !isEval){
		        logger.error("训练、测试均未开启");
		    }
		    
		    this.modelPath = prop.getProperty("model_path", "result/model.txt");
		    this.categoryPath = prop.getProperty("category_path", "result/category.txt");
		    this.termsPath = prop.getProperty("terms_path", "result/terms.txt");
		    
		    this.ignoreTypePath = prop.getProperty("ignoretype_path", "/ignoretype.txt");
		    this.stopwordPath = prop.getProperty("stopword_path", "/stopword.txt");
		    
		} catch (Exception e) {
			logger.error("config.txt error#"+e.toString());
		} 
	}

	public boolean isTrain() {
		return isTrain;
	}

	public String getTrainPath() {
		return trainPath;
	}

	public boolean isEval() {
		return isEval;
	}

	public String getEvalPath() {
		return evalPath;
	}

	public String getModelPath() {
		return modelPath;
	}

	public String getTermsPath() {
		return termsPath;
	}

	public String getCategoryPath() {
		return categoryPath;
	}

	public String getStopwordPath() {
		return stopwordPath;
	}

	public String getIgnoreTypePath() {
		return ignoreTypePath;
	}

}
