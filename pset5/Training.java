/**
 * @Author Kelly Lao (99%) and Shane Wattenmaker (1%) - Shane wrote this
 * CS 010 2/28/2025
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;

public class Training {
    HashMap<String, Map<String, Integer>> transitionsMap;
    HashMap<String, Map<String, Integer>> observationsMap; // for every word, map with key of every part of speech
    //value of how many times that POS appears
    int transitionCount;
    int observationCount;
    private HashMap<String, Map<String, Double>> transitionProbabilities;
    HashMap<String, Map<String, Double>> observationProbabilities;

    // training constructor --> An object that stores the values
    public Training(){
        transitionsMap = new HashMap<>();
        observationsMap = new HashMap<>();
        transitionCount = 0;
        observationCount = 0;
        //Hash map storing probabilities
        transitionProbabilities = new HashMap<>();
        observationProbabilities = new HashMap<>();
    }
    // Trainer method takes strings of words and Parts of Speech and matches them
    public void trainer(String wordFile, String codeFile) throws Exception {
        List<String[]> sentences = new ArrayList<>();
        List<String[]> tagsList = new ArrayList<>();
        // reading wordFile
        BufferedReader wordReader = null;
        try {
            wordReader = new BufferedReader(new FileReader(wordFile));
            String line;
            while ((line = wordReader.readLine()) != null) {
                String[] tokens = line.toLowerCase().split(" ");
                sentences.add(tokens);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            wordReader.close();
        }

        // reading codeFile
        BufferedReader codeReader = null;
        try {
            codeReader = new BufferedReader(new FileReader(codeFile));
            String line;
            while ((line = codeReader.readLine()) != null) {
                String[] tokens = line.toLowerCase().split(" ");
                String[] updatedCode = new String[tokens.length + 1];
                updatedCode[0] = "#";
                for (int i = 0; i < tokens.length; i++) {
                    updatedCode[i+1] = tokens[i];
                }
                tagsList.add(updatedCode);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            codeReader.close();
        }

        //iterating through each sentence and set of POS
        for (int j = 0; j < sentences.size(); j++) {
            String[] sentence = sentences.get(j);
            String[] tags = tagsList.get(j);
            //getting each word and POS
            for (int i = 0; i < sentence.length; i++){
                String word = sentence[i];
                String code = tags[i];
                String nextCode = tags[i + 1];
                //Checks if code exists, increments by one or adds a new
                if (!observationsMap.containsKey(nextCode)) {
                    observationsMap.put(nextCode, new HashMap<>());
                    observationsMap.get(nextCode).put(word, 1);
                } else {
                    if (observationsMap.get(nextCode).containsKey(word)) {
                        observationsMap.get(nextCode).put(word, observationsMap.get(nextCode).get(word) + 1);
                    } else {
                        observationsMap.get(nextCode).put(word, 1);
                    }
                }
                //puts in for transitions
                if (i < sentences.size() - 1) {
                    if (!transitionsMap.containsKey(code)) {
                        transitionsMap.put(code, new HashMap<>());
                        transitionsMap.get(code).put(nextCode, 1);
                    } else {
                        if (transitionsMap.get(code).containsKey(nextCode)) {
                            transitionsMap.get(code).put(nextCode, transitionsMap.get(code).get(nextCode) + 1);
                        } else {
                            transitionsMap.get(code).put(nextCode, 1);
                        }
                    }
                }
            }
        }

        normalize();
    }
    //normalizes the counts
    private void normalize() {
        for (String state : transitionsMap.keySet()) {
            Map<String, Integer> transitionCounts = transitionsMap.get(state);

            // Calculate total transitions from this state
            int stateCount = 0;
            for (String nextState : transitionCounts.keySet()) {
                stateCount += transitionCounts.get(nextState);
            }
            //divide number for specified count by total for state, then natural log
            Map<String, Double> probabilities = new HashMap<>();
            for (String nextState : transitionCounts.keySet()) {
                int count = transitionCounts.get(nextState);
                double probability = Math.log((double) count / stateCount);
                probabilities.put(nextState, probability);
            }
            transitionProbabilities.put(state, probabilities);
        }
        for (String POS : observationsMap.keySet()) {
            Map<String, Integer> observationCount = observationsMap.get(POS);

            // Calculate total transitions from this state
            int stateCount = 0;
            for (int occur : observationCount.values()) {
                stateCount += occur;
            }
            //divide number for specified count by total for state, then natural log
            Map<String, Double> probabilities = new HashMap<>();
            for (String code : observationCount.keySet()) {
                int count = observationCount.get(code);
                double probability = Math.log((double) count / stateCount);
                probabilities.put(code, probability);
            }
            observationProbabilities.put(POS, probabilities);
        }
    }
    //getters
    public HashMap<String, Map<String, Double>> getTransitionProbabilities(){return transitionProbabilities;}
    public HashMap<String, Map<String, Double>> getObservationProbabilities(){return observationProbabilities;}

    //main function tests trainer
    public static void main(String[] args) throws Exception {
        Training train = new Training();
        train.trainer("pset5/brown-train-sentences.txt", "pset5/brown-train-tags.txt");
        System.out.println(train.observationsMap);
        System.out.println(train.transitionsMap);
        System.out.println(train.transitionProbabilities);
        System.out.println(train.observationProbabilities);
    }


}