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

                Story female = new FemaleCharacter("Born of steel and witchcraft, she was forged as a weapon. But a weapon cannot dream... or can it?", 
                                           "A dark knight stands before you.", 
                                           "You fall. The world keeps turning. So will you.");

        Story male = new MaleCharacter("From the frozen wastes where his clan was slaughtered, he emerged. Born of vengeance, he is the North's reckoning",
                                       "A dragon blocks your path.", 
                                       "Darkness takes you. Dawn will give you back.");

        Story enemy = new EnemyCharacterStory("Born from darkness, the monster seeks chaos.",
                                              "The enemy challenges the hero.",
                                              "The enemy fades into shadows.");
        
        System.out.print("--- Character Introduction ---\n");
        female.introduction();

        System.out.print("\n--- Next Character ---\n");
        male.introduction();

        System.out.print("\n--- Enemy Story ---\n");
        enemy.introduction();
        enemy.encounter();
        enemy.defeat();

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

