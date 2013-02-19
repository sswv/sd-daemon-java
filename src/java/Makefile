all: main example

main:
	# sd-daemon-java classes
	javac org/linjian/sd_daemon_java/NativeUtil.java
	javac org/linjian/sd_daemon_java/SdSocketUtil.java
	# sd-daemon-java jar
	jar cf sd-daemon-java.jar org/linjian/sd_daemon_java/*.class

example: main
	# Example for ServerSocket
	javac org/linjian/sd_daemon_java/example/EchoServer.java
	javac org/linjian/sd_daemon_java/example/EchoServerNew.java
	# Example for ServerSocketChannel (nio)
	javac org/linjian/sd_daemon_java/example/TCPProtocol.java
	javac org/linjian/sd_daemon_java/example/EchoSelectorProtocol.java
	javac org/linjian/sd_daemon_java/example/EchoSelectorServer.java
	javac org/linjian/sd_daemon_java/example/EchoSelectorServerNew.java

clean:
	rm -rvf org/linjian/sd_daemon_java/*.class
	rm -rvf org/linjian/sd_daemon_java/example/*.class
	rm -rvf sd-daemon-java.jar
