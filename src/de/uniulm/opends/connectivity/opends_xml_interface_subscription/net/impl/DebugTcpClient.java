package de.uniulm.opends.connectivity.opends_xml_interface_subscription.net.impl;

import java.nio.charset.Charset;

import de.uniulm.opends.connectivity.opends_xml_interface_subscription.net.TCPClient;
/**
 * Debug TCP Client that prints out inputs and status
 * @author Fahrsimulator (Phil)
 *
 */
public class DebugTcpClient extends TCPClient{

	public DebugTcpClient(String adress, int port) {
		super(adress, port);
	}

	@Override
	protected void recv(byte[] bytes, int read) {
		System.out.println(new String(bytes,0,read,Charset.forName("UTF-8")));
	}

	@Override
	protected void onError(Exception e) {
		e.printStackTrace();
	}

	@Override
	protected void onConnectionClosed() {
		System.out.println("Connection closed by foreign host");
	}

}
