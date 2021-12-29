package com.nju.edu.server;

import com.nju.edu.control.GameController;
import com.nju.edu.screen.GameScreen;
import org.json.JSONObject;

import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Zyi
 */
public class Server {

    private ServerSocketChannel channel;
    private Selector selector;
    private SelectionKey mainKey;

    // private Handler handler;

    private static final int PORT = 8080;
    private static final String ADDRESS = "localhost";
    private int playerNumber = 2;

    public Server() {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void bind() throws IOException {
        channel = ServerSocketChannel.open();
        // bind server socket channel to port
        // 非阻塞模式的设置必须在设置端口前完成
        channel.configureBlocking(false);
        channel.socket().bind(new InetSocketAddress(ADDRESS, PORT));
        // 连接selector
        channel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("server is ready, port = " + PORT);
    }

    public void startServer() throws IOException {
        this.bind();
        while (true) {
            // wait for events
            int nReady = selector.select();
            if (nReady == 0) {
                continue;
            }

            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                if (!key.isValid()) {
                    continue;
                }
                if (key.isAcceptable()) {
                    this.accept(key);
                    System.out.println("用户连接成功");
                }
                if (key.isReadable()) {
                    this.read(key);
                }
                if (key.isWritable()) {
                    this.write(key);
                }
            }
            it.remove();
        }
    }

    /**
     * accept from client connection
     * @param key selector key
     * @throws IOException IO异常
     */
    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);
        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        System.out.println("Connected to: " + remoteAddr);

        /*
         * Register channel with selector for further IO (record it for read/write
         * operations, here we have used read operation)
         */
        channel.register(this.selector, SelectionKey.OP_READ);
    }

    /**
     * read from client
     * @param key selector key
     * @throws IOException IO异常
     */
    private void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        Socket socket = channel.socket();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();

        try {
            int count = channel.read(buffer);
            if (count > 0) {
                ByteArrayInputStream input = new ByteArrayInputStream(buffer.array());
                ObjectInputStream objectInputStream = new ObjectInputStream(input);

                Object obj = objectInputStream.readObject();
                // TODO: decode the object
            } else if (count == -1) {
                System.out.println("客户端已经断开连接");
                socket.close();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("客户端已经断开连接");
            e.printStackTrace();
        } finally {
            socket.close();
        }

        channel.register(this.selector, SelectionKey.OP_WRITE);
    }

    /**
     * write to client
     * @param key selector key
     * @throws IOException IO异常
     */
    private void write(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        Socket socket = channel.socket();


        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        GameScreen gameScreen = new GameScreen("Calabash Game", Color.WHITE);
        GameController gameController = new GameController(30);
        gameScreen.add(gameController);
        gameController.start();
        gameScreen.setVisible(true);

        int score = gameController.getScore();
        JSONObject object = new JSONObject();
        object.put("score:", score);
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();

        // channel.register(this.selector, SelectionKey.OP_READ);
    }

    public static void main(String[] args) {
        try {
            new Server().startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
