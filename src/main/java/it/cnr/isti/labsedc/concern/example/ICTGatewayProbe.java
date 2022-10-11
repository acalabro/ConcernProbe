package it.cnr.isti.labsedc.concern.example;

import java.net.UnknownHostException;
import java.util.Properties;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.naming.NamingException;
import it.cnr.isti.labsedc.concern.cep.CepType;
import it.cnr.isti.labsedc.concern.event.ConcernBaseEvent;
import it.cnr.isti.labsedc.concern.event.ConcernICTGatewayEvent;
import it.cnr.isti.labsedc.concern.probe.ConcernAbstractProbe;
import it.cnr.isti.labsedc.concern.utils.ConnectionManager;
import it.cnr.isti.labsedc.concern.utils.DebugMessages;

public class ICTGatewayProbe extends ConcernAbstractProbe {

	public ICTGatewayProbe(Properties settings) {
		super(settings);
	}

	public static void main(String[] args) throws UnknownHostException, InterruptedException {
		//creating a probe
		ICTGatewayProbe aGenericProbe = new ICTGatewayProbe(
				ConnectionManager.createProbeSettingsPropertiesObject(
						"org.apache.activemq.jndi.ActiveMQInitialContextFactory",
						"tcp://localhost:61616","system", "manager",
						"TopicCF","DROOLS-InstanceOne", false, "SUA_probe",	
						"it.cnr.isti.labsedc.concern,java.lang,javax.security,java.util",
						"vera", "griselda"));
		//sending events
		try {
			DebugMessages.line();
			DebugMessages.println(System.currentTimeMillis(), ICTGatewayProbe.class.getSimpleName(),"Sending ICTGateway messages");

						
			ICTGatewayProbe.sendICTMessage(aGenericProbe, new ConcernICTGatewayEvent<String>(
					System.currentTimeMillis(),
					"ICTGW_Probe",
					"Monitoring",
					"sessionID",
					"noChecksum",
					"#ANameForTheMessage",
					"ICTMessagePayload",
					CepType.DROOLS, 
					false, 
					"ICTMessageType", 
					"ICTMessageCategory")
			);
			
			Thread.sleep(1000);
			
			ICTGatewayProbe.sendICTMessage(aGenericProbe, new ConcernICTGatewayEvent<String>(
					System.currentTimeMillis(),
					"ICTGW_Probe",
					"Monitoring",
					"sessionID",
					"noChecksum",
					"#ANameForTheMessage1",
					"ICTMessagePayload1",
					CepType.DROOLS, 
					false, 
					"ICTMessageType1", 
					"ICTMessageCategory1")
			);
						
		} catch (IndexOutOfBoundsException | NamingException e) {} catch (JMSException e) {
			e.printStackTrace();
		} 
	}
	
	
	protected static void sendICTMessage(ICTGatewayProbe aGenericProbe, ConcernICTGatewayEvent<String> message) throws JMSException,NamingException {

		DebugMessages.print(
				System.currentTimeMillis(), 
				DTProbe.class.getSimpleName(),
				"Creating Message ");
		try
		{
			ObjectMessage messageToSend = publishSession.createObjectMessage();
			messageToSend.setJMSMessageID(String.valueOf(MESSAGEID++));
			messageToSend.setObject(message);
			DebugMessages.ok();
			DebugMessages.print(System.currentTimeMillis(), DTProbe.class.getSimpleName(),"Publishing message  ");
			mProducer.send(messageToSend);
			DebugMessages.ok();
			DebugMessages.line();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
//	protected static void sendRegistrationICTMessage();
//	protected static void sendDataICTMessage();
//	


	@Override
	public void sendMessage(ConcernBaseEvent<?> event, boolean debug) {
		// TODO Auto-generated method stub
		
	}
}