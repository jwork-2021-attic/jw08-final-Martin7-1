package com.nju.edu.client;

import com.nju.edu.control.GameController;
import com.nju.edu.screen.GameScreen;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @author Zyi
 */
public class Client implements Runnable {

    private static final String HOST_NAME = "localhost";
    private static final int PORT = 8080;

    private void startClient() throws IOException, InterruptedException {
        InetSocketAddress hostAddress = new InetSocketAddress(HOST_NAME, PORT);
        SocketChannel clientChannel = SocketChannel.open(hostAddress);

        System.out.println("Client... started");

        String TheadName = Thread.currentThread().getName();
        // 创建Game Screen
        GameScreen gameScreen = new GameScreen("Calabash Game", 30, Color.WHITE);

        clientChannel.close();
    }

    @Override
    public void run() {
        try {
            startClient();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new Client()).start();
    }
}
