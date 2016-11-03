package common.mail;

import jsmarty.core.common.properties.MailProperties;

public class MailConfig
{

    private String host;
    private int port;
    private String user;
    private String from;
    private String password;
    private boolean ssl;
    private boolean asynchronous = true;
    private int maxFailRetries = 0;

    /** host = Fully qualified domain name the SMTP-service, port = port to smtp
     * server, login = Your Personal Area Mail login,pass = pass for Your
     * Personal Area Mail, ssl = depending on server configuration. Use
     * asynchronous=if you want to send messages asynchronously
     * 
     * @param host
     * @param port
     * @param login
     * @param pass
     * @param ssl */
    public MailConfig(String host, int port, String user, String pass, String from, boolean ssl, boolean asynchronous) {
	this.host = host;
	this.port = port;
	this.from = from;
	this.user = user;
	this.password = pass;
	this.ssl = ssl;
	this.asynchronous = asynchronous;
    }

    public MailConfig(String host, int port, String user, String pass, String from, boolean ssl) {
	this.host = host;
	this.port = port;
	this.from = from;
	this.user = user;
	this.password = pass;
	this.ssl = ssl;
    }

    public MailConfig(){
	this.host = MailProperties.getHOST();
	this.port = MailProperties.getPORT();
	this.from = MailProperties.getSEND_FROM();
	this.user = MailProperties.getUSER();
	this.password = MailProperties.getPASSWORD();
	this.ssl = MailProperties.getUSE_SSL();
    }

    public int getMaxFailRetries()
    {
	return maxFailRetries;
    }

    public void setMaxFailRetries(int maxFailRetries)
    {
	this.maxFailRetries = maxFailRetries;
    }

    public String getHost()
    {
	return host;
    }

    public void setHost(String host)
    {
	this.host = host;
    }

    public int getPort()
    {
	return port;
    }

    public void setPort(int port)
    {
	this.port = port;
    }

    public String getUser()
    {
	return user;
    }

    public void setUser(String user)
    {
	this.user = user;
    }

    public String getPassword()
    {
	return password;
    }

    public void setPassword(String password)
    {
	this.password = password;
    }

    public boolean isSsl()
    {
	return ssl;
    }

    public void setSsl(boolean ssl)
    {
	this.ssl = ssl;
    }

    public boolean isAsynchronous()
    {
	return asynchronous;
    }

    public void setAsynchronous(boolean asynchronous)
    {
	this.asynchronous = asynchronous;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

}
