package asis.eshub.agent.config;

import org.apache.camel.CamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: jeongtak
 * Date: 13. 6. 27
 * Time: 오후 5:01
 */
public class ContextInitializer {

    private static Logger logger = LoggerFactory.getLogger(ContextInitializer.class);

    private static AbstractApplicationContext springContext;
    private static CamelContext camelContext;

    public static void initializeContext() {

        logger.info("Spring Context Initialization...");
        springContext = new ClassPathXmlApplicationContext("classpath:spring-context.xml");

        logger.info("Camel Context Initialization...");
       camelContext = springContext.getBean("camel", CamelContext.class);

    }

    public static void closeContext() {

        if (springContext != null)
            springContext.stop();

        if (camelContext != null) {
            try {
                camelContext.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static AbstractApplicationContext getSpringContext() {
        return springContext;
    }

    public static CamelContext getCamelContext() {
        return camelContext;
    }
}
