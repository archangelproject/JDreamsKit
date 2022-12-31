package org.archangelproject.metadreams.metadata.image.png;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataFormatImpl;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;

import org.archangelproject.metadreams.exception.MetadataException;
import org.archangelproject.metadreams.metadata.MetadataNode;
import org.archangelproject.metadreams.metadata.image.ImageDimension;
import org.archangelproject.metadreams.metadata.image.ImageMetadataReader;
import org.w3c.dom.NodeList;

public class PNGReader extends ImageMetadataReader {
	
	private IIOMetadata metadata;
	private Map<String, MetadataNode> nodes;
	private ImageDimension dimension;
	private boolean closed;

	public PNGReader() {
	}
	
	
	private void init() throws MetadataException {
		this.closed = false;
		this.nodes=new HashMap<String, MetadataNode>();
		
		try (ImageInputStream input = ImageIO.createImageInputStream(this.getFile())){

            Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
            ImageReader reader = readers.next(); // TODO: Validate that there are readers
            
            reader.setInput(input);
            this.dimension = new ImageDimension(reader.getWidth(0), reader.getHeight(0));
            this.metadata = reader.getImageMetadata(0);
            reader.dispose();
        } catch (IOException e) {
			throw new MetadataException("The specified file dies not exist");
		} catch(NoSuchElementException e) {
			throw new MetadataException("The specified file is not an image: "+this.getFile().getName());
		}
	}

	@Override
	public Map<String, MetadataNode> getMetadata() throws MetadataException{
		if (!this.isClosed()) {
			this.init();
			
			IIOMetadataNode root = (IIOMetadataNode) this.metadata.getAsTree(IIOMetadataFormatImpl.standardMetadataFormatName);
			NodeList entries = root.getElementsByTagName("TextEntry");
		
			IIOMetadataNode node;
			MetadataNode metadataNode;
			for (int i = 0; i < entries.getLength(); i++) {
				node = (IIOMetadataNode)entries.item(i);
				metadataNode = new MetadataNode(node.getAttribute("keyword"), node.getAttribute("value"));
				this.nodes.put(metadataNode.getKeyword(), metadataNode);
			}
		}
		
		return this.nodes;
	}

	@Override
	public File setFile(String filepath) {
		super.setFile(filepath);
		
		this.close();
		this.closed = false;
		
		return this.getFile();
	}

	@Override
	public void setFile(File file) {
		super.setFile(file);
		
		this.close();
		this.closed = false;
	}

	@Override
	public void refresh() throws MetadataException{
		this.init();
	}

	@Override
	public void close() {
		if(!this.isClosed()) {
			this.closed=true;
			this.metadata = null;
			this.nodes = null;
		}
	}
	
	@Override
	public boolean isClosed() {
		return this.closed;
	}

	@Override
	public ImageDimension getSize() {
		return this.dimension;
	}
}