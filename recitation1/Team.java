public class Team {
    String mascotName;
    int currentScore;

    public Team(String name){
        mascotName = name;
        currentScore = 0;
    }

    public String getName(){return mascotName;}
    public int getScore(){return currentScore;}

    public void score(){
        currentScore += 2;
    }

    public static void main(String[] args) {
        Team first = new Team("Red");
        Team second = new Team("Blue");
        first.score();
        second.score();
        second.score();
        if (first.getScore() > second.getScore()){
            System.out.println(first.currentScore);
        }else{
            System.out.println(second.currentScore);
        }
    }

}
