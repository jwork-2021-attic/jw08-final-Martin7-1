package com.nju.edu.util;

import com.nju.edu.bullet.CalabashBullet;
import com.nju.edu.bullet.MonsterBullet;
import com.nju.edu.control.GameController;
import com.nju.edu.sprite.MonsterOne;
import com.nju.edu.sprite.MonsterThree;
import com.nju.edu.sprite.MonsterTwo;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @author Zyi
 */
public class MessageHelper {

    private MessageHelper() {

    }

    /**
     * 解析
     * @param bytes 消息字节
     * @param gameController 主界面
     */
    public static void decode(byte[] bytes, GameController gameController) {
        // 将bytes转换成字符串
        String temp = new String(bytes);
        // 判断是不是第一次创建
        if ("createCalabash ".endsWith(temp)) {
            decodeCalabash(gameController);
            return;
        }
        String[] messages = temp.split(" ");

        for (String message : messages) {
            String[] arr = message.split(",");
            System.out.println(arr[arr.length - 1]);
            String[] positions = Arrays.copyOfRange(arr, 0, arr.length - 1);
            Message msg = MessageFactory.createMessage(message);
            if (msg == null) {
                continue;
            }
            decodeMsg(positions, msg, gameController);
        }
    }

    private static void decodeMsg(String[] positions, Message msg, GameController gameController) {
        switch (msg) {
            case Calabash_Move:
                decodeMove(positions, gameController);
                break;
            case Calabash_Shoot:
                decodeCalabashBullet(positions, gameController);
                break;
            case Monster_One:
                decodeMonsterOne(positions, gameController);
                break;
            case Monster_Two:
                decodeMonsterTwo(positions, gameController);
                break;
            case Monster_Three:
                decodeMonsterThree(positions, gameController);
                break;
            case Monster_Shoot:
                decodeMonsterBullet(positions, gameController);
                break;
            default:
        }
    }

    /**
     * 设置新的葫芦娃
     * @param gameController 主界面
     */
    private static void decodeCalabash(GameController gameController) {
        gameController.setNewCalabash();
    }

    /**
     * 将葫芦娃的坐标更新
     * @param pos 坐标
     * @param gameController 主界面
     */
    private static void decodeMove(String[] pos, GameController gameController) {
        gameController.setCalabashTwoPos(pos[0], pos[1]);
    }

    /**
     * 更新葫芦娃的子弹坐标
     * @param positions 坐标
     * @param gameController 主界面
     */
    private static void decodeCalabashBullet(String[] positions, GameController gameController) {
        gameController.decodeCalabashBullet(positions);
    }

    /**
     * 更新妖怪
     * @param positions 位置
     * @param gameController 主界面
     */
    private static void decodeMonsterOne(String[] positions, GameController gameController) {
        gameController.decodeMonster(positions, MonsterOne.class);
    }

    private static void decodeMonsterTwo(String[] positions, GameController gameController) {
        gameController.decodeMonster(positions, MonsterTwo.class);
    }

    private static void decodeMonsterThree(String[] positions, GameController gameController) {
        gameController.decodeMonster(positions, MonsterThree.class);
    }

    private static void decodeMonsterBullet(String[] positions, GameController gameController) {
        gameController.decodeMonsterBullet(positions);
    }

    /**
     * 传输创建新的葫芦娃的消息
     * @param gameController 主界面
     * @return create new Calabash
     */
    public static byte[] encodeNewClient(GameController gameController) {
        return "createCalabash ".getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 根据消息类型来进行编码的传输
     * @param message 消息类型
     * @return 消息
     */
    public static byte[] encode(Message message, String[] pos) {
        byte[] bytes = null;
        switch (message) {
            case Calabash_Move:
                bytes = encodeMove(pos);
                break;
            case Calabash_Shoot:
                bytes = encodeShoot(pos);
                break;
            case Monster_One:
                bytes = encodeMonsterOne(pos);
                break;
            case Monster_Two:
                bytes = encodeMonsterTwo(pos);
                break;
            case Monster_Three:
                bytes = encodeMonsterThree(pos);
                break;
            case Monster_Shoot:
                bytes = encodeMonsterBullet(pos);
                break;
            default:
        }

        return bytes;
    }

    private static byte[] encodeMove(String[] pos) {
        return ("" + pos[0] + "," + pos[1] + ",CalabashMove ").getBytes(StandardCharsets.UTF_8);
    }

    private static byte[] encodeShoot(String[] pos) {
        return ("" + pos[0] + "," + pos[1] + ",CalabashShoot ").getBytes(StandardCharsets.UTF_8);
    }

    private static byte[] encodeMonsterOne(String[] pos) {
        return ("" + pos[0] + "," + pos[1] + ",MonsterOne ").getBytes(StandardCharsets.UTF_8);
    }

    private static byte[] encodeMonsterTwo(String[] pos) {
        return ("" + pos[0] + "," + pos[1] + ",MonsterTwo ").getBytes(StandardCharsets.UTF_8);
    }

    private static byte[] encodeMonsterThree(String[] pos) {
        return ("" + pos[0] + "," + pos[1] + ",MonsterThree ").getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] encodeMonsterBullet(String[] pos) {
        return ("" + pos[0] + "," + pos[1] + ",MonsterShoot ").getBytes(StandardCharsets.UTF_8);
    }
}
