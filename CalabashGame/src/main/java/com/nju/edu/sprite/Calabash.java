package com.nju.edu.sprite;

import com.nju.edu.bullet.CalabashBullet;
import com.nju.edu.screen.GameScreen;
import com.nju.edu.skill.Skill;
import com.nju.edu.util.ReadImage;
import com.nju.edu.world.World;

/**
 * @author Zyi
 */
public class Calabash extends Sprite {

    private static final Calabash CALABASH = new Calabash(World.getWorld(), 100, 320);

    public static Calabash getInstance() {
        return CALABASH;
    }

    public Skill skill;
    private boolean isFirstUse = true;
    /**
     * 葫芦娃的血量
     */
    public int HP = 100;
    private int fireInterval = 120;

    private Calabash(World world, int x, int y) {
        super(world, 100, 100, ReadImage.Calabash);
        this.tile.setxPos(x);
        this.tile.setyPos(y);
        this.speed = 10;
    }

    private Calabash(World world, int x, int y, int speed) {
        super(world, 100, 100, ReadImage.Calabash);
        this.tile.setxPos(x);
        this.tile.setyPos(y);
        this.speed = speed;
    }

    public void moveUp() {
        if (this.tile.getyPos() - speed >= 0) {
            this.tile.setyPos(this.tile.getyPos() - speed);
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

    public CalabashBullet calabashFire() {
        CalabashBullet bullet = new CalabashBullet(this.tile.getxPos() + width, this.tile.getyPos() + height / 2);
        return bullet;
    }

    /**
     * 获得当前Calabash的血量
     * @return 血量
     */
    public int getHP() {
        return this.HP;
    }

    /**
     * 受到伤害减少血量
     * @param damage 伤害
     */
    public void decreaseHP(int damage) {
        this.HP -= damage;
    }

    public void recover() {
        if (this.HP + 10 > 100) {
            this.HP = 100;
        } else {
            this.HP += 10;
        }
    }

    public void resetHP() {
        this.HP = 100;
    }

    public boolean haveSkill() {
        return this.skill != null;
    }

    public void useSkill() {
        System.out.println("Use skill: " + this.skill.getName());
        this.skill.start();
    }

    public boolean isFirstUse() {
        return this.isFirstUse;
    }

    public void setFirstUse() {
        isFirstUse = !isFirstUse;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Skill getCurSkill() {
        // 获得当前拥有的技能
        return this.skill;
    }

    public void speedUp(boolean haveSkill) {
        if (haveSkill) {
            // 加速
            this.speed += 5;
        }
    }

    private void speedDown() {
        this.speed -= 5;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void clearSkillImpact() {
        // 根据当前技能来清空技能效果
        if (this.skill != null) {
            if ("MoveSkill".equals(this.skill.getName()) && this.speed == 15) {
                speedDown();
            } else if ("CDSkill".equals(this.skill.getName()) && this.fireInterval == 80) {
                this.fireInterval = 120;
            } else {
                // nothing to do, recover do not need to reset
            }
        }
    }

    public int getFireInterval() {
        return this.fireInterval;
    }

    public void setFireInterval(int fireInterval) {
        this.fireInterval = fireInterval;
    }
}
