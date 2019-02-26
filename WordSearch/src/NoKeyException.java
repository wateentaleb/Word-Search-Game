/**
 * No Key Exception
 * ---------------------
 * The exception thrown if the key requested in a remove doesn't exist.
 * @author Daniel Page
 * @date Winter 2019
 */
public class NoKeyException extends Exception {

	/**
	 * Constructor
	 */
	public NoKeyException(String key){
		super("Key " + key + " is not in the dictionary.");
	}
}
