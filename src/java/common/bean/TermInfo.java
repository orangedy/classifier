package common.bean;

public class TermInfo {

	private int termID;
	
	private int[] documentFrequency;
	
	private double weight;

	public int getTermID() {
		return termID;
	}

	public void setTermID(int termID) {
		this.termID = termID;
	}

	public int[] getDocumentFrequency() {
		return documentFrequency;
	}

	public void setDocumentFrequency(int[] documentFrequency) {
		this.documentFrequency = documentFrequency;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public TermInfo(){
		
	}
}
