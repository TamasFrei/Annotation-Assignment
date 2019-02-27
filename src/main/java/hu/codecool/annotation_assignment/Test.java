package hu.codecool.annotation_assignment;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class Test {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new MyHandler(new Routes()));
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
