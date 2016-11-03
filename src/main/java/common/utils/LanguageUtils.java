package common.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LanguageUtils {

	private static Map<String, String> iso3Iso2Language;

	/**
	 * Retrieve the ISO3 code (3 chars) of the given language name using Locale API
	 * @param langName
	 * @return
	 */
	public static String getCodeForName(String langName) {
		return Locale.forLanguageTag(langName).getISO3Language() ;
	}


	private static void initIso3Iso2Map(){
		iso3Iso2Language = new HashMap<String, String>();
		for (String iso2Language : Locale.getISOLanguages()){
			Locale locale = new Locale(iso2Language);
			iso3Iso2Language.put(locale.getISO3Language(), iso2Language);
		}
	}

	public static String getIso2FromIso3(String iso3Language){
		if(iso3Iso2Language == null){
			initIso3Iso2Map();
		}
		return iso3Iso2Language.get(iso3Language);
	}

}