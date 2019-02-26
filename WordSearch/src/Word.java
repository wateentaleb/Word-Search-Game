/*
Name: Wateen Taleb
Student Number: 250 841 825
Date: The 25th of January, 2019
 */

public class Word {


   private String word; 
   private int score; 


    // A constructor which returns a new Word object with the specified word and its associated score.
    // The String word will be used as the
    // key attribute for every Word object, and integer value score is the value attribute.
    public Word (String word, int score){
    	this.word = word; 
    	this.score = score; 
    }

//Returns the word String stored in a Word object.
public String getKey(){
return word;
}

//Returns the score stored in a Word object.
public int getValue() { 
return score;
}

}
