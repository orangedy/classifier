package category;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ClassifierMain {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
		AbstractClassifier classifier = (AbstractClassifier)ctx.getBean("svmClassifier");
		if(classifier.isNeedTrain()){
			classifier.excuteTrain();
		}
		if(classifier.isNeedEval()){
			classifier.excuteEval();
		}
	}
}
