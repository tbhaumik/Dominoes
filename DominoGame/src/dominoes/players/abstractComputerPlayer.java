package dominoes.players;

import dominoes.Bone;
import dominoes.BoneYard;
import dominoes.CantPlayException;
import dominoes.Play;
import dominoes.Table;

/**
 * 
 *This is the abstract Computer Player class. 
 *
 *@author Tirthankar Bhaumik
 *@see abstractPlayer
 */
public abstract class abstractComputerPlayer extends abstractPlayer {
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
	 * player will draw a bone from the boneyard and append 
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
	 * @return String  name
	 */
	@Override
	public String getName() {
		return null;
	}
	/**
	 * Returns a players' points 
	 * 
	 * @return integer points
	 */	
	@Override
	public int getPoints() {
		return 0;
	}
	/**
	 * Picks a bone to play and where it should be played left or 
	 * right of the table. This method picks up the first playable Bone.
	 * For this it goes through all the players' bones and checks if it
	 * can be played on either ends of the table as it is, or flip'ed.
	 * If no bones match then a CantPlayException is thrown. 
	 * 
	 * @return Play 
	 */
	@Override
	public Play makePlay(Table arg0) throws CantPlayException {
		return null;
	}
	/**
	 * Clear out a players hand  
	 */
	@Override
	public void newRound() {
	}
	/**
	 * Returns the number of bones in hand
	 * 
	 * @return integer number of bones in hand
	 */
	@Override
	public int numInHand() {
		return 0;
	}
	/**
	 * Sets the name of the player
	 * 
	 * @param String name
	 */
	@Override
	public void setName(String arg0) {
	}
	/**
	 * Sets the points of a player
	 * 
	 * @param integer points
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

}
