/**
 * GridPosition stores locations of letters in a line.  Used to help fill in lines in the WordSearch class.
 * @author Daniel Page
 * @date Winter 2019
 *
 */
public class GridPosition {
	/**
	 * A row
	 */
	private int x;
	
	/**
	 * A column
	 */
	private int y;
	
	/**
	 * If applicable, a character stored in a String.
	 */
	private String character;
	
	/**
	 * Constructor
	 * @param x - a row
	 * @param y - a column
	 * @param character - a character
	 */
	public GridPosition(int x, int y, String character){
		this.x = x;
		this.y = y;
		this.character = character;
	}
	
	/**
	 * Get row
	 * @return x
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * Get column
	 * @return y
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * Get an associated symbol
	 * @return character as String
	 */
	public String getCharacter(){
		return character;
	}
	
	/**
	 * A toString method
	 * @return String representation of GridPosition
	 */
	public String toString(){
		return "x:" + x + ", y:" + y;
	}
	
}
