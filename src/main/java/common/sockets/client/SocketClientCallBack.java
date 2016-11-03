package common.sockets.client;

public interface SocketClientCallBack
{
    /**
     * Method to be called when a response is back from the server
     * 
     * @param string
     * @return
     */
    public String socketResponseCallback(String string);
}
