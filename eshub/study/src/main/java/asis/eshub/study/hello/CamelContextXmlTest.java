package asis.eshub.study.hello;


import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CamelContextXmlTest extends CamelSpringTestSupport {

	// TODO Create test message bodies that work for the route(s) being tested
	// Expected message bodies
	protected Object[] expectedBodies = {
			"<something id='1'>expectedBody1</something>",
			"<something id='2'>expectedBody2</something>" };
	// Templates to send to input endpoints
	@Produce(uri = "direct:start")
	protected ProducerTemplate inputEndpoint;
	// Mock endpoints used to consume messages from the output endpoints and then perform assertions
	@EndpointInject(uri = "mock:end")
	protected MockEndpoint outputEndpoint;

	@Test
	public void testCamelRoute() throws Exception {

		// Define some expectations

		// For now, let's just wait for some messages
		outputEndpoint.expectedMessageCount(2);
		// TODO Add some expectations here
		// Send some messages to input endpoints
		for (Object expectedBody : expectedBodies) {
			inputEndpoint.sendBody(expectedBody);
		}

		// Validate our expectations
		assertMockEndpointsSatisfied();
	}

	@Override
	protected ClassPathXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext(
				"asis/eshub/study/hello/camelContext.xml");
	}

}
