Interface Story {
    void introduction();
    void encounter();
    void defeat();
}
 
class StoryBg implements Story {
 
    public void introduction() {
        System.out.println("\nLong ago, a dangerous enemy appeared...");
    }
 
    public void encounter() {
        System.out.println("Your adventure starts now!");
    }
 
    public void defeat() {
        System.out.println("The story ends here...");
    }
}

//not final
