package de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml;

import de.uniulm.opends.connectivity.opends_xml_interface_subscription.net.impl.Utf8StringTcpClient;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.protocol.Abbonement;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml.XmlMessageProtocol.XmlMessageProtocolCallback;

/**
 * XML Client receives XML Data and sends XML Messages
 * @author Fahrsimulator (Phil)
 *
 */
public class XmlTcpClient extends Utf8StringTcpClient{

	private final XmlMessageProtocol xmlMsgProtocol;


	public XmlTcpClient(String adress, int port,
			XmlMessageProtocolCallback callback) {
		super(adress, port);
		xmlMsgProtocol = new XmlMessageProtocol(callback);
	}


	@Override
	protected void onConnectionClosed() {
	
		System.out.println("connection lost");
	}

	

	@Override
	protected void recv(String s) {
		xmlMsgProtocol.pipeIn(s);
	}

	public void sendMessage(Abbonement xmlData){
		sendUTF8String(xmlData.getXML());
	}


	@Override
	protected void onConnectionEstablished() {
		
	}


	@Override
	protected void onError(Exception e) {
		
	}

	
	
	
	
}
