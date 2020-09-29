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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class is a testbench for the CreateWordsSets program.
 *  
 * @author Jim Williams
 * TODO add your name when you add test cases.
 *
 */
public class TestCreateWordSets {

    /**
     * This contains the calls to the various test methods. 
     * Uncomment the method calls and run to execute the test cases.
     * 
     * These test cases are not intended to be comprehensive but
     * provide some examples.  Extend with your own test cases to be
     * comprehensive.
     * 
     * @param args unused
     */
	public static void main(String[] args) {

		//Part 1: Read Dictionary
		testHasVowel();
		testOnlyLetters();
		testReadDictionary();
		
		//Part 2: Filtering Methods
		testLengthFilter();
		testLetterCounts();
		testSameLetters();
		
		//Part 3: Writing Word Sets
		testCreateSets();
	}
	
	/**
	 * Tests the hasVowel method.
	 */
	private static void testHasVowel() {
	    boolean error = false;
	    
		if (!CreateWordSets.hasVowel("abc")) {
		    error = true;
		    System.out.println("testHasVowel 1: abc has a vowel.");
		}
        if (CreateWordSets.hasVowel("bcd")) {
            error = true;
            System.out.println("testHasVowel 2: bcd doesn't have a vowel.");
        }
        if (!CreateWordSets.hasVowel("my")) {
            error = true;
            System.out.println("testHasVowel 3: my has a vowel.");
        }    
        if (CreateWordSets.hasVowel("")) {
            error = true;
            System.out.println("testHasVowel 4: \"\" doesn't have a vowel.");
        }    
        if (!CreateWordSets.hasVowel("yoyo")) {
            error = true;
            System.out.println("testHasVowel 5: yoyo has a vowel.");
        }    
        
        if ( error) {
            System.out.println("testHasVowel failed.");
        } else {
            System.out.println("testHasVowel passed.");
        }
	}
	
	/**
	 * Tests the onlyLetters method.
	 */
	private static void testOnlyLetters() {
        boolean error = false;
        
        if (CreateWordSets.onlyLetters("Abc")) {
            error = true;
            System.out.println("testOnlyLetters 1: Abc has an upper case letter.");
        }
        if (CreateWordSets.onlyLetters("1st")) {
            error = true;
            System.out.println("testOnlyLetters 2: 1st has a digit.");
        }
        if (CreateWordSets.onlyLetters("don't")) {
            error = true;
            System.out.println("testOnlyLetters 3: don't has an '.");
        }    
        if (!CreateWordSets.onlyLetters("happy")) {
            error = true;
            System.out.println("testOnlyLetters 4: happy has only letters.");
        }         
        if (CreateWordSets.onlyLetters("")) {
            error = true;
            System.out.println("testOnlyLetters 5: \"\" has no letters");
        }
        if (CreateWordSets.onlyLetters(" ")) {
            error = true;
            System.out.println("testOnlyLetters 6: \" \" has no letters");
        }
        if (CreateWordSets.onlyLetters("happY")) {
            error = true;
            System.out.println("testOnlyLetters 7: happY has an upper case letter.");
        }
        if ( error) {
            System.out.println("testOnlyLetters failed.");
        } else {
            System.out.println("testOnlyLetters passed.");
        }
    }
	
	/**
     * Tests the readDictionary method.
     */
    private static void testReadDictionary() {
        boolean error = false;
        
        ArrayList<String> dictionary = CreateWordSets.readDictionary("testingDictionary.txt");
        if ( !dictionary.toString().equals(
            "[get, online, fly, manner, instrumentation, matrix, ci, avi, diploma, cabin, fourth]"
            )) {
            error = true;
            System.out.println("testReadDictionary 1: contents: " + dictionary.toString());
        }

        if ( error) {
            System.out.println("testReadDictionary failed.");
        } else {
            System.out.println("testReadDictionary passed.");
        }
    }
    
    /**
     * This tests the lengthFilter method.
     */
    private static void testLengthFilter() {
        boolean error = false;
        String [] list = {"a","ag", "ago", "am", "amp", "ar", "arm", "armor", 
            "gap", "map", "par", "pro", "program", "ram", "rom"};
        ArrayList<String> dictionary = new ArrayList<>(Arrays.asList(list));
        CreateWordSets.lengthFilter(dictionary, 3, 5);
        
        if ( !dictionary.toString().equals(
            "[ago, amp, arm, armor, gap, map, par, pro, ram, rom]")) {
            error = true;
            System.out.println("testLengthFilter 1: contents: " + dictionary.toString());
        }
        
        if ( error) {
            System.out.println("testLengthFilter failed.");
        } else {
            System.out.println("testLengthFilter passed.");
        }        
    }
    
    /**
     * This test letterCounts method.
     */
    private static void testLetterCounts() {
        boolean error = false;
        final int LETTER_COUNT_LENGTH = 'z' - 'a' + 1;
        
        { //Note: using a block, { }, so that the variables within it
          //could also be used in another block within this method.
          // test "ago"
            int[] expected = new int[LETTER_COUNT_LENGTH];
            expected['a' - 'a'] = 1;
            expected['g' - 'a'] = 1;
            expected['o' - 'a'] = 1;

            int[] actual = CreateWordSets.letterCounts("ago");
            for (int i = 0; i < expected.length; i++) {
                if (expected[i] != actual[i]) {
                    error = true;
                    System.out.println(
                        "testLetterCounts 1: for \"ago\" at index " + i + " counts differ.");
                }
            }
            
            expected = new int[LETTER_COUNT_LENGTH];
            expected['b' - 'a'] = 2;
            expected['o' - 'a'] = 1;
            //expected['o' - 'a'] = 2;
            
            actual = CreateWordSets.letterCounts("bob");
            for (int i = 0; i < expected.length; i++) {
                if (expected[i] != actual[i]) {
                    error = true;
                    System.out.println(
                        "testLetterCounts 2: for \"bob\" at index " + i + " counts differ.");
                }
            }
            
            expected = new int[LETTER_COUNT_LENGTH];
            expected['a' - 'a'] = 2;
            expected['g' - 'a'] = 2;
            expected['o' - 'a'] = 2;
            
            actual = CreateWordSets.letterCounts("agoago");
            for (int i = 0; i < expected.length; i++) {
                if (expected[i] != actual[i]) {
                    error = true;
                    System.out.println(
                        "testLetterCounts 2: for \"agoago\" at index " + i + " counts differ.");
                }
            }
            
            expected = new int[LETTER_COUNT_LENGTH];
            expected['b' - 'a'] = 4;
            expected['i' - 'a'] = 2;
            expected['g' - 'a'] = 1;
            expected['r' - 'a'] = 1;
            expected['a' - 'a'] = 2;
            expected['n' - 'a'] = 1;
            expected['y' - 'a'] = 1;
            
            actual = CreateWordSets.letterCounts("bigbrainbaby");
            for (int i = 0; i < expected.length; i++) {
                if (expected[i] != actual[i]) {
                    error = true;
                    System.out.println(
                        "testLetterCounts 2: for \"bigbrainbaby\" at index " + i + " counts differ.");
                }
            }
        }

        if ( error) {
            System.out.println("testLetterCounts failed.");
        } else {
            System.out.println("testLetterCounts passed.");
        }        
    }
    
    /**
     * This test sameLetters method.
     */
    private static void testSameLetters() {
        boolean error = false;
        final int LETTER_COUNT_LENGTH = 'z' - 'a' + 1;
        
        // test "ago" to "ago"
        int[] keyword = new int[LETTER_COUNT_LENGTH];
        keyword['a' - 'a'] = 1;
        keyword['g' - 'a'] = 1;
        keyword['o' - 'a'] = 1;

        int[] checkWord = new int[LETTER_COUNT_LENGTH];
        checkWord['a' - 'a'] = 1;
        checkWord['g' - 'a'] = 1;
        checkWord['o' - 'a'] = 1;

        boolean result = CreateWordSets.sameLetters(keyword, checkWord);
        if (!result) {
            error = true;
            System.out.println("testSameLetters 1: \"ago\" doesn't have same letters as \"ago\".");
        }
        //can you come up with more tests to more thoroughly test?
        //                                                        yuh.

        // test "afoo" to "ago"
        keyword = new int[LETTER_COUNT_LENGTH];
        keyword['a' - 'a'] = 1;
        keyword['f' - 'a'] = 1;
        keyword['o' - 'a'] = 2;
        
        checkWord = new int[LETTER_COUNT_LENGTH];
        checkWord['a' - 'a'] = 1;
        checkWord['g' - 'a'] = 1;
        checkWord['o' - 'a'] = 1;
        
        boolean result2 = CreateWordSets.sameLetters(keyword, checkWord);
        if (result2) {
            error = true;
            System.out.println("testSameLetters 2: \"afoo\" doesn't have same letters as \"ago\".");
        }
        
        // test "aago" to "aag"
        keyword = new int[LETTER_COUNT_LENGTH];
        keyword['a' - 'a'] = 2;
        keyword['g' - 'a'] = 1;
        keyword['o' - 'a'] = 1;
        
        checkWord = new int[LETTER_COUNT_LENGTH];
        checkWord['a' - 'a'] = 2;
        checkWord['g' - 'a'] = 1;
        checkWord['o' - 'a'] = 0;
        
        boolean result3 = CreateWordSets.sameLetters(keyword, checkWord);
        if (!result3) {
            error = true;
            System.out.println("testSameLetters 3: \"aag\" doesn't have same letters as \"aago\".");
        }
        
        // test "aagoo" to "aagoo"
        keyword = new int[LETTER_COUNT_LENGTH];
        keyword['a' - 'a'] = 2;
        keyword['g' - 'a'] = 1;
        keyword['o' - 'a'] = 2;
        
        checkWord = new int[LETTER_COUNT_LENGTH];
        checkWord['a' - 'a'] = 2;
        checkWord['g' - 'a'] = 1;
        checkWord['o' - 'a'] = 2;
        
        boolean result4 = CreateWordSets.sameLetters(keyword, checkWord);
        if (!result4) {
            error = true;
            System.out.println("testSameLetters 4: \"aagoo\" doesn't have same letters as \"aagoo\".");
        }

        if ( error) {
            System.out.println("testSameLetters failed.");
        } else {
            System.out.println("testSameLetters passed.");
        }        
    }   
    
    /*
     * A supporting method for testCreateSets.
     */
    private static String readFile(String filename) {
        String result = "";
        Scanner input = null;
        try {
            input = new Scanner(new File(filename));
            while (input.hasNextLine()) {
                result += input.nextLine().trim() + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if ( input != null) 
                input.close();
        }  
        return result;
    }
    
    /**
     * This tests the createSets method.
     */
    private static void testCreateSets() {
        boolean error = false;
        String [] list = {"know","non", "known", "now", "won", "own", "low", "leo", 
            "who", "whole", "how", "hole"};
        ArrayList<String> dictionary = new ArrayList<>(Arrays.asList(list));

        final String TEST_FILE_NAME = "testCreateSets.txt";
        CreateWordSets.createSets(dictionary, TEST_FILE_NAME, 5, 2);
        
        String fileContents = readFile(TEST_FILE_NAME);
        String expectedFileContents = "known: know known non now own won\nwhole: hole how leo low who whole\n";
        
        if ( !fileContents.equals(expectedFileContents)) {
            error = true;
            System.out.println("Error: unexpected file contents\n" + fileContents);
        }
        
        if ( error) {
            System.out.println("testCreateSets failed.");
        } else {
            System.out.println("testCreateSets passed.");
        }        
    }   
}
