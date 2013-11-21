package de.uniulm.opends.connectivity.opends_xml_interface_subscription.net;


import java.io.IOException;
import java.net.Socket;
import java.util.Observable;

public abstract class TCPClient extends Observable implements Runnable{

	private final int PORT;
	private final String ADRESS;
	
	private Socket s;
	private final Thread listent;
	
	private boolean isRunning = true;
	
	public TCPClient(String adress,int port) {
		this.PORT = port;
		this.ADRESS=adress;
		
		listent=new Thread(this);
		
		
	}
	
	public void startListening(){
		try {
			s = new Socket(ADRESS,PORT);
			listent.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		
		byte[] buffer = new byte[1024];
		while(isRunning){
			try {
				int read=s.getInputStream().read(buffer);
				if (read==-1){
					onConnectionClosed();
					break;
				}
				recv(buffer, read);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	protected abstract void recv(byte[] bytes,int read);
	
	protected void send(byte[] toSend){
		try {
			s.getOutputStream().write(toSend);
			s.getOutputStream().flush();
		} catch (IOException e) {
			onError(e);
		}
	}
	
	public void stop(){
		isRunning=false;
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected abstract void onConnectionClosed();
	protected abstract void onError(Exception e);
}
