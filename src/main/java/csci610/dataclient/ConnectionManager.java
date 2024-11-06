package csci610.dataclient;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * A singleton class, needed to keep the connection persistent across the page
 * movements
 *
 * @author nicka
 */
public class ConnectionManager {

    private static ConnectionManager inst;
    private Socket s;
    private BufferedReader in;
    private PrintWriter out;

    private ConnectionManager() {

    }

    public static ConnectionManager getInstance() {
        if (inst == null) {
            inst = new ConnectionManager();
        }
        return inst;
    }

    public void connect(String host, int port) {

        try {
            if (s == null || s.isClosed()) {
                s = new Socket(host, port);
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                out = new PrintWriter(s.getOutputStream(), true);
            }

        } catch (IOException e) {
            System.out.println("error in connection manager");
            e.printStackTrace();
        }

    }

    public BufferedReader getInputStream() {
        return in;
    }

    public PrintWriter getOutputStream() {
        return out;
    }

    public void close() {
        try {

            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (s != null) {
                s.close();
            }

        } catch (IOException e) {
            System.out.println("error closing socket");
            e.printStackTrace();
        }
    }
}
