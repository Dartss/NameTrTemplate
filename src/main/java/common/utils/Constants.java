package common.utils;

/**
 * TODO move to GlobalConstants
 * @author rud
 *
 */
public class Constants {

	public enum Command {start, stop, exit, unrecognized, welcome}

	/*
	 * MIND THAT "FRE" SHOULD BE CHANGED TO "FRA" OR VICE VERSA
	 * 
	 * 
	 */
	public enum NEEDED_LANGUAGE {eng, ara, fra, spa}
	public enum ENTITY_PARSER_API {ALCHEMY,KLANGOO}
	public enum KEYWORD_PARSER_API {ALCHEMY,KLANGOO}
	public enum SENTIMENT_TYPE {positive, negative, neutral}
	public enum TRANSLATION_API {yandex}
	public enum SEARCH_API {google,yandex,bing}
	public enum GEOCODE_API {google}
	public enum CHECK_RESULT {TRUE, FALSE, FAIL}
	public enum CALL_TYPE {RMI, LOCAL}

}
