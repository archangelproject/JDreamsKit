package org.archangelproject.metadreams.drivers.invokeai;

import java.util.List;

import org.archangelproject.metadreams.export.xml.XMLKeys;
import org.archangelproject.metadreams.export.xml.XMLMultiElement;
import org.w3c.dom.Document;

public class Folder extends MultiElement {
	
	private static final String PATH_SEPARATOR = "/";
	
	private Folder parent;
	
	public Folder(Document doc, Folder parent, String name) {
		super(doc, XMLKeys.XML_FOLDER_KEY);

		if(name == null)
			throw new IllegalArgumentException("Folder name must not be null");
		
		this.parent = parent;
		this.addAttribute(new Attribute(XMLKeys.XML_FOLDER_KEY_FOLDERNAME, name));
	}
	
	/**
	 * @return the folder name
	 */
	public String getFolderName() {
		return this.getAttribute(XMLKeys.XML_FOLDER_KEY_FOLDERNAME).getValue();
	}
	
	/**
	 * @return the complete file path
	 */
	public String getFolderPath() {
		return this.getParent()==null?".":this.getParent().getFolderPath()+PATH_SEPARATOR+this.getFolderName();
	}

	/**
	 * @return the parent
	 */
	public Folder getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Folder parent) {
		this.parent = parent;
	}
	
	public void addImage(Image image) {
		if(image != null) 
			super.addElement(image);
	}
	
	public Folder addFolder(Folder folder) {
		if (folder != null) {
			folder.setParent(this);
			super.addElement(folder);
		}
		
		return folder;
	}
	
	public List<XMLMultiElement> getChildren(){
		return this.getMultiElements();
	}
}
