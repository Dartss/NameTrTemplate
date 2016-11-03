package common.logging;

import jsmarty.core.common.constants.Component;
import jsmarty.core.common.logging.core.BaseLogger;
import jsmarty.core.common.logging.core.Logger;

public abstract class LocationsLogger
{
    private static final String logName = Component.LOCATIONS_PARSER_CODE;
    private static Logger instance;
    //
    public static Logger getInstance()
    {
	if(null!=instance) return instance;
	else instance = new BaseLogger(logName);
	//
	return instance;
    }

}
