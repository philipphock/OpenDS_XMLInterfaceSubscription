package de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml;

import de.uniulm.opends.connectivity.opends_xml_interface_subscription.net.impl.Utf8StringTcpClient;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.protocol.Subscription;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml.XmlMessageProtocol.XmlMessageProtocolCallback;

/**
 * XML Client receives XML Data and sends XML Messages
 * 
 * @author Fahrsimulator (Phil)
 * 
 */
public class XmlTcpClient extends Utf8StringTcpClient {

	private  XmlMessageProtocol xmlMsgProtocol;

	public XmlTcpClient(String adress, int port) {
		super(adress, port);
		
	}

	@Override
	protected void recv(String s) {
		xmlMsgProtocol.pipeIn(s);
	}

	public void sendMessage(Subscription xmlData) {
		sendUTF8String(xmlData.getXML());
	}

	@Override
	protected void onConnectionEstablished() {
		// implemented in callback
	}

	@Override
	protected void onError(Exception e) {
		// implemented in callback
	}

	@Override
	protected void onConnectionClosed() {
		// implemented in callback
	}
	
	public void setCallback(XmlMessageProtocolCallback callback){
		xmlMsgProtocol = new XmlMessageProtocol(callback);
	}

}
