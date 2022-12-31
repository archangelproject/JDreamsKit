package org.archangelproject.metadreams.export.xml;

public abstract class XMLSingleElement extends XMLElement{
	private String value;
	
	public XMLSingleElement(String key, String value) {
		super(key);
		if (value == null)
			throw new IllegalArgumentException("Value must not be null");

		this.setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
