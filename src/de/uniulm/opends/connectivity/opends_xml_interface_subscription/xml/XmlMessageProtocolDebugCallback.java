package de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import de.uniulm.opends.connectivity.opends_xml_interface_subscription.xml.XmlMessageProtocol.XmlMessageProtocolCallback;

/**
 * Debug class that prints out recv xml files
 * @author Fahrsimulator (Phil)
 *
 */
public class XmlMessageProtocolDebugCallback implements XmlMessageProtocolCallback{

	@Override
	public void onXmlMessage(Document document) {
		System.out.println("---------------------------");
		try {
			printDocument(document,System.out);
		} catch (IOException | TransformerException e) {
			e.printStackTrace();
		}
		System.out.println("---------------------------");
	}

	//from stackoverflow: http://stackoverflow.com/questions/2325388/java-shortest-way-to-pretty-print-to-stdout-a-org-w3c-dom-document
	/**pretty print an XML Document
	 * 
	 * @param doc
	 * @param out
	 * @throws IOException
	 * @throws TransformerException
	 */
	public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	    transformer.transform(new DOMSource(doc), 
	         new StreamResult(new OutputStreamWriter(out, "UTF-8")));
	}
}
