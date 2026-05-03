package game.characters;

import game.characters.Character;

public class Ashe extends Character {
    public Ashe() {
        super("Ashe",350, 150,150);
        super.skill1 = "Frost Shot";
        super.skill2 = "";
        super.skill3 = "Enchanted Crystal Arrow";

        // level
        super.level = 1;

        // sa min-max dmg per skill
        super.skill1DmgMin = 25;
        super.skill1DmgMax = 45;

        super.skill2DmgMin = 80;
        super.skill2DmgMax = 100;

        super.skill3DmgMin = 275;
        super.skill3DmgMax = 325;

        super.maxSkill1Cost = 0.2f;
        super.maxSkill2Cost = 0.35f;
        super.maxSkill3Cost = 0.5f;

        super.skill2CD = 1;
        super.skill3CD = 2;
    }

    @Override
    public void Encounter(){
        String CharacterAshe = "You picked Ashe.. \n\nElara: Now, Ashe... go. The first Shard is in the deepest canyon, at the citadel of\n" +
                "Yoshul. And I wish you the Best.";

        System.out.println(CharacterAshe);

    }

    @Override
    public float Skill1(int manaUsage){
        setMP(getMP() - manaUsage);
        float damage = randomDmg(skill1DmgMin , skill1DmgMax);
        System.out.println("Player used (" + skill1 + ") Damage: [" +  (int)damage + ']');
        return damage;
    }
    @Override
    public float Skill2(int manaUsage){
        super.skill2CD = 1;
        setMP(getMP() - manaUsage);
        float damage = randomDmg(skill2DmgMin , skill2DmgMax);
        System.out.println("Player used (" + skill2 + ") Damage: [" +  (int)damage + ']');
        return damage;
    }
    @Override
    public float Skill3(int manaUsage){
        super.skill3CD = 2;
        setMP(getMP() - manaUsage);
        float damage = randomDmg(skill3DmgMin , skill3DmgMax);
        System.out.println("Player used (" + skill3 + ") Damage: [" +  (int)damage + ']');
        return damage;
    }
}
