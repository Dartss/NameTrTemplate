package common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Command line reader
 */
public class Console {

	public static String readCmd() throws IOException {
		System.out.print("cmd> ");
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		return bufferRead.readLine();
	}

}