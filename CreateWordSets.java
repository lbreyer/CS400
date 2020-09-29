///////////////////////// TOP OF FILE COMMENT BLOCK ////////////////////////////
//
// Title:           descriptive title of the program making use of this file
// Course:          course number, term, and year
//
// Author:          Sahil Srivastava
// Email:           srivastava34@wisc.edu email address
// Lecturer's Name: Marc Renault
//
///////////////////////////////// CITATIONS ////////////////////////////////////
//
// https://cs200-www.cs.wisc.edu/wp/syllabus/#academicintegrity
// Source or Recipient; Description
// Examples:
// Jane Doe; helped me with for loop in reverse method
// https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html; 
//         counting for loop
// John Doe; I helped with switch statement in main method.
//
/////////////////////////////// 80 COLUMNS WIDE ////////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class CreateWordSets {

	/**
	 * Returns whether there is a vowel in the word.
	 * Vowels are the lower case letters: aeiouy
	 * 
	 * @param word The word to check letters.
	 * @return true if at least one letter in word is a vowel,
	 *     false otherwise.
	 */
	public static boolean hasVowel(String word) {
	    if (word.contains("a")) {
	        return true;
	    }
	    
	    if (word.contains("e")) {
	        return true;
	    }
	    
	    if (word.contains("i")) {
	        return true;
	    }
	    
	    if (word.contains("o")) {
	        return true;
	    }
	    
	    if (word.contains("u")) {
	        return true;
	    }
	    
	    if (word.contains("y")) {
	        return true;
	    }
	    
	    return false;
	}

	/**
	 * Returns whether the word parameter has only lower case letters between
	 * 'a' and 'z', inclusive.
	 * 
	 * @param word The word to check for letters.
	 * @return true if the word only contains letters, false otherwise.
	 */
	public static boolean onlyLetters(String word) {
        int i;
        
        if (word.length() == 0) {
            return false;
        }
        
        for (i = 0; i < word.length(); ++i) {
            if (word.charAt(i) < 96 || word.charAt(i) > 123) {
                return false;
            }
        }
	    
	    return true;
	}
	
	/**
	 * Opens and reads a dictionary file returning a list of words.
	 * Each word is checked to see if it contains a vowel (hasVowel) and is
	 * composed only of letters (onlyLetters). If not the word
	 * is discarded.
	 * 
	 * If there is an error reading the file, such as the file cannot be found,
	 * then the following message is shown: 
	 *     Error: Unable to read file <dictionaryFilename>
	 * with <dictionaryFilename> replaced with the parameter value.
	 * 
	 * @param dictionaryFilename The dictionary file to read.
	 * @return An ArrayList of words.
	 */
	public static ArrayList<String> readDictionary(String dictionaryFilename) {
	    File file = new File(dictionaryFilename);
	    ArrayList<String> dictionaryList = new ArrayList<String>();
	    
	    try {
            Scanner input = new Scanner(file);
            while(input.hasNextLine()) {
                String potentialWord = input.nextLine();
                
                if (onlyLetters(potentialWord)) {
                    if (hasVowel(potentialWord)) {
                        dictionaryList.add(potentialWord);
                    }
                }
            }
        } catch (Exception e) {
            System.out.print("Error: Unable to read file " + dictionaryFilename);
        }
	    
	    return dictionaryList;
	}
	
	/**
	 * This removes all the words from the dictionary that are outside the 
	 * length parameters.
	 *     
	 * @param dictionary The dictionary to filter.
	 * @param minLength  The minimum length of words in the dictionary.
	 * @param maxLength  The maximum length of words in the dictionary.
	 */
	public static void lengthFilter( ArrayList<String> dictionary, int minLength, int maxLength) {
	    int i;
	    
	    for (i = 0; i < dictionary.size(); ) {
	        if (dictionary.get(i).length() >= minLength && dictionary.get(i).length() <= maxLength) {
	            ++i;
	        }
	        else {
	            dictionary.remove(i);
	        }
	    }
	}

	/**
	 * Returns an array with the count of each letter in the word. This only
	 * check for the letters from lower case 'a' to 'z' and the 0th index 
	 * in the returned array corresponds to the count of 'a' in the word. 
	 * 
	 * @param word The word to count its letters.
	 * @return The array of counts of each letter in the word.
	 */
	public static int [] letterCounts(String word) {
	    int [] wordCount = new int [26];
	    
	    final int asciiA = 97;
	    
	    int currentChar;
	    int index;
	    for (currentChar = asciiA, index = 0; currentChar < wordCount.length + asciiA; ++currentChar, ++index) {
	        int i;
	        for (i = 0; i < word.length(); ++i) {
	            if (word.charAt(i) == currentChar) {
	                wordCount[index] += 1;
	            }
	        }
	    }
	    return wordCount;
	}
	
	/**
	 * This checks that the check word has no more of any letter than 
	 * the keyword. The counts parameters were created with the letterCounts method.
	 * 
	 * @param keywordLetterCounts The counts of each letter for the keyword.
	 * @param checkWordLetterCounts The counts of each letter for the check word.
	 * @return
	 */
	public static boolean sameLetters(int[]keywordLetterCounts, int[] checkWordLetterCounts) {
		int i;
		int sum = 0;
		for (i = 0; i < keywordLetterCounts.length; ++i) {
		    if (keywordLetterCounts[i] >= checkWordLetterCounts[i]) {
		        sum += 1;
		    }
		}
		
		if (sum == 26) {
		    return true;
		}
	    
	    return false;
	}
	
    /**
     * For each word in the dictionary that has length keywordLength this creates 
     * sets of words that are made of the same or subset of the letters.
     * 
     * Algorithm:
     * For each word in the dictionary having a length equal to the keywordLength
     *     Create a new word set.
     *     Count the number of each letter in the keyword (letterCounts)
     *     For each word in the dictionary that has the same or a subset of the
     *         same letters (sameLetters) then add that word to the word set.
     *     If the word set has at least minWordSetSize words
     *          Sort the word set (If wordSet is an ArrayList of String then
     *              use java.util.Collections.sort(wordSet)).
     *          Write the keyword to the file as the first word on the line,
     *              followed by a : and space.
     *          Then write each word of the word set to the same line of the
     *          file with a space separating each word.
     * 
     * If there is an error writing the file then the following message is shown: 
     *     Error: Unable to write file <wordSetFilename>
     * with <wordSetFilename> replaced with the parameter value.
     *  
     * File Example: 
     * shall: all ash hall has las shall
     * award: ada award draw raw war ward
     * 
     * The first words, shall and award, are the keywords, one word that uses 
     * all the letters. All the other words are made up of a subset (or all) of 
     * the letters. Also see the wordSets.txt file.
     * 
     * @param dictionary The dictionary of words.
     * @param wordSetFilename The name of the file to write the word sets
     * @param keywordLength length of the keyword
     * @param minWordSetSize The minimum number of words necessary in order to
     *        save the word set. 
     */
	public static void createSets(ArrayList<String> dictionary, 
        String wordSetFilename, int keywordLength, int minWordSetSize) {
	    File file = new File(wordSetFilename);
        try {
            PrintWriter writer = new PrintWriter(file);
            int i;
            int minWords = 0;
            ArrayList<String> wordlists = new ArrayList<String>();
            for (i = 0; i < dictionary.size(); i++) {
                if (dictionary.get(i).length() == keywordLength) {
                    int[] dictionarywordCount = letterCounts(dictionary.get(i));
                    int j;
                    for (j = 0; j < dictionary.size(); j++) {
                        int[] wordCount = letterCounts(dictionary.get(j));
                        if (sameLetters(dictionarywordCount, wordCount)) {
                            wordlists.add(dictionary.get(j));
                        }
                    }
                    if (wordlists.size() >= minWordSetSize) {
                        writer.print(dictionary.get(i) + ":");
                        Collections.sort(wordlists);
                        for (String word: wordlists) {
                            writer.print(" " + word);
                        }
                        writer.print("\n");
                    }
                    wordlists.clear();
                }
            }
            
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Unable to write file " + wordSetFilename);
        }
	    
	    return;
	}
	
	/**
	 * This reads in a dictionary and filters the dictionary to contain only
	 * words from 3 to 5 letters long.  Then sets of words to be used in the 
	 * WordDetective program are saved to a file.
	 * 
	 * The source dictionary, google10000english.txt, is from google: 
	 * https://github.com/first20hours/google-10000-english/blob/master/google-10000-english-no-swears.txt
	 *  
	 * @param args unused.
	 */
	public static void main(String[] args) {
        ArrayList<String> dictionary = readDictionary("google10000english.txt");
        lengthFilter(dictionary, 3, 5);
        createSets(dictionary, "wordSets.txt", 5, 6);
	}
}
