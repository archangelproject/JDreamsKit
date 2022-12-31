package org.archangelproject.metadreams.export;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.archangelproject.metadreams.drivers.invokeai.Folder;
import org.archangelproject.metadreams.drivers.invokeai.Image;
import org.archangelproject.metadreams.exception.ExportException;
import org.archangelproject.metadreams.export.xml.XMLMultiElement;
import org.w3c.dom.Element;

public abstract class Export {

	private XMLMultiElement root;
	private StreamResult result;
	
	public Export(XMLMultiElement root, StreamResult result) throws ParserConfigurationException {
		if (root == null)
			throw new IllegalArgumentException("Root element must not be null");
		if (result == null)
			throw new IllegalArgumentException("Output stream must not be null");
		
		this.root = root;
		this.result = result;
	}
	
	private void exportXML(Element element) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute("indent-number", 4);
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        
        DOMSource source = new DOMSource(element.getOwnerDocument());

        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);

        transformer.transform(source, this.result);
	}

	public void export(Folder folder) throws ExportException {
		if (folder == null)
			throw new IllegalArgumentException("The foler to be exported must not be null");
		
		this.root.addElement(folder);
		
		try {
			Element element = (Element)this.root.getXMLNode();
			element.getOwnerDocument().appendChild(element);
			this.exportXML(element);
		} catch (TransformerException e) {
			throw new ExportException(e);
		}
	}
	
	public void export(Image image) throws ExportException {
		if (image == null)
			throw new IllegalArgumentException("The image to be exported must not be null");
		
		this.root.addElement(image);
		
        try {
        	Element element = (Element)this.root.getXMLNode();
			element.getOwnerDocument().appendChild(element);
			this.exportXML(element);
		} catch (TransformerException e) {
			throw new ExportException(e);
		}
	}
}