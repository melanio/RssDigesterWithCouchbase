package reyes.melanio.testrunner;


import reyes.melanio.portfolio.rssdigester.RssDigester;
import org.apache.log4j.Logger;


import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class TestRunner {

	static Logger logger = Logger.getLogger(TestRunner.class);
	
	public static void main(String[] args) {
		
		XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource(
				"spring.cfg.xml"));
		
		RssDigester rssDigester = (RssDigester) beanFactory.getBean("RssDigesterBean");
		rssDigester.digestFeeds();	
		rssDigester.shutdownClient();
	}

}

