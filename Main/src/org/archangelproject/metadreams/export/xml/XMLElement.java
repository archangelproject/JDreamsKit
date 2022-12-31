package org.archangelproject.metadreams.export.xml;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class XMLElement {
	
	private String key;
	private Map<String, XMLAttribute> attributes;

	public XMLElement(String key) {
		super();
		if (key == null)
			throw new IllegalArgumentException("Key must not be null");
		
		this.key = key;
		this.attributes = new HashMap<String, XMLAttribute>();
	}

	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public XMLAttribute addAttribute(String key, String value) {
		return this.addAttribute(new XMLAttribute(key, value));
	}
	
	public XMLAttribute addAttribute(XMLAttribute attribute) {
		if (attribute != null) {
			this.attributes.put(attribute.getKey(), attribute);
			return attribute;
		}
		
		return null;
	}
	
	public Set<String> getAttributeKeys() {
		return this.attributes.keySet();
	}
	
	public XMLAttribute getAttribute(String key) {
		return this.attributes.get(key);
	}
	
	public XMLAttribute deleteAttribute(String key) {
		return this.attributes.remove(key);
	}

	public abstract Object getXMLNode();
}
