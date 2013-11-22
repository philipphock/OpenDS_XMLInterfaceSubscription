package de.uniulm.opends.connectivity.opends_xml_interface_subscription.protocol;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * XML subscription Enum.
 * Intended to be used wit XmlTcpClient @see {@link XmlTcpClient}
 * @author Fahrsimulator (Phil)
 *
 */
public enum Abbonement {
	
	//define enums and load data
	SPEED(Abbonement.loadXMLtoString("speed.xml"));
	
	
		
	private final String xml;
	
	private Abbonement(String xml) {
		this.xml=xml;
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
