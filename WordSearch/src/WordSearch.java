import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.*;
import java.util.*;

/**
 * WordSearch controls the graphical user interface for Wordy's Search Game, it also manages the score.  It interacts with WordSearchOperations, a class you will implement.
 * @author Daniel Page
 * @date Winter 2019
 *
 */
public class WordSearch extends JFrame {

	/**
	 * The dimensions of buttons.
	 */
	private final int BUTTON_SIZE = 50;

	/**
	 * The text for the title.
	 */
	private final int WORD_SEARCH_TITLE_SIZE = 20;

	/**
	 * The font size for letters in buttons.
	 */
	private final int BIG_FONT_SIZE = 16;

	/**
	 * Default button.
	 */
	private final Icon RESTING_BUTTON = new ImageIcon("button_blank.png");

	/**
	 * Pressed button.
	 */
	private final Icon PRESSED_BUTTON = new ImageIcon("button_pressed.png");

	/**
	 * Button for word found.
	 */
	private final Icon USED_BUTTON = new ImageIcon("button_letterused.png");

	/**
	 * Currently active button.
	 */
	private final Icon CURRENT_BUTTON = new ImageIcon("button_current.png");

	/**
	 * When puzzle is done, turns normal buttons to this picture.
	 */
	private final Icon DONE_BUTTON = new ImageIcon("button_done.png");

	/**
	 * When we extract lines, this acts as the placeholder for inactive letters.
	 */
	private final String BLANK_SPACE = " ";

	/**
	 * The buttons that correspond to the grid of letters for the word search.
	 */
	private JButton [][] letterGridButtons;

	/**
	 * The label storing the score so far
	 */
	private JLabel currentScore;

	/**
	 * The state of the game so far!
	 */
	private JLabel gameState;

	/**
	 * The states of the buttons... 0 means resting, 1 is pressed, 2 is used.
	 */
	private int[][] buttonStates;
	/**
	 * If the maximum score is reached, the game is over!
	 */
	private int maxScore;

	/**
	 * The score in the game so far
	 */
	private int score;

	/**
	 * Word Search Operations are needed, need WordSearchOperations object...
	 */
	private WordSearchOperations wordSearch;

	/**
	 * We need to keep track of the letters we get from a line of letters as we click.  Need to make sure we are not deviating from a line being clicked on...
	 */
	private int[][] posArray;

	/**
	 * How long is the number of clicked letters?
	 */
	private int posLength;

	/**
	 * Constructor
	 * @param fileName - the file provided to pass along to build the dictionary and any relevant data.
	 * @param wordTextFile - the file needed to build the dictionary we use to reference words.
	 */
	public WordSearch(String fileName, String wordTextFile){

		//First, create WordSearchOperations object, it will give us the important information we need to build the graphical user interface.
		try{
			wordSearch = new WordSearchOperations(fileName,wordTextFile);
		}
		catch(IOException e){
			System.out.println("An error occurred when opening word search text file " + fileName + " or " + wordTextFile);
			System.exit(0);
		}
		catch(DictionaryException e){
			System.out.println(e.getMessage());
			System.exit(0);
		}

		//we need the grid size.
		int size = wordSearch.getSize();

		//need to obtain the maximum score, this is computed after constructing WordSearchOperations.
		maxScore = wordSearch.getMaxScore();
		score=0;//start off with 0 value as a score.

		//Before, just set up the posArray, this is used to keep track of actively selected lines.
		posArray = new int[2][size];
		posLength = 0;

		//Now we introduce some panels to form our interface.
		JPanel mainPanel = new JPanel();
		JPanel scorePanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		scorePanel.setLayout(new GridLayout(2,3));
		buttonPanel.setLayout(new GridLayout(size,size));

		//Construct the labels.
		JLabel totalScore = new JLabel("Maximum Score: " + maxScore);
		totalScore.setForeground(Color.MAGENTA);
		totalScore.setHorizontalAlignment(JLabel.LEFT);
		currentScore = new JLabel("Current Score: " + "0");
		currentScore.setForeground(Color.BLUE);
		currentScore.setHorizontalAlignment(JLabel.LEFT);
		JLabel nameOfGame= new JLabel("Wordy's Search Game");
		nameOfGame.setFont(new Font("Arial", Font.PLAIN, WORD_SEARCH_TITLE_SIZE));
		gameState = new JLabel("IN PLAY");
		gameState.setHorizontalAlignment(JLabel.CENTER);

		//Now add the constructed objects to the score panel.
		scorePanel.add(totalScore);
		scorePanel.add(nameOfGame);
		scorePanel.add(currentScore);
		scorePanel.add(gameState);

		//handles button clicks.
		ClickHandler handler = new ClickHandler(size);

		//Now set up buttons and add to the button panel.
		this.letterGridButtons = new JButton[size][size];
		this.buttonStates = new int[size][size];
		for(int i =0; i<size; i++){
			for(int j = 0;j<size; j++){
				this.letterGridButtons[i][j] = new JButton("",RESTING_BUTTON);
				this.letterGridButtons[i][j].setBackground(Color.BLACK);
				this.letterGridButtons[i][j].setForeground(Color.BLACK);
				this.letterGridButtons[i][j].setFont(new Font("Arial", Font.PLAIN, BIG_FONT_SIZE));
				this.letterGridButtons[i][j].setHorizontalTextPosition(JButton.CENTER);
				this.letterGridButtons[i][j].setVerticalTextPosition(JButton.CENTER);
				this.letterGridButtons[i][j].setPreferredSize(new Dimension(BUTTON_SIZE,BUTTON_SIZE));
				this.letterGridButtons[i][j].setText(wordSearch.getLetter(i,j));
				this.letterGridButtons[i][j].setEnabled(true);
				this.buttonStates[i][j]=0;
				buttonPanel.add(this.letterGridButtons[i][j]);
				this.letterGridButtons[i][j].addActionListener(handler);
			}
		}
		//The score panel should be "gold"
		scorePanel.setBackground(new Color(255,204,51));

		//Now put this all together.
		mainPanel.add(scorePanel);
		mainPanel.add(buttonPanel);
		//Main panel background should be "gold"
		mainPanel.setBackground(new Color(255,204,51));
		add(mainPanel);
	}

	/**
	 * Update the visual appearance of all the buttons.
	 * @param row - given on active click.
	 * @param column - given on active click.
	 */
	public void updateButtonStates(int row, int column){
		//first check if the game is finished...
		boolean doneGame = isGameFinished();

		//now we need to update the buttons, to reset appearance.
		for(int i=0; i < getBoardSize(); i++){
			for(int j=0; j < getBoardSize(); j++){
				switch(this.buttonStates[i][j]){
					case 0: if(!doneGame){letterGridButtons[i][j].setIcon(RESTING_BUTTON);} break;
					case 1: if(!doneGame){if(row==i && column==j){letterGridButtons[i][j].setIcon(CURRENT_BUTTON);}else{letterGridButtons[i][j].setIcon(PRESSED_BUTTON);}} break;
					case 2: letterGridButtons[i][j].setIcon(USED_BUTTON);break;
				}
				//just some redrawing stuff!
				letterGridButtons[i][j].paint(letterGridButtons[i][j].getGraphics());
				validate();
				repaint();
			}
		}
	}


	/**
	 * If passed new words found, it will update the score by adding the values of each new word to the score.
	 * @param newWords - ArrayList newWords, all the new words found on a click.
	 */
	public void updateScore(ArrayList<Word> newWords){
		//first get the current score.
		int cScore = this.score;

		//now we add the scores of each new word, we are assuming newWords provides us with all the new Words found.
		for(int i=0; i < newWords.size(); i++){
			cScore += newWords.get(i).getValue();
		}
		//we have the new score!
		this.score= cScore;

		//update our score label
		currentScore.setText("Current Score: " + this.score);
	}

	/**
	 * Check if the game is finished
	 * @return true/false - if the score is the maximum possible score.
	 */
	public boolean isGameFinished(){

		boolean doneGame=false;
		if(this.score >= this.maxScore){
			doneGame=true;

		}

		return doneGame;
	}

	/**
	 * If the game finishes, set the label to state the game is finished and change the button colours differently.
	 */
	private void gameFinished(){
		//update the label now
		gameState.setText("DONE GAME");
		//change all letters that were letters to other colours.
		for(int i = 0;i<wordSearch.getSize();i++){
			for(int j = 0;j<wordSearch.getSize();j++){
				if(buttonStates[i][j]<2){
					letterGridButtons[i][j].setIcon(DONE_BUTTON);
					this.letterGridButtons[i][j].setForeground(Color.WHITE);
				}
			}
		}
	}

	/**
	 * @param caseValue
	 * 0 - horizontal line
	 * 1 - vertical line
	 * 2 - diagonal top-left to bottom-right
	 * 3 - diagonal top-right to bottom-left
	 * @param row - row of last clicked entry
	 * @param column - column of last clicked entry
	 * @param diag - corresponds to a corner if a diagonal is given (case 2 or 3, should be 0 in other cases).
	 * @return a string corresponding to a line, return null if length of string of useful characters is 3 or less.
	 */
	public String extractString(int caseValue, int row, int column, int diag){
		String line = "";
		int len=0;
		if(caseValue==0){
			//horizontal line at "row".
			for(int i=0;i<wordSearch.getSize();i++){
				if(buttonStates[row][i]>0){
					line+=wordSearch.getLetter(row,i);
					len++;
				}
				else{
					line+=BLANK_SPACE;
				}
			}
		}
		else if(caseValue==1){
			//vertical line at "column".
			for(int i=0;i<wordSearch.getSize();i++){
				if(buttonStates[i][column]>0){
					line+=wordSearch.getLetter(i,column);
					len++;
				}
				else{
					line+=BLANK_SPACE;
				}
			}
		}
		else if(caseValue==2){
			//diagonal top-left to bottom-right
			for(int i=0;i<wordSearch.getSize() && diag+i<wordSearch.getSize() && column-(row-diag)+i < wordSearch.getSize();i++){
				if(buttonStates[diag+i][column-(row-diag)+i]>0){
					line+=wordSearch.getLetter(diag+i,column-(row-diag)+i);
					len++;
				}
				else{
					line+=BLANK_SPACE;
				}
			}
		}
		else if(caseValue==3){
			//diagonal bottom-left to top-right
			for(int i=0;i<wordSearch.getSize() && diag+i<wordSearch.getSize() && column+(row-diag)-i >=0;i++){

				if(buttonStates[diag+i][column+(row-diag)-i]>0){
					line+=wordSearch.getLetter(diag+i,column+(row-diag)-i);
					len++;
				}
				else{
					line+=BLANK_SPACE;
				}
			}
			//we read diagonals from top-right to bottom-left actually bottom up, so we need to reverse the string...
			String revString = "";
			for(int i=line.length()-1;i>=0;i--){
				revString += line.charAt(i);
			}
			line=revString;
		}
		if(len>=4){
			return line;
		}
		else{
			return null;
		}
	}

	/**
	 * fillLine activates letters of words newly found, setting them to be done.
	 * @param words - An ArrayList containing the words that will be marked in the line.  We assume there is one time a word can occur in a line.
	 * @param caseValue - 0 for horizontal, 1 for vertical, 2 for T-L diagonal, 3 for T-R diagonal.
	 * @param row - row number, if relevant
	 * @param column - column number, if relevant
	 * @param diag - the diagonal corner, if relevant.
	 */
	private void fillLine(ArrayList<Word> words, int caseValue, int row, int column, int diag){
		String line = "";
		int len=0;
		LineData lineData = new LineData(wordSearch.getSize());
		if(caseValue==0){
			//horizontal line at "row".
			for(int i=0;i<wordSearch.getSize();i++){
				if(buttonStates[row][i]>0){
					line+=wordSearch.getLetter(row,i);

					len++;
				}
				else{
					line+=BLANK_SPACE;
				}
				lineData.addPosition(row,i);
			}
		}
		else if(caseValue==1){
			//vertical line at "column".
			for(int i=0;i<wordSearch.getSize();i++){
				if(buttonStates[i][column]>0){
					line+=wordSearch.getLetter(i,column);
					len++;
				}
				else{
					line+=BLANK_SPACE;
				}
				lineData.addPosition(i,column);
			}
		}
		else if(caseValue==2){

			for(int i=0;i<wordSearch.getSize() && diag+i<wordSearch.getSize() && column-(row-diag)+i < wordSearch.getSize();i++){
				if(buttonStates[diag+i][column-(row-diag)+i]>0){
					line+=wordSearch.getLetter(diag+i,column-(row-diag)+i);
					len++;
				}
				else{
					line+=BLANK_SPACE;
				}
				lineData.addPosition(diag+i,column-(row-diag)+i);
			}
		}
		else if(caseValue==3){

			for(int i=0;i<wordSearch.getSize() && diag+i<wordSearch.getSize() && column+(row-diag)-i >=0;i++){

				if(buttonStates[diag+i][column+(row-diag)-i]>0){
					line+=wordSearch.getLetter(diag+i,column+(row-diag)-i);
					len++;
				}
				else{
					line+=BLANK_SPACE;
				}
				//must add in reverse here...

				lineData.addPositionRev(diag+i,column+(row-diag)-i);
			}
			//we interpret the String in reverse...
			String stringRev = "";
			for(int i=line.length()-1;i>=0;i--){
				stringRev += line.charAt(i);
			}
			//also reverse the array of positions, but we need to be careful so as to keep the null references outside the range for the line.
			int countNull=0;
			for(int i=0;i<line.length();i++){
				if(lineData.getPosition(i)==null){
					countNull++;
				}
			}
			LineData temp = new LineData(wordSearch.getSize());
			for(int i=0;i<wordSearch.getSize()-countNull;i++){
				temp.setPosition(i,lineData.getPosition(i+countNull));
			}
			lineData=temp;
			line=stringRev;
		}

		//now, we try to find each word on the line... we can assume no repeats of the same sequence twice on a line.
		for(int i=0;i<words.size();i++){
			int position = line.indexOf(words.get(i).getKey());
			int endOfWord = position + words.get(i).getKey().length();
			for(int j=position;j<endOfWord;j++){
				GridPosition gridPos = lineData.getPosition(j);
				buttonStates[gridPos.getX()][gridPos.getY()]=2;
			}
		}

	}

	/**
	 * Get the dimensions of the grid.
	 * @return size of board (n x n, get n)
	 */
	public int getBoardSize(){
		return wordSearch.getSize();
	}

	/**
	 * The button size
	 * @return the button size used for the buttons.
	 */
	public int getButtonSize(){
		return BUTTON_SIZE;
	}

	/**
	 * Main program, our game runs this method.
	 * @param args[0] - the word list text file, args[1] is the game board/grid and game data.
	 */
	public static void main(String []args){

		int textWidth=80;//Just a bit of padding

		JFrame board = new WordSearch(args[1],args[0]);//Create the game window and game information is assembled.  It creates a WordSearchOperations object, you need to implement WordSearchOperations.

		//Just some setup for the window.
		int buttonSize = ((WordSearch)board).getButtonSize();
		int size = ((WordSearch)board).getBoardSize();
		int width = Math.max(size*buttonSize+textWidth,450);
		board.setSize(width, (size+1)*buttonSize+56);
		board.setLocationRelativeTo(null);
		board.setVisible(true);

		//Adding the information for closing the window when you click the "exit" button on the window.
		board.addWindowListener(new WindowAdapter(){ public void windowClosing(WindowEvent event){System.exit(0);}});
	}

	private class ClickHandler implements ActionListener{

		/**
		 * boardSize - the size of the grid/board
		 */
		private int boardSize;

		/**
		 * Constructor
		 * @param size - the size of the grid/board.
		 */
		public ClickHandler(int size){
			boardSize = size;
		}

		/**
		 * When we have not selected a valid line, the buttons clicked need to be reset...
		 */
		public void cleanButtonClicks(){
			for(int i=0;i<posLength;i++){
				if(buttonStates[posArray[0][i]][posArray[1][i]]==1){
					buttonStates[posArray[0][i]][posArray[1][i]] = 0;
				}
			}
			posLength=0;
			updateButtonStates(-1,-1);
		}

		/**
		 * The code for when a click occurs!
		 * @param event - an event.
		 */
		public void actionPerformed(ActionEvent event){
			if(!isGameFinished() && event.getSource() instanceof JButton){
				int row = -1;
				int column = -1;
				//determine the position (row, column) the button click happened in...
				for(int i = 0; i < boardSize; i++){
					for(int j = 0; j < boardSize; j++){
						if(event.getSource() == letterGridButtons[i][j]){
							row=i;
							column=j;
							break;
						}
					}
					if(row != -1){
						break;
					}
				}

				int structureCase=-1;//need to determine if we are looking at a row, column, or a diagonal.  Set to be -1 by default.
				int diagonalValue=-1;//used for extracting the string later.

				//need a list to store lines found...
				ArrayList<LineParameters> lineList = new ArrayList<LineParameters>();

				//first check if we have clicked an unresting button...
				if(buttonStates[row][column]==0){

					//determine the "line pattern"
					boolean lineFound = true;
					//check that element is adjacent to at least one letter clicked... to avoid broken chains of letters
					if(lineFound){
						boolean findMatch=false;
						for(int i=0;i<posLength && !findMatch;i++){
							if(row==posArray[0][i]+1 && column==posArray[1][i]){
								findMatch=true;
							}
							else if(row==posArray[0][i]-1 && column==posArray[1][i]){
								findMatch=true;
							}
							else if(row==posArray[0][i] && column==posArray[1][i]-1){
								findMatch=true;
							}
							else if(row==posArray[0][i] && column==posArray[1][i]+1){
								findMatch=true;
							}
							else if(row==posArray[0][i]+1 && column==posArray[1][i]+1){
								findMatch=true;
							}
							else if(row==posArray[0][i]+1 && column==posArray[1][i]-1){
								findMatch=true;
							}
							else if(row==posArray[0][i]-1 && column==posArray[1][i]+1){
								findMatch=true;
							}
							else if(row==posArray[0][i]-1 && column==posArray[1][i]-1){
								findMatch=true;
							}
						}
						//now we need to make sure there isn't an already found word we are extending off of...
						if(!findMatch){
							if(row > 0){
								//here we check above positions (UP-LEFT, UP, UP-RIGHT)
								if(column>0 && buttonStates[row-1][column-1] == 2){
									findMatch=true;
								}
								if(buttonStates[row-1][column] == 2){
									findMatch=true;
								}
								if(column+1<wordSearch.getSize() && buttonStates[row-1][column+1]==2){
									findMatch=true;
								}
							}
							if(row+1 < wordSearch.getSize()){
								//here we check below positions (DOWN-LEFT, DOWN, DOWN-RIGHT)
								if(column>0 && buttonStates[row+1][column-1]==2){
									findMatch=true;
								}
								if(buttonStates[row+1][column]==2){
									findMatch=true;
								}
								if(column+1<wordSearch.getSize() && buttonStates[row+1][column+1]==2){
									findMatch=true;
								}
							}
							//check position directly to right
							if(column+1 < wordSearch.getSize() && buttonStates[row][column+1]==2){
								findMatch=true;
							}
							//check position directly to left
							if(column-1 > 0 && buttonStates[row][column-1]==2){
								findMatch=true;
							}
						}

						//did we find any match?
						if(!findMatch){
							lineFound=false;
							cleanButtonClicks();
						}
					}


					//case 1: horizontal line?  Keep in mind by default a row, column, or diagonal with only one clicked spot can be any type of line.
					if(detectRow(row)!=-1 || posLength==0){
						lineFound=true;
						structureCase=0;
						lineList.add(new LineParameters(structureCase,row,column,-1));
					}
					else{
						lineFound=false;
					}
					//case 2: vertical line?
					if(!lineFound || posLength==0){
						if(detectColumn(column)!=-1){
							lineFound=true;
							structureCase=1;
							lineList.add(new LineParameters(structureCase,row,column,-1));
						}
						else{
							lineFound=false;
						}
					}
					//case 3: diagonal (top-left to bottom-right)
					if(!lineFound || posLength==0){
						if(detectDiagonalTL(row,column)!=-1){
							lineFound=true;
							structureCase=2;
							diagonalValue=detectDiagonalTL(row,column);
							lineList.add(new LineParameters(structureCase,row,column,diagonalValue));
						}
						else{
							lineFound=false;
						}
					}
					//case 4: diagonal (top-right to bottom-left)
					if(!lineFound || posLength==0){
						if(detectDiagonalTR(row,column)!=-1){
							lineFound=true;
							structureCase=3;
							diagonalValue=detectDiagonalTR(row,column);
							lineList.add(new LineParameters(structureCase,row,column,diagonalValue));
						}
						else{
							lineFound=false;
						}
					}

					//no "line" found at all, time to clean out button clicks...
					if(!lineFound){
						cleanButtonClicks();
					}

				}
				if(buttonStates[row][column]==0){//no click yet, better activate it.
					buttonStates[row][column]=1;
					posArray[0][posLength]=row;
					posArray[1][posLength]=column;
					posLength++;
				}
				else if(buttonStates[row][column]==1){
					cleanButtonClicks();//reset the chain of clicks, we are unselecting.
				}

				if(buttonStates[row][column] <= 2 && structureCase<=3 && structureCase!=-1){

					boolean foundNewWord=false;//using this to check if we found at least one new word.

					for(int i = 0; i< lineList.size();i++){
						//We will extract a line corresponding to a row, column, or diagonal detected.
						int caseValue = lineList.get(i).getCaseValue();
						int rowValue = lineList.get(i).getRow();
						int columnValue = lineList.get(i).getColumn();
						int diagonalBoundary = lineList.get(i).getDiagonal();

						//extract the string.
						String lineText = extractString(caseValue,rowValue,columnValue,diagonalBoundary);

						int numWords = wordSearch.getNumWordsFound();//check the current number of words so far.

						//we have a line to work with, we need to make sure we can't find new words in it!
						if(lineText!=null){
							//IMPORTANT: HERE WE FIND WORDS IN A LINE.  It stores the words in an ArrayList.
							ArrayList<Word> words = wordSearch.findWords(lineText);

							//now we need to update the word list, we will give the whole list of found words and return back the new words.
							ArrayList<Word> newWords = null;
							try{
								//returns the new words found!
								newWords = wordSearch.updateWordList(words);
							}
							catch(DictionaryException e){
								//This will happen if you don't check if a word already exists in the dictionary.
								System.out.println("A Dictionary Exception was thrown..." + e.getMessage() + "; make sure that your updateWordList doesn't throw this exception.\n Hint: check to see if the key is in first, before applying put the Word in...");
							}
							//now what we need to do is fill in the lines of words we found.
							fillLine(words,caseValue,rowValue,columnValue,diagonalBoundary);

							//now we update the score!
							updateScore(newWords);

							//now check to see if we are done playing!
							if(isGameFinished()){
								gameFinished();
							}
						}
						//the new number of words
						int numWordsAfter = wordSearch.getNumWordsFound();

						//did we find a new word?
						if(numWordsAfter>numWords){
							foundNewWord=true;
						}



					}
					//if we found a new word, we should reset the selection of letters so far.
					if(foundNewWord){
						cleanButtonClicks();
					}

				}

				updateButtonStates(row,column);//pass along row and column to make sure the current button pressed is known.
			}
		}

		/**
		 * This method determines if there is a horizontal line currently selected.  If so, returns the row number.
		 * @return number row if there is a horizontal line, -1 otherwise.
		 */
		public int detectRow(int row){
			boolean lineFound=true;
			//determine that all clicked buttons are in a row.
			for(int i = 0; i < posLength; i++){
				if(posArray[0][i]!=row){
					lineFound=false;
				}
			}
			if(lineFound){
				return row;
			}
			else{
				return -1;
			}
		}

		/**
		 * This method determines if there is a vertical line of currently selected buttons.  If so, return the column number.
		 * @return number column if there is a vertical line, -1 otherwise.
		 */
		public int detectColumn(int column){
			boolean lineFound=true;
			//very simple, make sure all clicked buttons are in a column.
			for(int j = 0; j< posLength; j++){
				if(posArray[1][j]!=column){
					lineFound=false;
				}
			}
			if(lineFound){
				return column;
			}
			else{
				return -1;
			}
		}

		/**
		 * Detect a diagonal from top left to bottom right.
		 * @param row
		 * @param column
		 * @return the top corner from the diagonal if line found, -1 otherwise.
		 */
		public int detectDiagonalTL(int row, int column){
			//count the number of elements in the diagonal that are clicked.
			int countElements=0;
			int cornerVar=0;
			for(int i=0;i<=boardSize-(row+1) && i+column < boardSize;i++){
				for(int j=0;j < posLength;j++){
					if(posArray[0][j]==i+row && posArray[1][j]==i+column){
						countElements++;
					}
				}
			}
			cornerVar=row;
			for(int i=0;i<=row-1 && row-i-1>=0;i++){
				for(int j=0;j < posLength;j++){
					if(posArray[0][j]== row-i-1 && posArray[1][j]==column-i-1){
						countElements++;
					}
				}

				if(column-i-1>=0){
					cornerVar=row-i-1;
				}
			}
			//does our count match the number of active button clicks.
			if(countElements==posLength){
				return cornerVar;
			}
			else{
				return -1;
			}
		}

		/**
		 * Detect a diagonal from top-right to bottom left.
		 * @param row
		 * @param column
		 * @return bottom corner if found, -1 otherwise.
		 */
		public int detectDiagonalTR(int row, int column){
			//we're going to count the number of elements along the diagonal.
			int countElements=0;
			int cornerVar=0;
			for(int i=0;i<=boardSize-(row+1) && column-i>=0;i++){
				for(int j=0;j < posLength;j++){
					if(posArray[0][j]==row+i && posArray[1][j]==column-i){
						countElements++;
					}
				}

			}
			cornerVar=row;
			for(int i=0;i<=row-1 && column+i+1<boardSize;i++){
				for(int j=0;j < posLength;j++){

					if(posArray[0][j]== row-i-1 && posArray[1][j]==column+i+1){
						countElements++;

					}
				}
				if(column+i+1 <= boardSize-1){
					cornerVar=row-i-1;
				}
			}
			//did we find the number of elements in the diagonal selected so far, does this match our clicks?
			if(countElements==posLength){
				return cornerVar;
			}
			else{
				return -1;
			}
		}
	}
}
