package common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;


public class ConfigHelper {
	static ConfigHelper config = null;
	
	static final Logger logger = Logger.getLogger(ConfigHelper.class);

	String driver = "";
	
	// 默认数据库的地址帐号、密码
	String url = "";
	String user = "";
	String password = "";
	
	// 分类器名称
	String category = "";
	
	// 单分类(binary)/多分类(multi)
	String type = "";
	
	String multiIds = "";
	
	// 操作-训练
	boolean isTrain = false;
	String trainPath = "";
	
	// 操作-测试
	boolean isEval = false;
	String evalPath = "";
	
	
	public static ConfigHelper getConfig(){
		if(config == null){
			config = new ConfigHelper();
		}
		return config;
	}

	ConfigHelper() {
		
		Properties prop = new Properties();
		try {
		    //String path = FileUtil.getClasspath() ;
	        try {
	        	System.out.println(System.getProperty("user.dir"));
	        	
	            InputStreamReader is = new InputStreamReader(new FileInputStream(new File("./bin/config.txt")),"utf-8");
	            prop.load(is);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			this.driver = prop.getProperty("driver", "");
			this.url = prop.getProperty("url", "");
			this.user = prop.getProperty("user", "");
			this.password = prop.getProperty("password", "");
			
			category = prop.getProperty("category", "");
			if(category.length() == 0 ){
                logger.error("分类器名称为空");
            }
			
		    type = prop.getProperty("type", "");
		    if(type.length() == 0 ){
		        logger.error("分类器类型为空");
		    }
		    if(!"binary".equals(type) && !"multi".equals(type)){
		        logger.error("分类器类型type 取值 为binary 或 multi");
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
		    
		    multiIds = prop.getProperty("multi_ids", "");
			
		} catch (Exception e) {
			logger.error("config.txt error#"+e.toString());
		} 
		
		

	}


	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
	
    public String getCategory() {
        return category;
    }

    
    public String getType() {
        return type;
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

    
    public String getMultiIds() {
        return multiIds;
    }


	
}
