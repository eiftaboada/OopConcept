package game.characters;

public class Noah extends Character {

    public Noah() {
        super("Noah", 400, 20, 100);

        skill1 = "Terrorist Barrage";
        skill2 = "Air Strike";
        skill3 = "Dual Wield";

        level = 1;

        skill1DmgMin = 25;
        skill1DmgMax = 45;

        skill2DmgMin = 80;
        skill2DmgMax = 100;

        skill3DmgMin = 400;
        skill3DmgMax = 800;

        maxSkill1Cost = 0.15f;
        maxSkill2Cost = 0.35f;
        maxSkill3Cost = 1f;

        skill2CD = 2;
        skill3CD = 4;
    }

    // ilisanan pani ug unsa jud ila i sulti kay wapata kakhibaw unsa jud ila specific story ngano ga away ni sila ahahhhha
    @Override
    public void Encounter() {
        System.out.println(
                "You picked Noah..\n\n" +
                        "Elara: Now, Noah... go. The first Shard is in the deepest canyon, at the citadel of Yoshul."
        );
    }

    // short story nis character gais, gi abstract ni siyaa so need mag specific implement each  character
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
