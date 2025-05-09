public class MultidimensionalArrays {
    public static void main(String[] args) {
        int numberOfStudents = 10;
        int numberOfQuizzes = 5;
        double[][] scores = new double[numberOfStudents][numberOfQuizzes];

        //3rd student 2nd quiz
        scores[2][1] = 98;

        int quiz;
        for (int student = 0; student < numberOfStudents; student ++){
            for (quiz = 0; quiz < numberOfQuizzes - 1; quiz ++){
                System.out.print(scores[student][quiz] + ", ");
            }
            System.out.println(scores[student][quiz]);
        }

        System.out.println();
        for (int i = 0; i < numberOfStudents; i++){
            for (int j = 0; j < numberOfQuizzes-1; j++){
                System.out.print(scores[i][j] + ", ");
            }
            System.out.println(scores[i][4]);
        }
    }
}