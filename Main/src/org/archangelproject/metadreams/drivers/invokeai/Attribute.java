package org.archangelproject.metadreams.drivers.invokeai;

import org.archangelproject.metadreams.export.xml.XMLAttribute;

public class Attribute extends XMLAttribute {

	public Attribute(String key, String value, String description) {
		super(key, value, description);
	}

	public Attribute(String key, String value) {
		super(key, value);
	}
}
