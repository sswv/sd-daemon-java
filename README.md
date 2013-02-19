sd-daemon-java
==============

Enabling systemd-style socket service written in Java

Licensed under the GNU Lesser General Public License
Copyright (c) 2012-2013, Jian Lin <http://linjian.org>

Version 0.1.0 build 20130113

How to use
----------

Please read the example code in "src/java/org/linjian/sd_daemon_java/example", 
diff'ing the "Server" ones with the "ServerNew" ones.

Notice
------

This program is not yet mature. Only a part of functions in ServerSocket and 
ServerSocketChannel are covered, which may contain bugs. The simple examples 
run properly with this program. However, if you want to use it in more 
complicated or more crucial applications, this program should be improved.

Reference
---------

systemd-style socket service in C <http://0pointer.de/blog/projects/socket-activation.html>
