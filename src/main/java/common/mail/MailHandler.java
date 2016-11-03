package common.mail;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.mail.EmailException;

public class MailHandler
{
    private MailHelper mailHelper;
    private Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public MailHandler(MailConfig mainConfig) {
	mailHelper = new MailHelper(mainConfig);
    }

    /** send message to one recipient without file. */
    public boolean send(String from, String recipients, String subject, String text)
    {
	try
	    {
		return mailHelper.send(from, recipients, subject, text);
	    } catch (IOException | EmailException e)
	    {
		LOGGER.info("Could not send the message " + e.getMessage());
		return false;
	    }
    }

    /** send message to one recipient with file. */
    public boolean send(String from, String recipients, String subject, String text, File file)
    {
	try
	    {
		return mailHelper.send(from, recipients, subject, text, file);
	    } catch (IOException | EmailException e)
	    {
		LOGGER.info("Could not send the message " + e.getMessage());
		return false;
	    }
    }

    /** send message to many recipient without file. */
    public boolean send(String from, List<String> recipients, String subject, String text)
    {
	try
	    {
		return mailHelper.send(from, recipients, subject, text);
	    } catch (IOException | EmailException e)
	    {
		LOGGER.info("Could not send the message " + e.getMessage());
		return false;
	    }
    }

    /** send message to many recipient with file. */
    public boolean send(String from, List<String> recipient, String subject, String text, File file)
    {
	try
	    {
		return mailHelper.send(from, recipient, subject, text, file);
	    } catch (IOException | EmailException e)
	    {
		LOGGER.info("Could not send the message " + e.getMessage());
		return false;
	    }
    }

    /** send message to many recipient with many file. */
    public boolean send(String from, List<String> recipients, String subject, String text, List<File> file)
    {
	try
	    {
		return mailHelper.send(from, recipients, subject, text, file);
	    } catch (IOException | EmailException e)
	    {
		LOGGER.info("Could not send the message " + e.getMessage());
		return false;
	    }
    }
}
