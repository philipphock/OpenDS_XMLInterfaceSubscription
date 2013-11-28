package de.uniulm.opends.connectivity.opends_xml_interface_subscription;

import de.uniulm.opends.connectivity.opends_xml_interface_subscription.net.TCPClient;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.protocol.Abbonement;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml.XmlMessageProtocolDebugCallback;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml.XmlTcpClient;

public class Main {

	public static void main(String[] args) {
		final XmlTcpClient client = new XmlTcpClient("127.0.0.1", 8080, new XmlMessageProtocolDebugCallback());
		client.addTCPClientListener(new TCPClient.TCPClientListener() {
			
			@Override
			public void onConnectionEstablished() {
				client.sendMessage(Abbonement.SPEED);
				
			}
			
			@Override
			public void onConnectionClosed() {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

}
