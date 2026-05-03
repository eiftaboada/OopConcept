package game.bosses;

public class Raven extends Boss implements Dialogue_Boss {

    public Raven(int level) {
        float multiplier = 1 + (float) level / 4;
        super("Raven (The Reaper of Souls)", 300);
        super.bossSkill1 = "Vein slash";
        super.bossSkill2 = "Darkness Obliterate";
        super.bossSkill3 = "Death's Grasp";

        super.fieldEffect = "You will bleed to death by his sword!";
        //kani min-max dmg per skill
        super.bossSkill1DmgMin = 15 * multiplier;
        super.bossSkill1DmgMax = 35 * multiplier;

        super.bossSkill2DmgMin = 25 * multiplier;
        super.bossSkill2DmgMax = 45 * multiplier;

        super.bossSkill3DmgMin = 35 * multiplier;
        super.bossSkill3DmgMax = 60 * multiplier;

        super.medals = 5000;


        super.bossSkill1CD = 0;
        super.bossSkill2CD = 2;
        super.bossSkill3CD = 3;
    }


    @Override
    public float bossSkill1(){
        float damage = randomDmg(bossSkill1DmgMin, bossSkill1DmgMax);
        System.out.println(bossName + " used (" + bossSkill1 + ") Damage: [" +  (int)damage + ']');
        return damage;
    }
    @Override
    public float bossSkill2(){
        bossSkill2CD = 2;
        float damage = randomDmg(bossSkill2DmgMin, bossSkill2DmgMax);
        System.out.println(bossName + " used (" + bossSkill2 + ") Damage: [" +  (int)damage + ']');
        return damage;
    }
    @Override
    public float bossSkill3(){
        bossSkill3CD = 3;
        float damage = randomDmg(bossSkill3DmgMin, bossSkill3DmgMax);
        System.out.println(bossName + " used (" + bossSkill3 + ") Damage: [" +  (int)damage + ']');
        return damage;
    }
}
