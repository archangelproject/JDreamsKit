package org.archangelproject.metadreams.metadata.image;

import org.archangelproject.metadreams.metadata.MetadataReader;

public abstract class ImageMetadataReader extends MetadataReader {

	public abstract ImageDimension getSize();
}
