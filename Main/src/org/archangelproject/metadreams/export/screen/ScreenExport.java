package org.archangelproject.metadreams.export.screen;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamResult;

import org.archangelproject.metadreams.export.Export;
import org.archangelproject.metadreams.export.xml.XMLMultiElement;

public class ScreenExport extends Export {

	public ScreenExport(XMLMultiElement root) throws ParserConfigurationException {
		super(root, new StreamResult(System.out));
	}
}