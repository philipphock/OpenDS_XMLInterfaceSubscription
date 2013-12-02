package de.uniulm.opends.connectivity.opends_xml_interface_subscription;

import java.util.Scanner;

import de.uniulm.opends.connectivity.opends_xml_interface_subscription.net.TCPClient.TCPClientListener;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.protocol.AbbonementListener;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.protocol.OpenDSValue;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.protocol.Subscription;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.protocol.SubscriptionClient;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml.MessageBuilder;

public class Main implements TCPClientListener{

	private volatile boolean isConnected=false;
	private SubscriptionClient client; 
	public static void main(String[] args) {
		
		
		Main m = new Main();
		m.tryListen();	
		
		
	}
	
	
	public void tryListen(){
		client = new SubscriptionClient("127.0.0.1", 5578);
		client.addAbbonementListener(new AbbonementListener() {
			
			@Override
			public void eventReceived(OpenDSValue<?> value) {
				System.out.println(value);
				
			}
		});
		client.addTCPClientListener(this);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				@SuppressWarnings("resource")
				Scanner ss = new Scanner(System.in);
				ss.nextLine();
				client.send(MessageBuilder.abolish(Subscription.SPEED,Subscription.RPM));
				ss.nextLine();
				
			}
		}).start();
		
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
				}catch(Exception e){e.printStackTrace();}
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
		String es = MessageBuilder.establish(100,Subscription.SPEED,Subscription.RPM);
		client.send(es);
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


