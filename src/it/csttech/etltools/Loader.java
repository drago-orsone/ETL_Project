package it.cst.etltools

/**
 * Loader
*/
public interface Loader {

  /* 
   * TEMP! così dipende dall'implementazione? 
   * No! la classe concreta che implementa la sua interfaccia avrà come attributo le info del file
   */
  public void Load();

}

