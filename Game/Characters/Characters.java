package game.characters;

import java.util.*;
import java.util.LinkedList;

public abstract class Character implements Dialogue_Character {
    //Attributes BAIIIII
    private String name;
    private int HP;
    private int maxHP;
    private int MP;
    protected int INIT_maxMP;
    private int maxMP;


    //Skill names ni baiii
    protected String skill1;
    protected String skill2;
    protected String skill3;
    protected int skill2CD;
    protected int skill3CD;



    //levels
    protected int level;
    private int skill1ManaAdd = 1;
    private int skill2ManaAdd = 1;
    private int skill3ManaAdd = 1;

    //base damage
    // kani is the min-max dmg of every skill sa players
    //note that every player skill has different dmg range// so si plr1 lahi ug dmg ni plr2 // plr is = player
    protected float skill1DmgMin;
    protected float skill1DmgMax;

    protected float skill2DmgMin;
    protected float skill2DmgMax;

    protected float skill3DmgMin;
    protected float skill3DmgMax;

    protected float maxSkill1Cost;
    protected float maxSkill2Cost;
    protected float maxSkill3Cost;


    //effects
    private String effectName = "";
    public float effectDamage = 0f;


    public Character(String name, int HP, int MP, int maxMP) {
        this.name = name;

        this.HP = HP;
        this.maxHP = HP;

        this.MP = MP;
        this.maxMP = maxMP;
        INIT_maxMP = maxMP;

        this.skill2CD = 0;
        this.skill3CD = 0;

    }

    public void Cooldown() {
        if (skill2CD > 0) {
            skill2CD--;
        } else {
            skill2CD = 0;
        }

        if (skill3CD > 0) {
            skill3CD--;
        } else {
            skill3CD = 0;
        }
    }


    public int getSkill2CD() {
        return this.skill2CD;
    }

    public int getSkill3CD() {
        return this.skill3CD;
    }


   

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }


    //HP
    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getHP() {
        return HP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    //Mana
    public void setMP(int MP) {
        this.MP = MP;
    }

    public int getMP() {
        return MP;
    }

    public void setMaxMP(int maxMP) {
        this.maxMP = maxMP;
    }

    public int getMaxMP() {
        return maxMP;
    }

    

    //skills max mana cost
    public int getSkill1_MAX_cost() {
        return (int) (getMaxMP() * maxSkill1Cost);
    }

    public int getSkill2_MAX_cost() {
        return (int) (getMaxMP() * maxSkill2Cost);
    }

    public int getSkill3_MAX_cost() {
        return (int) (getMaxMP() * maxSkill3Cost);
    }


    //skills mana cost
    public int getMaxSkill1Cost() {
        if (getSkill1_MAX_cost() < (INIT_maxMP * maxSkill1Cost) + skill1ManaAdd) {
            return getSkill1_MAX_cost();
        }
        return (int) (INIT_maxMP * maxSkill1Cost) + skill1ManaAdd;
    }

    public int getMaxSkill2Cost() {
        if (getSkill2_MAX_cost() < (INIT_maxMP * maxSkill2Cost) + skill2ManaAdd) {
            return getSkill2_MAX_cost();
        }
        return (int) (INIT_maxMP * maxSkill2Cost) + skill2ManaAdd;
    }

    public int getMaxSkill3Cost() {
        if (getSkill3_MAX_cost() < (INIT_maxMP * maxSkill3Cost) + skill3ManaAdd) {
            return getSkill3_MAX_cost();
        }
        return (int) (INIT_maxMP * maxSkill3Cost) + skill3ManaAdd;
    }


    //damage randomizer
    public float randomDmg(float min, float max) {
        return min + (float) (Math.random() * ((max - min) + 1));
    }

   
    // Skills
    public abstract float Skill1(int manaUsage);

    public abstract float Skill2(int manaUsage);

    public abstract float Skill3(int manaUsage);

    //for the story
    @Override
    public abstract void Encounter();

    public float attack(int skill) {

        // ================= COOLDOWN BLOCK =================
        if (skill == 2 && skill2CD > 0) {
            System.out.println("Skill 2 on cooldown!");
            return 0;
        }

        if (skill == 3 && skill3CD > 0) {
            System.out.println("Skill 3 on cooldown!");
            return 0;
        }

        float damage = 0f;
        int random;

        if (effectName.equals("Blind")) {
            random = (int) randomDmg(1f, 5f);
            if (random == 1) {
                System.out.println("Player Attack Miss");
                return 0;
            }
        }

        switch (skill) {
            case 1 -> damage = Skill1(getMaxSkill1Cost());
            case 2 -> damage = Skill2(getMaxSkill2Cost());
            case 3 -> damage = Skill3(getMaxSkill3Cost());
        }


        return damage;
    }


    //take damage
    public void takeDamage(float enemyDamage) {

            enemyDamage += effectDamage;
            setHP(getHP() - (int) enemyDamage);
        


}
