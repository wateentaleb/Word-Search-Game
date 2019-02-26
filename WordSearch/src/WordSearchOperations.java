/*
Name: Wateen Taleb
Student Number: 250 841 825
Date: The 25th of January, 2019
 */



import java.io.IOException;
import java.util.ArrayList;
import java.io.*;


public class WordSearchOperations {

    private String line;
    private Word word;
    private String input = "";
    private String score = "";
    private int grideSize;
    private int maxScore = 0;
    private char[][] letters;



    // create a dictionary to store all the words
    HashChainDictionary dict = new HashChainDictionary(75000);

    //1. create to store all found words found so far
    HashChainDictionary foundWords = new HashChainDictionary(85);



    // constructor
    public WordSearchOperations(String fileName, String wordTextFile) throws IOException, DictionaryException {



        try {
            BufferedReader reader = new BufferedReader(new FileReader(wordTextFile));




            while ((line = reader.readLine()) != null) {
                // now i need to scan the line to find where theres a comma to differentiate the string with the score
                for (int i = 0; i < line.length(); i++) {
                    // its still the word so keep adding it to the string
                    if (Character.isLetter(line.charAt(i))) {
                        input += line.charAt(i);
                    } else if (Character.isDigit(line.charAt(i))) {
                        score += line.charAt(i);
                    }
                }
                // made a new word with our word and score
                word = new Word(input, Integer.parseInt(score));
                // now i need to insert the word into the dictionary
                dict.put(word);

                // need to clear my strings
                input = "";
                score = "";
            } // we finished adding all the words into the dictionary

            reader.close();


        } catch (IOException e) {
            throw new DictionaryException("The file doesn't exist or is invalid");
        }


        // Now reading fileName

            /* the structure of fileName is as follows
             Line (1) : m and n of the square matrix (two-dimensional array)
             Line (2) - (gridSize+1): is each row of the table
             Line (gridSize+2) - (end of line): the words that can be found in the table
            */

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            // first line is the gridSize
            this.grideSize = Integer.parseInt(reader.readLine());
            this.letters = new char[this.grideSize][this.grideSize];

            // putting the words inside the two-dimensional array row by row
            // using a nested for loop
            int k = 0;
            while (((line = reader.readLine()) != null) && k < grideSize) {
                for (int j = 0; j < grideSize; j++) {
                    letters[k][j] = line.charAt(j);
                }
                k++;
            }
            // before closing the fileName, you must read a list of words that be found in game
            // you do that in order to determine the maximum score possible for the game
            // these words should already be in the dictionary by now

            // 1. Find the words that can be matched in our game
            // removed line = reader.readLine()!=null because it skipped the first line

            while (line != null) {
                // 2. get in our HashChainDictionary returns a Word type, then we get the key(score for that)
                Word word = dict.get(line);
                // putting in a hashtable with answers

                maxScore += word.getValue();
                line = reader.readLine();
            }

            // Finally you should create a second dictionary to store the words found by the game SO FAR
            // you can decide the size of thus hash table, the found words list is assumed to be small; a prime number between
            // 70 to 100 should suffice)


            // 2. use method closes the stream and releases any system resources associated with it.
            reader.close();

        } catch (IOException e) {
            throw new DictionaryException("The file doesn't exist or is invalid");
        }


    }


    //Return the grid size (dimension) of the letter grid (e.g. in Figure 3, this would return 6).
    public int getSize() {
        return this.grideSize;
    }

    //Returns the maximum score for the game being played.
    // This is used by the WordSearch class to determine if the game should finish or not.
    public int getMaxScore() {
        return this.maxScore;
    }

    //Returns the letter on row i and column j in the grid, the String letters[i][j].
    public String getLetter(int i, int j) {
        String str = "";
        str += letters[i][j];
        return str;
    }


    // Return the number of words found so far during playtime.
    public int getNumWordsFound() {
        // the words found so far are stored in our foundWord hashtable
        // returning the size of the foundWords hashtable
        return foundWords.size();
    }

    //Given a String line corresponding to a line of actively selected or currently used letters in the game grid,
    // return the ArrayList of containing all words found within line (in no particular order).
    // Inactive/unselected letters on a line are represented by blank spaces.
    // To split a String on multiple blank spaces, use
    //String[] wordStrings = line.split("\\s+");
    //This method should use checkWords as a subroutine. For example, if the String given is (periods indicating blank spaces) ..LNOMADIC,
    // it should return an ArrayList of Word objects containing the the words “NOMAD” and “NOMADIC”.
    // Keep in mind that there might be multiple words separated by (possibly multiple) blank spaces,
    // so make sure you check each one. You can assume that a line of letters is given to you in the correct order.
    // NOTE: This method only is executed by the main program when it detects a line of at least four letters.
    public ArrayList<Word> findWords(String line) {

        // recommended in the lab manual, this java function "split" will split the string into multiple strings whenever it
        // detects the occurrence of spaces
        String[] list = line.split("\\s+");

        // created a string and initializing it to empty
        // Note: when tried to initialize it to null, it caused issues
        String string = "";

        // Creating an array list
        ArrayList<String> array = new ArrayList<>();

        // adding all the elements of teh list string array into our arraylist
        for (int i = 0; i<list.length; i++){
            array.add(list[i]);
        }

        // adding all the string elements of arraylist to str;
        for (String str: array){
            // using concatenation
            string += str;
        }

        // returning the arraylist of object word that is given when passing our string unto checkWords
        return checkWords(string);

    }


    //Given an ArrayList of words, put all the newly found words
    // (i.e. words not existing yet in the found words dictionary) from words into the found word dictionary,
    // and return an ArrayList containing the Word objects for all the newly found words (in no particular order);
    // keep in mind that the ArrayList words will often contain words already found during playtime. Do
    //not handle the DictionaryException inside your method, you may assume the code provided to you will
    // handle the DictionaryException; your code should not actually throw the exception.
    // Tip: When manipulating the word dictionary, you should only need to use put and get operations.
    //For example, if words=[CANDY,FISH] but the found word dictionary already contains “CANDY”,
    // only put “FISH” into the found word dictionary, and the ArrayList returned by the method
    // should only contain the Word object for “FISH”.

    public ArrayList<Word> updateWordList(ArrayList<Word> words) throws DictionaryException {
        ArrayList<Word> list = new ArrayList<Word>();

        for (int i = 0; i < words.size(); i++) {
                 // checking if the word is already inside our dictionary
            if (foundWords.get(words.get(i).getKey()) != null) {
               // then its already there so do nothing
            }
            // the words selected have not been previously selected
            else {
                // so we add it to our dictionary that holds all the words found SO FAR
                foundWords.put(words.get(i));
                // then we are adding the new words not found in our foundWords dictionary and adding it to our ArrayList
                list.add(words.get(i));
            }
        }
        // returning the list of all the new found words
        return list;
    }


    //Given a String string, you will return an ArrayList containing Word objects
    // (in no particular order), each corresponding to valid words in the dictionary that are substrings of string.
    // Remember that all valid words have at least four letters, but no more than seven letters.
    // You will find the String class’s substring method useful here,
    // see https://docs.oracle.com/javase/7/docs/api/java/lang/String.html# substring(int,%20int).
    // For example, if we were given the String “FISHBED”,
    // it should return an ArrayList containing the Word objects from the word dictionary for “FISH” and “FISHBED”.
    // You must check all possible substrings for valid words.

    // hard coding


    public ArrayList<Word> checkWords(String string) {

        // letters cannot be greater than 7 characters, therefore our upperbound is 8 since the second
        // parameter needs to be 8 in order to exclude the 8th character thus making it a 7 letter word
        int max = 8;
        // size of the string given
        int size = string.length();
        // this string will be used to compare
        String substr = "";
        // array list of object words
        ArrayList<Word> words = new ArrayList<>();

        // Initializing counters
        int i,j;
        // this will loop through till i is greater than the size-2, reason being is that
        // we want to only analyze 4 characters meaning, we want to for instance check location[0] and location[3] which is 4 characters long
            for (i = 0; i <= size - 2; i++) {
                for (j = 0; j < 3; j++) {
                    // the second bound of the array will be 3 characters further than the i position which upholds our minimum of 4 characters word
                    int k = 4 + i;
                    // while the difference in the lower bound and upperbound does not exceed the 7 characters
                    while((k-i)<max && (k<=size)){
                        // get the substring at those limits and input it into a substring
                        substr = string.substring(i,k);
                        // then we check if that substring is in the dictionary,
                        // 1. if it is in the dictionary, then we will add the substring into our ArrayList of object Words "words"
                        // 2. its in the dictionary, increment counter and loop
                        if (dict.get(substr)!=null)
                            words.add(dict.get(substr));
                        k++;
                    }
                }
            }
            // returning all possible words in the given string that are in the dictionary AND are atleast 4 characters long
        // and no more than 7 characters long.
        return words;
    }
}