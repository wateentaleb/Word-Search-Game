/**
 * Dictionary ADT
 * --------------
 * An interface that sets up the primary operations required for a dictionary used for A2.
 * @author Daniel Page
 * @date Winter 2019
 */
public interface DictionaryADT
{
	/**
	 * get the Word object associated with key String inputWord, return null if not in the dictionary.
	 * @param inputWord
	 * @return null if not found, otherwise the Word object with key inputWord.
	 */
	public Word get(String inputWord);

	/**
	 * insert a Word into the dictionary.
	 * @param word - a word that has a string and score associated with it.
	 * @return int, 1 if a collision occurs, 0 otherwise.
	 * @throws DictionaryException - if two keys (words) of two Word objects are the same.
	 */
    public int put(Word word) throws DictionaryException;

    /**
     * remove key-value pair with key (String) inputWord from the dictionary and return the Word object for that record.
     * @param key - a key for the key-value pair
     * @return Word - the Word object associated with the key for this dictionary.
     * @throws NoKeyException - if attempting to remove, there is no record with the key.
     */
    public Word remove(String inputWord) throws NoKeyException;

    /**
     * get the number of key-value pairs currently in the dictionary.
     * @return number of key-value pairs in dictionary.
     */
    public int size();

}