package game.characters;

public class Noah extends Character {
    public Noah(){
        super("Noah", 400, 20,100);
        super.skill1 = "Terrorist Barrage";
        super.skill2 = "Air Strike";
        super.skill3 = "Dual Wield";

        //level
        super.level = 1;

        //kani min-max dmg per skill
        super.skill1DmgMin = 25;
        super.skill1DmgMax = 45;

        super.skill2DmgMin = 80;
        super.skill2DmgMax = 100;

        super.skill3DmgMin = 400;
        super.skill3DmgMax = 800;

        super.maxSkill1Cost = 0.15f;
        super.maxSkill2Cost = 0.35f;
        super.maxSkill3Cost = 1;

        super.skill2CD = 2;
        super.skill3CD = 4;
    }
  
    @Override
    public void Encounter(){
        String CharacterNoah = "You picked Noah.. \n\nElara: Now, Noah... go. The first Shard is in the deepest canyon, at the citadel of\n" +
                "Yoshul. And I wish you the Best.";

        System.out.println(CharacterNoah);

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
        super.skill2CD = 2;
        setMP(getMP() - manaUsage);
        float damage = randomDmg(skill2DmgMin , skill2DmgMax);
        System.out.println("Player used (" + skill2 + ") Damage: [" +  (int)damage + ']');
        return damage;
    }
    @Override
    public float Skill3(int manaUsage){
        super.skill3CD = 5;
        setMP(getMP() - manaUsage);
        float damage = randomDmg(skill3DmgMin , skill3DmgMax);
        System.out.println("Player used (" + skill3 + ") Damage: [" +  (int)damage + ']');
        return damage;
    }


}
