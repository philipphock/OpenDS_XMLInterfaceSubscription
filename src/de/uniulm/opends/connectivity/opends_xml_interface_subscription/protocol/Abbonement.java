package de.uniulm.opends.connectivity.opends_xml_interface_subscription.protocol;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml.XmlTcpClient;

/**
 * XML subscription Enum.
 * Intended to be used wit XmlTcpClient @see {@link XmlTcpClient}
 * @author Fahrsimulator (Phil)
 *
 */
public enum Abbonement {
	
	//define enums and load data
	SPEED(Abbonement.loadXMLtoString("speed.xml"),"//root/thisVehicle/physicalAttributes/Properties/speed"),
	RPM(Abbonement.loadXMLtoString("rpm.xml"),"//root/thisVehicle/exterior/engineCompartment/engine/Properties/actualRpm"),
	ESTABLISH_CONNECTION(Abbonement.loadXMLtoString("establishConnection.xml"),null),
	ABOLISH_CONNECTION(Abbonement.loadXMLtoString("abolishConnection.xml"),null);
	
		
	private final String xml;
	private final String xpath;
	private Abbonement(String xml,String xPath) {
		this.xml=xml;
		this.xpath=xPath;
	}
	
	/**
	 * 
	 * @return xml as UTF-8 String
	 */
	public String getXML() {
		return xml;

	}
	
	/**
	 * 
	 * @return xpath to the value, delivered by remote endpoint
	 */
	public String getXPath(){
		return xpath;
	}
	
	/**
	 * 
	 * @param filename
	 * @return Xml File as String
	 */
	public static String loadXMLtoString(String filename){
		String fileDir = System.getProperty("user.dir") + File.separator+"xml"+File.separator;
		 try {
			String text = new String(Files.readAllBytes(Paths.get(fileDir+filename)), StandardCharsets.UTF_8);
			return text;
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		
		return "";
	}
}
