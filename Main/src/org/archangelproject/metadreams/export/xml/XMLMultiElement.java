package org.archangelproject.metadreams.export.xml;

import java.util.ArrayList;
import java.util.List;

public abstract class XMLMultiElement extends XMLElement {
	private List<XMLSingleElement> singles;
	private List<XMLMultiElement> multis;

	public XMLMultiElement(String key) {
		super(key);
		this.singles = new ArrayList<XMLSingleElement>();
		this.multis = new ArrayList<XMLMultiElement>();
	}

	public void addElement(XMLSingleElement element) {
		if (element == null)
			throw new IllegalArgumentException("Element must not be null");
		
		this.singles.add(element);
	}
	
	public void addElement(XMLMultiElement element) {
		if (element == null)
			throw new IllegalArgumentException("Element must not be null");
		
		this.multis.add(element);
	}
	
	public List<XMLSingleElement> getSingleElements(){
		return this.singles;
	}
	
	public List<XMLMultiElement> getMultiElements(){
		return this.multis;
	}
}
