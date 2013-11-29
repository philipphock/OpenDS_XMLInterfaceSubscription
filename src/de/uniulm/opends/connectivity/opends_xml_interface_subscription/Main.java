package de.uniulm.opends.connectivity.opends_xml_interface_subscription;

import de.uniulm.opends.connectivity.opends_xml_interface_subscription.net.TCPClient.TCPClientListener;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.protocol.Abbonement;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.protocol.AbbonementClient;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml.XmlMessageProtocolDebugCallback;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml.XmlTcpClient;

public class Main implements TCPClientListener{

	private volatile boolean isConnected=false;
	private AbbonementClient client; 
	public static void main(String[] args) {
		Main m = new Main();
		m.tryListen();	
		
		
	}
	
	
	public void tryListen(){
		client = new AbbonementClient("127.0.0.1", 5578);
		client.addTCPClientListener(this);
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				client.sendMessage(Abbonement.ABOLISH_CONNECTION);
			}
		}));
		
		while (!isConnected){
			client.startListening();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}


	@Override
	public void onConnectionEstablished() {
		isConnected=true;
		client.sendMessage(Abbonement.ESTABLISH_CONNECTION);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		client.sendMessage(Abbonement.SPEED);
	}


	@Override
	public void onConnectionClosed() {
		isConnected=false;
	}


	@Override
	public void onError(Exception e) {
		if (e.getMessage().equals("not connected")){
			System.out.println("tried to connect.. try again in 1 second");
		}
	}



}


