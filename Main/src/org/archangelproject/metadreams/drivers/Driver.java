package org.archangelproject.metadreams.drivers;

import java.io.File;

import org.archangelproject.metadreams.drivers.invokeai.Folder;
import org.archangelproject.metadreams.drivers.invokeai.Image;
import org.archangelproject.metadreams.exception.MetadataException;
import org.archangelproject.metadreams.export.xml.XMLMultiElement;

public abstract class Driver {
	
	public abstract Image readImage(String file, Folder parent) throws MetadataException;
	
	public abstract Image readImage(File file, Folder parent) throws MetadataException ;

	public abstract Folder readFolder(File folder, Folder parent) throws MetadataException;

	public abstract Folder readFolder(String folder, Folder parent) throws MetadataException;
	
	public abstract XMLMultiElement getRoot();
}
