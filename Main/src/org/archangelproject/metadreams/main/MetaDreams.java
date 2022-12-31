package org.archangelproject.metadreams.main;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.archangelproject.metadreams.drivers.Driver;
import org.archangelproject.metadreams.drivers.DriverHandler;
import org.archangelproject.metadreams.drivers.invokeai.Folder;
import org.archangelproject.metadreams.drivers.invokeai.Image;
import org.archangelproject.metadreams.drivers.invokeai.InvokeAIDriver;
import org.archangelproject.metadreams.exception.ExportException;
import org.archangelproject.metadreams.exception.MetadataException;
import org.archangelproject.metadreams.export.Export;
import org.archangelproject.metadreams.export.screen.ScreenExport;

public class MetaDreams extends DriverHandler{
	
	private final static String option_f_short = "f";
	private final static String option_f_long  = "file";
	
	private final static String option_d_short = "d";
	private final static String option_d_long  = "dreams";
	
	private final static String option_r_short = "r";
	private final static String option_r_long  = "recursive";
	
	private final static String option_o_short = "o";
	private final static String option_o_long  = "output";
	
	private final static String option_c_short = "c";
	private final static String option_c_long  = "ckpt";
	
	private final static String option_v_short = "v";
	private final static String option_v_long  = "verbose";
	
	private final static String option_h_short = "h";
	private final static String option_h_long  = "help";
	
	private final static String option_V_short = "V";
	private final static String option_V_long  = "version";
	
	public final static String _version_ = "v.0.5.0";

	private static Options loadOptions() {
		Options options = new Options();
		Option option;
		
		// -f --file
		option = new Option(option_f_short, option_f_long, true, "If a PNG file is specified, the metadata info will be printed in the screen. If a folder is specified, the metadata in the PNG files will be read and stored in the metadata.xml file");
		option.setRequired(true);
		options.addOption(option);
		
		// -d --dreams
		option = new Option(option_d_short, option_d_long, false, "This option creates a prompts.sdp file in the folder specified in the option -f where all the prompts are stored");
		option.setRequired(false);
		options.addOption(option);
		
		//-r --recursive
		option = new Option(option_r_short, option_r_long, false, "Recursivity enabled to read all the png files stored in the subfolders. The metadata information will be stored in the metada.xml file in the root folder, and if the -d option is set, the prompts will be stored in the prompts.sdp in the root folder");
		option.setRequired(false);
		options.addOption(option);
		
		//-o --output
		option = new Option(option_o_short, option_o_long, false, "Option to add in the stored prompt the -o option that will create the dream in the same folder where it was read");
		option.setRequired(false);
		options.addOption(option);
		
		//-c --ckpt
		option = new Option(option_c_short, option_c_long, false, "Set manually the ckpt model used to generate the images, this info is stores in the metadata.xml file");
		option.setRequired(false);
		options.addOption(option);
		
		//-v --verbose
		option = new Option(option_v_short, option_v_long, false, "Verbose mode enabled");
		option.setRequired(false);
		options.addOption(option);
		
		//-h --help
		option = new Option(option_h_short, option_h_long, false, "Help showed");
		option.setRequired(false);
		options.addOption(option);
		
		//-v --version
		option = new Option(option_V_short, option_V_long, false, "Version showed");
		option.setRequired(false);
		options.addOption(option);
		
		return options;
	}
	
	private static void displaySingleFile(File file) {
		DriverHandler handler = new DriverHandler();
		try {
			Driver driver = new InvokeAIDriver(handler);
			Image image = driver.readImage(file, null);
			Export export = new ScreenExport(driver.getRoot());
			export.export(image);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MetadataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void displayFolder(File file) {
		DriverHandler handler = new DriverHandler();
		try {
			Driver driver = new InvokeAIDriver(handler);
			Folder folder = driver.readFolder(file, null);
			Export export = new ScreenExport(driver.getRoot());
			export.export(folder);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MetadataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void exit(String message, int status) {
		System.out.println(message);
		System.exit(status);
		return;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Options options = MetaDreams.loadOptions();
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;

		try {
			cmd = parser.parse(options, args);
			
			//get the value of the filepath
			try {
				File file = new File(cmd.getOptionValue(option_f_short));
				if (file.isFile() && file.exists()) {
					//Display metadata of the file
					MetaDreams.displaySingleFile(file);
				}
				else if (file.isDirectory() && file.exists()) {
					//Folder selected
					MetaDreams.displayFolder(file);
				}
			}
			catch(Exception e) {
				MetaDreams.exit(e.getMessage(), 1);
			}
		}
		catch(ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("utility-name", options);
			
			MetaDreams.exit("", 1);
		}
	}
}