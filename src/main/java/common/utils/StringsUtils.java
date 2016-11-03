package common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class StringsUtils
{
//    private static org.apache.commons.lang3.StringUtils StringUtils;

    public static void main(String[] args){
    	Map<String, String> mp = new HashMap();
    	mp.put("a", "aa");
    	StringsUtils.replace("hanna", mp);
    }
    //
    public static List<String> extractBodyAndUrls(String body) throws Exception
    {
	if (body == null)
	{
	    throw new Exception("ERROR: null body");
	} else if ("".equals(body))
	{
	    throw new Exception("ERROR: empty body");
	}

	List<String> urlsList = new ArrayList<String>();
	List<String> bodyUrlsList = new ArrayList<String>();

	String urlPattern = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
	Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
	Matcher m = p.matcher(body);

	while (m.find())
	{

	    urlsList.add(body.substring(m.start(0), m.end(0)));
	}

	for (String url : urlsList)
	{

	    body = body.replace(url, "");
	}
	bodyUrlsList.add(0, body);

	for (int i = 1; i <= urlsList.size(); i++)
	{

	    bodyUrlsList.add(i, urlsList.get(i - 1));
	}
	return bodyUrlsList;
    }

    public static String minimizedCleanText(String text)
    {
	if (text == null || text.isEmpty())
	{
	    return text;
	}

	List<String> toIgnore = new ArrayList<String>();

	String rtRegex = "RT ([A-Za-z0-9_]+):";
	Pattern rtPattern = Pattern.compile(rtRegex);
	Matcher rtMatcher = rtPattern.matcher(text);
	while (rtMatcher.find())
	{
	    toIgnore.add(text.substring(rtMatcher.start(0), rtMatcher.end(0)));
	}

	String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
	Pattern urlPatterm = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
	Matcher urlMatcher = urlPatterm.matcher(text);
	while (urlMatcher.find())
	{
	    toIgnore.add(text.substring(urlMatcher.start(0), urlMatcher.end(0)));
	}

	String mentionsRegex = "@([A-Za-z0-9_]+)";
	Pattern mentionsPatterm = Pattern.compile(mentionsRegex, Pattern.CASE_INSENSITIVE);
	Matcher mentionsMatcher = mentionsPatterm.matcher(text);
	while (mentionsMatcher.find())
	{
	    toIgnore.add(text.substring(mentionsMatcher.start(0), mentionsMatcher.end(0)));
	}

	for (String ignored : toIgnore)
	{

	    text = text.replace(ignored, "");
	}
	return text;
    }

    /**
     * TODO fix to avoid clearing all arabic characters
     * 
     * @param text
     * @return
     */
    public static String removeSpecialCharacters(String text)
    {
	String utf8text = "";
	try
	{
	    byte[] utf8Bytes = text.getBytes("UTF-8");

	    utf8text = new String(utf8Bytes, "UTF-8");

	} catch (UnsupportedEncodingException e)
	{
	    e.printStackTrace();
	}
	Pattern unicodeOutliers = Pattern.compile("[^\\x00-\\x7F]", Pattern.UNICODE_CASE | Pattern.CANON_EQ | Pattern.CASE_INSENSITIVE);
	Matcher unicodeOutlierMatcher = unicodeOutliers.matcher(utf8text);

	utf8text = unicodeOutlierMatcher.replaceAll(" ");
	return utf8text;
    }

    public static String excludeUtf8Emoticons(String text)
    {
	String regexPattern = "[\uD83C-\uDBFF\uDC00-\uDFFF]+";
	Pattern pattern = Pattern.compile(regexPattern);
	Matcher matcher = pattern.matcher(text);
	List<String> matchList = new ArrayList<String>();
	while (matcher.find())
	{
	    matchList.add(matcher.group());
	}
	for (int i = 0; i < matchList.size(); i++)
	{
	    text = text.replace(matchList.get(i), "");
	}
	return text;
    }

    public static String excludeSpecialCharacters(String text)
    {
	text = text.replace(";", " ");
	text = text.replace(":", " ");
	text = text.replace("/", " ");
	text = text.replace("\\", " ");
	return text;
    }

    public static String asciiToHex(String asciiValue)
    {
	char[] chars = asciiValue.toCharArray();
	StringBuffer hex = new StringBuffer();
	for (int i = 0; i < chars.length; i++)
	{
	    hex.append(Integer.toHexString((int) chars[i]));
	}
	return hex.toString();
    }

    public static String hexToASCII(String hexValue)
    {
	StringBuilder output = new StringBuilder("");
	for (int i = 0; i < hexValue.length(); i += 2)
	{
	    String str = hexValue.substring(i, i + 2);
	    output.append((char) Integer.parseInt(str, 16));
	}
	return output.toString();
    }

    /**
     * Reads an InputStream and returns its contents as a String.
     * Also effects rate control.
     * 
     * @param inputStream
     *            The InputStream to read from.
     * @param encoding
     *            charset, usually you should use UTF-8
     * @return The contents of the InputStream as a String.
     * @throws Exception
     *             on error.
     */
    public static String inputStreamToString(final InputStream inputStream, String encoding) throws Exception
    {
	final StringBuilder outputBuilder = new StringBuilder();

	try
	{
	    String string;
	    if (inputStream != null)
	    {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, encoding));
		while (null != (string = reader.readLine()))
		{
		    // TODO Can we remove this?
		    // Need to strip the Unicode Zero-width Non-breaking Space.
		    // For some reason, the Microsoft AJAX
		    // services prepend this to every response
		    outputBuilder.append(string.replaceAll("\uFEFF", ""));
		}
	    }
	} catch (Exception ex)
	{
	    throw new Exception("Error reading stream.", ex);
	}
	return outputBuilder.toString();
    }

    /**
     * Concatenate elements from the List with separators into a String
     * 
     * @param elems
     * @param separator
     * @return
     */
    public static String concatenate(List<Object> elems, String separator)
    {
	if (elems == null)
	{
	    return "";
	}
	StringBuffer strBuffer = new StringBuffer();
	int size = elems.size();
	for (int i = 0; i < size; i++)
	{
	    strBuffer.append(elems.get(i));
	    strBuffer.append(separator);
	}
	strBuffer.setLength(Math.max(strBuffer.length() - separator.length(), 0));
	return strBuffer.toString();
    }

    public static String excludeHashTagsAndMentions(String text)
    {
	return text.replaceAll("#[^\\s]+", "").replaceAll("@[^\\s]+", "");
    }

    /**
     * Checks if list1 and list2 have at least 1 common item. ignoring casing or
     * not (case sensitive)
     * 
     * @param list1
     * @param list2
     * @param caseSensitive
     * @return
     */
    public static boolean isListsHaveCommonItem(List<String> list1, List<String> list2, boolean caseSensitive)
    {
	for (String string : list1)
	{
	    if (isListContainString(string, list2, caseSensitive))
	    {
		return true;
	    }
	}
	return false;
    }

    /**
     * Checks if list contain a certain string. ignoring casing or not (case
     * sensitive)
     * 
     * @param string
     * @param list
     * @param caseSensitive
     * @return
     */
    public static boolean isListContainString(String string, List<String> list, boolean caseSensitive)
    {
	if (!caseSensitive)
	{
	    string = string.toLowerCase().trim();
	}

	for (String item : list)
	{
	    if (!caseSensitive)
	    {
		item = item.toLowerCase().trim();
	    }
	    if (string.equals(item))
	    {
		return true;
	    }
	}
	return false;
    }

    public static List<String> split(String text)
    {
	String delimiter = ",";
	return split(text, delimiter);
    }

    public static List<String> split(String text, String delimiter)
    {
	if (text != null && delimiter != null)
	{
	    String[] array = StringUtils.split(text, delimiter);
	    //
	    List<String> list = Arrays.asList(array);
	    return list;
	} else
	{
	    return null;
	}

    }

    /**
     * Replaces all occurrences of existing words in the given text
     */
    public static String replace(String text, Map<String, String> replaceBy)
    {
	for (Map.Entry<String, String> entry : replaceBy.entrySet())
	{
	    text = StringUtils.replace(text, entry.getKey(), entry.getValue());
	}
	return text;
    }

    public static String joinList(List<String> lst, String separator)
    {
	return StringUtils.join(lst, separator);
    }
}
