package category;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ClassifierMain {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("ApplicationContent.xml");
		AbstractTrainer train = (AbstractTrainer)ctx.getBean("trainer");
		train.train();
	}
}
