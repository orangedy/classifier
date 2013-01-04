package common.bean;

import org.apache.log4j.Logger;

public class Term {

	private Logger log = Logger.getLogger(Term.class);
	
	/**
	 * 词包括词本身和词性组合而成，这样相同的词但是词性不同可以当做两个不同的Term
	 */
	private String term;
	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	/**
	 * 词频，这个词在Document中出现的次数
	 */
	private int frequency;
	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	/**
	 * 词性，可在词性过滤等地方用到
	 */
	private String termType;
	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public Term(String sterm) {
		this.term = sterm;
		this.frequency = 1;
		init();
	}

	public void init() {
		if(this.term.length() <= 2){
			log.error("can't init the Term, because its length less than 2");
		}else{
			this.termType = this.term.substring(this.term.length() - 2);
		}
	}
	
	/**
	 * 将词频加1，可能需要考虑线程同步的问题，暂时未加
	 */
	public void addFrequency(){
		this.frequency++;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(obj != null && obj instanceof Term){
			Term objTemp = (Term) obj;
			if(objTemp.getTerm().equals(this.getTerm())){
				return true;
			}
		}
		return false;
	}
}
