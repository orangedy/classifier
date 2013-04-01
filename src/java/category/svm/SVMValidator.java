package category.svm;

import java.io.File;
import java.io.IOException;

import libsvm.svm;
import libsvm.svm_model;
import common.bean.Document;

import category.AbstractValidator;

public class SVMValidator extends AbstractValidator{
	
	private svm_model model;

	public void initModel(){
		String path = "result/model.txt";
		path = System.getProperty("user.dir") + File.separator + path;
		try {
			this.model = svm.svm_load_model(path);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("初始化model信息失败，请检查result/model.txt文件是否正确");
		}
	}
	
	public double doEval(Document document){
		return svm.svm_predict(this.model, document.getSvmNodeArray());
	}
}
