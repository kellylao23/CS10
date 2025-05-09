//import java.util.*;
//import java.util.stream.*;
//
///**
// * Answers to Problem 4 of CS10 Practice Final
// *
// * @author Warren Shepard, Dartmouth CS 10, Fall 2023
// */
//
//public class Streams {
//    public static void main(String[] args) {
//
//        // 4.1
//        String[] words = {"four", "five", "six"};
//        List<String> uppercaseWords = Arrays.stream(words)
//                .filter(word -> word.length() > 3 && word.length() < 6)
//                .map(String::toUpperCase)
//                .collect(Collectors.toList());
//
//        System.out.println("4.1: " + uppercaseWords);
//
//        //4.2
//        String[] strings = {"apple", "ion", "iowa"};
//        Double averageLength = Arrays.stream(strings)
//                .filter(s -> s.matches("^[AEIOUaeiou].*"))
//                .mapToInt(String::length)
//                .average()
//                .orElse(0.0);
//
//        System.out.println("4.3: Average length: " + averageLength);
//
//        //4.4
//        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(17, 18, 33, 1, 2, 3, 4, 19, 5, 6, 7, 8));
//        List<Integer> list = numbers.stream()
//                .filter(n -> n % 2 != 0)
//                .sorted()
//                .collect(Collectors.toList());
//
//        System.out.println(list);
//    }
//}
//
