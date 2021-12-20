package com.nju.edu.sprite;

import com.nju.edu.screen.GameScreen;
import com.nju.edu.skill.CDSkill;
import com.nju.edu.skill.MoveSkill;
import com.nju.edu.skill.RecoverSkill;
import com.nju.edu.skill.Skill;
import com.nju.edu.util.ReadImage;
import com.nju.edu.world.World;

import java.awt.image.BufferedImage;

/**
 * 爷爷类
 * @author Zyi
 */
public class GrandFather extends Sprite {

    private static final GrandFather GRAND_FATHER = new GrandFather(World.getWorld(), 0, 320);

    public static GrandFather getInstance() {
        return GRAND_FATHER;
    }

    private GrandFather(World world, int x, int y) {
        super(world, 100, 100, ReadImage.GrandFather);
    }

    /**
     * 给予技能的次数
     */
    private int giveTime = 0;
    private Calabash calabash = Calabash.getInstance();
    private int speed = calabash.getSpeed();

    public void moveUp() {
        if (this.tile.getyPos() - speed >= 0) {
            this.tile.setyPos(tile.getyPos() - speed);
        }
    }

    public void moveDown() {
        if (this.tile.getyPos() + speed <= GameScreen.getHei() - 150) {
            this.tile.setyPos(this.tile.getyPos() + speed);
        }
    }

    public void moveLeft() {
        if (this.tile.getxPos() - speed >= 0) {
            this.tile.setxPos(this.tile.getxPos() - speed);
        }
    }

    public void moveRight() {
        if (this.tile.getxPos() + speed <= GameScreen.getWid() - 150) {
            this.tile.setxPos(this.tile.getxPos() + speed);
        }
    }

    /**
     * 给予葫芦娃一个技能
     */
    public void giveSkill() {
        // 循环给予
        if (giveTime % Skill.SKILL_AMOUNT == 0) {
            System.out.println("give move skill");
            this.calabash.setSkill(new MoveSkill());
        } else if (giveTime % Skill.SKILL_AMOUNT == 1) {
            System.out.println("give cd skill");
            this.calabash.setSkill(new CDSkill());
        } else if (giveTime % Skill.SKILL_AMOUNT == 2) {
            System.out.println("give recover skill");
            this.calabash.setSkill(new RecoverSkill());
        }
        this.giveTime++;
    }

    public void speedUp(boolean isSpeedUp) {
        if (isSpeedUp) {
            this.speed += 5;
        }
    }

    public void clearSkillImpact() {
        if (calabash.haveSkill()) {
            if ("MoveSkill".equals(calabash.getCurSkill().getName()) && this.speed == 15) {
                this.speed -= 5;
            }
        }
    }
}
