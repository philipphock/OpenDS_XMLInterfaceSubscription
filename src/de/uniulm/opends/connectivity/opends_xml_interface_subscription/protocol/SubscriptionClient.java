package de.uniulm.opends.connectivity.opends_xml_interface_subscription.protocol;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml.XmlMessageProtocol.XmlMessageProtocolCallback;
import de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml.XmlTcpClient;

public class SubscriptionClient extends XmlTcpClient implements XmlMessageProtocolCallback{

	private ArrayList<AbbonementListener> listener;
	private XPathExpression rpmXpath;
	private XPathExpression speedXpath;

	public SubscriptionClient(String adress, int port){
		super(adress, port);
		setCallback(this);
		listener = new ArrayList<>();
		try {
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			rpmXpath = xpath.compile("/"+Subscription.RPM.getXPath());
			speedXpath = xpath.compile("/"+Subscription.SPEED.getXPath());
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
		
		List<OpenDSValue<?>> values = getSubscriptionsFromXML(document);

		if (values == null) return;
		for (OpenDSValue<?> v:values){
			for(AbbonementListener l:listener){
				l.eventReceived(v);
			}	
		}
			
		
		
	}
	
	private List<OpenDSValue<?>> getSubscriptionsFromXML(Document d){
		OpenDSValue<?> ret0 = null;
		ArrayList<OpenDSValue<?>> values = new ArrayList<>();
		
		ret0 = tryGetDataFromXML(rpmXpath,d,Subscription.RPM);
		if (ret0 != null ) values.add(ret0);
		
		ret0 = tryGetDataFromXML(speedXpath,d,Subscription.SPEED);
		if (ret0 != null ) values.add(ret0);
		
		//XXX add more types here
		return values;
	}
	
	
	private OpenDSValue<?> tryGetDataFromXML(XPathExpression exp,Document d,Subscription type){

		try {
			String val = (String) exp.evaluate(d, XPathConstants.STRING);
			
			if (val == null || val.equals("")){
				return null;
			}
			
			switch (type) {
			case RPM:
				return new OpenDSValue<Integer>(Subscription.RPM, Integer.parseInt(val));

				
			case SPEED:
				return new OpenDSValue<Double>(Subscription.SPEED, Double.parseDouble(val));
				
			
			default:
				return null;
			}
			
			//XXX add more types here
			
			
		} catch (XPathExpressionException e) {
			//nop
		}
		return null;
	}



	public void send(String message){
		sendUTF8String(message);
	}
	
}
