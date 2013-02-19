/**
 * This example (nio) comes from
 * http://cs.ecs.baylor.edu/~donahoo/practical/JavaSockets2/textcode.html
 * Modified by Jian Lin <http://linjian.org> to make it work with sd-daemon-java
 */

package org.linjian.sd_daemon_java.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import org.linjian.sd_daemon_java.example.EchoSelectorProtocol;
import org.linjian.sd_daemon_java.SdSocketUtil;

public class EchoSelectorServerNew {

	private static final int BUFSIZE = 256; // Buffer size (bytes)
	private static final int TIMEOUT = 3000; // Wait timeout (milliseconds)

	public static void main(String[] args) throws IOException {

		SdSocketUtil.writePIDFile("/tmp/EchoSelectorServerNew.pid");
		int listenPID = SdSocketUtil.initListenPID();
		System.out.println("new LISTEN_PID: " + listenPID);

		if (args.length < 1) { // Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Port> ...");
		}

		// Create a selector to multiplex listening sockets and connections
		Selector selector = Selector.open();

		int n = SdSocketUtil.sdListenFds(false);
		if (n > 1) {
			System.err.println("Too many file descriptors received.");
			System.exit(1);
		} else if (n == 1) {
			System.out.println("== New style (sd-daemon-java) ==");

			try {
				// Allow only one port for simplicity
				ServerSocketChannel listnChannel = 
					SdSocketUtil.openBindServerSocketChannel();
				listnChannel.configureBlocking(false); // must be nonblocking to register
				// Register selector with channel. The returned key is ignored
				listnChannel.register(selector, SelectionKey.OP_ACCEPT);
			} catch (Exception e) {
				System.err.println("Exception in new style (sd-daemon-java).");
				e.printStackTrace();
				System.exit(1);
			}

		} else {
			System.out.println("== Old style ==");

		// Create listening socket channel for each port and register selector
		for (String arg : args) {
			ServerSocketChannel listnChannel = ServerSocketChannel.open();
			listnChannel.socket().bind(
					new InetSocketAddress(Integer.parseInt(arg)));
			listnChannel.configureBlocking(false); // must be nonblocking to register
			// Register selector with channel. The returned key is ignored
			listnChannel.register(selector, SelectionKey.OP_ACCEPT);
		}

		}

		// Create a handler that will implement the protocol
		TCPProtocol protocol = new EchoSelectorProtocol(BUFSIZE);

		while (true) { // Run forever, processing available I/O operations
			// Wait for some channel to be ready (or timeout)
			if (selector.select(TIMEOUT) == 0) { // returns # of ready chans
				System.out.println("Waiting for connection ...");
				continue;
			}

			// Get iterator on set of keys with I/O to process
			Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
			while (keyIter.hasNext()) {
				SelectionKey key = keyIter.next(); // Key is bit mask
				// Server socket channel has pending connection requests?
				if (key.isAcceptable()) {
					protocol.handleAccept(key);
					System.out.println("key.isAcceptable: " + key.toString());
				}
				// Client socket channel has pending data?
				if (key.isReadable()) {
					protocol.handleRead(key);
					System.out.println("key.isReadable: " + key.toString());
				}
				// Client socket channel is available for writing and
				// key is valid (i.e., channel not closed)?
				if (key.isValid() && key.isWritable()) {
					protocol.handleWrite(key);
					System.out.println("key.isWritable: " + key.toString());
				}
				keyIter.remove(); // remove from set of selected keys
			}
		}
	}
}
