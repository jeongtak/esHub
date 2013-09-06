package asis.eshub.server;

import java.net.InetAddress;
import java.net.URI;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http://activemq.apache.org/how-do-i-embed-a-broker-inside-a-connection.html
 * 
 * @author jeongtak
 * 
 */
public class EmbeddedActiveMQ {

	protected static Logger logger = LoggerFactory.getLogger(EmbeddedActiveMQ.class);
	private int port;
	
	private BrokerService broker = new BrokerService();
	
	public EmbeddedActiveMQ(int port) {
		this.port = port;
	}

	public void start() throws Exception {

		
		broker.setBrokerName("eshub-broker");
		broker.setUseShutdownHook(false);

//		TransportConnector connector = new TransportConnector();
//		connector.setUri(mqUri);

		String localhost = InetAddress.getLocalHost().getHostAddress();
		String mqUri = "tcp://" + localhost + ":" + port;

		logger.info("MQ Start with "+mqUri.toString());
		broker.addConnector(mqUri);
		broker.start();
	}
	
	public BrokerService getInstance(){
		return broker;
	}
}
