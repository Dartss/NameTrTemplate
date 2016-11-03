package common.properties.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.configuration2.AbstractConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ConfigurationBuilder;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.ex.ConversionException;

public class BaseProperties
{

    private final static String DELIM = ",";
    //
    private String propertyFileName;
    private AbstractConfiguration configuration;
    private static Map<String, AbstractConfiguration> propertiesMap = new HashMap<String, AbstractConfiguration>();

    // Can't use the customized logger here, sine the customized logger uses
    // this class as well
    private Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public BaseProperties(String propertyFileName) {
	this.propertyFileName = propertyFileName;
	loadProperties(propertyFileName, true);
    }

    /**
     * load properties to map
     * 
     * @param fileName
     * @return
     */
    public boolean loadProperties(String fileName)
    {
	return loadProperties(fileName, false);
    }

    /**
     * load / refresh the properties and add / update it in shared map
     * 
     * @param fileName
     * @param refresh
     * @return
     */
    public boolean loadProperties(String fileName, boolean refresh)
    {
	if (!refresh && propertiesMap.containsKey(fileName))
	{
	    propertiesMap.get(fileName);
	} else
	{
	    AbstractConfiguration configuration = loadConfigProperties(fileName);
	    if (configuration != null)
	    {
		propertiesMap.put(fileName, configuration);
		return true;
	    }
	}
	return false;
    }

    /**
     * 
     * @param fileName
     *            load properties file from classpath
     *            if not found search properties file in a given path on the
     *            machine
     *            if not found search properties file in build path
     *            Path on server and build path are predefined in the
     *            application
     * @return AbstractConfiguration
     */
    //
    private AbstractConfiguration loadConfigProperties(String fileName)
    {
	String fullPath = fileName;
	//
	LOGGER.info("Loading property file from classpath: " + fullPath);
	if (null == this.loadConfigPropertiesFile(fullPath))
	{
	    fullPath = PropertiesConstants.PROPERTIES_FILE_PATH_ON_SERVER + fileName;
	    LOGGER.info("loading properties file from the server directory " + fullPath);
	    //
	    if (null == this.loadConfigPropertiesFile(fullPath))
	    {
		fullPath = PropertiesConstants.PROPERTIES_FILE_BUILD_PATH + fileName;
		LOGGER.info("loading properties file from the build path " + fullPath);
		//
		this.loadConfigPropertiesFile(fullPath);
	    }
	}
	return this.configuration;
    }

    //
    private AbstractConfiguration loadConfigPropertiesFile(String fileName)
    {
	Parameters params = new Parameters();
	ConfigurationBuilder builder;
	try
	{
	    //
	    builder = new FileBasedConfigurationBuilder(PropertiesConfiguration.class).configure(params.properties().setFileName(fileName));
	    //
	    // Has been commented because we might have a list of values and the
	    // value itself contains , characters
	    // So the recommended usage of list is to use the same key multiple
	    // times
	    // .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
	    //
	    this.configuration = (AbstractConfiguration) builder.getConfiguration();
	    //
	} catch (ConfigurationException e)
	{
	    LOGGER.info("Property file not found : " + fileName + " - " + e.getMessage());
	}
	//
	return this.configuration;
    }

    private AbstractConfiguration getPropsInstance(String fileName)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    return propertiesMap.get(fileName);
	} else
	{
	    LOGGER.warning("The properties file was not loaded: " + fileName);
	    return this.configuration;
	}
    }

    /*
     * TODO make generic, so it could be used with different prefixes ..
     * Got from the old implementation - kept momentarily
     * REVIEW - FIX - TEST - CONFIRM
     */
    protected PropertiesMap loadMap(String fileName, String prefix)
    {

	String mapElemHost = prefix + ".map.host.";
	String mapElemPort = prefix + ".map.port.";
	String mapElemkey = prefix + ".map.api_key.";
	String elemHost;
	PropertiesMap map = new PropertiesMap();

	int i = 0;
	do
	{
	    elemHost = getString(fileName, mapElemHost + i);
	    if (elemHost != null)
	    {
		final Integer elemPort = getInt(fileName, mapElemPort + i);
		final String elemKey = getString(fileName, mapElemkey + i);
		map.getMap().put(elemHost, new HashMap<String, String>(2)
		{
		    {
			put("port", elemPort.toString());
			put("api_key", elemKey);
		    }
		});
	    }
	    i++;
	} while (elemHost != null);

	return map;
    }

    /**
     * 
     * TODO - REVIEW - TEST - FIX - CONFIRM
     * 
     * load properties to map
     * for example
     * prefix - "alchemy" ,
     * var - "map" List,
     * suffix -"host,port,api_key" suffix - key for properties
     * 
     * @param prefix
     * @param var
     * @param suffix
     * @return ProppertiesMap
     *         public PropertiesMap loadDynamicMap(String prefix, String var,
     *         List<String> suffix) {
     *         loadProperties(propertyFileName);
     *         PropertiesMap globalMap = new PropertiesMap();
     *         HashMap<String, String> prop = new HashMap<String, String>();
     *         String check = null;
     *         do {
     *         check = getString(propertyFileName, prefix + "." + var + "." +
     *         suffix.get(0));
     *         if (check != null) {
     *         for (int x = 0; x < suffix.size(); x++) {
     *         prop.put(suffix.get(x), getString(propertyFileName, prefix + "."
     *         + var + "." + suffix.get(x)));
     *         }
     * 
     *         }
     *         if (check != null) {
     *         globalMap.getMap().put(prefix, prop);
     *         }
     *         } while (check != null);
     *         if (globalMap.getMap().isEmpty()) {
     *         LOGGER.warning("Can't find MAP elements with keys egual
     *         '" + prefix + "." + var + "." + suffix);
     *         }
     *         return globalMap;
     *         }
     */

    public Configuration getConfiguration(String propFileName)
    {
	return loadConfigProperties(propFileName);
    }

    /**
     * Get a int associated with the given configuration key.
     * 
     * @param fileName
     * @param key
     * @return
     */
    public int getInt(String fileName, String key)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    try
	    {
		return configuration.getInt(key);
	    } catch (ConversionException cex)
	    {
		LOGGER.info("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
	    }
	} else
	{
	    LOGGER.info("The properties file was not loaded: " + fileName);
	}
	return 0;
    }

    public int getInt(String key)
    {
	return getInt(this.propertyFileName, key);
    }

    public int getInt(String fileName, String key, int defaultValue)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    try
	    {
		return configuration.getInt(key, defaultValue);
	    } catch (ConversionException cex)
	    {
		LOGGER.warning("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
		cex.printStackTrace();
	    }
	} else
	{
	    LOGGER.warning("The properties file was not loaded: " + fileName);
	}
	return defaultValue;
    }

    public int getInt(String key, int defaultValue)
    {
	return getInt(this.propertyFileName, key, defaultValue);
    }

    /**
     * Get a long associated with the given configuration key.
     * 
     * @param fileName
     * @param key
     * @return
     */
    public Long getLong(String fileName, String key)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    try
	    {
		return configuration.getLong(key);
	    } catch (ConversionException cex)
	    {
		LOGGER.warning("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
		cex.printStackTrace();
	    }
	} else
	{
	    LOGGER.warning("The properties file was not loaded: " + fileName);
	}
	return null;
    }

    public Long getLong(String key)
    {
	return getLong(this.propertyFileName, key);

    }

    public Long getLong(String fileName, String key, Long defaultValue)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    try
	    {
		return configuration.getLong(key, defaultValue);
	    } catch (ConversionException cex)
	    {
		LOGGER.info("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
		cex.printStackTrace();
	    }
	} else
	{
	    LOGGER.warning("The properties file was not loaded: " + fileName);
	}
	return defaultValue;
    }

    public Long getLong(String key, Long defaultValue)
    {
	return getLong(this.propertyFileName, key, defaultValue);

    }

    /**
     * Get a short associated with the given configuration key.
     * 
     * @param fileName
     * @param key
     * @return
     */
    public Short getShort(String fileName, String key)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    try
	    {
		return configuration.getShort(key);
	    } catch (ConversionException cex)
	    {
		LOGGER.warning("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
		cex.printStackTrace();
	    }
	} else
	{
	    LOGGER.warning("The properties file was not loaded: " + fileName);
	}
	return null;
    }

    public Short getShort(String key)
    {
	return getShort(this.propertyFileName, key);

    }

    public Short getShort(String fileName, String key, Short defaultValue)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    try
	    {
		return configuration.getShort(key, defaultValue);
	    } catch (ConversionException cex)
	    {
		LOGGER.warning("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
		cex.printStackTrace();
	    }
	} else
	{
	    LOGGER.warning("The properties file was not loaded: " + fileName);
	}
	return defaultValue;
    }

    public Short getShort(String key, Short defaultValue)
    {
	return getShort(this.propertyFileName, key, defaultValue);

    }

    /**
     * Get a byte associated with the given configuration key.
     * 
     * @param fileName
     * @param key
     * @return
     */
    public Byte getByte(String fileName, String key)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    try
	    {
		return configuration.getByte(key);
	    } catch (ConversionException cex)
	    {
		LOGGER.warning("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
		cex.printStackTrace();
	    }
	} else
	{
	    LOGGER.warning("The properties file was not loaded: " + fileName);
	}
	return null;
    }

    public Byte getByte(String key)
    {
	return getByte(this.propertyFileName, key);
    }

    public Byte getByte(String fileName, String key, Byte defaultValue)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    try
	    {
		return configuration.getByte(key, defaultValue);
	    } catch (ConversionException cex)
	    {
		LOGGER.info("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
		cex.printStackTrace();
	    }
	} else
	{
	    LOGGER.warning("The properties file was not loaded: " + fileName);
	}
	return defaultValue;
    }

    public Byte getByte(String key, Byte defaultValue)
    {
	return getByte(this.propertyFileName, key, defaultValue);
    }

    /**
     * Get a double associated with the given configuration key.
     * 
     * @param fileName
     * @param key
     * @return
     */
    public Double getDouble(String fileName, String key)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    try
	    {
		return configuration.getDouble(key);
	    } catch (ConversionException cex)
	    {
		LOGGER.warning("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
		cex.printStackTrace();
	    }
	} else
	{
	    LOGGER.info("The properties file was not loaded: " + fileName);
	}
	return null;
    }

    public Double getDouble(String key)
    {
	return getDouble(this.propertyFileName, key);

    }

    public Double getDouble(String fileName, String key, Double defaultValue)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    try
	    {
		return configuration.getDouble(key, defaultValue);
	    } catch (ConversionException cex)
	    {
		LOGGER.warning("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
		cex.printStackTrace();
	    }
	} else
	{
	    LOGGER.warning("The properties file was not loaded: " + fileName);
	}
	return defaultValue;
    }

    public Double getDouble(String key, Double defaultValue)
    {
	return getDouble(this.propertyFileName, key, defaultValue);

    }

    /**
     * Get a float associated with the given configuration key.
     * 
     * @param fileName
     * @param key
     * @return
     */
    public Float getFloat(String fileName, String key)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    try
	    {
		return configuration.getFloat(key);
	    } catch (ConversionException cex)
	    {
		LOGGER.warning("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
		cex.printStackTrace();
	    }
	} else
	{
	    LOGGER.warning("The properties file was not loaded: " + fileName);
	}
	return null;
    }

    public Float getFloat(String key)
    {
	return getFloat(this.propertyFileName, key);

    }

    public Float getFloat(String fileName, String key, Float defaultValue)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    try
	    {
		return configuration.getFloat(key, defaultValue);
	    } catch (ConversionException cex)
	    {
		LOGGER.warning("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
		cex.printStackTrace();
	    }
	} else
	{
	    LOGGER.warning("The properties file was not loaded: " + fileName);
	}
	return defaultValue;
    }

    public Float getFloat(String key, Float defaultValue)
    {
	return getFloat(this.propertyFileName, key, defaultValue);

    }

    /**
     * Get a boolean associated with the given configuration key.
     * 
     * @param fileName
     * @param key
     * @return
     */
    public Boolean getBoolean(String fileName, String key)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    try
	    {
		return configuration.getBoolean(key);
	    } catch (ConversionException cex)
	    {
		LOGGER.warning("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
		cex.printStackTrace();
	    }
	} else
	{
	    LOGGER.warning("The properties file was not loaded: " + fileName);
	}
	return null;
    }

    public Boolean getBoolean(String key)
    {
	return getBoolean(this.propertyFileName, key);

    }

    public Boolean getBoolean(String fileName, String key, Boolean defaultValue)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    try
	    {
		return configuration.getBoolean(key, defaultValue);
	    } catch (ConversionException cex)
	    {
		LOGGER.warning("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
		cex.printStackTrace();
	    }
	} else
	{
	    LOGGER.warning("The properties file was not loaded: " + fileName);
	}
	return defaultValue;
    }

    public Boolean getBoolean(String key, Boolean defaultValue)
    {
	return getBoolean(this.propertyFileName, key, defaultValue);

    }

    public String getString(String key)
    {
	return getString(this.propertyFileName, key);

    }

    /**
     * Get a string associated with the given configuration key.
     * 
     * @param fileName
     * @param key
     * @return
     */
    public String getString(String fileName, String key)
    {
	return this.getString(fileName, key, null);
    }

    public String getString(String fileName, String key, String defaultValue)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    try
	    {
		return configuration.getString(key, defaultValue);
	    } catch (ConversionException cex)
	    {
		LOGGER.warning("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
		cex.printStackTrace();
	    }
	} else
	{
	    LOGGER.warning("The properties file was not loaded: " + fileName);
	}
	return defaultValue;
    }

    public String getStringOrDefault(String key, String defaultValue)
    {
	return getString(this.propertyFileName, key, defaultValue);
    }

    /**
     * Get an array of typed objects associated with the given configuration
     * key. If the key doesn't map to an existing object, an empty list is
     * returned.
     * 
     * @param fileName
     * @param key
     * @param cls
     * @return
     */
    public Object getArray(String fileName, String key, Class<?> cls)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    try
	    {
		return configuration.getArray(cls, key);
	    } catch (ConversionException cex)
	    {
		LOGGER.warning("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
		cex.printStackTrace();
	    }
	} else
	{
	    LOGGER.warning("The properties file was not loaded: " + fileName);
	}
	return null;
    }

    public Object getArray(String key, Class<?> cls)
    {

	return getArray(this.propertyFileName, key, cls);

    }

    public Object getArray(String fileName, String key, Class<?> cls, Object defaultValue)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    try
	    {
		return configuration.getArray(cls, key, defaultValue);
	    } catch (ConversionException cex)
	    {
		LOGGER.warning("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
		cex.printStackTrace();
	    }
	} else
	{
	    LOGGER.warning("The properties file was not loaded: " + fileName);
	}
	return defaultValue;
    }

    public Object getArray(String key, Class<?> cls, Object defaultValue)
    {
	return getArray(this.propertyFileName, key, cls, defaultValue);
    }

    /**
     * Get a collection of typed objects associated with the given configuration
     * key using the values in the specified default collection if the key does
     * not map to an existing object. This method is similar to
     * {@code getList()}, however, it allows specifying a target collection.
     * Results are added to this collection. This is useful if the data
     * retrieved should be added to a specific kind of collection, e.g. a set to
     * remove duplicates.
     * 
     * @param fileName
     * @param cls
     * @param key
     * @param target
     * @return
     */
    public <T> Collection<T> getCollection(String fileName, Class<T> cls, String key, Collection<T> target)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    try
	    {
		return configuration.getCollection(cls, key, target);
	    } catch (ConversionException cex)
	    {
		LOGGER.warning("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
		cex.printStackTrace();
	    }
	} else
	{
	    LOGGER.warning("The properties file was not loaded: " + fileName);
	}
	return null;
    }

    public <T> Collection<T> getCollection(Class<T> cls, String key, Collection<T> target)
    {
	return getCollection(this.propertyFileName, cls, key, target);
    }

    public <T> Collection<T> getCollection(String fileName, Class<T> cls, String key, Collection<T> target, Collection<T> defaultValue)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    try
	    {
		return configuration.getCollection(cls, key, target, defaultValue);
	    } catch (ConversionException cex)
	    {
		LOGGER.warning("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
		cex.printStackTrace();
	    }
	} else
	{
	    LOGGER.warning("The properties file was not loaded: " + fileName);
	}
	return defaultValue;
    }

    public <T> Collection<T> getCollection(Class<T> cls, String key, Collection<T> target, Collection<T> defaultValue)
    {
	return getCollection(this.propertyFileName, cls, key, target, defaultValue);
    }

    /**
     * Gets a property from the configuration. This is the most basic get method
     * for retrieving values of properties. In a typical implementation of the
     * {@code Configuration} interface the other get methods (that return
     * specific data types) will internally make use of this method. On this
     * level variable substitution is not yet performed. The returned object is
     * an internal representation of the property value for the passed in key.
     * It is owned by the {@code Configuration} object. So a caller should not
     * modify this object. It cannot be guaranteed that this object will stay
     * constant over time (i.e. further update operations on the configuration
     * may change its internal state).
     * 
     * @param fileName
     * @param key
     * @return
     */
    public Object getProperty(String fileName, String key)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    return configuration.getProperty(key);
	} else
	{
	    LOGGER.warning("The properties file was not loaded: " + fileName);
	}
	return null;
    }

    public Object getProperty(String key)
    {
	return getProperty(this.propertyFileName, key);
    }

    /**
     * Get an array of strings associated with the given configuration key. If
     * the key doesn't map to an existing object an empty array is returned
     * 
     * @param fileName
     * @param key
     * @return
     */
    public List<Object> getList(String fileName, String key)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    try
	    {
		return configuration.getList(key);
	    } catch (ConversionException cex)
	    {
		LOGGER.warning("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
		cex.printStackTrace();
	    }
	} else
	{
	    LOGGER.warning("The properties file was not loaded: " + fileName);
	}
	return null;
    }

    public List<Object> getList(String key)
    {
	return getList(this.propertyFileName, key);
    }

    /**
     * Returns a list of String values, these value should be defined in the
     * same key many time
     * 
     * @param key
     * @return
     */
    public List<String> getListString(String key)
    {
	return getListString(this.propertyFileName, key);
    }

    /**
     * Returns a list of String values, these value should be defined in the
     * same key many time
     * 
     * @param fileName
     * @param key
     * @return
     */
    public List<String> getListString(String fileName, String key)
    {
	if (propertiesMap.containsKey(fileName))
	{
	    try
	    {
		return configuration.getList(String.class, key);
	    } catch (ConversionException cex)
	    {
		LOGGER.warning("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
		cex.printStackTrace();
	    }
	} else
	{
	    LOGGER.warning("The properties file was not loaded: " + fileName);
	}
	return null;
    }

    public List<String> getStringArrayList(String key)
    {
	return getStringList(this.propertyFileName, key);
    }

    public List<String> getStringList(String fileName, String key)
    {

	List<String> strings = new ArrayList<>();
	List zList = getList(fileName, key);
	if (null != zList)
	{
	    for (Object object : zList)
	    {
		strings.add((String) object);
	    }
	}
	return strings;
    }

    /**
     * If the initial returned array is of size = 1, we will split the given result by the DELIM=","
     * 
     * @param key
     * @return
     */
    public List<String> getStringListDelim(String key)
    {
	return this.getStringListDelim(this.propertyFileName, key);
    }

    /**
     * If the initial returned array is of size = 1, we will split the given result by the DELIM=","
     * 
     * @param fileName
     * @param key
     * @return
     */
    public List<String> getStringListDelim(String fileName, String key)
    {
	return Arrays.asList(this.getStringArray(fileName, key));
    }

    /**
     * Calling the getString method and split given result by the DELIM=","
     * 
     * @param key
     * @return
     */
    public String[] getStringArray(String key)
    {
	return this.getStringArray(this.propertyFileName, key);
    }

    /**
     * If the initial returned array is of size = 1, we will split the given result by the DELIM=","
     * 
     * @param fileName
     * @param key
     * @return
     */
    public String[] getStringArray(String fileName, String key)
    {
	try
	{
	    String[] values = this.getPropsInstance(fileName).getStringArray(key);
	    if(null!=values && values.length == 1) values = values[0].split(DELIM);
	    //
	    return values;
	} catch (ConversionException cex)
	{
	    LOGGER.warning("Wrong Property Value Type '" + key + "' in  properties file: '" + fileName + "'");
	    cex.printStackTrace();
	}
	return null;
    }

    public int[] getIntegerArray(String key)
    {
	List<Object> list = getList(key);
	int[] ints = new int[list.size()];

	for (int i = 0; i < list.size(); i++)
	{
	    ints[i] = Integer.parseInt((String) list.get(i));
	}
	return ints;
    }

    /**
     * Get the value of a string property that is stored in encoded form in this
     * configuration using a default {@code ConfigurationDecoder}. This method
     * works like the method with the same name, but it uses a default
     * {@code ConfigurationDecoder} associated with this configuration. It
     * depends on a specific implementation how this default decoder is
     * obtained.
     * 
     * @param fileName
     * @param key
     * @return
     */
    public String getEncodedString(String fileName, String key)
    {
	return this.getPropsInstance(fileName).getEncodedString(key);
    }

    public String getEncodedString(String key)
    {
	return getEncodedString(this.propertyFileName, key);
    }

}