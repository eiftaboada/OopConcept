package game.characters;

public class Quinn extends Character {

    public Quinn() {
        super("Quinn", 500, 10, 50);

        skill1 = "Thunder Strike";
        skill2 = "Infernal Lotus";
        skill3 = "Spirit Abjuration";

        level = 1;

        skill1DmgMin = 25;
        skill1DmgMax = 45;

        skill2DmgMin = 125;
        skill2DmgMax = 175;

        skill3DmgMin = 250;
        skill3DmgMax = 350;

        maxSkill1Cost = 0.2f;
        maxSkill2Cost = 0.5f;
        maxSkill3Cost = 1f;

        skill2CD = 2;
        skill3CD = 3;
    }

    // ilisanan pani
    @Override
    public void Encounter() {
        System.out.println(
                "You picked Quinn..\n\n" +
                        "Elara: Now, Quinn... go. The first Shard is in the deepest canyon, at the citadel of Yoshul."
        );
    }
    // implement each character
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
        skill2CD = 2;
        setMP(getMP() - manaUsage);
        float damage = randomDmg(skill2DmgMin, skill2DmgMax);
        System.out.println("Player used (" + skill2 + ") Damage: [" + (int) damage + "]");
        return damage;
    }

    @Override
    public float Skill3(int manaUsage) {
        skill3CD = 5;
        setMP(getMP() - manaUsage);
        float damage = randomDmg(skill3DmgMin, skill3DmgMax);
        System.out.println("Player used (" + skill3 + ") Damage: [" + (int) damage + "]");
        return damage;
    }
}
