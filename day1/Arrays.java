public class Arrays {
    public static void main(String[] args) {
        int numberOfScores = 5;
        double[] scores = new double[numberOfScores];
        scores[0] = 10;
        scores[1] = 3.2;
        scores[2] = 6.5;
        scores[3] = 7.8;
        scores[4] = 8.8;
        System.out.println(scores); // prints a value based on memory address

        //C-style for loop: initialization step, conditional step, increment step
        System.out.print("[");
        for (int i = 0; i < numberOfScores-1; i++){
            System.out.print(scores[i] + ", ");
        }
        System.out.print(scores[numberOfScores-1] + "]");
    }
}
