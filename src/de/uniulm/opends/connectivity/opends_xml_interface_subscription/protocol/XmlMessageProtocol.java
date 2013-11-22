package de.uniulm.opends.connectivity.opends_xml_interface_subscription.protocol;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Protocol for xml abbonements
 * @author Fahrsimulator (Phil)
 *
 */
public class XmlMessageProtocol {

	private final StringBuilder sb = new StringBuilder();
	private final String DELIMITER ="</Message>";
	private final XmlMessageProtocolCallback callback;
	
	
	private  final DocumentBuilderFactory factory;
	private  DocumentBuilder builder;
	
	
	/**
	 * 
	 * @param callback called when a message is received
	 */
	public XmlMessageProtocol(XmlMessageProtocolCallback callback) {
		factory = DocumentBuilderFactory.newInstance();
		try {
			builder  = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		this.callback = callback;
		
	}
	
	/**
	 * 
	 * @param s  Input String from remote server 
	 */
	public synchronized void pipeIn(String s){
		sb.append(s);
		if (sb.length()==0) return;
		
		int delimiterIndex = sb.toString().indexOf(DELIMITER);
		if (delimiterIndex==-1){
			//delimiter not found
		}else{
			//delimiter
			String message0=sb.substring(0, delimiterIndex+DELIMITER.length());
			String messageN=sb.substring(delimiterIndex+DELIMITER.length());
			sb.setLength(0);
			callback.onXmlMessage(toXml(message0));
			pipeIn(messageN);

		}
		
	}
	
	/**
	 * Callback interface
	 * @author Fahrsimulator (Phil)
	 *
	 */
	public interface XmlMessageProtocolCallback{
		public void onXmlMessage(Document document);
	}
	
	private Document toXml(String s){
		InputSource is = new InputSource(new StringReader(s));
	    try {
			return builder.parse(is);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
	    return null;
		
	}
}
