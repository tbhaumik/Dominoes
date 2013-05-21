package dominoes.players;

import java.util.ArrayList;
import java.util.Collections;
import dominoes.Bone;
import dominoes.BoneYard;
import dominoes.CantPlayException;
import dominoes.Play;
import dominoes.Table;
import dominoes.dominoesGlobal;

/**
 * 
 *This is the Interactive Player class. 
 *
 *@author Tirthankar Bhaumik
 *@see computerPlayer
 */
public class interactivePlayer extends abstractInteractivePlayer {

	ArrayList<Bone> alistPlayerBones = null; //stores bones in array list to avoid re-sizing
	String sPlayerName;	//stores the name of the player
	int iPointsScored; //stores points scored
	int iLastAction; //records the last action of the player - either Play or Pass

	//the two values below will be used to create a play object
	//based on the bone and the end of the table the Player decided to play on.
	Bone bonePlay = null;
	int tableEnd;
	//********************

	/**
	 * Constructor - initialises points to 0 and 
	 * creates a new Array list of bones.
	 */
	public interactivePlayer(){
		iPointsScored= 0; 
		alistPlayerBones = new ArrayList<>();
	}

	/**
	 * Returns the bones in hand as an array of Bone objects
	 * 
	 * @return bones held by the player as an array of Bones[] 
	 */
	@Override
	public Bone[] bonesInHand() {
		Bone[] arPlayerBones = new Bone[alistPlayerBones.size()];
		//convert arraylist to array and then return it
		alistPlayerBones.toArray(arPlayerBones);
		return arPlayerBones;
	}

	/**
	 * Player will draw a bone from the boneyard and append 
	 * to his array list of bones
	 * 
	 * @param BoneYard 
	 */
	@Override
	public void draw(BoneYard BONEYARD){
		alistPlayerBones.add(BONEYARD.draw());
	}

	/**
	 * Returns the name of the player  
	 * 
	 * @return String name
	 */
	@Override
	public String getName() {

		return sPlayerName;
	}

	/**
	 * Returns a players' points 
	 * 
	 * @return integer points
	 */
	@Override
	public int getPoints() {

		return iPointsScored;
	}

	/**
	 * Sets the Bone to play and which end of the 
	 * table to play on, based on the players' selection,
	 * from a GUI for example 
	 * 
	 * @param Bone
	 * @param integer
	 */
	public void setPlayBone(Bone pBone, int itableEnd){
		bonePlay=pBone;
		tableEnd= itableEnd;
	}


	/**
	 * Picks a bone to play and where it should be played left or 
	 * right of the table. 
	 * 
	 * @return Play 
	 */
	@Override
	public Play makePlay(Table dominoTable) throws CantPlayException {
		// An interactive player will visually choose (from GUI) which Bone to play
		// and which side of the table layout to play on and the setPlayBone method
		// will be called.
		//Please see computerPlayer.java class for different implementation.
		Play playerPlay = null;
		if (bonePlay!=null){
			playerPlay = new Play(bonePlay,tableEnd);
		}
		else
		{
			throw new CantPlayException(dominoesGlobal.makePlayMsg);
		}
		return playerPlay;
	}

	/**
	 * Clear out a players hand  
	 */
	@Override
	public void newRound() {

		alistPlayerBones.clear();
	}

	/**
	 * Returns the number of bones in hand
	 * 
	 * @return integer number of bones in hand
	 */
	@Override
	public int numInHand() {

		return alistPlayerBones.size();
	}

	/**
	 * Sets the name of the player
	 * 
	 * @param String name
	 */
	@Override
	public void setName(String sName) {

		sPlayerName=sName;
	}

	/**
	 * Sets the points of a player
	 * 
	 * @param integer points
	 */
	@Override
	public void setPoints(int iPoints) {

		iPointsScored=iPoints;
	}

	/**
	 * Tell the player to take back the specified bone
	 * This usually happens when the player has tried to play an invalid bone.
	 * The Bone will added back to the players' bones array list.
	 * 
	 * @param Bone
	 */
	@Override
	public void takeBack(Bone takeBackBone) {
		alistPlayerBones.add(takeBackBone);
	}

	/**
	 * Updates a players stack of Bones
	 * with the Bone array passed in input
	 * 
	 * @param Bone[] array
	 */
	public void updatePlayerBones(Bone[] updatedBones){
		//updates the bones a player holds - for example, if the players flips a  bone (but may not play)
		alistPlayerBones.clear();
		//copy
		Collections.addAll(alistPlayerBones, updatedBones);
	}

	/**
	 * Sets the last action of the player
	 * 
	 * @param integer PLAY or PASS
	 */
	public void setLastAction(int iAction){
		iLastAction=iAction;
	}

	/**
	 * Gets the last action of the player
	 * 
	 * @return integer PLAY or PASS
	 */
	public int getLastAction(){
		return iLastAction;
	}

	/**
	 * Gets sum of all the numbers on the bones 
	 * a player has.
	 * 
	 * @return integer weight in hand
	 */
	public int weightInHand(){
		//returns the total weight in hand
		int iweightInHand=0;
		//loop through and sum the l and r values of every bone
		for(int i=0;i<=alistPlayerBones.size()-1;i++)
		{	
			iweightInHand = iweightInHand+alistPlayerBones.get(i).left()+alistPlayerBones.get(i).right();
		}
		return iweightInHand;
	}
}
