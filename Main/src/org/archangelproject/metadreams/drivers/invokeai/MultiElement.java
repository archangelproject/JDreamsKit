package org.archangelproject.metadreams.drivers.invokeai;

import java.util.Iterator;

import org.archangelproject.metadreams.export.xml.XMLMultiElement;
import org.archangelproject.metadreams.export.xml.XMLSingleElement;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MultiElement extends XMLMultiElement {

	private Document doc;
	
	public MultiElement(Document doc, String key) {
		super(key);
		
		if (doc == null)
			throw new IllegalArgumentException("Document must not be null");
		
		this.doc = doc;
	}
	
	protected Document getDoc() {
		return this.doc;
	}

	@Override
	public Object getXMLNode() {
		Element rootElement = this.getDoc().createElement(this.getKey());
		
		// Adding the attributes for the tag
		Attr attrnode;
		for (String key : this.getAttributeKeys()) {
			attrnode = this.getDoc().createAttribute(key);
			attrnode.setValue(this.getAttribute(key).getValue());
			rootElement.setAttributeNode(attrnode);
		}
		
		for (Iterator<XMLMultiElement> iterator = this.getMultiElements().iterator(); iterator.hasNext();) {
			rootElement.appendChild((Element)iterator.next().getXMLNode());
		}
		
		for (Iterator<XMLSingleElement> iterator = this.getSingleElements().iterator(); iterator.hasNext();) {
			rootElement.appendChild((Element)iterator.next().getXMLNode());
		}
		
		return rootElement;
	}
}