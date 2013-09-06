package asis.eshub.server;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http://quartz-scheduler.org/overview/quick-start
 * @author jeongtak
 *
 */
public class EmbeddedScheduler {

	protected static Logger logger = LoggerFactory.getLogger(EmbeddedFtpServer.class);
	
	private Scheduler scheduler;
	
	public void start(){
		
		try {
            // Grab the Scheduler instance from the Factory
            
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			
            // and start it off
            scheduler.start();

            //scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
	}
	
	public Scheduler getInstance(){
		return scheduler;
	}
	
}
