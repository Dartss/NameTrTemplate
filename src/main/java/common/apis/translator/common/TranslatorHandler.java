package common.apis.translator.common;

import java.util.List;

import jsmarty.core.common.apis.translator.common.model.TranslatorResponse;

public interface TranslatorHandler
{
	void setApiKey(String apiKey);
	TranslatorResponse translate(String text, String langFrom, String langTo);
	TranslatorResponse translate(List<String> textList, String langFrom, String langTo);
}
