/**
 * This example comes from
 * http://www.cs.uic.edu/~troy/spring05/cs450/sockets/EchoServer.java
 */

package org.linjian.sd_daemon_java.example;

import java.net.*;
import java.io.*;

public class EchoServer extends Thread {
	protected Socket clientSocket;

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(10008);
			System.out.println("Connection Socket Created");
			try {
				while (true) {
					System.out.println("Waiting for Connection");
					new EchoServer(serverSocket.accept());
				}
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port: 10008.");
			System.exit(1);
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				System.err.println("Could not close port: 10008.");
				System.exit(1);
			}
		}
	}

	private EchoServer(Socket clientSoc) {
		clientSocket = clientSoc;
		start();
	}

	public void run() {
		System.out.println("New Communication Thread Started");

		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
					true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			System.out.println ("Session start: " + Thread.currentThread().getName());

			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				System.out.println("Server recv: " + inputLine);
				out.println("Server echo: " + inputLine);

				if (inputLine.equals("Bye."))
					break;
			}

			out.close();
			in.close();
			clientSocket.close();
			System.out.println ("Session end: " + Thread.currentThread().getName());
		} catch (IOException e) {
			System.err.println("Problem with Communication Server");
			System.exit(1);
		}
	}
}
