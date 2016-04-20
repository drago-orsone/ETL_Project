package it.csttech.etltools;

import it.csttech.etltools.extractor.*;
import it.csttech.etltools.loader.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
* A cli interface to launch a simple ETL Suite
*
* <p>
*
* </p>
*
* @author drago-orsone, MasterToninus
*
*/
public class UseETL {

  public static final String DEFAULT_PROPERTIES = "config/etltools_default.properties";

  public static final String IN_FORMAT_OPT = "if";
  public static final String OUT_FORMAT_OPT = "of";
  public static final String HELP_OPT = "h";
  public static final String PROPERTIES_OPT = "p";


  static final Logger log = LogManager.getLogger();

  /**
  * Main
  *
  * @param  args argomenti passati
  */
  public static void main(String[] args) {

    CommandLine cmdLine = manageOption(args);
    if (cmdLine == null)
    return;

    String propFile = cmdLine.getOptionValue(PROPERTIES_OPT, DEFAULT_PROPERTIES);
    Properties prop = readProperties(propFile);

    String inputFormat = cmdLine.getOptionValue(IN_FORMAT_OPT, prop.getProperty("default.inFormat"));
    String outputFormat = cmdLine.getOptionValue(OUT_FORMAT_OPT, prop.getProperty("default.outFormat"));

    ExtractorFactory extractorFactory = new ExtractorFactory(prop);
    Extractor extractor = extractorFactory.getExtractor(inputFormat);
    if( extractor == null ) {
      log.error("Invalid input format " + inputFormat + ".");
      return;
    }

    LoaderFactory loaderFactory = new LoaderFactory(prop);
    Loader loader = loaderFactory.getLoader(outputFormat);
    if( loader == null ) {
      log.error("Invalid output format " + outputFormat + ".");
      return;
    }

    loader.load(extractor.extract());


  }

  public static CommandLine manageOption(String[] args) {

    Option helpOption = Option.builder(HELP_OPT)
    .longOpt("help")
    .hasArg(false)
    .required(false)
    .desc("print guide")
    .build();

    Option inFormatOption = Option.builder(IN_FORMAT_OPT)
    .argName("input format")
    .longOpt("inputFormat")
    .required(false)
    .numberOfArgs(1)
    .desc("input file format")
    .build();

    Option outFormatOption = Option.builder(OUT_FORMAT_OPT)
    .argName("output format")
    .longOpt("outputFormat")
    .numberOfArgs(1)
    .required(false)
    .desc("output file format")
    .build();

    Option propertiesOption = Option.builder(PROPERTIES_OPT)
    .argName("properties file")
    .longOpt("properties")
    .numberOfArgs(1)
    .required(false)
    .desc("path to properties file")
    .build();

    Options options = new Options();
    options.addOption(helpOption);
    options.addOption(inFormatOption);
    options.addOption(outFormatOption);
    options.addOption(propertiesOption);

    CommandLine cmdLine = null;

    try{
      CommandLineParser parser = new DefaultParser();
      cmdLine = parser.parse(options, args); //throws ParseException. IF statement is skipped if ParseExc is catched.

      if (cmdLine.hasOption("help")) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Change Format (db, csv, fw, xml, sys, gui)", options);
        cmdLine = null;
      }

    }catch( ParseException pe ){
      log.error("Invalid option(s).");
      cmdLine = null;
    }finally{
      return cmdLine;
    }

  }

  public static Properties readProperties( String propFile ){
    Properties prop = new Properties();
    InputStream input = null;

    try {
      input = new FileInputStream(propFile);

      // load a properties file
      prop.load(input);
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return prop;
    }
  }

  public UseETL(){

  }

}
