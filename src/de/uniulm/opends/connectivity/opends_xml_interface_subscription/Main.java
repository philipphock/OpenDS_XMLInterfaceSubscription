package de.uniulm.opends.connectivity.opends_xml_interface_subscription;

import de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml.XmlMessageProtocolDebugCallback;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml.XmlTcpClient;

public class Main {

	public static void main(String[] args) {
		new XmlTcpClient("127.0.0.1", 8080, new XmlMessageProtocolDebugCallback());		
	}

}
