package common.bean;

import java.util.List;

import libsvm.svm_node;

public class Document {

	private String category;
	private List<Term> terms;
	public boolean isPositive() {
		// TODO Auto-generated method stub
		return false;
	}
	public svm_node[] getSvmNodeArray() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
