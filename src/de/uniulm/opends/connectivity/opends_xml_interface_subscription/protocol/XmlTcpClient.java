package de.uniulm.opends.connectivity.opends_xml_interface_subscription.protocol;

import de.uniulm.opends.connectivity.opends_xml_interface_subscription.net.impl.Utf8StringTcpClient;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.protocol.XmlMessageProtocol.XmlMessageProtocolCallback;


public class XmlTcpClient extends Utf8StringTcpClient{

	private final XmlMessageProtocol xmlMsgProtocol;
	public XmlTcpClient(String adress, int port,XmlMessageProtocolCallback callback) {
		super(adress, port);
		xmlMsgProtocol = new XmlMessageProtocol(callback);
		startListening();
	}


	@Override
	protected void onConnectionClosed() {
	
		System.out.println("connection lost");
	}

	@Override
	protected void onError(Exception e) {
		e.printStackTrace();
		
	}

	@Override
	protected void recv(String s) {
		xmlMsgProtocol.pipeIn(s);
	}

	public void sendMessage(Abbonement xmlData){
		sendUTF8String(xmlData.getXML());
	}

	
	
	
	
}
