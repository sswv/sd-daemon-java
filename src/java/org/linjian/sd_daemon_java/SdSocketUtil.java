/**
 * Licensed under the GNU Lesser General Public License
 * Copyright (c) 2012-2013
 * Jian Lin <http://linjian.org>
 */

package org.linjian.sd_daemon_java;

import org.linjian.sd_daemon_java.NativeUtil;
import java.net.ServerSocket;
import java.net.SocketImpl;
import java.net.SocketAddress;
import java.io.FileDescriptor;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;

public class SdSocketUtil
{

	public static final int SD_LISTEN_FDS_START = 3;

	public static int initListenPID()
	{
		int pid = NativeUtil.getpid();
		NativeUtil.setenv("LISTEN_PID", Integer.toString(pid), 1);
		return pid;
	}

	public static void writePIDFile(String filename)
	{
		try {
			FileWriter fw = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fw);
			out.write(Integer.toString(NativeUtil.getpid()));
			out.newLine();
			out.close();
		} catch (IOException e) {
			// Do nothing
		}
	}

	public static int sdListenFds(boolean unset_environment)
	{
		int n = NativeUtil.sd_listen_fds(unset_environment ? 1 : 0);
		return n;
	}

	public static void prepareServerSocket(ServerSocket svrSoc)
		throws SecurityException, ReflectiveOperationException
	{
		prepareServerSocketSetFd(svrSoc);
		prepareServerSocketBindStream(svrSoc);
	}

	private static void prepareServerSocketSetFd(ServerSocket svrSoc)
		throws SecurityException, ReflectiveOperationException
	{
		Field f1, f2, f3;

		f1 = ServerSocket.class.getDeclaredField("impl");
		f1.setAccessible(true);
		SocketImpl impl = (SocketImpl)f1.get(svrSoc);

		f2 = SocketImpl.class.getDeclaredField("fd");
		f2.setAccessible(true);

		FileDescriptor newFd = new FileDescriptor();
		f3 = FileDescriptor.class.getDeclaredField("fd");
		f3.setAccessible(true);
		f3.setInt(newFd, SD_LISTEN_FDS_START);

		f2.set(impl, newFd);
	}

	private static void prepareServerSocketBindStream(ServerSocket svrSoc)
		throws SecurityException, ReflectiveOperationException
	{
		Field f1, f2, f3, f4;
		Class<?> classAPS = Class.forName("java.net.AbstractPlainSocketImpl");

		f1 = ServerSocket.class.getDeclaredField("impl");
		f1.setAccessible(true);
		SocketImpl impl = (SocketImpl)f1.get(svrSoc);

		f2 = classAPS.getDeclaredField("stream");
		f2.setAccessible(true);
		f2.setBoolean(impl, Boolean.TRUE);

		f3 = ServerSocket.class.getDeclaredField("bound");
		f3.setAccessible(true);
		f3.setBoolean(svrSoc, Boolean.TRUE);

		f4 = ServerSocket.class.getDeclaredField("created");
		f4.setAccessible(true);
		f4.setBoolean(svrSoc, Boolean.TRUE);
	}

	public static ServerSocketChannel openBindServerSocketChannel()
		throws SecurityException, ReflectiveOperationException
	{
		return openBindServerSocketChannel(null);
	}

	public static ServerSocketChannel openBindServerSocketChannel(SocketAddress addr)
		throws SecurityException, ReflectiveOperationException
	{
		Field f1, f2;
		Constructor c1;

		Class<?> classSSCI = Class.forName("sun.nio.ch.ServerSocketChannelImpl");
		c1 = classSSCI.getDeclaredConstructor(SelectorProvider.class, 
			FileDescriptor.class, boolean.class);
		c1.setAccessible(true);

		FileDescriptor newFd = new FileDescriptor();
		f1 = FileDescriptor.class.getDeclaredField("fd");
		f1.setAccessible(true);
		f1.setInt(newFd, SD_LISTEN_FDS_START);

		SelectorProvider sp = SelectorProvider.provider();
		ServerSocketChannel impl = (ServerSocketChannel)c1.newInstance(sp, newFd, true);

		if (addr != null) {
			f2 = classSSCI.getDeclaredField("localAddress");
			f2.setAccessible(true);
			f2.set(impl, addr);
		}

		return impl;
	}

}
