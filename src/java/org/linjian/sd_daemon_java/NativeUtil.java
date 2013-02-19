/**
 * Licensed under the GNU Lesser General Public License
 * Copyright (c) 2012-2013
 * Jian Lin <http://linjian.org>
 */

package org.linjian.sd_daemon_java;

public class NativeUtil
{

	static {
		System.loadLibrary("sd-daemon-java");
	}

	// Functions from systemd 
	native public static int sd_listen_fds(int unset_environment);
	native public static int sd_booted();

	// Functions from C library
	native public static int getpid();
	native public static String getenv(String name);
	native public static int setenv(String name, String value, int replace);
	native public static int system(String cmd);

}
