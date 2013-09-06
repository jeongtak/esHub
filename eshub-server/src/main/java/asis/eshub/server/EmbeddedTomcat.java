package asis.eshub.server;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbeddedTomcat {

	protected static Logger logger = LoggerFactory.getLogger(EmbeddedTomcat.class);
	private int port;
	private boolean isAutoDeploy;

	private Tomcat tomcat;

	public EmbeddedTomcat(int port) {
		tomcat = new Tomcat();
		tomcat.setPort(port);
		tomcat.getHost().setDeployOnStartup(true);
	}

	public void start() {

		try {
			File webAppRoot = new File(Main.APP_HOME);

			if (webAppRoot.exists()) {
				File[] files = webAppRoot.listFiles();
				for (File webapp : files) {
					tomcat.addWebapp("/" + webapp.getName(), webapp.getAbsolutePath());
					logger.info(">> 어플리케이션 로딩:" + webapp.getName());
				}
			} else {
				logger.info("주의! WebApp가 하나도 없습니다");
			}

			tomcat.start();

		} catch (LifecycleException e) {
			e.printStackTrace();
		} catch (ServletException se) {
			se.printStackTrace();
		}
	}
	
	public int getPort() {
		return port;
	}

	public boolean isAutoDeploy() {
		return isAutoDeploy;
	}

	public void setAutoDeploy(boolean isAutoDeploy) {
		tomcat.getHost().setAutoDeploy(isAutoDeploy);
		this.isAutoDeploy = isAutoDeploy;
	}

	public void await() {
		tomcat.getServer().await();
	}

	public void stop(String port) throws LifecycleException, IOException {
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
	
	public Tomcat getInstance(){
		return tomcat;
	}
}
