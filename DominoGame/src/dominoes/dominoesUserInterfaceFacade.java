package dominoes;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import dominoes.players.abstractPlayer;
import dominoes.players.abstractPlayerFactory;
import dominoes.players.DominoPlayer;
import dominoes.players.computerPlayerFactory;
import dominoes.players.interactivePlayerFactory;

/**
 * 
 * This class will be a FACADE (Design Pattern) and has been implemented as
 * a SINGLETON (Design Pattern). This is a high-level interface 
 * that makes subsystem easy to use. This will sit between the GUI(the client)
 * and the subsystem. Advantage is that the GUI (client) can be implemented 
 * independently of the subsystem.
 * 
 * This class can also be extended to works as a MEDIATOR (Design Pattern)
 * for enabling / disabling GUI controls based on user actions.
 *  
 * @author Tirthankar Bhaumik
 * @see interactivePlayerScreen
 */
public class dominoesUserInterfaceFacade implements dominoes.DominoUI{

	//static instance variable
	private static dominoesUserInterfaceFacade instance;

	//****Declare the game variables****

	private BoneYard BONEYARD = null;
	private Table tTableLayout = null;

	//number of points necessary to win the game
	//set to 0, by default
	private int iTargetPoints=0; 

	//Track the round
	int iDominoGameRound = 0;

	//Dominoes Game object
	private Dominoes dDominoesGame = null;

	//Bone and direction to play that a player selects on the GUI
	//wrapped in a play object
	Play playerPlay = null;

	//**********************************

	/**
	 * This method will only instantiate
	 * the object once and so the same
	 * instance will be returned every time.
	 * 
	 * @return dominoesUserInterfaceFacade instance
	 */
	public synchronized static dominoesUserInterfaceFacade getInstance(){
		if (instance == null){
			instance = new dominoesUserInterfaceFacade();
		}
		return instance;
	}

	/**
	 * Private constructor
	 */
	private dominoesUserInterfaceFacade() {}

	/**
	 * Sets the number of points required to win the game.
	 * 
	 * @param number of points required to win the game
	 */
	public void setTargetPoints(int iPointGoal){
		iTargetPoints=iPointGoal;
	}

	/**
	 * Gets the number of points required to win the game.
	 * 
	 * @return number of points required to win the game
	 */	
	public int getTargetPoints(){
		return iTargetPoints;
	}

	/**
	 * Creates the two players using a Player Factory (Design Pattern). Thus this class does not 
	 * any access to the actual/concrete player classes, only the abstract player classes will do.
	 * Using this method you can specify - a player can play against the computer, 
	 * the computer against itself, or two interactive players play against each other.
	 * 
	 * @param Player1 enumeration - COMPUTER or INTERACTIVE
	 * @param Player2 enumeration - COMPUTER or INTERACTIVE
	 * @return abstractPlayer[]
	 */
	public abstractPlayer[] createPlayers(abstractPlayerFactory.PlayerType playerType1, abstractPlayerFactory.PlayerType playerType2 ){

		//an array of players
		abstractPlayer[] gamePlayers = new abstractPlayer[dominoesGlobal.NO_OF_PLAYERS];

		abstractPlayerFactory computerPlayerFactory = new computerPlayerFactory();
		abstractPlayerFactory interactivePlayerFactory = new interactivePlayerFactory();

		if (playerType1==abstractPlayerFactory.PlayerType.INTERACTIVE)
		{
			gamePlayers[0]=  interactivePlayerFactory.createPlayer(abstractPlayerFactory.PlayerType.INTERACTIVE);
		}
		else
		{
			gamePlayers[0]=  computerPlayerFactory.createPlayer(abstractPlayerFactory.PlayerType.COMPUTER);
		}

		if (playerType2==abstractPlayerFactory.PlayerType.INTERACTIVE)
		{
			gamePlayers[1] = interactivePlayerFactory.createPlayer(abstractPlayerFactory.PlayerType.INTERACTIVE);
		}
		else
		{
			gamePlayers[1] = computerPlayerFactory.createPlayer(abstractPlayerFactory.PlayerType.COMPUTER);
		}

		return gamePlayers;
	}

	/**
	 * Initialises a new game, a player will be 
	 * able to start a new game at any time.
	 * 
	 * @param int
	 * @param DominoPlayer
	 * @param DominoPlayer
	 * @param int 
	 */	
	public void startNewGame(int iPointsGoal,abstractPlayer Player1, abstractPlayer Player2){
		//initialise the boneyard
		BONEYARD = new BoneYard(dominoesGlobal.MAX_DOTS);

		//initialise the table to play the bones on
		tTableLayout = new Table();

		//initialise the round count
		this.setRound(1);

		//set target points
		this.setTargetPoints(iPointsGoal);


		//clearout the players' hands 
		Player1.newRound();
		Player2.newRound();

		//initialise players' scores
		Player1.setPoints(0);
		Player2.setPoints(0);

		Player1.setLastAction(0);
		Player2.setLastAction(0);

		//create a Dominoes' game
		dDominoesGame = new Dominoes((DominoUI) this, Player1, Player2, iTargetPoints, dominoesGlobal.MAX_DOTS);
	}

	/**
	 * Gets the current round
	 * of the game.
	 * 
	 * @return int
	 */	
	public int getRound(){
		return iDominoGameRound;
	}

	/**
	 * Sets the current round
	 * of the game
	 * 
	 * @param int
	 */	
	public void setRound(int iRound){
		iDominoGameRound = iRound;
	}

	/**
	 * Shows the number of bones a player has.
	 * This method is agnostic of the type of 
	 * player - Computer OR Interactive.
	 * Will work for both types.
	 * 
	 * @param DominoPlayer
	 * @return int
	 */	
	public int countPlayerBones(DominoPlayer Player){
		return Player.numInHand();
	}

	/**
	 * Returns the bones in hand for a player 
	 * as an array of Bone objects.
	 * This method is agnostic of the type of 
	 * player - Computer OR Interactive.
	 * Will work for both types.
	 * 
	 * @param DominoPlayer
	 * @return Bones[]
	 */	
	public Bone[] showPlayerBones(DominoPlayer Player){
		return Player.bonesInHand();
	}

	/**
	 * Shows the number of Bones left in the Boneyard 
	 *  
	 * @return int
	 */	
	public int getBoneyardCount(){
		return this.BONEYARD.size();
	}

	/**
	 * Shows the Bones played on the table  
	 * 
	 * @return Bone[]
	 */
	public Bone[] getBonesOnTable(){
		return this.tTableLayout.layout();
	}

	/**
	 * Returns False if bones have been played on
	 * the table, else returns True.
	 * 
	 * @return Boolean
	 */
	public boolean isTableEmpty(){
		if ((this.tTableLayout.layout().length)>0)
		{
			return false;
		}
		return true;
	}

	/**
	 * Method for a player to draw a tile
	 * from the Boneyard  
	 * 
	 * @return Bone[]
	 * @throws emptyBoneyardException 
	 * 
	 * NOTE
	 * In terms of design, it would have been better if I were to 
	 * throw this exception from the Player class, but the DominoPlayer
	 * interface does not allow this. 
	 */
	public void drawBone(DominoPlayer Player) throws emptyBoneyardException{
		//check if Boneyard is empty
		if (this.getBoneyardCount()>0){
			Player.draw(this.BONEYARD);
		}
		else
		{
			//boneyard empty, throw an exception
			throw new emptyBoneyardException(dominoesGlobal.boneyardEmptyMsg);
		}
	}	
	

	/**
	 * Method for a player to pass his turn.
	 * It is only the players' name that will
	 * uniquely identify which player passed.
	 * This is required because if BOTH players
	 * pass then the round ends (and possibly the game).
	 * Exception will be thrown if Bones available in the
	 * boneyard but player chooses to pass.
	 * 
	 * @param String
	 * @throws illegalPassException 
	 */
	public void passTurn(String playerName, abstractPlayer Player1, abstractPlayer Player2) throws illegalPassException{

		// if bones available to draw then throw exception
		if (this.getBoneyardCount()>0){
			throw new illegalPassException(dominoesGlobal.illegalPassMsg);
		}

		// Determine which player passed
		if (playerName.equals(Player1.getName())){
			//Player 1 passed
			if (Player2.getLastAction()==abstractPlayer.PASS){ 
				//the other players' (Player 2) last action was also PASS 
				//do round end processing
				endRound(Player1,Player2);
			}
			else
			{
				// sets last action to PASS
				Player1.setLastAction(abstractPlayer.PASS);
			}
		}
		else //Player 2 passed
		{
			if (Player1.getLastAction()==abstractPlayer.PASS){ 
				//the other players' (Player 1) last action was also PASS
				//do round end processing
				endRound(Player1,Player2);
			}
			else
			{
				// sets last action to PASS
				Player2.setLastAction(abstractPlayer.PASS);
			}
		}
	}

	/**
	 * Shows the number of points each player has. 
	 * 
	 * @param abstractPlayer
	 * @return integer
	 */	
	public int getPlayerPoints(abstractPlayer Player){
		return Player.getPoints();
	}

	/**
	 * Sets the Bone to play and which end of the 
	 * table to play on, based on the players' selection,
	 * from a GUI for example. This is will only be called by an
	 * interactive Player. 
	 * 
	 * @param AbstractPlayer
	 * @param Bone
	 * @param integer
	 */	
	public void createPlay(abstractPlayer iPlayer, Bone playerBone,int tableEnd){
		iPlayer.setPlayBone(playerBone, tableEnd);		
	}
	
	/**
	 * This method will do the following :
	 * 1. Find out who won the round and calculate points
	 * 2. Check if a player achieved target score
	 * 3. Check if the game is over
	 * 4. Show Game or Round winner
	 * 3. Initialise next round or game 
	 */	
	public void endRound(abstractPlayer Player1, abstractPlayer Player2){
		//find out the winner of the round 
		if (Player1.weightInHand()<Player2.weightInHand())
		{
			//Player 1 is winner of the round - calculate points
			Player1.setPoints(Player1.getPoints()+Player2.weightInHand());
			//check if Player 1 has achieved target points
			if (Player1.getPoints()>=iTargetPoints)
			{
				//game over - Player 1 has won
				//display game winner
				displayGameWinner(Player1);
			}
			else //no, then go to next round
			{
				//show round winner
				displayRoundWinner(Player1);
				//next round
				newRound(Player1,Player2);
			}
		}
		else if (Player1.weightInHand()>Player2.weightInHand()){

			//Player 2 is winner - calculate points	
			Player2.setPoints(Player2.getPoints()+Player1.weightInHand());
			//check if player 2 had achieved target
			if (Player2.getPoints()>=iTargetPoints)
			{
				//game over - Player 2 has won
				//display game winner
				displayGameWinner(Player2);
			}
			else
			{
				//show round winner
				displayRoundWinner(Player2);
				//next round
				newRound(Player1,Player2);
			}
		}
		else //this is a draw 
		{
			//display Draw message
			displayRoundWinner(null);
			//next round
			newRound(Player1,Player2);
		}
	}

	/**
	 *  This method will do the following :
	 *  1. Announce the round winner
	 *  2. Re-initialise to begin a new round
	 */	
	private void newRound(abstractPlayer roundWinner, abstractPlayer roundLoser){
		//Initialise a new round		
		//clear out both players hands
		roundWinner.newRound(); 
		roundLoser.newRound();
		//increment the round
		this.iDominoGameRound++; 
		//clear the table layout
		this.tTableLayout = new Table();
		//re-initialise boneyard
		this.BONEYARD = new BoneYard(dominoesGlobal.MAX_DOTS); 
	}

	/**
	 *  This method will display the game winner.
	 */	
	private void displayGameWinner(abstractPlayer gameWinner){
		//show the game winner
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, gameWinner.getName()+" wins round"+Integer.toString(iDominoGameRound)+
				" and the game. This game will now end.");
		System.exit(0);
	}	

	/**
	 * Sets a players name
	 * 
	 * @param AbstractPlayer
	 * @param String
	 */	
	public void setName(abstractPlayer aPlayer,String sPName){
		aPlayer.setName(sPName);
	}

	/**
	 * Gets a players name
	 * 
	 * @param AbstractPlayer
	 * @return String
	 */	
	public String getName(abstractPlayer aPlayer){
		return aPlayer.getName();
	}

	/**
	 * Locates a Bone on the Players array
	 * and then flips it
	 * 
	 * @param AbstractPlayer
	 * @param String
	 */	
	public void flipPlayerBone(abstractPlayer aPlayer,String boneToFlip){

		Bone tempBoneToFlip = dominoesGlobal.convertStringToBone(boneToFlip);
		//get Players bones into a temporary array
		Bone tempArray [] = aPlayer.bonesInHand();
		//loop through to find the correct bone
		for(int i=0;i<=tempArray.length-1;i++)
		{
			if (tempBoneToFlip.equals(tempArray[i]))
			{	
				//found it... FLIP it
				tempArray[i].flip();
				//update Players' bones
				aPlayer.updatePlayerBones(tempArray);
			}
		}
	}

	/**
	 * Locates a Bone on the Players array
	 * and then deletes it.
	 * 
	 * @param AbstractPlayer
	 * @param Bone
	 */	
	public void removePlayerBone(abstractPlayer aPlayer,Bone boneToDelete){
		//get Players bones into a temporary arraylist
		ArrayList<Bone> tempArray = new ArrayList<>(); 
		Collections.addAll(tempArray, aPlayer.bonesInHand());

		//remove the bone that was played successfully
		for(int i=0; i<=tempArray.size()-1;i++)
		{
			Bone delBone = tempArray.get(i);
			if (delBone.left()==boneToDelete.left() && delBone.right()==boneToDelete.right())
			{
				tempArray.remove(i);
			}
		}
		//convert arraylist to array
		Bone[] tempPBones = new Bone[tempArray.size()];
		tempPBones = tempArray.toArray(tempPBones);
		//update the bones that the player holds
		aPlayer.updatePlayerBones(tempPBones);

	}

	/**
	 * Creates a PLAY object and returns it.
	 * 
	 * @param Abstract Player
	 * @return Play
	 * @throws CantPlayException 
	 */
	public Play makePlayerPlay(abstractPlayer aPlayer) throws CantPlayException{
		return aPlayer.makePlay(this.tTableLayout);
	}

	/**
	 * Plays a bone to the Domino table.
	 * 
	 * @param Play
	 * @throws InvalidPlayException 
	 */
	public void playToTable(Play pPlay) throws InvalidPlayException{
		this.tTableLayout.play(pPlay);
	}

	/**
	 * Sets a players last action
	 * 
	 * @param Player
	 * @param int
	 */
	public void setLastPlayerAction(abstractPlayer aPlayer,int iPlayerAction) {
		aPlayer.setLastAction(iPlayerAction);
	}

	/**
	 * This method displays the current state of the players,
	 * the table, and the boneyard as appropriate for the UI.
	 * 
	 * @param DominoPlayer[]
	 * @param Table
	 * @param BoneYard
	 */
	@Override
	public void display(DominoPlayer[] dominoPlayers, Table dominoTable, BoneYard dominoBoneyard) {
		String sGameTableLayout = dominoesGlobal.convertBonestoString(dominoTable.layout());

		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, dominoPlayers[1].getName()+" has "+dominoPlayers[1].getPoints()+". "+
				dominoPlayers[2].getName()+" has "+dominoPlayers[2].getPoints()+". "+"Boneyard size is "+dominoBoneyard.size()
				+"and the table layout is "+sGameTableLayout);	
	}

	/**
	 * Displays that the specified player made an 
	 * illegal play.
	 * 
	 * @param DominoPlayer
	 */
	@Override
	public void displayInvalidPlay(DominoPlayer dPlayer) {
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, dPlayer.getName()+" has made an incorrect play.");		
	}

	/**
	 * This method displays the winner for the round.
	 * 
	 * @param DominoPlayer
	 */	
	@Override
	public void displayRoundWinner(DominoPlayer dPlayer) {
		JFrame frame = new JFrame();
		if (dPlayer!=null){
			JOptionPane.showMessageDialog(frame, dPlayer.getName()+" wins round "+
					Integer.toString(iDominoGameRound)+" Next round will begin.");
		}
		else
		{
			JOptionPane.showMessageDialog(frame,dominoesGlobal.drawMsg);
		}
	}
}
