abstract class Story {
    protected String introduction;
    protected String encounter;
    protected String defeat;

    public Story(String introduction, String encounter, String defeat) {
        this.introduction = introduction;
        this.encounter = encounter;
        this.defeat = defeat;
    }

    public void introduction() {
        System.out.println(introduction);
    }

    public void encounter() {
        System.out.println(encounter);
    }

    public void defeat() {
        System.out.println(defeat);
    }
}

abstract class CharacterBackgroundStory extends Story {
    public CharacterBackgroundStory(String introduction, String encounter, String defeat) {
        super(introduction, encounter, defeat);
    }
}

class FemaleCharacter extends CharacterBackgroundStory {
    public FemaleCharacter(String introduction, String encounter, String defeat) {
        super(introduction, encounter, defeat);
    }
}

class MaleCharacter extends CharacterBackgroundStory {
    public MaleCharacter(String introduction, String encounter, String defeat) {
        super(introduction, encounter, defeat);
    }
}

class EnemyCharacterStory extends Story {
    public EnemyCharacterStory(String introduction, String encounter, String defeat) {
        super(introduction, encounter, defeat);
    }
}
