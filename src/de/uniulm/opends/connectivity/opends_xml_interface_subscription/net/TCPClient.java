package de.uniulm.opends.connectivity.opends_xml_interface_subscription.net;


import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;

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
	
	private final ArrayList<TCPClientListener> tcpClientListeners = new ArrayList<>();
	

	
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
	
	public void addTCPClientListener(TCPClientListener listener){
		this.tcpClientListeners.add(listener);
	}
	public void removeTCPClientListener(TCPClientListener listener){
		this.tcpClientListeners.remove(listener);
	}

	
	
	
	/**
	 * creates the socket and starts listening to the remote server (async) 
	 */
	public void startListening(){
		try {
			
			s = new Socket(ADRESS,PORT);
			
			if (s.isConnected()){
				onConnectionEstablished();
				for(TCPClientListener c:tcpClientListeners){
					c.onConnectionEstablished();
				}
				listent.start();
			}
			
		} catch (IOException e) {
			for(TCPClientListener c:tcpClientListeners){
				c.onError(new ConnectException("not connected"));
			}
			onError(e);
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
					for(TCPClientListener c:tcpClientListeners){
						c.onConnectionClosed();
					}
					break;
				}
				recv(buffer, read);
			} catch (IOException e) {
				onError(e);
				for(TCPClientListener c:tcpClientListeners){
					c.onError(e);
				}
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
			for(TCPClientListener c:tcpClientListeners){
				c.onError(e);
			}
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
	
	protected abstract void onConnectionEstablished();
	
	/**
	 * callback, called when the remote part closes the connection
	 */
	protected abstract void onConnectionClosed();
	
	/**
	 * callback, called when an error occurs
	 * @param e Exception
	 */
	protected abstract void onError(Exception e);
	
	public interface TCPClientListener{
		public void onConnectionEstablished();
		public void onConnectionClosed();
		public void onError(Exception e);
	}
}
