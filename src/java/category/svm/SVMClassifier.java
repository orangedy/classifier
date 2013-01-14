package category.svm;

import org.apache.log4j.Logger;

import category.AbstractClassifier;

import common.util.ConfigHelper;

public class SVMClassifier extends AbstractClassifier {

	private static final Logger log = Logger.getLogger("SVMClassifierMain.class");
	
	public static void doBinary(){
        ConfigHelper config = ConfigHelper.getConfig();
        
        String category = config.getCategory();
        
        boolean isTrain = config.isTrain();
        if(isTrain){
            doBinaryTrain(category, config.getTrainPath());
        }
        boolean isEval = config.isEval();
        if(isEval){
            doBinaryEval(category, config.getEvalPath());
        }
    }

	private static void doBinaryEval(String category, String evalPath) {
		// TODO Auto-generated method stub
		
	}

	private static void doBinaryTrain(String category, String trainPath) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		ConfigHelper config = ConfigHelper.getConfig();
        String type = config.getType();
        
        if("binary".equals(type)){
            doBinary();
        } else if("multi".equals(type)){
            //doMulti();
        }
	}
}
