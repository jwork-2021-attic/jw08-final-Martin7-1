package com.nju.edu.client;

import com.nju.edu.control.GameController;
import com.nju.edu.screen.GameScreen;
import com.nju.edu.util.GameState;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author Zyi
 */
public class Client {

    private static final String HOST_NAME = "localhost";
    private static final int PORT = 8080;
    private GameScreen gameScreen;
    private GameController gameController;

    private void startClient() throws IOException {
        InetSocketAddress hostAddress = new InetSocketAddress(HOST_NAME, PORT);
        SocketChannel clientChannel = SocketChannel.open(hostAddress);
        clientChannel.configureBlocking(false);

        gameScreen = new GameScreen("Calabash Game", Color.WHITE);
        gameController = new GameController(30);
        gameScreen.add(gameController);
        gameScreen.setVisible(true);
        gameController.setFocusable(true);
        gameController.requestFocus();
        gameController.start();

        System.out.println("Client... started");

        // 发送消息到服务器端
        send();
        while (GameController.STATE == GameState.RUNNING) {
            read();
        }

        clientChannel.close();
    }

    private void send() {
        // 需要输送到服务器端的位置
        // 当前葫芦娃的位置
    }

    private void read() {
        // 需要从服务器端读取的数据:
        // 小怪的位置和子弹的位置
    }

    public static void main(String[] args) {
        try {
            new Client().startClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
