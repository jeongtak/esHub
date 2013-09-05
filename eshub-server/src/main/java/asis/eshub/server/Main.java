package asis.eshub.server;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	protected static Logger logger = LoggerFactory.getLogger(Main.class);

	public static File configHomeFile = null;

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

	private static Tomcat tomcat = new Tomcat();
	private static EmbeddedActiveMQ activeMq;
	private static EmbeddedFtpServer ftpServer;

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

		if (mqPort == null || mqPort.isEmpty()) {
			mqPort = "8888";
			logger.info("주의! esHub MQ Sever 포트를 default:8888로 설정합니다");
		}

		activeMq = new EmbeddedActiveMQ(Integer.valueOf(mqPort));

		if (ftpPort == null || ftpPort.isEmpty()) {
			ftpPort = "9999";
			logger.info("주의! esHub FTP Sever 포트를 default:9999로 설정합니다");
		}

		if (ftpSslKeystore != null && !ftpSslKeystore.isEmpty()) {
			ftpServer.setSSL(new File(ftpSslKeystore), ftpSslPassword);
			logger.info("FTP 서버에 SSL이 설정되었습니다");
		}

		ftpServer = new EmbeddedFtpServer(Integer.valueOf(ftpPort));

		boolean isAutoDeploy = "true".equals(props.getProperty("http.autodeploy")) ? true : false;

		tomcat.setPort(Integer.valueOf(webPort));
		tomcat.getHost().setAutoDeploy(isAutoDeploy);
		tomcat.getHost().setDeployOnStartup(true);

		File webAppRoot = new File(APP_HOME);

		if (webAppRoot.exists()) {
			File[] files = webAppRoot.listFiles();
			for (File webapp : files) {
				tomcat.addWebapp("/" + webapp.getName(), webapp.getAbsolutePath());
				logger.info(">> 어플리케이션 로딩:" + webapp.getName());
			}
		} else {
			logger.info("주의! WebApp가 하나도 없습니다");
		}

		// tomcat.setSilent(true);
		tomcat.start();
		activeMq.start();
		ftpServer.start();

		tomcat.getServer().await();

	}

	public static void stop(String port) throws LifecycleException, IOException {
		tomcat.stop();
		tomcat.destroy();
		// Tomcat creates a work folder where the temporary files are stored
		FileUtils.deleteDirectory(new File("work"));
		FileUtils.deleteDirectory(new File("tomcat." + port));
	}

	// public void deploy(String appName) throws MalformedURLException,
	// ServletException, LifecycleException {
	// // You _must_ get Catalina context file and call setConfigFile with the
	// // URI
	// // identifying your context.xml file
	// Context ctx = tomcat.addWebapp("/GlossaryService", "build/web");
	// File configFile = new File("build/web/META-INF/context.xml");
	// ctx.setConfigFile(configFile.toURI().toURL());
	// // Tomcat can only be started _after_ the setConfigFile is called
	// tomcat.start();
	// }

	public String getApplicationUrl(String appName) {
		return String.format("http://%s:%d/%s", tomcat.getHost().getName(), tomcat.getConnector().getLocalPort(), appName);
	}

	public boolean isRunning() {
		return tomcat != null;
	}
}
