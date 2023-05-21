package view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Lead Authors:
 *
 * @author Matthew Jacobsen; 5550026131
 * @author Daniel Blasczyk; 5550063899
 *
 * References:
 * 
 * 		Morelli, R., & Walde, R. (2016). 
 * 		Java, Java, Java: Object-Oriented Problem Solving
 * 		Retrieved from https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *
 * 		Gaddis, T. (2015). Starting Out With Java Myprogramming Lab 
 * 		From Control Structures Through Objects. (6th ed.). Addison-Wesley. 
 * 
 * 		https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
 *		https://docs.oracle.com/javase/7/docs/api/javax/swing/JOptionPane.html
 *
 * Version: 1
 *
 * Responsibilities of class: Displays the chess model in a GUI.
 *
 */
public class ChessView extends JFrame
{
	// Instance variable declarations
	private BoardButton[][] board;			// An array of buttons that make up the chess board
	private JPanel boardPanel;				// Holds the inner board panel, sets the size of the board
	private JPanel innerBoardPanel;			// Holds the board buttons
	private String userColor;				// The color of the user
	private JMenuBar menuBar;				// The menu bar 
	private JMenu gameMenu;					// The game menu
	private JMenuItem newGameMenuItem;		// Starts a new game
	private JRadioButtonMenuItem whiteRadio;// Changes the user color to white on next new game
	private JRadioButtonMenuItem blackRadio;// Changes the user color to black on next new game
	
	/**
	 * Constructor
	 */
	public ChessView()
	{
		// Sets the user Color as White
		userColor = "White"; 
		
		// Builds the panel
		buildBoardPanel();
		
		// Builds the menu
		buildMenu();
		
		// Sets the default close operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		// Packs and views the frame
		pack();	
		setVisible(true);
	}
	
	/**
	 * Builds and displays the board in the frame
	 */
	public void buildBoardPanel()
	{
		// Initializes the board panel
		boardPanel = new JPanel();
				
		// Sets the Preferred and min/max size of the panel
		boardPanel.setPreferredSize(new Dimension(720,720));
		boardPanel.setMinimumSize(new Dimension(720,720));
		boardPanel.setMaximumSize(new Dimension(720,720));
		
		// Initializes the inner board panel with a new 8x8 grid layout
		innerBoardPanel = new JPanel();
		innerBoardPanel.setLayout(new GridLayout(8,8));	
		
		// Initializes the board
		board = new BoardButton[8][8];
		
		// If the user is white, orient the board with white at the bottom
		if (userColor.equals("White"))
		{
			// Nested for loops iterate through each button on the board
			for(int rank = 7;  rank >= 0; rank --)
			{
				for (int file = 0; file <= 7; file ++)
				{
					// Creates a new board button 
					board[file][rank] = new BoardButton();
					
					// Adds the button to the inner board panel
					innerBoardPanel.add(board[file][rank]);
				}
			}
		}
		
		// Else the user is black, orient the board with black at the bottom
		else
		{
			// Nested for loops iterate through each button on the board
			for(int rank = 0;  rank <= 7; rank ++)
			{
				for (int file = 7; file >= 0; file --)
				{
					// Creates a new board button 
					board[file][rank] = new BoardButton();
					
					// Adds the button to the inner board panel
					innerBoardPanel.add(board[file][rank]);
				}
			}
		}
		
		// Adds the board panel to the sized board panel
		boardPanel.add(innerBoardPanel);
		
		// Adds the sized board panel to the frame
		add(boardPanel);
	}
	
	/**
	 * Builds the view's menus
	 */
	private void buildMenu()
	{
		// Initialize the menu bar
		menuBar = new JMenuBar();
		
		// Initialize the game menu and set mnemonic key 
		gameMenu = new JMenu("Game");
		gameMenu.setMnemonic(KeyEvent.VK_G);
		
		// Initialize the player color menu and set mnemonic key
		JMenu playerColorMenu = new JMenu("Player Color");
		playerColorMenu.setMnemonic(KeyEvent.VK_C);
		
		// Initializes the new game menu item and set mnemonic key
		newGameMenuItem = new JMenuItem("New Game");
		newGameMenuItem.setMnemonic(KeyEvent.VK_N);
		
		// Adds the new game menu item to the menu
		gameMenu.add(newGameMenuItem);
	
		// Declares and initializes a new button group
		ButtonGroup colorRadioGroup = new ButtonGroup();
		
		// Initializes the white and black radio buttons
		whiteRadio = new JRadioButtonMenuItem("White");
		blackRadio = new JRadioButtonMenuItem("Black");
		
		// Sets mnemonic keys on radio buttons
		whiteRadio.setMnemonic(KeyEvent.VK_W);
		blackRadio.setMnemonic(KeyEvent.VK_B);
		
		// Adds the radio buttons to the button group
		colorRadioGroup.add(whiteRadio);
		colorRadioGroup.add(blackRadio);
		
		// Adds the radio group to the player color menu
		playerColorMenu.add(whiteRadio);
		playerColorMenu.add(blackRadio);
		
		// Sets the white radio as selected
		whiteRadio.setSelected(true);
		
		// Adds the player color menu to the game menu
		gameMenu.add(playerColorMenu);
		
		// Add the game menu to the menu bar
		menuBar.add(gameMenu);
		
		// Set the menu bar
		setJMenuBar(menuBar);
	}
	
	/**
	 * Displays the Promote Piece dialog window
	 * @param color The color of the promoting piece
	 * @return An integer representing the player's choice, 0-3 for Queen, Rook, Bishop, or Knight
	 */
	public int promotePiece(String color, int[] index)
	{
		// The color of the square the Piece is promoting on
		String squareColor = null;
		
		// Sets the square color of the space
		if ((index[0] + index[1]) % 2 == 0)
			squareColor = "Dark";
		else
			squareColor = "Light";
		
		// Declares an array of icons to display
		ImageIcon icons[] = new ImageIcon[4];
		
		// Declares a String array to hold the icon file names
		String[] iconFileNames = new String[4];
		
		// Declares and initializes Strings naming the four possible pieces to promotes
		String[] promotionPieces = {"Queen", "Rook", "Bishop", "Knight"};		
		
		// For loop iterates four times
		for (int i = 0; i < 4; i++)
		{
			// Sets the icon file name
			iconFileNames[i] = String.format("icons/%s%s%s.png",squareColor, color, promotionPieces[i]);
			
			// Initializes the icon
			icons[i] = new ImageIcon(iconFileNames[i]);
		}
		
		// Returns the value selected from the piece promotion option pane
		return  JOptionPane.showOptionDialog(this, "Choose a piece.", "Promote Piece", JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, icons, icons[0]);
	}
	
	/**
	 * Clears the board of borders
	 */
	public void clearBorders()
	{
		// Nested for loops iterate through each button on the board
		for(int rank = 7;  rank >= 0; rank --)
		{
			for (int file = 0; file < 8; file ++)
			{				
				// Hides the button's border
				board[file][rank].setBorderPainted(false);
			}
		}
	}

	/**
	 * Paints the border of every button in the indices list
	 * @param indices A list of button indices to paint the borders of
	 */
	public void paintBorders(ArrayList<int[]> indices)
	{
		// For every index in indices 
		for (int[] index : indices)
		{
			// Paint the button border
			board[index[0]][index[1]].setBorderPainted(true);
		}
	}
	
	/**
	 * Displays the game end message dialog
	 * @param gameOutcome the outcome of the game
	 */
	public void endGame(String gameOutcome)
	{
		JOptionPane.showMessageDialog(null, gameOutcome);
	}

	/**
	 * @return the board
	 */
	public BoardButton[][] getBoard()
	{
		return board;
	}

	/**
	 * @return the boardPanel
	 */
	public JPanel getBoardPanel()
	{
		return boardPanel;
	}

	/**
	 * @return the userColor
	 */
	public String getUserColor()
	{
		return userColor;
	}

	/**
	 * @return the gameMenu
	 */
	public JMenu getGameMenu()
	{
		return gameMenu;
	}

	/**
	 * @return the newGameMenuItem
	 */
	public JMenuItem getNewGameMenuItem()
	{
		return newGameMenuItem;
	}

	/**
	 * @return the whiteRadio
	 */
	public JRadioButtonMenuItem getWhiteRadio()
	{
		return whiteRadio;
	}

	/**
	 * @return the blackRadio
	 */
	public JRadioButtonMenuItem getBlackRadio()
	{
		return blackRadio;
	}

	/**
	 * @param board the board to set
	 */
	public void setBoard(BoardButton[][] board)
	{
		this.board = board;
	}

	/**
	 * @param boardPanel the boardPanel to set
	 */
	public void setBoardPanel(JPanel boardPanel)
	{
		this.boardPanel = boardPanel;
	}

	/**
	 * @param userColor the userColor to set
	 */
	public void setUserColor(String userColor)
	{
		this.userColor = userColor;
	}

	/**
	 * @param gameMenu the gameMenu to set
	 */
	public void setGameMenu(JMenu gameMenu)
	{
		this.gameMenu = gameMenu;
	}

	/**
	 * @param newGameMenuItem the newGameMenuItem to set
	 */
	public void setNewGameMenuItem(JMenuItem newGameMenuItem)
	{
		this.newGameMenuItem = newGameMenuItem;
	}

	/**
	 * @param whiteRadio the whiteRadio to set
	 */
	public void setWhiteRadio(JRadioButtonMenuItem whiteRadio)
	{
		this.whiteRadio = whiteRadio;
	}

	/**
	 * @param blackRadio the blackRadio to set
	 */
	public void setBlackRadio(JRadioButtonMenuItem blackRadio)
	{
		this.blackRadio = blackRadio;
	}


}
