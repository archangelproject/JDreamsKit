package org.archangelproject.metadreams.export.xml;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamResult;

import org.archangelproject.metadreams.export.Export;

public class XMLExport extends Export {

	public XMLExport(File output, XMLMultiElement root) throws ParserConfigurationException {
		super(root, new StreamResult(output));
	}

}
