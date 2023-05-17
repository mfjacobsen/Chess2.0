package view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;

/**
 * View 
 * 
 * References:
 *
 * https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
 * https://docs.oracle.com/javase/7/docs/api/javax/swing/JOptionPane.html
 * 
 * @author mfjac
 *
 */
public class ChessView extends JFrame
{
	// Instance variable declarations
	private JButton[][] board;
	private JPanel boardPanel;
	private String userColor;
	private JMenuBar menuBar;
	private JMenu gameMenu;
	private JMenuItem newGameMenuItem;
	private JRadioButtonMenuItem whiteRadio;
	private JRadioButtonMenuItem blackRadio;
	
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
		// Initializes the board
		board = new JButton[8][8];
		
		// Initializes the board panel with a new 8x8 grid layout
		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(8,8));
		
		// If userColor is white
		if (userColor.equals("White"))
		{
			// Nested for loops iterate through each button on the board
			for(int rank = 7;  rank >= 0; rank --)
			{
				for (int file = 0; file < 8; file ++)
				{
					buildButton(file,rank);
				}
			}
		}
		
		// Else the user is black
		else
		{
			// Nested for loops iterate through each button on the board
			for(int rank = 0;  rank <= 7; rank ++)
			{
				for (int file = 0; file < 8; file ++)
				{
					buildButton(file,rank);
				}
			}
		}
	
		// Adds the board panel to the frame
		add(boardPanel);
	}
	
	private void buildButton(int file, int rank)
	{
		// Initializes the button and displays its index
		board[file][rank] = new JButton();
		
		// Sets the borders on the Buttons
		board[file][rank].setBorder(BorderFactory.createLineBorder(Color.black));
		
		// Hides the button's border
		board[file][rank].setBorderPainted(false);
		
		// Sets color of the black spaces
		if ((file + rank) % 2 == 0)
			board[file][rank].setBackground(Color.GRAY);
		
		// Sets the color of the white spaces
		else
			board[file][rank].setBackground(Color.WHITE);
		
		// Sets the preferred size of the button
		board[file][rank].setPreferredSize(new Dimension(90,90));
			
		// Adds the button to the board panel
		boardPanel.add(board[file][rank]);
	}
	
	private void buildMenu()
	{
		// Initialize the menu bar
		menuBar = new JMenuBar();
		
		// Initialize the game menu
		gameMenu = new JMenu("Game");
		gameMenu.setMnemonic(KeyEvent.VK_G);
		
		// Initialize the player color menu
		JMenu playerColorMenu = new JMenu("Player Color");
		
		// Initializes the new game menu item
		newGameMenuItem = new JMenuItem("New Game");
		
		// Adds the new game menu item to the menu
		gameMenu.add(newGameMenuItem);
	
		// Declares and initializes a new button group
		ButtonGroup colorRadioGroup = new ButtonGroup();
		
		// Initializes the white and black radio buttons
		whiteRadio = new JRadioButtonMenuItem("White");
		blackRadio = new JRadioButtonMenuItem("Black");
		
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
	
	public int promotePiece(String color)
	{
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
			iconFileNames[i] = String.format("icons/%s%s.png", color, promotionPieces[i]);
			
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
	 * Paints the border of every button in the idices list
	 * @param indices a list of button indices to paint
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
	 * @return the board
	 */
	public JButton[][] getBoard()
	{
		return board;
	}

	/**
	 * @param board the board to set
	 */
	public void setBoard(JButton[][] board)
	{
		this.board = board;
	}
}
