package de.uniulm.opends.connectivity.opends_xml_interface_subscription.net;


import java.io.IOException;
import java.net.Socket;

/**
 * TCP client with own receiver thread
 * 
 * @author Fahrsimulator (Phil)
 *
 */
public abstract class TCPClient implements Runnable{

	private final int PORT;
	private final String ADRESS;
	
	private Socket s;
	private final Thread listent;
	
	private boolean isRunning = true;
	
	/**
	 * inits the client and creates a thread
	 * @param adress
	 * @param port
	 */
	public TCPClient(String adress,int port) {
		this.PORT = port;
		this.ADRESS=adress;
		
		listent=new Thread(this);
		
		
	}
	
	/**
	 * creates the socket and starts listening to the remote server (async) 
	 */
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
	
	/**
	 * 
	 * @param bytes buffer
	 * @param read count of input bytes read
	 */
	protected abstract void recv(byte[] bytes,int read);
	
	/**
	 * send bytes to remote, on error 
	 * @param toSend
	 */
	protected void send(byte[] toSend){
		try {
			s.getOutputStream().write(toSend);
			s.getOutputStream().flush();
		} catch (IOException e) {
			onError(e);
		}
	}
	
	/**
	 * stops the client and closes the connection
	 */
	public void stop(){
		isRunning=false;
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * callback, called when the remote part closes the connection
	 */
	protected abstract void onConnectionClosed();
	
	/**
	 * callback, called when an error occurs
	 * @param e Exception
	 */
	protected abstract void onError(Exception e);
}
