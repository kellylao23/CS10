/**
 * @Author Kelly Lao (99%) and Shane Wattenmaker (1%) - Shane wrote this
 * CS 010 2/28/2025
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Viterbi {

    /**
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    //Reads a string or file and returns a list of sentences
    public static List<String[]> getSentences(String fileName) throws IOException {
        ArrayList<String[]> sentences = new ArrayList<>();

        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = in.readLine()) != null) {
            if (!(line.length() == 0)) {
                String[] tokens = line.split(" ");
                sentences.add(tokens);
            }
        }
        return sentences;
    }

    //Implementation of the Viterbi algorithim
    public static List<String> tagSentence(String[] sentence, Map<String, Map<String, Double>> transitions, Map<String, Map<String, Double>> observations, Double penalty){
        List<String> result = new ArrayList<>();
        List<String> currStates = new ArrayList<>();
        Map<String, Double> currScores = new HashMap<>();
        ArrayList<Map<String, String>> backTraceList = new ArrayList<>();
        currStates.add("#");
        currScores.put("#", 0.0);

        //iterates through the list of sentences
        for (int i = 0; i < sentence.length; i++) {

            List<String> nextStates = new ArrayList<>(); // stores next POS from current state POS
            Map<String, Double> nextScores = new HashMap<>(); // stores scores associated with going from current state POS to next state POS
            Map<String, String> backTrace = new HashMap<>();

            // As long as there are current states
            if (!currStates.isEmpty()) {
                for (String currState : currStates) {
                    if (transitions.containsKey(currState)) {
                        Map<String, Double> nextStatesMap = transitions.get(currState); // set to map with all next states and their probabilities
                        // System.out.println("NextStateMap: " + nextStatesMap);
                        for (String nextState : transitions.get(currState).keySet()) {

                            nextStates.add(nextState);
                            Double nextScore = 0.0;

                            // calculate score based on whether observed word is under next state node
                            if (observations.get(nextState).containsKey(sentence[i])) {
                                nextScore = currScores.get(currState) +
                                        transitions.get(currState).get(nextState) +
                                        observations.get(nextState).get(sentence[i]);
                                // System.out.println(nextScore);
                            } else {
                                nextScore = currScores.get(currState) +
                                        transitions.get(currState).get(nextState) - penalty;
                            }

                            if (!nextScores.containsKey(nextState) || nextScores.get(nextState) < nextScore) {
                                nextScores.put(nextState, nextScore);
                                backTrace.put(nextState, currState);
                            }
                        }

                    }
                }
                // System.out.println("CurrScore: " + currScores);
            }
            currStates = nextStates;
            currScores = nextScores;
            backTraceList.add(backTrace);
        }

        //find POS state to trace back from
        String maxState = null;
        for (String key: currScores.keySet()) {
            if (maxState == null) {
                maxState = key;
            } else if (currScores.get(key) > currScores.get(maxState)) {
                maxState = key;
            }
        }

        // adds all estimated POS in order to result list
        // System.out.println("Max score" + max);
        for (int j = backTraceList.size() - 1; j >= 0; j--) {
            result.add(0, maxState);
            maxState = backTraceList.get(j).get(maxState);
        }
        for (int k = 0; k < sentence.length; k++){
            System.out.print(sentence[k] + " ");
        }
        System.out.println();
//        System.out.println("Result: " + result);

        for (int k = 0; k < result.size(); k++){
//            if (result.get(k).equals("#") || result.get(k).equals(".")){
//                continue;
//            }else{
            System.out.print(result.get(k).toUpperCase() + " ");
//            }
        }
        System.out.println();
        System.out.println();
        return result;
    }
    //Test for console implementation
    public static void consoleBasedTest() throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a sentence: ");
        String sentence = in.nextLine();
        String[] tokens = sentence.split(" ");
        Training t = new Training();
        t.trainer("pset5/brown-train-sentences.txt", "pset5/brown-train-tags.txt");
        Map<String, Map<String, Double>> transitions = t.getTransitionProbabilities();
        Map<String, Map<String, Double>> observations = t.getObservationProbabilities();
        tagSentence(tokens, transitions, observations, (double) 100);
        in.close();
    }
    //Checks the values against correct implementation to get correct accuracy
    public static void correctness() throws Exception{
        Integer matches = 0;
        Integer nonMatches = 0;
        Integer sentence = 0;

        Training train = new Training();
        train.trainer("pset5/simple-train-sentences.txt", "pset5/simple-train-tags.txt");
        Map<String, Map<String, Double>> transitions = train.getTransitionProbabilities();
        // System.out.println(transitions);
        Map<String, Map<String, Double>> observations = train.getObservationProbabilities();
        // System.out.println(observations);
        List<String[]> sentences = getSentences("pset5/simple-test-sentences.txt");

        BufferedReader r = null;
        try {
            r = new BufferedReader(new FileReader("pset5/simple-test-tags.txt"));
            String line;
            while ((line = r.readLine()) != null) {
                Integer curr = 0;
                ArrayList<String> correctness = new ArrayList<>();
                List<String> sTagged = (tagSentence(sentences.get(sentence), transitions, observations, (double) 100));
                line = line.toLowerCase();
                String[] tokens = line.split(" ");
                for (int i = 0; i < tokens.length; i++){
                    if (!tokens[i].equals(sTagged.get(curr))){
                        nonMatches++;
                    }else{
                        matches++;
                    }
                    curr++;
                }
                sentence++;
                curr = 0;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            r.close();
        }

        System.out.println("Matches: " + matches + "\nNon-matches: " + nonMatches);
    }

    //Checks for matches in our path arraylist and the test tags.If the tags match at a particular index it increments the number of matches
    public static void correctnessForMatchAndSentence() throws Exception {

        ArrayList<String[]> correctness = new ArrayList<>();
        BufferedReader r = null;
        try {
            r = new BufferedReader(new FileReader("Problem Sets/PS-5/simple-test-tags.txt"));
            String line;
            while ((line = r.readLine()) != null) {
                line = line.toLowerCase();
                String[] tokens = line.split(" ");
                correctness.add(tokens);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            r.close();
        }

        Integer matches = 0;
        Integer notMatches = 0;
        Integer cSentences = 0;
        Integer iSentences = 0;

        Training train = new Training();
        train.trainer("pset5/brown-train-sentences.txt", "pset5/brown-train-tags.txt");
        Map<String, Map<String, Double>> transitions = train.getTransitionProbabilities();
        // System.out.println(transitions);
        Map<String, Map<String, Double>> observations = train.getObservationProbabilities();
        // System.out.println(observations);
        List<String[]> sentences = getSentences("pset5/brown-test-sentences.txt");
        ArrayList<List<String>> tagged = new ArrayList<>();

        for (int i = 0; i < sentences.size(); i++){
            tagged.add(tagSentence(sentences.get(i), transitions, observations, (double) 100));
        }
        boolean sentence = true;
        for (int stringArray = 0; stringArray < correctness.size();){
            for (int stringList = 0; stringList < tagged.size(); stringList++){
                for (int i = 0; i < correctness.get(stringArray).length;){
                    for (int j = 0; j < tagged.get(stringList).size(); j++){
                        if (!correctness.get(stringArray)[i].equals(tagged.get(stringList).get(j))){
                            notMatches ++;
                            sentence = false;
                        }else{
                            matches ++;
                        }
                        i++;
                    }
                }
                if (sentence){
                    cSentences++;
                }else{
                    iSentences++;
                }
                sentence = true;
                stringArray++;
            }
        }
        System.out.println("Matches: " + matches + "\nNon-matches: " + notMatches + "\nCorrect Sentences: " + cSentences + "\nIncorrect Sentences: " + iSentences);
    }
    //Test files
    public static void test(String fileName) throws Exception {
        Training train = new Training();
        train.trainer("pset5/brown-train-sentences.txt", "pset5/brown-train-tags.txt");
        Map<String, Map<String, Double>> transitions = train.getTransitionProbabilities();
        // System.out.println(transitions);
        Map<String, Map<String, Double>> observations = train.getObservationProbabilities();
        // System.out.println(observations);
        List<String[]> sentences = getSentences(fileName);
        for (int i = 0; i < sentences.size(); i++){
            tagSentence(sentences.get(i), transitions, observations, (double) 100);
        }
    }
    //main function for test implementation
    public static void main(String[] args) throws Exception {
        consoleBasedTest();
    }
}