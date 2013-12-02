package de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml;

import de.uniulm.opends.connectivity.opends_xml_interface_subscription.protocol.Subscription;

public class MessageBuilder {

	//MessageBuilder.establish(Subscription.SPEED,200)
	

	/**
	 * 
	 * @param subscription
	 * @param interval
	 * @return
	 */
	public static String establish(int interval,Subscription... subscription){
		String ret = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
						"<Message>\n"+
							"<Event Name=\"EstablishConnection\">"+interval+"</Event>\n";
		
							for (Subscription s:subscription){
								ret += ("<Event Name=\"Subscribe\">\n"+
										s.getXPath()+"\n"+
								"</Event>\n");	
							}
							
						ret +="</Message>\n";
		return ret;
	}
	
	/**
	 * TODO not yet tested, unsure if this corresponds to the protocol
	 * @param subscription
	 * @return the string to send
	 */
	public static String abolish(Subscription... subscription){
		String ret = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
				"<Message>\n"+
					"<Event Name=\"AbolishConnection\"></Event>\n";

					for (Subscription s:subscription){
						ret += ("<Event Name=\"Unsubscribe\">\n"+
								s.getXPath()+"\n"+
						"</Event>\n");	
					}
					
				ret +="</Message>\n";
		return ret;
	}
	
	
	
}
