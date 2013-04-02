package category.processor;

import category.AbstractTrainer;
import common.bean.Document;

public abstract class Processor {
	
	public abstract void process(Document document);
	
	public abstract void init();
}
