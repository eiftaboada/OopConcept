package game.characters;

public class Ashe extends Character {

    public Ashe() {
        super("Ashe", 350, 150, 150);

        skill1 = "Frost Shot";
        skill2 = "Ice Barrier";
        skill3 = "Enchanted Crystal Arrow";

        level = 1;

        skill1DmgMin = 25;
        skill1DmgMax = 45;

        skill2DmgMin = 80;
        skill2DmgMax = 100;

        skill3DmgMin = 275;
        skill3DmgMax = 325;

        maxSkill1Cost = 0.2f;
        maxSkill2Cost = 0.35f;
        maxSkill3Cost = 0.5f;

        skill2CD = 1;
        skill3CD = 2;
    }

    @Override
    public void Encounter() {
        System.out.println(
                "You picked Ashe..\n\n" +
                        "Elara: Now, Ashe... go. The first Shard is in the deepest canyon, at the citadel of Yoshul."
        );
    }

    // biography 
    @Override
    public void Biography() {

    }

    @Override
    public float Skill1(int manaUsage) {
        setMP(getMP() - manaUsage);
        float damage = randomDmg(skill1DmgMin, skill1DmgMax);
        System.out.println("Player used (" + skill1 + ") Damage: [" + (int) damage + "]");
        return damage;
    }

    @Override
    public float Skill2(int manaUsage) {
        skill2CD = 1;
        setMP(getMP() - manaUsage);
        float damage = randomDmg(skill2DmgMin, skill2DmgMax);
        System.out.println("Player used (" + skill2 + ") Damage: [" + (int) damage + "]");
        return damage;
    }

    @Override
    public float Skill3(int manaUsage) {
        skill3CD = 2;
        setMP(getMP() - manaUsage);
        float damage = randomDmg(skill3DmgMin, skill3DmgMax);
        System.out.println("Player used (" + skill3 + ") Damage: [" + (int) damage + "]");
        return damage;
    }
}
