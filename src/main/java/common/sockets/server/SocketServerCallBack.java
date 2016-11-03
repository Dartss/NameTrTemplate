package common.sockets.server;

import java.net.Socket;

public interface SocketServerCallBack
{
    /**
     * Callback method called by the socket server in order to customize the
     * parsing and process of the request
     * 
     * 
     * @param socket
     *            : Socket client instance
     * @param request
     *            : Socket client request string
     * @return
     */
    public String socketRequestCallback(String request, Socket socket);
}
