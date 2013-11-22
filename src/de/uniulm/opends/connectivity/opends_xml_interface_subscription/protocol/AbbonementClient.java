package de.uniulm.opends.connectivity.opends_xml_interface_subscription.protocol;

import java.util.ArrayList;

import org.w3c.dom.Document;

import de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml.XmlMessageProtocol.XmlMessageProtocolCallback;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml.XmlTcpClient;

public class AbbonementClient extends XmlTcpClient implements XmlMessageProtocolCallback{

	private ArrayList<AbbonementListener> listener;
	public AbbonementClient(String adress, int port,
			XmlMessageProtocolCallback callback) {
		super(adress, port, callback);
		listener = new ArrayList<>();
	}
	
	public void addAbbonementListener(AbbonementListener l){
		if (!listener.contains(l)){
			listener.add(l);
		}
	}
	
	public void removeAbbonementListener(AbbonementListener l){
		listener.remove(l);
	}

	@Override
	public void onXmlMessage(Document document) {
		
		ArrayList<Abbonement> as = getAbbonnementsFromXML(document);
		for(Abbonement a:as){
			for(AbbonementListener l:listener){
				l.eventReceived(a);
			}	
		}
		
	}
	
	private ArrayList<Abbonement> getAbbonnementsFromXML(Document d){
		//TODO implement
		return null;
	}

}
