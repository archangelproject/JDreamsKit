package org.archangelproject.metadreams.drivers.invokeai;

import java.io.File;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.archangelproject.metadreams.drivers.Driver;
import org.archangelproject.metadreams.drivers.DriverHandler;
import org.archangelproject.metadreams.exception.MetadataException;
import org.archangelproject.metadreams.export.xml.XMLKeys;
import org.archangelproject.metadreams.export.xml.XMLMultiElement;
import org.archangelproject.metadreams.main.MetaDreams;
import org.archangelproject.metadreams.metadata.MetadataNode;
import org.archangelproject.metadreams.metadata.image.ImageMetadataReader;
import org.archangelproject.metadreams.metadata.image.png.PNGReader;
import org.w3c.dom.Document;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class InvokeAIDriver extends Driver{
	
	private DriverHandler handler;
	private ImageMetadataReader reader;
	private Document document;
	private MultiElement root;
	
	public InvokeAIDriver(DriverHandler handler) throws ParserConfigurationException {
		if (handler == null)
			throw new IllegalArgumentException("Handler must not be null");
		
		this.handler = handler;
		this.reader = new PNGReader();
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        
        //Create the document and the metadata tag
        this.document = docBuilder.newDocument();
        this.root = new MultiElement(this.document, XMLKeys.XML_METADATA_KEY);
        this.root.addAttribute(XMLKeys.XML_METADATA_SOFTWARE_KEY, "MetaDreams "+MetaDreams._version_);
	}
	
	public XMLMultiElement getRoot() {
		return this.root;
	}
	
	private Map<String, JsonElement> parseJSon(String json) {
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
		return jsonObject.asMap();
	}

	@Override
	public Image readImage(String file, Folder parent) throws MetadataException {
		if (file == null)
			throw new IllegalArgumentException("File path must not be null");
		
		return this.readImage(new File(file), parent);
	}

	@Override
	public Image readImage(File file, Folder parent) throws MetadataException {
		if (file == null)
			throw new IllegalArgumentException("Image must not be null");
		
		if (!file.isFile())
			throw new IllegalArgumentException("The argument is not a file. (Directory?: "+file.isDirectory()+")");
		
		this.reader.setFile(file);
		Map<String, MetadataNode> metadata = this.reader.getMetadata();
		
		Image image = new Image(this.document, parent, file.getName());
		image.setSize(reader.getSize());
		
		String[] keys = {XMLKeys.XML_IMAGE_KEY_MODEL,
		               XMLKeys.XML_IMAGE_KEY_MODEL_ID,
		               XMLKeys.XML_IMAGE_KEY_MODEL_HASH,
		               XMLKeys.XML_IMAGE_KEY_APP_ID,
		               XMLKeys.XML_IMAGE_KEY_APP_VERSION,
		               XMLKeys.XML_IMAGE_KEY_THRESHOLD,
		               XMLKeys.XML_IMAGE_KEY_INIT_MASK,
		               XMLKeys.XML_IMAGE_KEY_CFG_SCALE,
		               XMLKeys.XML_IMAGE_KEY_SPLITTED_PROMPT,
		               XMLKeys.XML_IMAGE_KEY_PERLIN,
		               XMLKeys.XML_IMAGE_KEY_FACETOOL,
		               XMLKeys.XML_IMAGE_KEY_STEPS,
		               XMLKeys.XML_IMAGE_KEY_UPSCALE,
		               XMLKeys.XML_IMAGE_KEY_SEED,
		               XMLKeys.XML_IMAGE_KEY_FACETOOL_STRENGTH,
		               XMLKeys.XML_IMAGE_KEY_POSTPROCESSING,
		               XMLKeys.XML_IMAGE_KEY_SAMPLER,
		               XMLKeys.XML_IMAGE_KEY_VARIATIONS,
		               XMLKeys.XML_IMAGE_KEY_TYPE};
		
		image.setPrompt(metadata.get(XMLKeys.XML_IMAGE_KEY_DREAM).getValue());
		metadata.remove(XMLKeys.XML_IMAGE_KEY_PROMPT);
		
		Map<String, JsonElement> map;
		String value;
		try {
			//Adding what is inside sd-metadata
			map = this.parseJSon(metadata.get(XMLKeys.XML_IMAGE_SD_METADATA).getValue());
			
			//Adding what is inside image to the current map, and deleting the original tag
			map.putAll(this.parseJSon(map.get(XMLKeys.XML_IMAGE_KEY).toString()));
			map.remove(XMLKeys.XML_IMAGE_KEY);
			
			for(int i=0;i<keys.length;i++) {
				try {
					value = map.get(keys[i]).getAsString();
				} catch (Exception e) {
					value = map.get(keys[i]).toString();
					if (value.startsWith("\""))
						value = value.substring(1);
					if (value.endsWith("\""))
						value = value.substring(0, value.length()-1);
				}
				image.addElement(new SingleElement(this.document, keys[i], value));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		return image;
	}
	
	@Override
	public Folder readFolder(String folder, Folder parent) throws MetadataException {
		if (folder == null) 
			throw new IllegalArgumentException("Folder path must not be null");
		
		return this.readFolder(new File(folder), parent);
	}
	
	@Override
	public Folder readFolder(File folder, Folder parent) throws MetadataException {
		if (folder == null)
			throw new IllegalArgumentException("Folder path must not be null");
		
		Folder currentFolder = null;
		if (folder.exists())
			if (folder.isDirectory()) {
				File[] files = folder.listFiles();
				currentFolder = new Folder(this.document, parent, folder.getName());
				for (File file : files) {
					if (file.isFile()) {
						try {
							currentFolder.addImage(this.readImage(file, currentFolder));
						}
						catch (MetadataException except) {
							if (this.handler.verboseEnabled())
								System.out.println("File: "+file.getName()+" ignored");
						}
					}
					else if (file.isDirectory())
						currentFolder.addFolder(this.readFolder(file, currentFolder));
					else {
						if (this.handler.verboseEnabled())
							System.out.println("File \""+file.getName()+" in \""+folder.getName()+"\" ignored.");
					}
						
				}
			}
			else {
				if (this.handler.verboseEnabled())
					System.out.println("Folder: "+folder.getName()+" is not a directory");
			}
		else
			if (this.handler.verboseEnabled())
				System.out.println("Folder: "+folder.getName()+" does not exist");
		
		return currentFolder;
	}
}