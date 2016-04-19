package it.csttech.etltools;

import it.csttech.etltools.extractor.*;
import it.csttech.etltools.loader.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;



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

  public static final String CSV_FORMAT = "csv";
  public static final String FW_FORMAT = "fw";
  public static final String XML_FORMAT = "xml";
  public static final String DB_FORMAT = "db";
  public static final String SYSTEM_FORMAT = "sys";
  public static final String GUI_FORMAT = "gui";

  public static final String IN_FILE_OPT = "i";
  public static final String OUT_FILE_OPT = "o";
  public static final String IN_FORMAT_OPT = "if";
  public static final String OUT_FORMAT_OPT = "of";
  public static final String HELP_OPT = "h";
  public static final String TABLE_OPT = "t";

  public static final String DEFAULT_IN_FILE = "data.csv";
  public static final String DEFAULT_OUT_FILE = "output.dat";
  public static final String DEFAULT_TABLE = "TEST";

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

    String inputFormat = cmdLine.getOptionValue(IN_FORMAT_OPT, CSV_FORMAT);
    String outputFormat = cmdLine.getOptionValue(OUT_FORMAT_OPT, GUI_FORMAT);
    String inputFile = cmdLine.getOptionValue(IN_FILE_OPT, DEFAULT_IN_FILE);
    String outputFile = cmdLine.getOptionValue(OUT_FILE_OPT, DEFAULT_OUT_FILE);
    String table = cmdLine.getOptionValue(TABLE_OPT, DEFAULT_TABLE);

    ExtractorFactory extractorFactory = new ExtractorFactory(inputFile, table);
    Extractor extractor = extractorFactory.getExtractor(inputFormat);
    if( extractor == null ) {
      log.error("Invalid input format " + inputFormat + ".");
      return;
    }

    LoaderFactory loaderFactory = new LoaderFactory(outputFile, table);
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

    Option inFileOption = Option.builder(IN_FILE_OPT)
    .argName("input file")
    .longOpt("inputFile")
    .required(false)
    .numberOfArgs(1)
    .desc("input file")
    .build();

    Option outFileOption = Option.builder(OUT_FILE_OPT)
    .argName("output file")
    .longOpt("outputFile")
    .numberOfArgs(1)
    .required(false)
    .desc("output file")
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

    Option tableOption = Option.builder(TABLE_OPT)
    .argName("database table")
    .longOpt("table")
    .numberOfArgs(1)
    .required(false)
    .desc("database table")
    .build();

    Options options = new Options();
    options.addOption(helpOption);
    options.addOption(inFileOption);
    options.addOption(outFileOption);
    options.addOption(inFormatOption);
    options.addOption(outFormatOption);
    options.addOption(tableOption);

    CommandLine cmdLine = null;

    try{
      CommandLineParser parser = new DefaultParser();
      cmdLine = parser.parse(options, args); //throws ParseException. IF statement is skipped if ParseExc is catched.

      if (cmdLine.hasOption("help")) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Change Format (db, csv, fw, xml, sys)", options);
        cmdLine = null;
      }

    }catch( ParseException pe ){
      log.error("Invalid option(s).");
      cmdLine = null;
    }finally{
      return cmdLine;
    }

  }
}
