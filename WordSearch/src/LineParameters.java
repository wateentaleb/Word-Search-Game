/**
 * When a line is detected, some parameters are needed to extract the String associated with that line.  This will store those parameters so later the line can be extracted by the class WordSearch.
 * @author Daniel Page
 * @date Winter 2019
 *
 */
public class LineParameters {
	
	/**
	 * caseValue stores the case for the line, a number between 0 and 3, inclusive.
	 */
	private int caseValue;
	
	/**
	 * A row number
	 */
	private int row;
	
	/**
	 * A column number
	 */
	private int column;
	
	/**
	 * A diagonal corner, if applicable
	 */
	private int diagonal;
	
	/**
	 * Constructor
	 * @param caseValue
	 * @param row
	 * @param column
	 * @param diagonal
	 */
	public LineParameters(int caseValue, int row, int column, int diagonal){
		this.caseValue = caseValue;
		this.row = row;
		this.column = column;
		this.diagonal = diagonal;
	}
	
	/**
	 * Get the caseValue
	 * @return caseValue
	 */
	public int getCaseValue(){
		return caseValue;
	}
	
	/**
	 * Get a row number
	 * @return row
	 */
	public int getRow(){
		return row;
	}
	
	/**
	 * Get a column number
	 * @return column
	 */
	public int getColumn(){
		return column;
	}
	
	/**
	 * Get a diagonal corner value
	 * @return diagonal
	 */
	public int getDiagonal(){
		return diagonal;
	}
	
}
