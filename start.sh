#!/bin/bash 

	MAIN_CLASS_FILE="it.csttech.etltools.UseETL"
	CLASS_PATH=".:lib/*:bin" #; in Windows
	LOG_CONFIG="config/log4j2.xml"


	echo "---------------------------------"
	echo "*** Launching $MAIN_CLASS_FILE $@ ***"
	echo "---------------------------------"
	echo " "


	java -Dlog4j.configurationFile=$LOG_CONFIG -cp $CLASS_PATH $MAIN_CLASS_FILE $@
	
	echo " "
	echo "---------------------------------"
	
