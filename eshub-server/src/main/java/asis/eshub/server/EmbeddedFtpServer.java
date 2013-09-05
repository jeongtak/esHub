package asis.eshub.server;

import java.io.File;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfigurationFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.ClearTextPasswordEncryptor;

/**
 * http://mina.apache.org/ftpserver-project/embedding_ftpserver.html
 * 
 * @author jeongtak
 * 
 */
public class EmbeddedFtpServer {

	private FtpServerFactory serverFactory = new FtpServerFactory();
	ListenerFactory factory = new ListenerFactory();

	private int port;
	private File userProperties;

	public EmbeddedFtpServer(int port) {
		this.port = port;
		factory.setPort(Integer.valueOf(port));
	}

	public void start() throws Exception {

		serverFactory.addListener("default", factory.createListener());

		FtpServer server = serverFactory.createServer();

		PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
		userManagerFactory.setFile(new File(Main.CONFIG_HOME+"ftpuser.properties"));
		userManagerFactory.setPasswordEncryptor(new ClearTextPasswordEncryptor());
		serverFactory.setUserManager(userManagerFactory.createUserManager());

		server.start();
	}

	public void setUserProperties(File userProperties){
		this.userProperties = userProperties;
	}
	public void setSSL(File keystore, String password) {

		SslConfigurationFactory ssl = new SslConfigurationFactory();
		ssl.setKeystoreFile(keystore);
		ssl.setKeystorePassword(password);

		factory.setSslConfiguration(ssl.createSslConfiguration());
		factory.setImplicitSsl(true);
		
	}

}
