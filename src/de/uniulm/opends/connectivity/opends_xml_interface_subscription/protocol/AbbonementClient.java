package de.uniulm.opends.connectivity.opends_xml_interface_subscription.protocol;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml.XmlMessageProtocol.XmlMessageProtocolCallback;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml.XmlMessageProtocolDebugCallback;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml.XmlTcpClient;

public class AbbonementClient extends XmlTcpClient implements XmlMessageProtocolCallback{

	private ArrayList<AbbonementListener> listener;
	private XPathExpression rpmXpath;
	private XPathExpression speedXpath;

	public AbbonementClient(String adress, int port){
		super(adress, port);
		setCallback(this);
		listener = new ArrayList<>();
		try {
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			rpmXpath = xpath.compile(Abbonement.RPM.getXPath());
			speedXpath = xpath.compile(Abbonement.SPEED.getXPath());
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	
	public void addAbbonementListener(AbbonementListener l){
		if (!listener.contains(l)){
			listener.add(l);
		}
	}
	
	public void removeAbbonementListener(AbbonementListener l){
		listener.remove(l);
	}

	@Override
	public void onXmlMessage(Document document) {
		
		ArrayList<Abbonement> as = getAbbonnementsFromXML(document);
		//TODO this is currently null
//		for(Abbonement a:as){
//			for(AbbonementListener l:listener){
//				l.eventReceived(a);
//			}	
//		}
		
	}
	
	private ArrayList<Abbonement> getAbbonnementsFromXML(Document d){
		
		try {
			String rpmVal = (String) rpmXpath.evaluate(d, XPathConstants.STRING);
			
			
			System.out.println("RPM: "+rpmVal);
			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		try {
			String speedVal = (String) speedXpath.evaluate(d, XPathConstants.STRING);
			System.out.println("SPEED "+speedVal);

		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
