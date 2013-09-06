package asis.eshub.server;

import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	protected static Logger logger = LoggerFactory.getLogger(Main.class);

	public static File configHomeFile = null;
	public static List<SimpleEntry<String,Object>> SERVER_LIST = new ArrayList<SimpleEntry<String,Object>>();

	static {
		try {
			configHomeFile = new File(Main.class.getResource("/eshub.properties").toURI()).getParentFile();
		} catch (URISyntaxException e) {
			logger.info("에러! eshub 설정파일(eshub.properties)을 찾을 수 없습니다");
			System.exit(-1);
		}
	}

	public static String CONFIG_HOME = configHomeFile.getAbsolutePath() + File.separator;
	public static String ESHUB_HOME = configHomeFile.getParentFile() + File.separator;
	public static String APP_HOME = ESHUB_HOME + "webapp" + File.separator;
	
	private static EmbeddedTomcat tomcat;
	private static EmbeddedActiveMQ activeMq;
	private static EmbeddedFtpServer ftpServer;
	private static EmbeddedScheduler scheduler;

	public static void main(String[] args) throws Exception {

		String webPort = null;
		String mqPort = null;
		String ftpPort = null;
		String ftpSslKeystore = null;
		String ftpSslPassword = null;

		Properties props = new Properties();
		FileReader reader = null;

		try {

			reader = new FileReader(CONFIG_HOME + "eshub.properties");
			props.load(reader);
			webPort = props.getProperty("http.port");
			
			mqPort = props.getProperty("mq.port");
			
			ftpPort = props.getProperty("ftp.port");
			
			ftpSslKeystore = props.getProperty("ftp.ssl.keystore");
			ftpSslPassword = props.getProperty("ftp.ssl.password");

		} catch (Exception e) {
			if (reader != null)
				reader.close();
		}

		if (webPort == null || webPort.isEmpty()) {
			webPort = "8080";
			logger.info("주의! esHub Http Sever 포트를 default:8080로 설정합니다");
		}
		
		boolean isAutoDeploy = "true".equals(props.getProperty("http.autodeploy")) ? true : false;
		
		tomcat = new EmbeddedTomcat(Integer.valueOf(webPort));
		tomcat.setAutoDeploy(isAutoDeploy);
		SERVER_LIST.add(new SimpleEntry<String, Object>("tomcat", tomcat));

		if (mqPort == null || mqPort.isEmpty()) {
			mqPort = "8888";
			logger.info("주의! esHub MQ Sever 포트를 default:8888로 설정합니다");
		}

		activeMq = new EmbeddedActiveMQ(Integer.valueOf(mqPort));
		SERVER_LIST.add(new SimpleEntry<String, Object>("activeMQ", activeMq));

		if (ftpPort == null || ftpPort.isEmpty()) {
			ftpPort = "9999";
			logger.info("주의! esHub FTP Sever 포트를 default:9999로 설정합니다");
		}

		if (ftpSslKeystore != null && !ftpSslKeystore.isEmpty()) {
			ftpServer.setSSL(new File(ftpSslKeystore), ftpSslPassword);
			logger.info("FTP 서버에 SSL이 설정되었습니다");
		}

		ftpServer = new EmbeddedFtpServer(Integer.valueOf(ftpPort));
		SERVER_LIST.add(new SimpleEntry<String, Object>("ftpServer", ftpServer));

		scheduler = new EmbeddedScheduler();
		SERVER_LIST.add(new SimpleEntry<String, Object>("scheduler", scheduler));
		
		tomcat.start();
		activeMq.start();
		ftpServer.start();
		scheduler.start();
		
		tomcat.await();

	}



}
