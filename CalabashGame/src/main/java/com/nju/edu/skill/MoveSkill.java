package com.nju.edu.skill;

import com.nju.edu.sprite.Calabash;
import com.nju.edu.sprite.GrandFather;

import java.io.Serializable;

/**
 * 加快葫芦娃的移动能力
 * @author Zyi
 */
public class MoveSkill implements Skill, Serializable {

    private static final long serialVersionUID = 5395347668849596522L;
    private boolean haveSkill = false;
    private final SkillName name = SkillName.MOVE_SKILL;
    private Calabash calabash = Calabash.getInstance();
    private GrandFather grandFather = GrandFather.getInstance();

    @Override
    public boolean isHaveSkill() {
        return this.haveSkill;
    }

    @Override
    public void haveSkill() {
        this.haveSkill = true;
    }

    @Override
    public void start() {
        // 加快移动速度
        this.calabash.speedUp(true);
        this.grandFather.speedUp(true);
    }

    @Override
    public SkillName getName() {
        return this.name;
    }
}
