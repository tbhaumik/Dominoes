package dominoes;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import dominoes.players.abstractPlayer;
import dominoes.players.abstractPlayerFactory;
import dominoes.players.abstractPlayerFactory.PlayerType;


/**
 * This screen implements the game between two interactive players only.
 * It interacts with the subsystem, i.e. the main driver classes via
 * a FACADE (Design Pattern), which is the dominoesUserInterfaceFacade.java class 
 * The FACADE class has been implemented as a SINGLETON.
 *  
 * @author Tirthankar Bhaumik
 * @see domioesUserInterfaceFacade
 */
public class interactivePlayerScreen extends JFrame {

	//************************************JLabels*********************************************************
	private JLabel boneyardCountL, roundL, pointsTargetL, lineOfPlayL, playerOneL, playerTwoL,
	availableBonesOneL,	availableBonesOneLValue,pointsScoredOneL, pointsScoredOneLValue,availableBonesTwoL,
	availableBonesTwoLValue,pointsScoredTwoL, pointsScoredTwoLValue,boneyardCountValueL, roundValueL, 
	pointsTargetValueL,lineOfPlayValueL,lFormHeading, lInstruction, lPlayer1Name, lPlayer2Name,lPointsTarget;

	//********JButtons****************************
	private JTextField tPlayer1Name, tPlayer2Name, tPointsTarget;

	//*************************************JButtons********************************************************
	private JButton player1Flip,player1PlayRight, player1PlayLeft, player1Pass, player1Draw,
	player2Flip,player2PlayRight, player2PlayLeft, player2Pass, player2Draw,exitGameB,bStartDominoGame
	,bExitGame, bRestartGame;

	//**********JList to display and individual players bones*****************************
	private JList playerOneBones,playerTwoBones;

	private JScrollPane listOneScroller, listTwoScroller;

	//**************Button handlers**************
	//Player One
	private player1DrawButtonHandler dp1bHandler;
	private player1FlipButtonHandler fp1bHandler;
	private player1PlayRightButtonHandler prp1bHandler;
	private player1PlayLeftButtonHandler plp1bHandler;
	private player1PassButtonHandler pap1bHandler;

	//Player Two
	private player2DrawButtonHandler dp2bHandler;
	private player2FlipButtonHandler fp2bHandler;
	private player2PlayRightButtonHandler prp2bHandler;
	private player2PlayLeftButtonHandler plp2bHandler;
	private player2PassButtonHandler pap2bHandler;
	//Exit and Start buttons
	private exitGameButtonHandler ebHandler;
	private startGameBHandler sGBHandler;
	private exitStartGameBHandler eGBHandler;
	private restartStartGameBHandler rsGBHandler;
	//********************************************
	dominoesUserInterfaceFacade DominoUI_Instance;

	//The Players
	abstractPlayer Player1 = null;
	abstractPlayer Player2 = null;

	/**
	 * Intialise and display the screen
	 */
	public static void main(String[] args) {
		interactivePlayerScreen domino = new interactivePlayerScreen();
	}

	/**
	 * Constructor method that initialises the game
	 * and loads the GUI components 
	 */
	public interactivePlayerScreen() {
		// loads swing components of the Frame 
		loadStartScreenComponents();
		loadGameScreenControls(); 
	}

	/**
	 * This procedure creates a new game.
	 * 
	 * @param abstractPlayerFactory.PlayerType
	 * @param abstractPlayerFactory.PlayerType
	 * @param String PlayerName
	 * @param String PlayerName
	 * @param int Points Goal
	 */
	public void createNewGame(abstractPlayerFactory.PlayerType playerType1, 
			abstractPlayerFactory.PlayerType playerType2,
			String p1Name,String p2Name, int intPointsGoal){

		// Get an instance object of the FACADE, that talks to the subsystem
		DominoUI_Instance = dominoesUserInterfaceFacade.getInstance();

		//call to Player factory
		abstractPlayer dominoPlayers[] = DominoUI_Instance.createPlayers(playerType1, playerType2);
		//allocate the players returned from factory
		Player1 = dominoPlayers[0];
		Player2 = dominoPlayers[1];

		//Set Player names
		DominoUI_Instance.setName(Player1,p1Name);
		DominoUI_Instance.setName(Player2,p2Name);

		//ask the FACADE to start a new game
		DominoUI_Instance.startNewGame(intPointsGoal, Player1, Player2); 
	}

	// Player 1 buttons

	/**
	 * Player 1 DRAW button
	 * Draw a bone from the Boneyard
	 */
	class player1DrawButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try {
				DominoUI_Instance.drawBone(Player1); //Draw
			} catch (emptyBoneyardException e1) {

				showMessage(e1.getMessage());
			}
			//update boneyard count on the GUI
			boneyardCountValueL.setText(String.valueOf(DominoUI_Instance.getBoneyardCount()));
			//populate Player1 JList of bones
			playerOneBones.setListData(dominoesGlobal.convertBonestoStringArray(DominoUI_Instance.showPlayerBones(Player1)));
			availableBonesOneLValue.setText(String.valueOf(DominoUI_Instance.countPlayerBones(Player1)));
		}
	}

	/**
	 * Player 1 FLIP button
	 * Flip a bone
	 */
	class player1FlipButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//extract value selected from JList, returns a STRING, 
			String tempStrBone =  (String) playerOneBones.getSelectedValue(); 
			if (tempStrBone!=null) //only if a value is selected
			{
				DominoUI_Instance.flipPlayerBone(Player1, tempStrBone);
				//update display
				playerOneBones.setListData(dominoesGlobal.convertBonestoStringArray(DominoUI_Instance.showPlayerBones(Player1)));
			}	
			else
			{
				showMessage(dominoesGlobal.flipMsg);
			}
		}
	}

	/**
	 * Player 1 PLAY RIGHT button
	 * Play a selected Bone to the RIGHT of the table layout
	 */
	class player1PlayRightButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//extract value from JList,  returns a STRING
			String tempStrBone = (String) playerOneBones.getSelectedValue();
			if (tempStrBone!=null) //only if a value is selected
			{
				Bone boneToPlay = dominoesGlobal.convertStringToBone(tempStrBone);
				Play actualPlay = null;
				if (DominoUI_Instance.isTableEmpty()) // no Bones have been played
				{
					//first Bone being played on the table
					DominoUI_Instance.createPlay(Player1, boneToPlay, Play.START);
				}
				else //table has bones played on it
				{
					DominoUI_Instance.createPlay(Player1, boneToPlay, Play.RIGHT);
				}
				try {
					//create a Play object
					actualPlay = DominoUI_Instance.makePlayerPlay(Player1);
				} catch (CantPlayException e1) {
					showMessage(e1.getMessage());
				}
				try {
					//makes a Play to the table
					DominoUI_Instance.playToTable(actualPlay);
					//sets the last action
					DominoUI_Instance.setLastPlayerAction(Player1, abstractPlayer.PLAY);
					// update players personal Bone array - delete
					DominoUI_Instance.removePlayerBone(Player1, boneToPlay);
					// refresh player JList Bone display
					playerOneBones.setListData(dominoesGlobal.convertBonestoStringArray(DominoUI_Instance.showPlayerBones(Player1)));
					availableBonesOneLValue.setText(String.valueOf(DominoUI_Instance.countPlayerBones(Player1)));
					// refresh Table display
					lineOfPlayValueL.setText(dominoesGlobal.convertBonestoString(DominoUI_Instance.getBonesOnTable()));
					// Round ends when a player has played all his bones
					if (DominoUI_Instance.countPlayerBones(Player1)<=0){
						DominoUI_Instance.endRound(Player1,Player2);
					}

				} catch (InvalidPlayException e1) {
					DominoUI_Instance.displayInvalidPlay(Player1);
				}
			}
			else
			{
				showMessage(dominoesGlobal.makePlayMsg);
			}
		}
	}
	
	/**
	 * Player 1 PLAY LEFT button
	 * Play a selected Bone to the LEFT of the table layout
	 */
	class player1PlayLeftButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//extract value from JList,  returns a STRING
			String tempStrBone = (String) playerOneBones.getSelectedValue();
			if (tempStrBone!=null) //only if a value is selected
			{
				Bone boneToPlay = dominoesGlobal.convertStringToBone(tempStrBone);
				Play actualPlay = null;
				if (DominoUI_Instance.isTableEmpty()) // no Bones have been played
				{
					//first Bone being played on the table
					DominoUI_Instance.createPlay(Player1, boneToPlay, Play.START);
				}
				else //table has bones played on it
				{
					DominoUI_Instance.createPlay(Player1, boneToPlay, Play.LEFT);
				}
				try {
					//create a Play object
					actualPlay = DominoUI_Instance.makePlayerPlay(Player1);
				} catch (CantPlayException e1) {
					showMessage(e1.getMessage());
				}
				try {
					//makes a Play to the table
					DominoUI_Instance.playToTable(actualPlay);
					//sets the last action
					DominoUI_Instance.setLastPlayerAction(Player1, abstractPlayer.PLAY);
					// update players personal Bone array - delete
					DominoUI_Instance.removePlayerBone(Player1, boneToPlay);
					// refresh player JList Bone display
					playerOneBones.setListData(dominoesGlobal.convertBonestoStringArray(DominoUI_Instance.showPlayerBones(Player1)));
					availableBonesOneLValue.setText(String.valueOf(DominoUI_Instance.countPlayerBones(Player1)));
					// refresh Table display
					lineOfPlayValueL.setText(dominoesGlobal.convertBonestoString(DominoUI_Instance.getBonesOnTable()));
					// Round ends when a player has played all his bones
					if (DominoUI_Instance.countPlayerBones(Player1)<=0){
						DominoUI_Instance.endRound(Player1, Player2);
					}

				} catch (InvalidPlayException e1) {
					DominoUI_Instance.displayInvalidPlay(Player1);
				}
			}
			else
			{
				showMessage(dominoesGlobal.makePlayMsg);
			}
		}
	}
	
	/**
	 * Player 1 PASS button
	 * Pass a turn
	 */
	class player1PassButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try {
				DominoUI_Instance.passTurn(Player1.getName(),Player1,Player2);
				loadGameComponentValues();
				//cannot pass if there are bones available in the boneyard
			} catch (illegalPassException e1) { 
				showMessage(e1.getMessage());
			}
		}
	}

	// Player 2 buttons
	/**
	 * Player 2 DRAW button
	 * Draw a Bone from the Boneyard
	 */
	class player2DrawButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try {
				DominoUI_Instance.drawBone(Player2);
			} catch (emptyBoneyardException e1) {

				showMessage(e1.getMessage());
			}
			//update boneyard count on the GUI
			boneyardCountValueL.setText(String.valueOf(DominoUI_Instance.getBoneyardCount()));
			//populate Player1 JList of bones
			playerTwoBones.setListData(dominoesGlobal.convertBonestoStringArray(DominoUI_Instance.showPlayerBones(Player2)));
			availableBonesTwoLValue.setText(String.valueOf(DominoUI_Instance.countPlayerBones(Player2)));
		}
	}

	/**
	 * Player 2 FLIP button
	 * Flip a Bone
	 */
	class player2FlipButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//extract value selected from JList, returns a STRING, 
			String tempStrBone =  (String) playerTwoBones.getSelectedValue(); 
			if (tempStrBone!=null) //only if a value is selected
			{
				DominoUI_Instance.flipPlayerBone(Player2, tempStrBone);
				//update display
				playerTwoBones.setListData(dominoesGlobal.convertBonestoStringArray(DominoUI_Instance.showPlayerBones(Player2)));
			}	
			else
			{
				showMessage(dominoesGlobal.flipMsg);
			}
		}
	}

	/**
	 * Player 2 PLAY RIGHT button
	 * Play a selected Bone to the RIGHT of the table layout
	 */
	class player2PlayRightButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//extract value from JList,  returns a STRING
			String tempStrBone = (String) playerTwoBones.getSelectedValue();
			if (tempStrBone!=null) //only if a value is selected
			{
				Bone boneToPlay = dominoesGlobal.convertStringToBone(tempStrBone);
				Play actualPlay = null;
				if (DominoUI_Instance.isTableEmpty()) // no Bones have been played
				{
					//first Bone being played on the table
					DominoUI_Instance.createPlay(Player2, boneToPlay, Play.START);
				}
				else //table already has bones played
				{
					DominoUI_Instance.createPlay(Player2, boneToPlay, Play.RIGHT);
				}
				try {
					//create a Play object
					actualPlay = DominoUI_Instance.makePlayerPlay(Player2);
				} catch (CantPlayException e1) {
					showMessage(e1.getMessage());
				}
				try {
					//makes a Play to the table
					DominoUI_Instance.playToTable(actualPlay);
					//sets the last action
					DominoUI_Instance.setLastPlayerAction(Player2, abstractPlayer.PLAY);
					// update players personal Bone array - delete
					DominoUI_Instance.removePlayerBone(Player2, boneToPlay);
					// refresh player JList Bone display
					playerTwoBones.setListData(dominoesGlobal.convertBonestoStringArray(DominoUI_Instance.showPlayerBones(Player2)));
					availableBonesTwoLValue.setText(String.valueOf(DominoUI_Instance.countPlayerBones(Player2)));
					// refresh Table display
					lineOfPlayValueL.setText(dominoesGlobal.convertBonestoString(DominoUI_Instance.getBonesOnTable()));
					// Round ends when a player has played all his bones
					if (DominoUI_Instance.countPlayerBones(Player2)<=0){
						DominoUI_Instance.endRound(Player1,Player2);
					}

				} catch (InvalidPlayException e1) {
					DominoUI_Instance.displayInvalidPlay(Player2);
				}
			}
			else
			{
				showMessage(dominoesGlobal.makePlayMsg);
			}

		}
	}

	/**
	 * Player 2 PLAY LEFT button
	 * Play a selected Bone to the LEFT of the table layout
	 */
	class player2PlayLeftButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//extract value from JList,  returns a STRING
			String tempStrBone = (String) playerTwoBones.getSelectedValue();
			if (tempStrBone!=null) //only if a value is selected
			{
				Bone boneToPlay = dominoesGlobal.convertStringToBone(tempStrBone);
				Play actualPlay = null;
				if (DominoUI_Instance.isTableEmpty()) // no Bones have been played
				{
					//first Bone being played on the table
					DominoUI_Instance.createPlay(Player2, boneToPlay, Play.START);
				}
				else //table alreday has bones played to it
				{
					DominoUI_Instance.createPlay(Player2, boneToPlay, Play.LEFT);
				}
				try {
					//create a Play object
					actualPlay = DominoUI_Instance.makePlayerPlay(Player2);
				} catch (CantPlayException e1) {
					showMessage(e1.getMessage());
				}
				try {
					//makes a Play to the table
					DominoUI_Instance.playToTable(actualPlay);
					//sets the last action
					DominoUI_Instance.setLastPlayerAction(Player2, abstractPlayer.PLAY);
					// update players personal Bone array - delete
					DominoUI_Instance.removePlayerBone(Player2, boneToPlay);
					// refresh player JList Bone display
					playerTwoBones.setListData(dominoesGlobal.convertBonestoStringArray(DominoUI_Instance.showPlayerBones(Player2)));
					availableBonesTwoLValue.setText(String.valueOf(DominoUI_Instance.countPlayerBones(Player2)));
					// refresh Table display
					lineOfPlayValueL.setText(dominoesGlobal.convertBonestoString(DominoUI_Instance.getBonesOnTable()));
					// Round ends when a player has played all his bones
					if (DominoUI_Instance.countPlayerBones(Player2)<=0){
						DominoUI_Instance.endRound(Player1,Player2);
					}

				} catch (InvalidPlayException e1) {
					DominoUI_Instance.displayInvalidPlay(Player2);
				}
			}
			else
			{
				showMessage(dominoesGlobal.makePlayMsg);
			}
		}
	}

	/**
	 * Player 2 PASS button
	 * Pass a turn
	 */
	class player2PassButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try {
				DominoUI_Instance.passTurn(Player2.getName(),Player1,Player2);
				loadGameComponentValues();
				//cannot pass if there are bones available in the boneyard
			} catch (illegalPassException e1) {
				showMessage(e1.getMessage());
			}
		}
	}

	/**
	 * Game Exit button listener
	 */
	class exitGameButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}

	/**
	 * Populate the Swing components with values  
	 */
	public void loadGameComponentValues(){

		//Player 1 values 
		pointsScoredOneLValue.setText(String.valueOf(DominoUI_Instance.getPlayerPoints(Player1)));
		availableBonesOneLValue.setText(String.valueOf(DominoUI_Instance.countPlayerBones(Player1)));
		playerOneL.setText(DominoUI_Instance.getName(Player1));
		playerOneBones.setListData(dominoesGlobal.convertBonestoStringArray(DominoUI_Instance.showPlayerBones(Player1)));

		//Player 2 values
		pointsScoredTwoLValue.setText(String.valueOf(DominoUI_Instance.getPlayerPoints(Player2)));
		availableBonesTwoLValue.setText(String.valueOf(DominoUI_Instance.countPlayerBones(Player2)));
		playerTwoL.setText(DominoUI_Instance.getName(Player2));
		playerTwoBones.setListData(dominoesGlobal.convertBonestoStringArray(DominoUI_Instance.showPlayerBones(Player2)));

		//Other Values 		
		pointsTargetValueL.setText(String.valueOf(DominoUI_Instance.getTargetPoints()));
		roundValueL.setText(String.valueOf(DominoUI_Instance.getRound()));
		boneyardCountValueL.setText(String.valueOf(DominoUI_Instance.getBoneyardCount()));
		// Convert table layout of Bones[] to string and then display on GUI	

		if  (DominoUI_Instance.getBonesOnTable().length==0)
		{	
			lineOfPlayValueL.setText(dominoesGlobal.noBonesOntable);
		}
		else
		{
			lineOfPlayValueL.setText(dominoesGlobal.convertBonestoString(DominoUI_Instance.getBonesOnTable()));
			System.out.println(DominoUI_Instance.getBonesOnTable());
		}
	}

	/**
	 * Action to start a new game 
	 * 	 
	 */
	class startGameBHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if ((tPlayer2Name.getText().length() ==0) || 
					(tPlayer1Name.getText().length() == 0) ||
					(tPointsTarget.getText().length() ==0))
			{
				showMessage(dominoesGlobal.emptyTxtBoxes);
			}
			else
			{	
				setSize(dominoesGlobal.SCREEN_WIDTH,dominoesGlobal.SCREEN_HEIGHT);
				disableStartGameComponents();
				// calls procedure with values entered on the screen
				createNewGame(PlayerType.INTERACTIVE,
						PlayerType.INTERACTIVE,
						tPlayer1Name.getText(),tPlayer2Name.getText(),Integer.parseInt(tPointsTarget.getText()));

				enableGamePlayingComponents();
				loadGameComponentValues();
			}	
		}
	}

	/**
	 * Action to restart a game
	 */
	class restartStartGameBHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			createNewGame(PlayerType.INTERACTIVE,
					PlayerType.INTERACTIVE,
					tPlayer1Name.getText(),tPlayer2Name.getText(),Integer.parseInt(tPointsTarget.getText()));

			enableGamePlayingComponents();
			loadGameComponentValues();
		}

	}

	class exitStartGameBHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}


	/**
	 * Load Swing components of the main game play screen 
	 */
	public void loadGameScreenControls(){

		//Instantiate the labels:
		boneyardCountL = new JLabel("Boneyard Count: ");
		boneyardCountL.setLocation(0,0);
		boneyardCountL.setSize(100, 30);
		boneyardCountL.setHorizontalAlignment(0);
		boneyardCountL.setVisible(false);

		roundL = new JLabel("Round: ");
		roundL.setLocation(300,0);
		roundL.setSize(50, 30);
		roundL.setHorizontalAlignment(0);
		roundL.setVisible(false);

		pointsTargetL = new JLabel("Points Target: ");
		pointsTargetL.setLocation(500,0);
		pointsTargetL.setSize(100, 30);
		pointsTargetL.setHorizontalAlignment(0);
		pointsTargetL.setVisible(false);

		lineOfPlayL = new JLabel("Table Layout");
		lineOfPlayL.setLocation(300,100);
		lineOfPlayL.setSize(100, 30);
		lineOfPlayL.setHorizontalAlignment(0);
		lineOfPlayL.setVisible(false);		

		playerOneL = new JLabel();
		playerOneL.setLocation(0, 220);
		playerOneL.setSize(100, 30);
		playerOneL.setForeground(Color.RED);
		playerOneL.setHorizontalAlignment(0);
		playerOneL.setVisible(false);	

		playerTwoL = new JLabel();
		playerTwoL.setLocation(390, 220);
		playerTwoL.setSize(100, 30);
		playerTwoL.setForeground(Color.BLUE);
		playerTwoL.setHorizontalAlignment(0);
		playerTwoL.setVisible(false);	

		boneyardCountValueL = new JLabel();
		boneyardCountValueL.setLocation(25,20);
		boneyardCountValueL.setSize(50,30);
		boneyardCountValueL.setFont(new Font("Dialog", 1, 24));
		boneyardCountValueL.setHorizontalAlignment(0);
		boneyardCountValueL.setVisible(false);

		roundValueL = new JLabel();
		roundValueL.setLocation(299, 20);
		roundValueL.setSize(50,30);
		roundValueL.setFont(new Font("Dialog", 1, 24));
		roundValueL.setHorizontalAlignment(0);
		roundValueL.setVisible(false);

		pointsTargetValueL = new JLabel();
		pointsTargetValueL.setLocation(525, 20);
		pointsTargetValueL.setSize(50,30);
		pointsTargetValueL.setFont(new Font("Dialog", 1, 24));
		pointsTargetValueL.setHorizontalAlignment(0);
		pointsTargetValueL.setVisible(false);

		availableBonesOneL=new JLabel("Available Bones : ");
		availableBonesOneL.setLocation(20, 450);
		availableBonesOneL.setSize(100, 30);
		availableBonesOneL.setHorizontalAlignment(0);
		availableBonesOneL.setForeground(Color.RED);
		availableBonesOneL.setVisible(false);

		availableBonesOneLValue = new JLabel();
		availableBonesOneLValue.setLocation(110, 450);
		availableBonesOneLValue.setSize(30, 30);
		availableBonesOneLValue.setHorizontalAlignment(0);
		availableBonesOneLValue.setForeground(Color.RED);
		availableBonesOneLValue.setVisible(false);

		pointsScoredOneL = new JLabel("Scored Points : ");
		pointsScoredOneL.setLocation(14, 475);
		pointsScoredOneL.setSize(100, 30);
		pointsScoredOneL.setHorizontalAlignment(0);
		pointsScoredOneL.setForeground(Color.RED);
		pointsScoredOneL.setVisible(false);

		pointsScoredOneLValue = new JLabel();
		pointsScoredOneLValue.setLocation(75, 475);
		pointsScoredOneLValue.setSize(100, 30);
		pointsScoredOneLValue.setHorizontalAlignment(0);
		pointsScoredOneLValue.setForeground(Color.RED);
		pointsScoredOneLValue.setVisible(false);

		availableBonesTwoL=new JLabel("Available Bones : ");
		availableBonesTwoL.setLocation(420, 450);
		availableBonesTwoL.setSize(100, 30);
		availableBonesTwoL.setHorizontalAlignment(0);
		availableBonesTwoL.setForeground(Color.BLUE);
		availableBonesTwoL.setVisible(false);

		availableBonesTwoLValue= new JLabel();
		availableBonesTwoLValue.setLocation(510, 450);
		availableBonesTwoLValue.setSize(30, 30);
		availableBonesTwoLValue.setHorizontalAlignment(0);
		availableBonesTwoLValue.setForeground(Color.BLUE);
		availableBonesTwoLValue.setVisible(false);

		pointsScoredTwoL = new JLabel("Scored Points : ");
		pointsScoredTwoL.setLocation(414, 475);
		pointsScoredTwoL.setSize(100, 30);
		pointsScoredTwoL.setHorizontalAlignment(0);
		pointsScoredTwoL.setForeground(Color.BLUE);
		pointsScoredTwoL.setVisible(false);

		pointsScoredTwoLValue= new JLabel();
		pointsScoredTwoLValue.setLocation(475, 475);
		pointsScoredTwoLValue.setSize(100, 30);
		pointsScoredTwoLValue.setHorizontalAlignment(0);
		pointsScoredTwoLValue.setForeground(Color.BLUE);
		pointsScoredTwoLValue.setVisible(false);

		//Instantiate the text fields:
		lineOfPlayValueL = new JLabel();
		lineOfPlayValueL.setLocation(10, 125);
		lineOfPlayValueL.setSize(850,40);
		lineOfPlayValueL.setHorizontalAlignment(0);
		lineOfPlayValueL.setFont(new Font("Dialog", 1, 20));
		lineOfPlayValueL.setForeground(Color.DARK_GRAY);
		lineOfPlayValueL.setVisible(false);

		//Specify handlers for each button and add (register) ActionListeners to each button.
		exitGameB = new JButton("Exit Game");
		exitGameB.setLocation(350, 525);
		exitGameB.setSize(100,30);
		exitGameB.setHorizontalAlignment(0);

		ebHandler = new exitGameButtonHandler();
		exitGameB.addActionListener(ebHandler);
		exitGameB.setVisible(false);

		bRestartGame = new JButton("Start New Game");
		bRestartGame.setLocation(175, 525);	

		bRestartGame.setSize(150,30);
		bRestartGame.setHorizontalAlignment(0);

		rsGBHandler = new restartStartGameBHandler();
		bRestartGame.addActionListener(rsGBHandler);
		bRestartGame.setVisible(false);


		player1Flip= new JButton("Flip");
		player1Flip.setLocation(125, 250);
		player1Flip.setSize(90,30);
		player1Flip.setHorizontalAlignment(0);

		fp1bHandler = new player1FlipButtonHandler();
		player1Flip.addActionListener(fp1bHandler);
		player1Flip.setVisible(false);

		player1PlayRight= new JButton("Play Right");
		player1PlayRight.setLocation(125, 290);
		player1PlayRight.setSize(90,30);
		player1PlayRight.setHorizontalAlignment(0);

		prp1bHandler = new player1PlayRightButtonHandler();
		player1PlayRight.addActionListener(prp1bHandler);
		player1PlayRight.setVisible(false);

		player1PlayLeft= new JButton("Play Left");
		player1PlayLeft.setLocation(125, 330);
		player1PlayLeft.setSize(90,30);
		player1PlayLeft.setHorizontalAlignment(0);

		plp1bHandler = new player1PlayLeftButtonHandler();
		player1PlayLeft.addActionListener(plp1bHandler);
		player1PlayLeft.setVisible(false);

		player1Pass = new JButton("Pass");
		player1Pass.setLocation(125, 370);
		player1Pass.setSize(90, 30);
		player1Pass.setHorizontalAlignment(0);

		pap1bHandler = new player1PassButtonHandler();
		player1Pass.addActionListener(pap1bHandler);
		player1Pass.setVisible(false);

		player1Draw= new JButton("Draw");
		player1Draw.setLocation(125, 410);
		player1Draw.setSize(90, 30);
		player1Draw.setHorizontalAlignment(0);

		dp1bHandler = new player1DrawButtonHandler();
		player1Draw.addActionListener(dp1bHandler);
		player1Draw.setVisible(false);

		player2Flip= new JButton("Flip");
		player2Flip.setLocation(525, 250);
		player2Flip.setSize(90,30);
		player2Flip.setHorizontalAlignment(0);

		fp2bHandler = new player2FlipButtonHandler();
		player2Flip.addActionListener(fp2bHandler);
		player2Flip.setVisible(false);

		player2PlayRight= new JButton("Play Right");
		player2PlayRight.setLocation(525, 290);
		player2PlayRight.setSize(90,30);
		player2PlayRight.setHorizontalAlignment(0);

		prp2bHandler = new player2PlayRightButtonHandler();
		player2PlayRight.addActionListener(prp2bHandler);
		player2PlayRight.setVisible(false);

		player2PlayLeft= new JButton("Play Left");
		player2PlayLeft.setLocation(525, 330);
		player2PlayLeft.setSize(90,30);
		player2PlayLeft.setHorizontalAlignment(0);

		plp2bHandler = new player2PlayLeftButtonHandler();
		player2PlayLeft.addActionListener(plp2bHandler);
		player2PlayLeft.setVisible(false);

		player2Pass= new JButton("Pass");
		player2Pass.setLocation(525, 370);
		player2Pass.setSize(90,30);
		player2Pass.setHorizontalAlignment(0);

		pap2bHandler = new player2PassButtonHandler();
		player2Pass.addActionListener(pap2bHandler);
		player2Pass.setVisible(false);

		player2Draw= new JButton("Draw");
		player2Draw.setLocation(525, 410);
		player2Draw.setSize(90,30);
		player2Draw.setHorizontalAlignment(0);

		dp2bHandler = new player2DrawButtonHandler();
		player2Draw.addActionListener(dp2bHandler);
		player2Draw.setVisible(false);

		//Instantiate the lists and the scrollers				
		playerOneBones= new JList(); 
		playerOneBones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playerOneBones.setLayoutOrientation(JList.VERTICAL);
		playerOneBones.setVisibleRowCount(8);


		listOneScroller = new JScrollPane(playerOneBones);
		listOneScroller.setPreferredSize(new Dimension(250, 80));
		listOneScroller.setLocation(20, 250);
		listOneScroller.setSize(100,200);

		listOneScroller.setVisible(false);
		playerOneBones.setVisible(false);

		playerTwoBones= new JList(); 
		playerTwoBones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playerTwoBones.setLayoutOrientation(JList.VERTICAL);
		playerTwoBones.setVisibleRowCount(8);


		listTwoScroller = new JScrollPane(playerTwoBones);
		listTwoScroller.setPreferredSize(new Dimension(250, 80));
		listTwoScroller.setLocation(420, 250);
		listTwoScroller.setSize(100,200);

		listTwoScroller.setVisible(false);
		playerTwoBones.setVisible(false);

		Container pane = getContentPane();
		pane.setLayout(null);
		pane.setLocation(10, 0);
		pane.setSize(250, 30);

		//Add things to the pane in the order you want them to appear (left to right, top to bottom)
		pane.add(boneyardCountL);
		pane.add(boneyardCountValueL);
		pane.add(roundL);
		pane.add(roundValueL);
		pane.add(lineOfPlayL);
		pane.add(lineOfPlayValueL);
		pane.add(playerOneL);
		pane.add(playerTwoL);
		pane.add(player1Flip);
		pane.add(player1PlayRight);
		pane.add(player1PlayLeft);
		pane.add(player1Pass);
		pane.add(player1Draw);
		pane.add(availableBonesOneL);
		pane.add(availableBonesOneLValue);
		pane.add(pointsScoredOneL);
		pane.add(pointsScoredOneLValue);
		pane.add(listOneScroller);
		pane.add(player2Flip);
		pane.add(player2PlayRight);
		pane.add(player2PlayLeft);
		pane.add(player2Pass);
		pane.add(player2Draw);
		pane.add(listTwoScroller);
		pane.add(availableBonesTwoL);
		pane.add(availableBonesTwoLValue);
		pane.add(pointsScoredTwoL);
		pane.add(pointsScoredTwoLValue);
		pane.add(pointsTargetL);
		pane.add(pointsTargetValueL);
		pane.add(exitGameB);
		pane.add(bRestartGame);

		//Display settings
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Shows a message pop-up  
	 * @param String message
	 */
	public void showMessage(String message){
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, message);
	}

	public void disableStartGameComponents(){
		lFormHeading.setVisible(false);
		lInstruction.setVisible(false);
		lPlayer1Name.setVisible(false);
		lPlayer2Name.setVisible(false);
		tPlayer1Name.setVisible(false);
		tPlayer2Name.setVisible(false);
		bStartDominoGame.setVisible(false);
		bExitGame.setVisible(false);
		lPointsTarget.setVisible(false);
		tPointsTarget.setVisible(false);
	}

	/**
	 * Loads swing components of the startup screen. 
	 */
	public void loadStartScreenComponents(){

		lFormHeading= new JLabel("Welcome to Tirthankars' Domino Royale");
		lFormHeading.setLocation(5, 0);
		lFormHeading.setSize(500, 30);
		lFormHeading.setHorizontalAlignment(0);


		lInstruction = new JLabel("Please enter Player names and click on Start to begin.");
		lInstruction.setLocation(1, 55);
		lInstruction.setSize(500, 20);
		lInstruction.setHorizontalAlignment(0);

		lPlayer1Name= new JLabel("Player 1 Name:");
		lPlayer1Name.setLocation(1,100);
		lPlayer1Name.setSize(100, 20);
		lPlayer1Name.setHorizontalAlignment(0);

		lPlayer2Name= new JLabel("Player 2 Name:");
		lPlayer2Name.setLocation(1,150);
		lPlayer2Name.setSize(100, 20);
		lPlayer2Name.setHorizontalAlignment(0);

		tPlayer1Name= new JTextField();
		tPlayer1Name.setLocation(150, 100);
		tPlayer1Name.setSize(100, 30);
		tPlayer1Name.setHorizontalAlignment(0);
		tPlayer1Name.setColumns(5);

		tPlayer2Name= new JTextField();
		tPlayer2Name.setLocation(150,150);
		tPlayer2Name.setSize(100, 30);
		tPlayer2Name.setHorizontalAlignment(0);

		lPointsTarget = new JLabel("Points Target:");
		lPointsTarget.setLocation(1,195);
		lPointsTarget.setSize(100, 30);
		lPointsTarget.setHorizontalAlignment(0);

		tPointsTarget = new JTextField();
		tPointsTarget.setLocation(150,200);
		tPointsTarget.setSize(100, 30);
		tPointsTarget.setHorizontalAlignment(0);

		bStartDominoGame=new JButton("Start Game");
		bStartDominoGame.setLocation(50, 250);
		bStartDominoGame.setSize(100,35);
		bStartDominoGame.setHorizontalAlignment(0);
		sGBHandler = new startGameBHandler();
		bStartDominoGame.addActionListener(sGBHandler);

		bExitGame=new JButton("Exit");
		bExitGame.setLocation(200, 250);
		bExitGame.setSize(100,35);
		bExitGame.setHorizontalAlignment(0);
		eGBHandler = new exitStartGameBHandler(); 
		bExitGame.addActionListener(eGBHandler);

		setTitle("Welcome to Tirthankar's Domino Interactive Game");
		Container pane = getContentPane();
		pane.setLayout(null);
		pane.setLocation(10, 0);
		pane.setSize(250, 30);


		pane.add(lFormHeading);
		pane.add(lInstruction);
		pane.add(lPlayer1Name);
		pane.add(tPlayer1Name);
		pane.add(lPlayer2Name);
		pane.add(tPlayer2Name);
		pane.add(lPointsTarget);
		pane.add(bStartDominoGame);
		pane.add(tPointsTarget);
		pane.add(bExitGame);

		//Display settings
		setSize(dominoesGlobal.SCREEN_WIDTH-200,dominoesGlobal.SCREEN_HEIGHT-100);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Enables the main game play components 
	 */

	public void enableGamePlayingComponents() {
		boneyardCountL.setVisible(true);
		roundL.setVisible(true);
		pointsTargetL.setVisible(true);
		lineOfPlayL.setVisible(true);	
		playerOneL.setVisible(true);	
		playerTwoL.setVisible(true);	
		boneyardCountValueL.setVisible(true);
		roundValueL.setVisible(true);
		pointsTargetValueL.setVisible(true);
		availableBonesOneL.setVisible(true);
		availableBonesOneLValue.setVisible(true);
		pointsScoredOneL.setVisible(true);
		pointsScoredOneLValue.setVisible(true);
		availableBonesTwoL.setVisible(true);
		availableBonesTwoLValue.setVisible(true);
		pointsScoredTwoL.setVisible(true);
		pointsScoredTwoLValue.setVisible(true);
		lineOfPlayValueL.setVisible(true);
		exitGameB.setVisible(true);
		player1Flip.setVisible(true);
		player1PlayRight.setVisible(true);
		player1PlayLeft.setVisible(true);
		player1Pass.setVisible(true);
		player1Draw.setVisible(true);
		player2Flip.setVisible(true);
		player2PlayRight.setVisible(true);
		player2PlayLeft.setVisible(true);
		player2Pass.setVisible(true);
		player2Draw.setVisible(true);
		listOneScroller.setVisible(true);
		playerOneBones.setVisible(true);
		listTwoScroller.setVisible(true);
		playerTwoBones.setVisible(true);
		bRestartGame.setVisible(true);
	}

}
