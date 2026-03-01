class Main{

    public static void main(String[] args) {

        Scanner output = new Scanner(System.in);

        System.out.println("SIMPLE TEXT RPG");

     
        System.out.println("\nChoose Difficulty:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");

        int worldChoice = output.nextInt();

        World world;

        if (worldChoice == 1) {
            world = new Easy();
        } else if (worldChoice == 2) {
            world = new Medium();
        } else {
            world = new Hard();
        }

        world.start();

        System.out.println("\nChoose Character:");
        System.out.println("1. Male");
        System.out.println("2. Female");

        int charChoice = output.nextInt();

        Character player;

        if (charChoice == 1) {
            player = new MaleCharacter();
        } else {
            player = new FemaleCharacter();
        }

        System.out.println("\nCharacter Selected!");
        player.skill();

        Story story = new StoryBg();
        story.introduction();
        story.encounter();
 
        System.out.println("\n--- First Battle ---");
        Enemy mob = new Mobs("Goblin");
        mob.encounter();
 
        System.out.println("You attack!");
        player.skill();
 
        System.out.println("Enemy attacks!");
        int damage = mob.skill();
        System.out.println("You received " + damage + " damage!");
 
        mob.defeat();

        System.out.println("\n--- Final Boss ---");
        Enemy boss = new Boss();
        boss.encounter();

        System.out.println("You use ultimate skill!");
        player.skill();

        System.out.println("Boss attacks!");
        int bossDamage = boss.skill();
        System.out.println("You received " + bossDamage + " damage!");

        boss.defeat();

        story.defeat();

        System.out.println("\n===== GAME OVER =====");

    }
}
