/**
 * This example (nio) comes from
 * http://cs.ecs.baylor.edu/~donahoo/practical/JavaSockets2/textcode.html
 */

package org.linjian.sd_daemon_java.example;

import java.nio.channels.SelectionKey;
import java.io.IOException;

public interface TCPProtocol {
	void handleAccept(SelectionKey key) throws IOException;
	void handleRead(SelectionKey key) throws IOException;
	void handleWrite(SelectionKey key) throws IOException;
}
