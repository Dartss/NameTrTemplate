package common.sockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import jsmarty.core.common.logging.DefaultLogger;
import jsmarty.core.common.logging.core.Logger;
import jsmarty.core.common.properties.core.DefaultProperties;

/**
 * Client side. Make connection with socket server. Send request to server, and
 * then get a response from server.
 * 
 * @author yev
 *
 */
public class SocketClient {
	private static Logger logger = DefaultLogger.getInstance();
	//
	private int timeOut;
	private String host;
	private int port;
	private SocketClientCallBack socketClientCallBack;

	public SocketClient(String host, int port, int timeOut) throws UnknownHostException, IOException {
		init(host, port, timeOut);
	}

	public SocketClient(String host, int port) throws UnknownHostException, IOException {
		init(host, port, 0);
	}

	public SocketClient(String host, int port, SocketClientCallBack socketClientCallBack)
			throws UnknownHostException, IOException {
		this.socketClientCallBack = socketClientCallBack;
		init(host, port, 0);
	}

	private void init(String host, int port, int timeOut) {
		this.host = host;
		this.port = port;
		this.timeOut = timeOut;
	}

	private Socket connect(String host, int port, int timeOut) {
		Socket zSocket = null;
		try {
			zSocket = new Socket(host, port);
			if (timeOut > 0) {
				zSocket.setSoTimeout(timeOut);
			} else {
				timeOut = DefaultProperties.getDEFAULT_SOCKET_TIMEOUT();
			}
			//
			//
		} catch (UnknownHostException e) {
			logger.error("ERROR:UnknownHostException -> " + host + ":" + port, e);
		} catch (IOException e) {
			logger.error("ERROR:IOException I / O Error creating socket " + host + ":" + port, e);
		}
		//
		return zSocket;
	}

	/**
	 * Send request to server.
	 * 
	 * Make connect, write request into output stream. And returned response
	 * from server.
	 * 
	 * @param text
	 * @return
	 * @throws IOException
	 */
	public String sendRequest(String text) throws IOException {
		Socket zSocket = this.connect(this.host, this.port, this.timeOut);
		InputStream inputStream = zSocket.getInputStream();
		if (null == zSocket || zSocket.isClosed()) {
			logger.debug("Connection is closed");
			logger.debug("Trying to reconnect server:" + this.host + ":" + this.port);
			//
			this.connect(this.host, this.port, this.timeOut);
		}
		// Make connection and initialize streams
		// PrintWriter printWriter = new
		// PrintWriter(this.socket.getOutputStream(), true);
		PrintStream printWriter = new PrintStream(zSocket.getOutputStream(), true, "UTF-8");
		printWriter.println(text);
		printWriter.flush();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String responseServer = "";
		try {
			String reqLine = null;
			/**
			 * Reading all the request's lines returned from the socket server
			 */
			while (null != (reqLine = bufferedReader.readLine())) {
				responseServer = responseServer.concat(reqLine);
			}
		} catch (SocketTimeoutException e) {
			logger.error(
					"ERROR:SocketTimeoutException socket connection time is out, not able to read socket server reply",
					e);
		} finally {
			printWriter.flush();
			printWriter.close();
			bufferedReader.close();
		}
		if (null != this.socketClientCallBack) {
			responseServer = bufferedReader.readLine();
			final String localResponseServer = responseServer;
			new Thread(new Runnable() {
				@Override
				public void run() {
					socketClientCallBack.socketResponseCallback(localResponseServer);
				}
			}).start();
		}
		return responseServer;
	}

	/**
	 * Close client socket connection.
	 */
	private void close(Socket socket) {
		try {
			logger.debug("Closing client socket on [" + this.host + ":" + this.port + "]");
			socket.close();
		} catch (IOException e) {
			logger.error("Error closing client socket connection", e);
		}
	}
}