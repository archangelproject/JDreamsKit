package org.archangelproject.metadreams.drivers.invokeai;

import org.archangelproject.metadreams.export.xml.XMLSingleElement;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SingleElement extends XMLSingleElement {

	private Document doc;
	
	public SingleElement(Document doc, String key, String value) {
		super(key, value);
		
		if (doc == null)
			throw new IllegalArgumentException("Doc must not be null");
		
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
		
		rootElement.appendChild(this.getDoc().createTextNode(this.getValue()));
		
		return rootElement;
	}
}