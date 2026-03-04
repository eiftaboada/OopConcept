class Main {

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

      
        Story femaleStory = new FemaleCharacterStory(
                "Born of steel and witchcraft, she was forged as a weapon. But a weapon cannot dream... or can it?",
                "A dark knight stands before you.",
                "You fall. The world keeps turning. So will you."
        );

        Story maleStory = new MaleCharacterStory(
                "From the frozen wastes where his clan was slaughtered, he emerged. Born of vengeance, he is the North's reckoning",
                "A dragon blocks your path.",
                "Darkness takes you. Dawn will give you back."
        );

        Story enemyStory = new EnemyCharacterStory(
                "Born from darkness, the monster seeks chaos.",
                "The enemy challenges the hero.",
                "The enemy fades into shadows."
        );
                                              
        System.out.println("Choose a character:");
        System.out.println("1. Noah");
        System.out.println("2. Pam");
        System.out.println("3. About Enemy");
        int ch = output.nextInt();
        
        System.out.println();
        
        if(ch == 1) {
            maleStory.introduction();
        } else if (ch == 2)  {
            femaleStory.introduction();
        } else if (ch == 3) {
            enemyStory.introduction();
            enemyStory.encounter();
            enemyStory.defeat();
        } else {
            System.out.print("Choose a character.");
        }

        System.out.println("\n--- Final Boss ---");
        Enemy boss = new Boss();
        boss.encounter();

        System.out.println("You use ultimate skill!");
        player.skill();

        System.out.println("Boss attacks!");
        int bossDamage = boss.skill();
        System.out.println("You received " + bossDamage + " damage!");

        boss.defeat();

        System.out.println("\n===== GAME OVER =====");

        output.close();
    }
}

