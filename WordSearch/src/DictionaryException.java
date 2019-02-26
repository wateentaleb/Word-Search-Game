
/**
 * Dictionary Exception
 * ---------------------
 * The exception thrown if two records in a dictionary share the same exact key.
 * @author Daniel Page
 * @date Winter 2019
 */
public class DictionaryException extends Exception {

	/**
	 * Constructor
	 */
	public DictionaryException(String message){
		super("Two entries have the word: " + message);
	}
}
