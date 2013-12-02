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
public enum Subscription {
	
	//define enums and load data
	SPEED("/root/thisVehicle/physicalAttributes/Properties/speed"),
	RPM("/root/thisVehicle/exterior/engineCompartment/engine/Properties/actualRpm");
	//XXX add more types here
	
		
	
	private final String xpath;
	private Subscription(String xPath) {
		this.xpath=xPath;
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
	
	@Override
	public String toString() {

		return name();
	}
}
