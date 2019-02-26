/**
 * LineData stores data for a line, this is used by the WordSearch class.
 * @author Daniel Page
 * @date Winter 2019
 */
public class LineData{
	/**
	 * A String associated with a line.
	 */
	private String lineText;
	
	/**
	 * The positions associated with a line in the grid/board.
	 */
	private GridPosition[] positions;
	
	/**
	 * The number of positions in the row.
	 */
	private int size;
	
	/**
	 * Constructor
	 * @param length - size of grid/board.
	 */
	public LineData(int length){
		lineText=null;
		positions = new GridPosition[length];
		size=0;
	}
	
	/**
	 * To add a new position in the grid/board for a line.
	 * @param x - a row number
	 * @param y - a column number
	 */
	public void addPosition(int x, int y){
		GridPosition pos = new GridPosition(x,y,"");
		positions[size] = pos;
		size++;
	}
	
	/**
	 * Same as addPosition, but adds them in reverse.  Used by one of the line cases.
	 * @param x - a row number
	 * @param y - a column number
	 */
	public void addPositionRev(int x, int y){
		GridPosition pos = new GridPosition(x,y,"");
		size++;
		positions[positions.length - size] = pos;
	}
	
	/**
	 * Get the number of elements so far in the line
	 * @return size
	 */
	public int getSize(){
		return size;
	}
	
	/**
	 * Grab the GridPosition data 
	 * @param pos - a position in the array storing the positions.
	 * @return a GridPosition object at position pos.
	 */
	public GridPosition getPosition(int pos){
		return positions[pos];
	}
	
	/**
	 * Set the GridPosition object for position pos.
	 * @param pos - an integer
	 * @param gPos - a GridPosition object.
	 */
	public void setPosition(int pos, GridPosition gPos){
		positions[pos]=gPos;
	}
	
	/**
	 * To set the String for a line.
	 * @param lineText
	 */
	public void setLineText(String lineText){
		this.lineText=lineText;
	}
	
	/**
	 * To get the line of text
	 * @return a String
	 */
	public String getLineText(){
		return lineText;
	}
	
	/**
	 * A toString method
	 * @return String representation of LineData object.
	 */
	public String toString(){
		String lineDataString="";
		for(int i=0; i<positions.length;i++){
			lineDataString+="[" + positions[i] + "]";
		}
		return lineDataString;
	}
}
