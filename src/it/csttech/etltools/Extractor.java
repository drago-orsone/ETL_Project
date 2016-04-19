package it.csttech.etltools;
import java.util.*;

/**
* An object that extract a container of records from an unspecified source file.
*
* @author drago-orsone, MasterToninus
*/
public interface Extractor {

  /**
   * Returns a Records (container of record).
   * @return A container containg all the extracted records.
   */
  public Records extract();

}
