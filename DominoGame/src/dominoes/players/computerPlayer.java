package dominoes.players;

import java.util.ArrayList;
import dominoes.Bone;
import dominoes.BoneYard;
import dominoes.CantPlayException;
import dominoes.Play;
import dominoes.Table;
import dominoes.dominoesGlobal;

/**
 * 
 *This is the Computer Player class. 
 *
 *@author Tirthankar Bhaumik
 *@see interactivePlayer
 */
public class computerPlayer extends abstractComputerPlayer{

	ArrayList<Bone> alistPlayerBones = null; //stores bones in array list to avoid re-sizing
	String sPlayerName;	//stores the name of the player
	int iPointsScored; //stores points scored

	/**
	 * Constructor - initialises points to 0 and 
	 * creates a new Array list of bones.
	 */
	public computerPlayer(){
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
	 * player will draw a bone from the boneyard and append 
	 * to his array list of bones
	 * 
	 * @param BoneYard 
	 */
	@Override
	public void draw(BoneYard BONEYARD) {
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
	 * Picks a bone to play and where it should be played left or 
	 * right of the table. This method picks up the first playable Bone.
	 * For this it goes through all the players' bones and checks if it
	 * can be played on either ends of the table as it is, or flip'ed.
	 * If no bones match then a CantPlayException is thrown. 
	 * 
	 * @return Play 
	 */
	@Override
	public Play makePlay(Table dominoTable) throws CantPlayException {
		//Play variable
		Play playerPlay = null;
		//check if player has available bones 
		if (!alistPlayerBones.isEmpty()){
			//retrieve the table layout
			Bone[] dominoTableLayout = dominoTable.layout();
			//if the table is empty,i.e. have any bones been played?
			if (dominoTableLayout.length==0){
				//create play object with players' first bone and
				//play on the right of the table
				Bone tempBone = alistPlayerBones.get(0);
				playerPlay = new Play(tempBone,Play.RIGHT);
				//remove the bone from the players' hands
				alistPlayerBones.remove(0);
			}
			else //some bones have already been played  on the table
			{
				//get iterator
				java.util.Iterator<Bone> iTR = alistPlayerBones.iterator();
				//iterator over every player bone
				while(iTR.hasNext()) {
					Bone cBone = iTR.next();
					if (cBone.left()==dominoTable.right()){
						playerPlay = new Play(cBone,Play.RIGHT);
						iTR.remove(); //remove the bone from the players hands
						break;
					}
					else if (cBone.right()==dominoTable.left()){
						playerPlay = new Play(cBone,Play.LEFT);
						iTR.remove();//remove the bone from the players hands
						break;
					}
					else{// Bone as it is does not match
						//FLIP the Bone
						cBone.flip();
						//do the comparison again
						if (cBone.left()==dominoTable.right()){
							playerPlay = new Play(cBone,Play.RIGHT);
							iTR.remove();//remove the bone from the players hands
							break;
						}
						else if (cBone.right()==dominoTable.left()){
							playerPlay = new Play(cBone,Play.LEFT);
							iTR.remove();//remove the bone from the players hands
							break;
						}
					}
				} 

			}
		}
		else
		{
			throw new CantPlayException(dominoesGlobal.noPlayerBones);
		}
		//if no playable bones with player  
		if (playerPlay==null){
			throw new CantPlayException(dominoesGlobal.noPlayerBones);
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

		sPlayerName = sName;

	}

	/**
	 * Sets the points of a player 
	 * 
	 * @param integer points
	 */
	@Override
	public void setPoints(int iPoints) {

		iPointsScored = iPoints;

	}

	/**
	 * Tell the player to take back the specified bone
	 * This usually happens when the player has tried to play an invalid bone.
	 * The Bone will added back to the players' bones array list.
	 * @param Bone
	 */
	@Override
	public void takeBack(Bone takeBackBone) {

		alistPlayerBones.add(takeBackBone);

	}

}
