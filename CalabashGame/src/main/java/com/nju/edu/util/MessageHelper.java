package com.nju.edu.util;

import com.nju.edu.bullet.CalabashBullet;
import com.nju.edu.bullet.MonsterBullet;
import com.nju.edu.control.GameController;
import com.nju.edu.sprite.MonsterOne;
import com.nju.edu.sprite.MonsterThree;
import com.nju.edu.sprite.MonsterTwo;
import com.sun.istack.internal.NotNull;

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
        if ("createCalabash".endsWith(temp)) {
            decodeCalabash(gameController);
            return;
        }
        String[] messages = temp.split(" ");

        for (String message : messages) {
            String[] arr = message.split(",");
            String[] positions = Arrays.copyOfRange(arr, 0, arr.length - 1);
            Message msg = MessageFactory.createMessage(message);
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
     * @param positions
     * @param gameController
     */
    private static void decodeMonsterOne(String[] positions, GameController gameController) {
        // TODO
    }

    /**
     * 传输创建新的葫芦娃的消息
     * @param gameController 主界面
     * @return create new Calabash
     */
    @NotNull
    public static byte[] encodeNewClient(GameController gameController) {
        return "createCalabash".getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 传输移动的消息
     * @param gameController 主界面
     * @return message
     */
    @NotNull
    public static byte[] encodeMove(GameController gameController) {
        int x = gameController.getCalabashOne().getX();
        int y = gameController.getCalabashOne().getY();

        // format: x, y, calabashMove
        return ("" + x + ",y" + ",CalabashMove ").getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 传输射击的消息
     * @param gameController 主界面
     * @return message
     */
    @NotNull
    public static byte[] encodeShoot(GameController gameController) {
        List<CalabashBullet> list = gameController.getCalabashBulletList();
        StringBuilder message = new StringBuilder();
        message.append(list.size()).append(",");

        for (CalabashBullet bullet : list) {
            int x = bullet.getX();
            int y = bullet.getY();
            message.append(x).append(",").append(y).append(",");
        }
        message.append("CalabashBullet ");

        return message.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 传输怪物一的数量消息
     * @param gameController 主界面
     * @return message
     */
    @NotNull
    public static byte[] encodeMonsterOne(GameController gameController) {
        List<MonsterOne> list = gameController.getMonsterOneList();
        StringBuilder message = new StringBuilder();
        message.append(list.size()).append(",");

        for (MonsterOne monsterOne : list) {
            int x = monsterOne.getX();
            int y = monsterOne.getY();
            message.append(x).append(",").append(y).append(",");
        }
        message.append("MonsterOne ");

        return message.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 传输妖怪二的数量消息
     * @param gameController 主界面
     * @return message
     */
    @NotNull
    public static byte[] encodeMonsterTwo(GameController gameController) {
        List<MonsterTwo> list = gameController.getMonsterTwoList();
        StringBuilder message = new StringBuilder();
        message.append(list.size()).append(",");

        for (MonsterTwo monsterTwo : list) {
            int x = monsterTwo.getX();
            int y = monsterTwo.getY();
            message.append(x).append(",").append(y).append(",");
        }
        message.append("MonsterTwo ");

        return message.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 传输妖怪三的数量消息
     * @param gameController 主界面
     * @return message
     */
    @NotNull
    public static byte[] encodeMonsterThree(GameController gameController) {
        // format: i,x1,y1,x2,y2,...,xi,yi,monsterThree
        List<MonsterThree> list = gameController.getMonsterThreeList();
        // 还要返回位置
        StringBuilder message = new StringBuilder();
        message.append(list.size()).append(",");

        for (MonsterThree monsterThree : list) {
            int x = monsterThree.getX();
            int y = monsterThree.getY();
            message.append(x).append(",").append(y).append(",");
        }

        message.append("MonsterThree ");
        return message.toString().getBytes(StandardCharsets.UTF_8);
    }

    @NotNull
    public static byte[] encodeMonsterBullet(GameController gameController) {
        List<MonsterBullet> list = gameController.getMonsterBulletList();
        StringBuilder message = new StringBuilder();
        message.append(list.size()).append(",");

        for (MonsterBullet bullet : list) {
            int x = bullet.getX();
            int y = bullet.getY();
            message.append(x).append(",").append(y).append(",");
        }

        message.append("MonsterBullet ");
        return (message.toString()).getBytes(StandardCharsets.UTF_8);
    }
}
