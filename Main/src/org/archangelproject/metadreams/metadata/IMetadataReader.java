package org.archangelproject.metadreams.metadata;

import java.io.File;
import java.util.Map;

import org.archangelproject.metadreams.exception.MetadataException;

public interface IMetadataReader {
	
	public File setFile(String filepath);
	
	public void setFile(File file);
	
	public Map<String, MetadataNode> getMetadata() throws MetadataException;
	
	public void refresh() throws MetadataException;
	
	public void close();
	
	public boolean isClosed();
}
