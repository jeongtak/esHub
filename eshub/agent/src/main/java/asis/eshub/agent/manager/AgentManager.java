/**
 * Copyright (c) 2013 KTNET, Inc.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of KTNET, Inc.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with KTNET.
 *
 */

package asis.eshub.agent.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import asis.eshub.agent.config.ContextInitializer;

/**
 * #name : AgentManager.java
 * #description : Client를 시작하는 Entrypoint 클래스
 * <p/>
 * #revision
 * #
 * #    Date                  Author                  Description
 * # --------------       ---------     -------------------------------
 * # 2013. 6. 28.       jeongtak     Initial Creation
 *
 * @author jeongtak
 * @version 1.0
 * @since 2013. 6. 28.
 */

public class AgentManager {

    private static Logger logger = LoggerFactory.getLogger(AgentManager.class);

    private static int CURRENT_STATUS = 0;
    private static int STATUS_RUN = 1;
    private static int STATUS_STOP = 2;

    public static void main(String... args) throws Exception {

        logger.info("####### ETS hub agent start #######");

        ContextInitializer.initializeContext();
        logger.info("> context initialized");

        CURRENT_STATUS = STATUS_RUN;

//        RemoteListener.bootstrap(8080);
//
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
