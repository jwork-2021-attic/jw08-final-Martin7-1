package com.nju.edu.client;

import com.nju.edu.control.GameController;
import com.nju.edu.screen.GameScreen;
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

    private void startClient() throws IOException {
        InetSocketAddress hostAddress = new InetSocketAddress(HOST_NAME, PORT);
        SocketChannel clientChannel = SocketChannel.open(hostAddress);
        Socket socket = clientChannel.socket();

        System.out.println("Client... started");

        String TheadName = Thread.currentThread().getName();
        System.out.println("thread: " + TheadName + " start!");

        // TODO:向client write信息
        // 需要将葫芦娃的状态写到服务端上
        // 同时需要保证只有一个地方在生成怪物
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        new Thread(new ClientListen(socket, ois)).start();
        new Thread(new ClientSend(socket, oos)).start();

        clientChannel.close();
    }


    public static void main(String[] args) {
        try {
            new Client().startClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientListen implements Runnable {

    private Socket socket;
    private ObjectInputStream ois;

    public ClientListen(Socket socket, ObjectInputStream ois){
        this.socket = socket;
        this.ois = ois;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println(ois.readObject());
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientSend implements Runnable {

    private Socket socket;
    private ObjectOutputStream oos;

    public ClientSend(Socket socket, ObjectOutputStream oos){
        this.socket = socket;
        this.oos = oos;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(new InputStreamReader(System.in));
            //创建窗体对象：调用窗体的构造方法，制作窗体
            GameScreen frame = new GameScreen("Calabash Game", Color.WHITE);
            //创建面板对象：调用面板的构造方法，制作面板
            GameController panel =new GameController(30);
            //调用启动游戏的方法
            panel.start();
            //将面板加入到窗体中
            frame.add(panel);
            //显示窗体 true 显示， false 隐藏
            frame.setVisible(true);
            while (true){
                int score = panel.getScore();
                JSONObject object = new JSONObject();
                object.put("score: ", score);
                oos.writeObject(object);
                oos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
