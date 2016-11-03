package test;

import jsmarty.core.common.json.JsonHandler;
import jsmarty.core.common.json.impl.JsonHandlerImpl;
import jsmarty.core.common.properties.template.XXX_Properties;
import jsmarty.core.common.queuer.QueuerPoolHandler;
import jsmarty.core.common.queuer.QueuerPoolHandlerImpl;
import jsmarty.core.sdo.SDO;

/**
 * Test class. Just for create and push job to template manager-adaptor
 * 
 * 
 * run this class - then run manager/adaptor initializers under standalone
 * 
 * @author vit
 *
 */

public class JobCreator {
	
	public static void main(String[] args) {
		new XXX_Properties();
		QueuerPoolHandler handler = new QueuerPoolHandlerImpl(XXX_Properties.getRedisHost(), XXX_Properties.getRedisPort());
		
		for(int i=0; i < 50; i++){
			SDO sdo = new SDO();
			sdo.setBody("Hello world!" + i + " ");
			
			JsonHandler jsonHandler = new JsonHandlerImpl();
			handler.lpush(XXX_Properties.getInputQueueName(), jsonHandler.serialize(sdo));
			
		}

	}

}