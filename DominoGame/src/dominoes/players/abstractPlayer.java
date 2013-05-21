package dominoes.players;

import dominoes.Bone;
import dominoes.BoneYard;
import dominoes.CantPlayException;
import dominoes.Play;
import dominoes.Table;

/**
 *This is an abstract Player class
 * 
 *@author Tirthankar Bhaumik
 *@see abstractComputerPlayer
 *@see abstractInteractivePlayer
 */

public abstract class abstractPlayer implements DominoPlayer{
	//types of player actions
	public final static int PLAY=1;
	public final static int PASS=2;
	
	/**
	 * Returns the bones in hand as an array of Bone objects
	 * 
	 * @return bones held by the player as an array of Bones[] 
	 */	
	@Override
	public Bone[] bonesInHand() {
		return null;
	} 
	/**
	 * Player will draw a bone from the boneyard and append 
	 * to his array list of bones
	 * 
	 * @param BoneYard 
	 */
	@Override
	public void draw(BoneYard arg0) {
	}

	/**
	 * Returns the name of the player 
	 *  
	 * @return String 
	 */
	@Override
	public String getName() {
		return null;
	}

	/**
	 * Returns a players' points 
	 * 
	 * @return integer 
	 */
	@Override
	public int getPoints() {
		return 0;
	}
	/**
	 * Picks a bone to play and where it should be played left or 
	 * right of the table. 
	 *  
	 * @return Play 
	 */
	@Override
	public Play makePlay(Table arg0) throws CantPlayException {
		return null;
	}

	/**
	 * Clears the players hands
	 */
	@Override
	public void newRound() {
	}
	/**
	 * Returns the number of bones in hand
	 * 
	 * @return integer bones in hand
	 */
	@Override
	public int numInHand() {
		return 0;
	}
	/**
	 * Sets the name of the player
	 * 
	 * @param String Name
	 */
	@Override
	public void setName(String arg0) {

	}

	/**
	 * Sets the points of a player
	 * 
	 * @param integer Points
	 */
	@Override
	public void setPoints(int arg0) {
	}

	/**
	 * Tell the player to take back the specified bone
	 * This usually happens when the player has tried to play an invalid bone.
	 * The Bone will added back to the players' bones array list.
	 * 
	 * @param Bone
	 */
	@Override
	public void takeBack(Bone arg0) {
	}

	/**
	 * Sets the last action of the player
	 * 
	 * @param integer PLAY or PASS
	 */
	public void setLastAction(int iAction){
	}

	/**
	 * Gets the last action of the player
	 * 
	 * @return integer PLAY or PASS
	 */
	public int getLastAction(){
		return 0;
	}

	/**
	 * Gets sum of all the numbers on the bones 
	 * a player has.
	 * 
	 * @return integer weight in hand
	 */
	public int weightInHand() {
		return 0;
	}

	/**
	 * Updates a players stack of Bones
	 * with the Bone array passed in input 
	 * 
	 * @param Bone[] array of Bones
	 */
	public void updatePlayerBones(Bone[] updatedBones){
	}

	/**
	 * Sets the Bone to play and which end of the 
	 * table to play on, based on the players' selection,
	 * from a GUI for example 
	 * 
	 * @param Bone
	 * @param integer Play.LEFT or PLAY.RIGHT
	 */
	public void setPlayBone(Bone pBone, int wtableEnd){
	}

}
