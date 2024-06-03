package api.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;


public class Log {
	
	private static final Logger logger = LogManager.getLogger(Log.class.getName());
	 
	  static {
	        // Specify the location of the log4j2.xml file
	        Configurator.initialize(null, "log4j2.xml");
	    }

	    public static void startTestCase(String sTestCaseName) {
	        logger.info("================" + sTestCaseName + " TEST START======================");
	    }

	    public static void endTestCase(String sTestCaseName) {
	        logger.info("===============" + sTestCaseName + " TEST END=========================");
	    }

	    public static void info(String message) {
	        logger.info(message);
	    }

	    public static void warn(String message) {
	        logger.warn(message);
	    }

	    public static void error(String message) {
	        logger.error(message);
	    }

	    public static void fatal(String message) {
	        logger.fatal(message);
	    }

	    public static void debug(String message) {
	        logger.debug(message);
	    }

}
