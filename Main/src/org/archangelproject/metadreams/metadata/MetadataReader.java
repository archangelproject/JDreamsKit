package org.archangelproject.metadreams.metadata;

import java.io.File;

public abstract class MetadataReader implements IMetadataReader {
	
	private File file;
	
	public MetadataReader() {
		
	}

	@Override
	public File setFile(String filepath) {
		File file = null;
		if(filepath!=null) {
			file=new File(filepath);
			if(!file.exists()) throw new IllegalArgumentException("The specified file does not exist");
			if(file.isDirectory()) throw new IllegalArgumentException("The specified file is a directory");
		}
		else new IllegalArgumentException("The filepath must contain the path to the file");
		
		this.file = file;
		return this.file;
	}

	@Override
	public void setFile(File file) {
		if(file!=null) {
			if(!file.exists()) throw new IllegalArgumentException("The specified file does not exist");
			if(file.isDirectory()) throw new IllegalArgumentException("The specified file is a directory");
		}
		else
			throw new IllegalArgumentException("File must not be null");
		
		this.file = file;
	}

	public File getFile() {
		return file;
	}
}
