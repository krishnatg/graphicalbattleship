# graphicalbattleship

Since this implementation uses RMI, the server needs to first be registered with an rmiregistry. The configuration for the same can be modified in line 13 of Server.java

After successful compilation of the src files, the rmiregistry needs to be started with the same port number as mentioned in the Server.java:13 (by default, this is kept as 30097 on localhost).

Once the rmiregistry is listening on the correct port, the server is to be started. After this, the client side can be launched.

Enjoy playing!

- KTG
