/**
 *  Creates a search grid for a lost fisherman, where each row,col grid location contains the probability
 *  the fisherman is in that particular grid location.
 *
 * @author Kelly Lao, Dartmouth CS10, Winter 2025
 */

public class SearchAndRescue {
    private double[][] searchGrid; //probabilities for each (row, col) grid location
    private int numberOfRows, numberOfCols; //number of rows and cols


    /**
     * Constructor
     * @param rows number of grid rows
     * @param cols number of grid columns
     */
    public SearchAndRescue(int rows, int cols) {
        numberOfRows = rows;
        numberOfCols = cols;
        searchGrid = new double[rows][cols];
        for (int i = 0; i < rows; i++){
            for (int j= 0; j < cols; j++){
                searchGrid[i][j] = (double) 1/(rows*cols);
            }
        }
    }

    /**
     * Set the probability the person is in the (row, col) grid location.
     * Print error message if row, col location is out of bounds or probability is invalid
     * @param row
     * @param col
     * @param probability probability the fisherman is in this grid (row,col) location
     */

    public void setGridProbability(int row, int col, Double probability) {
        if (row < 0 || row > numberOfRows){
            System.out.println("Invalid row");
        }else if (col < 0 || col > numberOfCols){
            System.out.println("Invalid column");
        }else if (probability < 0 || probability > 1) {
            System.out.println("Invalid probability");
        }else{
            searchGrid[row][col] = probability;
        }
    }

    /**
     * Print the grid location with the highest probability
     * Break ties as you see fit
     */
    public void bestLocation() {
        double bestProbability = searchGrid[0][0]; //keep track of best probability
        int bestRow=0, bestCol=0;
        for (int i = 0; i < numberOfRows; i++){
            for (int j = 0; j < numberOfCols; j++){
                if (searchGrid[i][j] > bestProbability){
                    bestProbability = searchGrid[i][j];
                    bestRow = i;
                    bestCol = j;
                }
            }
        }
        System.out.println("Most likely location: row=" + bestRow + " col=" + bestCol );
    }


    /**
     * Convert grid to a string representation.
     * @return String representation of the search grid
     */
    public String toString() {
        String s = "\t";
        //create header row
        for (int x = 0; x < numberOfCols; x++) {
            s += x + "\t\t";
        }
        s += "\n";
        //create grid
        for (int row = 0; row < numberOfRows; row++) {
            s += row + "\t";
            for (int col = 0; col < numberOfCols; col++) {
                s += String.format("%.3f", searchGrid[row][col]) + "\t"; //format not required!
            }
            s += "\n";
        }
        return s;
    }

    public static void main(String[] args) {
        SearchAndRescue search = new SearchAndRescue(2,4);
        System.out.println("Initial grid");
        System.out.println(search); //calls toString

        System.out.println("Most likely location");
        search.bestLocation();

        System.out.println("Update grid locations");
        search.setGridProbability(1, 5, 0.3); //error, invalid location
        search.setGridProbability(1, 1, 0.3);
        search.setGridProbability(-1,-1,-1.0); //error, invalid location
        search.setGridProbability(1,2, 100.0); //error, invalid probability

        System.out.println("After updates");
        System.out.println(search); //calls toString
        System.out.println("New most likely");
        search.bestLocation();

    }
}