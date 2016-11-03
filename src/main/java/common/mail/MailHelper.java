package common.mail;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class MailHelper
{

    private String host;
    private int port;
    private String user;
    private String password;
    private boolean ssl;
    private boolean asynchronous = true;
    private int maxFailRetries;
    private Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public MailHelper(MailConfig mailConfig) {
	this.host = mailConfig.getHost();
	this.port = mailConfig.getPort();
	this.user = mailConfig.getUser();
	this.password = mailConfig.getPassword();
	this.ssl = mailConfig.isSsl();
	this.asynchronous = mailConfig.isAsynchronous();
	this.maxFailRetries = mailConfig.getMaxFailRetries();
    }

    /**
     * send message to one recipient without file.
     * 
     * @return
     */

    public boolean send(String from, String recipients, String subject, String text) throws IOException, EmailException
    {
	return send(from, Arrays.asList(recipients), subject, text);

    }

    /**
     * send message to one recipient with file.
     * 
     * @return
     */
    public boolean send(String from, String recipients, String subject, String text, File file) throws IOException, EmailException
    {
	return send(from, Arrays.asList(recipients), subject, text, Collections.<File> emptyList());

    }

    /**
     * send message to many recipient without file.
     * 
     * @return
     */

    public boolean send(String from, List<String> recipients, String subject, String text) throws IOException, EmailException
    {
	return send(from, recipients, subject, text, Collections.<File> emptyList());
    }

    /**
     * send message to many recipient with file.
     * 
     * @return
     */

    public boolean send(String from, List<String> recipients, String subject, String text, File file) throws IOException, EmailException
    {
	return send(from, recipients, subject, text, Arrays.asList(file));
    }

    /**
     * configure SMTP connection and send message to many recipient with many
     * file.
     * 
     * @return
     */
    public boolean send(final String from, final List<String> recipients, final String subject, final String text, final List<File> file)
	    throws IOException, EmailException
    {
	try
	{
	    if (this.asynchronous)
	    {

		Thread thread = new Thread(new Runnable()
		{

		    @Override
		    public void run()
		    {
			boolean success = false;
			int nbTries = 0;

			do
			{
			    try
			    {
				nbTries++;
				success = sendMesage(from, recipients, subject, text, file);
			    } catch (EmailException e)
			    {
				LOGGER.severe("recipients=[" + recipients + "]-subject=[" + subject + "]");
				LOGGER.severe(e.getMessage());
			    }
			} while (!success && nbTries <= maxFailRetries);
			LOGGER.info("subject: " + subject);
		    }
		});
		thread.start();
	    } else
	    {
		return sendMesage(from, recipients, subject, text, file);
	    }
	    return true;
	} catch (Exception e)
	{
	    LOGGER.info("Could not send the message in method send() " + e.getMessage());
	    return false;
	}

    }

    private boolean sendMesage(String from, List<String> recipients, String subject, String text, List<File> file) throws EmailException
    {
	try
	{
	    HtmlEmail email = new HtmlEmail();
	    text = text.replaceAll("\\n", "<br>");

	    // configure SMTP connection

	    email.setHostName(host);
	    email.setSmtpPort(port);
	    email.setAuthenticator(new DefaultAuthenticator(user, password));
	    email.setSSLOnConnect(ssl);
	    // set its properties accordingly

	    if (null == from || "".equals(from))
		from = this.user;

	    email.setFrom(from);
	    email.addTo(recipients.toArray(new String[] {}));
	    email.setSubject(subject);
	    email.setHtmlMsg(text);

	    if (file != null)
	    {
		for (int i = 0; i < file.size(); i++)
		{
		    email.attach(file.get(i));
		}
	    }
	    // send it!
	    email.send();
	} catch (Exception e)
	{
	    LOGGER.severe("HOST=[" + host + "]-port=[" + port + "]");
	    LOGGER.severe(e.getMessage());
	    return false;
	}
	return true;
    }

}
