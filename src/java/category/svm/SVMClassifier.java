package category.svm;

import java.io.File;
import java.io.IOException;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

import org.apache.log4j.Logger;

import category.AbstractTrainer;

public class SVMClassifier extends AbstractTrainer {
	private static Logger log = Logger.getLogger(SVMClassifier.class);
	
	private svm_model model;
	
	private svm_problem problem;
	
	private svm_parameter param;
	
	/**
	 * 输出路径
	 */
	private String outputPath;

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public void doTrain(){
		svm_parameter param = getParam();
		svm_problem problem = getSvmProblem();
		this.outputPath = System.getProperty("user.dir") + File.separator + this.outputPath;
		training(problem, param, this.outputPath, true);
		
	}
	
	protected svm_parameter getParam() {
		svm_parameter param = new svm_parameter();
		param.svm_type = svm_parameter.C_SVC;
		param.kernel_type = svm_parameter.RBF;
		param.degree = 3;
		param.gamma = 0.0001;	// 1/num_features
		param.coef0 = 0;
		param.nu = 0.5;
		param.cache_size = 100;
		param.C = 1;
		param.eps = 1e-3;
		param.p = 0.1;
		param.shrinking = 0;
		param.probability = 0;
		param.nr_weight = 0;
		param.weight_label = new int[0];
		param.weight = new double[0];
		return param;
	}
	
	protected svm_problem getSvmProblem() {
		int l = this.getDocuments().size();
		double[] y = new double[l];

		svm_node[][] x = new svm_node[l][];
		for (int i = 0; i < l; i++) {
			y[i] = this.getDocuments().get(i).getCategory().getCategoryId();
			x[i] = this.getDocuments().get(i).getSvmNodeArray();
		}
		svm_problem problem = new svm_problem();
		problem.l = l;
		problem.y = y;
		problem.x = x;
		return problem;
	}
	
	protected void training(svm_problem problem, svm_parameter param,String modelFilePath, boolean one) {

		log.debug("start check parameter");
		String result = svm.svm_check_parameter(problem, param);

		if (result == null) {
			log.debug("start param Optimization");
//			paramOptimization(problem, param, one);
			log.debug("start training and gain model");
			svm_model model = svm.svm_train(problem, param);
			try {
				log.info("start save model#" + modelFilePath);
				svm.svm_save_model(modelFilePath, model);
				log.debug("finish");
			} catch (IOException e) {
				throw new RuntimeException("SVM存储训练结果错误", e);
			}
		} else {
			log.debug("check parameter error : " + result);
		}
	}
}
