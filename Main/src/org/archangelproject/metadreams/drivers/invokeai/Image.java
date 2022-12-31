package org.archangelproject.metadreams.drivers.invokeai;


import org.archangelproject.metadreams.export.xml.XMLKeys;
import org.archangelproject.metadreams.metadata.image.ImageDimension;
import org.w3c.dom.Document;

public class Image extends MultiElement {
	private Folder parent;
	
	private ImageDimension size;
	
	public Image(Document doc, Folder parent, String filename) {
		super(doc, XMLKeys.XML_IMAGE_KEY);

		if (filename == null)
			throw new IllegalArgumentException("The filename must not be null");
		
		this.parent = parent;
		this.addAttribute(new Attribute(XMLKeys.XML_IMAGE_KEY_FILENAME, filename));
		this.addElement(new SingleElement(doc, XMLKeys.XML_IMAGE_KEY_PATH, this.getImagePath()));
	}
	
	/**
	 * @return the filepath
	 */
	public String getFileName() {
		return this.getAttribute(XMLKeys.XML_IMAGE_KEY_FILENAME).getValue();
	}
	
	/**
	 * @return the complete file path
	 */
	public String getImagePath() {
		return ((this.getParent()==null)?".":this.getParent().getFolderPath())+"/"+this.getFileName();
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
	/*public void setParent(Folder parent) {
		this.parent = parent;
	}*/

	/**
	 * @return the size
	 */
	public ImageDimension getSize() {
		return size;
	}
	
	/**
	 * @param width width of the image
	 * @param height height of the image
	 * @return the size
	 */
	public ImageDimension setSize(int width, int height) {
		this.setSize(new ImageDimension(width, height));
		return this.getSize();
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(ImageDimension size) {
		this.size = size;
	}

	/**
	 * @param prompt the prompt to set
	 */
	public void setPrompt(String prompt) {
		super.addElement(new SingleElement(this.getDoc(), XMLKeys.XML_IMAGE_KEY_PROMPT, prompt));
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		super.addElement(new SingleElement(this.getDoc(), XMLKeys.XML_IMAGE_KEY_MODEL, model));
	}

	/**
	 * @param model_id the model_id to set
	 */
	public void setModel_id(String model_id) {
		super.addElement(new SingleElement(this.getDoc(), XMLKeys.XML_IMAGE_KEY_MODEL_ID, model_id));
	}

	/**
	 * @param model_hash the model_hash to set
	 */
	public void setModel_hash(String model_hash) {
		super.addElement(new SingleElement(this.getDoc(), XMLKeys.XML_IMAGE_KEY_MODEL_HASH, model_hash));
	}

	/**
	 * @param app_id the app_id to set
	 */
	public void setApp_id(String app_id) {
		super.addElement(new SingleElement(this.getDoc(), XMLKeys.XML_IMAGE_KEY_APP_ID, app_id));
	}

	/**
	 * @param app_version the app_version to set
	 */
	public void setApp_version(String app_version) {
		super.addElement(new SingleElement(this.getDoc(), XMLKeys.XML_IMAGE_KEY_APP_VERSION, app_version));
	}

	/**
	 * @param threshold the threshold to set
	 */
	public void setThreshold(String threshold) {
		super.addElement(new SingleElement(this.getDoc(), XMLKeys.XML_IMAGE_KEY_THRESHOLD, threshold));
	}

	/**
	 * @param init_mask the init_mask to set
	 */
	public void setInit_mask(String init_mask) {
		super.addElement(new SingleElement(this.getDoc(), XMLKeys.XML_IMAGE_KEY_INIT_MASK, init_mask));
	}

	/**
	 * @param cfg_scale the cfg_scale to set
	 */
	public void setCfg_scale(String cfg_scale) {
		super.addElement(new SingleElement(this.getDoc(), XMLKeys.XML_IMAGE_KEY_CFG_SCALE, cfg_scale));
	}

	/**
	 * @param splitted_prompt the splitted_prompt to set
	 */
	public void setSplitted_prompt(String splitted_prompt) {
		super.addElement(new SingleElement(this.getDoc(), XMLKeys.XML_IMAGE_KEY_SPLITTED_PROMPT, splitted_prompt));
	}

	/**
	 * @param perlin the perlin to set
	 */
	public void setPerlin(String perlin) {
		super.addElement(new SingleElement(this.getDoc(), XMLKeys.XML_IMAGE_KEY_PERLIN, perlin));
	}

	/**
	 * @param facetool the facetool to set
	 */
	public void setFacetool(String facetool) {
		super.addElement(new SingleElement(this.getDoc(), XMLKeys.XML_IMAGE_KEY_FACETOOL, facetool));
	}

	/**
	 * @param steps the steps to set
	 */
	public void setSteps(String steps) {
		super.addElement(new SingleElement(this.getDoc(), XMLKeys.XML_IMAGE_KEY_STEPS, steps));
	}

	/**
	 * @param upscale the upscale to set
	 */
	public void setUpscale(String upscale) {
		super.addElement(new SingleElement(this.getDoc(), XMLKeys.XML_IMAGE_KEY_UPSCALE, upscale));
	}

	/**
	 * @param seed the seed to set
	 */
	public void setSeed(String seed) {
		super.addElement(new SingleElement(this.getDoc(), XMLKeys.XML_IMAGE_KEY_SEED, seed));
	}

	/**
	 * @param facetool_strengh the facetool_strengh to set
	 */
	public void setFacetool_strengh(String facetool_strengh) {
		super.addElement(new SingleElement(this.getDoc(), XMLKeys.XML_IMAGE_KEY_FACETOOL_STRENGTH, facetool_strengh));
	}

	/**
	 * @param postprocessing the postprocessing to set
	 */
	public void setPostprocessing(String postprocessing) {
		super.addElement(new SingleElement(this.getDoc(), XMLKeys.XML_IMAGE_KEY_POSTPROCESSING, postprocessing));
	}

	/**
	 * @param sampler the sampler to set
	 */
	public void setSampler(String sampler) {
		super.addElement(new SingleElement(this.getDoc(), XMLKeys.XML_IMAGE_KEY_SAMPLER, sampler));
	}

	/**
	 * @param variation the variation to set
	 */
	public void setVariation(String variation) {
		super.addElement(new SingleElement(this.getDoc(), XMLKeys.XML_IMAGE_KEY_VARIATIONS, variation));
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		super.addElement(new SingleElement(this.getDoc(), XMLKeys.XML_IMAGE_KEY_TYPE, type));
	}
}
