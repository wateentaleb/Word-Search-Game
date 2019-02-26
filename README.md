# Word Search Game

## Overview
Java program that plays a simplified word search game called Wordy’s Search Game. If you would like to see examples of Word Search games, see https://thewordsearch.com/, and for a general summary please read https://en.wikipedia.org/wiki/ Word_search. In Wordy’s Search Game, you are given a n × n grid of letters, and your goal is to score the maximum number of points by finding all the words.
Words can be found horizontally, vertically, or diagonally. For the sake of convenience, we will refer to a row, column, or diagonal as a line. Words in a line are read in the following way:
+ Horizontal Lines: Left-to-Right;
+ Vertical Lines: Top-to-Bottom;
+ Diagonal Lines: Diagonals that ascend from the left to the right, are read forwards bottom- to-top. Diagonals that descend from left to the right, are read forward top-to-bottom.


<img width="400" alt="title_pic" src="https://user-images.githubusercontent.com/16707828/53451613-3920e400-39ed-11e9-9bcc-38f7b18e436c.png">

The graphical user interface (GUI) allows you to select buttons corresponding to letters, when clicked in a line it will require checking if any new words have been found yet and updating the score by passing along newly found words back to the GUI. The words the game will use are from a text file `words.txt`. It has **56,130 dictinct words**, each with an associated score. The game is finished when all valid words are found, matching the maximum score,


## ScreenShots 

### Finding a Word 
![rightanswer](https://user-images.githubusercontent.com/16707828/53451244-5b663200-39ec-11e9-9fd2-74ff66be464b.gif)

### Incorrect Word 
![wronginput](https://user-images.githubusercontent.com/16707828/53451548-0971dc00-39ed-11e9-9b3d-08f8f2109183.gif)

### Finishing Game 
![finishing game](https://user-images.githubusercontent.com/16707828/53451543-00810a80-39ed-11e9-80ab-e30325a58c1d.gif)

## Constraints 
In Wordy’s Search Game, the following constraints are placed on the words you can select:
+ Valid words have at least four letters but no more than seven letters. For example, words like “cat” and “balderdash” are not valid. Finding a new word increases your total score by the score assigned to the word you found.
+ Words not included in words.txt are deemed invalid, you are not responsible for finding invalid words.
+ If a word exists on a line, it doesn’t appear on that line twice. Words can appear more than once though; these should not increase the score as you already found them before.


## Classes 

### `Class Word`
This class stores the data that each entry of HashChainDictionary will contain. A Word object stores a String word, and its integer score, as explained above. Word objects represent the valid words that can be found in a given word search grid, and the score associated with the word would be the value to add to the total score if (newly) found. In a Word object, the String word and integer value score are the key and value, respectively.

**`Public Methods Implemented`**

**Description:** A constructor which returns a new Word object with the specified word and its associated score. The String word will be used as the key attribute for every Word object, and integer value score is the value attribute.
`````````````
 public Word(String word, int score)
`````````````
**Description:** Returns the word String stored in a Word object.
`````
  public String getKey() 
``````
**Description:** Returns the score stored in a Word object.
````
 public int getValue()
````
---
### `Class HashChainDictionary`
his class implements a dictionary using a hash table with separate chaining.

***Important Specification:*** Recall that the load factor α = n/M where n is the number of elements stored in the hash table, and M is the size of the table.


**`Public Methods Implemented`**

**Description:** Inserts the given **Word** object referenced by **word** into the dictionary. This method must throw a **DictionaryException** if the String stored in the **Word** object (its key) is already in the dictionary.

  ````
public int put(Word word) throws DictionaryException;
   `````
   
   **Description:** A method which returns the Word object with String inputWord as its key.
   It must return null if the word does not exist in the dictionary.

   `````
public Word get(String inputWord)
   ``````
   
   **Description:** Removes the **Word** record with String **inputWord** as its key from the dictionary, and returns it. 
   This method must throw the **NoKeyException**  if the word is not in the dictionary.

   ``````
public Word remove(String inputWord) throws NoKeyException
   ````````
   **Description:** Returns the number of records stored in the hash table (not the size of the table)
   ````````
public int size()
``````````
---
### `Class WordSearchOperations`

This class implements all the support methods needed by Wordy’s Search Game to find new words, store the bank of words, and information about the game such as the grid of letters and the maximum score.



