package category;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import common.util.ConfigHelper;


public class ClassifierMain {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("ApplicationContent.xml");
		if(ConfigHelper.getConfig().isTrain()){
			AbstractTrainer train = (AbstractTrainer)ctx.getBean("trainer");
			train.train();
		}
		if(ConfigHelper.getConfig().isEval()){
			AbstractValidator validator = (AbstractValidator) ctx.getBean("validator");
			validator.eval();
		}
	}
}
