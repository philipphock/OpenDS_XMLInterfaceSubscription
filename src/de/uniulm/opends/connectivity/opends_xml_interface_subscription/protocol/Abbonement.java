package de.uniulm.opends.connectivity.opends_xml_interface_subscription.protocol;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public enum Abbonement {
	
	//define enums and load data
	SPEED(Abbonement.loadXMLtoString("speed.xml"));
	
	
		
	private final String xml;
	
	private Abbonement(String xml) {
		this.xml=xml;
	}
	
	public String getXML() {
		return xml;

	}
	
	
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
