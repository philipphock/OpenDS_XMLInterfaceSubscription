package de.uniulm.opends.connectivity.opends_xml_interface_subscription.net.impl;

import java.nio.charset.Charset;

import de.uniulm.opends.connectivity.opends_xml_interface_subscription.net.TCPClient;

public abstract class Utf8StringTcpClient extends TCPClient{

	public Utf8StringTcpClient(String adress, int port) {
		super(adress, port);
	}

	@Override
	protected void recv(byte[] bytes, int read) {
		recv(new String(bytes,0,read,Charset.forName("UTF-8")));
	}
	
	protected abstract void recv(String s);
	
	protected void sendUTF8String(String s){
		send(s.getBytes(Charset.forName("UTF-8")));
	}

	
}
