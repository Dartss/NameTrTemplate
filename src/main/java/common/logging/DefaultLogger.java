package common.logging;

import jsmarty.core.common.constants.Component;
import jsmarty.core.common.logging.core.BaseLogger;
import jsmarty.core.common.logging.core.Logger;

public abstract class DefaultLogger
{
    private static final String logName = Component.DEFAULT;
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
