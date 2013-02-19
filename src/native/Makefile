all: main example

main:
	gcc -o libsd-daemon-java.so -shared -fPIC \
		-I/usr/include/systemd -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux \
		-lsystemd-daemon -L/usr/lib org_linjian_sd_daemon_java_NativeUtil.c

example: main

clean:
	rm -rvf libsd-daemon-java.so
