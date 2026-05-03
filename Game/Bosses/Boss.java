package game.bosses;

public abstract class Boss implements Dialogue_Boss{
    //Attributes 
    protected String bossName;
    private int bossHP;
    private int bossMaxHP;
    protected String fieldEffect;

    //Skill names 
    protected String bossSkill1;
    protected String bossSkill2;
    protected String bossSkill3;
    protected int bossSkill1CD;
    protected int bossSkill2CD;
    protected int bossSkill3CD;
    protected int bossSkillChosen = 0;
  
    protected int medals;

    //base damage
    // kani is the min-max dmg of every skill sa players
    //note that every player skill has different dmg range// so si plr1 lahi ug dmg ni plr2 // plr is = player
    protected float bossSkill1DmgMin;
    protected float bossSkill1DmgMax;

    protected float bossSkill2DmgMin;
    protected float bossSkill2DmgMax;

    protected float bossSkill3DmgMin;
    protected float bossSkill3DmgMax;

    public Boss(String name,float HP) {
        this.bossName = name;

        this.bossHP = (int)HP;
        this.bossMaxHP = (int)HP;

    }

    public int getBossMaxHP(){
        return bossMaxHP;
    }

    public int attack() {

        int choice = (int)(Math.random() * 3) + 1;

        if (choice == 3 && bossSkill3CD == 0) {
            bossSkillChosen = 3;
            return (int) bossSkill3();
        }

        if (choice == 2 && bossSkill2CD == 0) {
            bossSkillChosen = 2;
            return (int) bossSkill2();
        }

        bossSkillChosen = 1;
        return (int) bossSkill1();
    }

    public void setBossHP(int bossHP){
        this.bossHP = bossHP;
    }
    public int getBossHP(){
        return bossHP;
    }
    public int getMedals(){
        return medals;
    }

    //damage
    public float randomDmg(float min, float max) {
        return min + (float)(Math.random() * ((max - min) + 1));
    }

    //Skills
    public abstract float bossSkill1();
    public abstract float bossSkill2();
    public abstract float bossSkill3();
    //Dialogue
    public abstract void Encounter();
    public abstract void Won();
    public abstract void Defeated();
}
