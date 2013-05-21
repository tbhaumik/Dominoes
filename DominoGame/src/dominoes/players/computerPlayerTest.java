package dominoes.players;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import dominoes.Bone;
import dominoes.BoneYard;
import dominoes.CantPlayException;
import dominoes.DominoUI;
import dominoes.Dominoes;
import dominoes.InvalidPlayException;
import dominoes.Play;
import dominoes.Table;
import dominoes.dominoesGlobal;
import dominoes.dominoesUserInterfaceFacade;

/**
 * Tests for the computerPlayer class
 * @author Tirthankar Bhaumik
 */
public class computerPlayerTest {
	private static computerPlayer computerPlayerUnderTest_One;
	private static computerPlayer computerPlayerUnderTest_Two;
	private static Table dominoTable; 
	private static BoneYard BONEYARD = null;

	/**
	 * Setup to run tests
	 */
	@Before
	public void setUp() throws Exception {
		//setup BONEYARD
		BONEYARD = new BoneYard(dominoesGlobal.MAX_DOTS);
		//create players
		computerPlayerUnderTest_One = new computerPlayer();
		computerPlayerUnderTest_Two =  new computerPlayer();
		//get UI instance
		dominoesUserInterfaceFacade dominoGameUI = dominoesUserInterfaceFacade.getInstance();
		//create a game
		Dominoes dominoGame = new Dominoes((DominoUI) dominoGameUI, computerPlayerUnderTest_One, computerPlayerUnderTest_Two
										   ,dominoesGlobal.POINTS_GOAL, dominoesGlobal.MAX_DOTS);
		
		//create 5 Bones
		Bone bone_one = new Bone(4,3);
		Bone bone_two = new Bone(6,6);
		Bone bone_three = new Bone(2,1);
		Bone bone_four = new Bone(6,5);
		Bone bone_five = new Bone(5,1);

		//add 3 of them to the players list, use takeBack method
		computerPlayerUnderTest_One.takeBack(bone_one);
		computerPlayerUnderTest_One.takeBack(bone_two);
		computerPlayerUnderTest_One.takeBack(bone_three);

		//create the table
		dominoTable = new Table();

		//create Play objects
		//IMPORTANT NOTE - it is not clear from the DOMINOES documentation supplied, how to play 
		//the very FIRST bone on the table. Playing with PLAY.LEFT or PLAY.RIGHT throws an exception 
		//The PLAY class has a constant 'PLAY.START' but sometimes it is not visible. 
		//So I have got around that problem with using integer 2 for the first play.(I guess 
		//2 is the value of START)
		Play play_first = new Play(bone_four,2); //Bone 6,5
		Play play_second = new Play(bone_five, Play.RIGHT); //Bone 5,1

		//play them on the table 
		dominoTable.play(play_first);
		dominoTable.play(play_second);
		//so the table is 6|5 5|1
	}

	/**
	 * Test bonesInHand method
	 */
	@Test
	public void testBonesInHand() {
		String expectedPlayerBones = "4|3 6|6 2|1";
		Bone[] tempBone = computerPlayerUnderTest_One.bonesInHand();
		//convert the array of Bones[] into a string
		String playerBonesReturned = dominoesGlobal.convertBonestoString(tempBone);
		assertEquals (expectedPlayerBones,playerBonesReturned);
	}
	
	@Test
	public void testNumInHand() {
		int expectedNumInHand = 3;
		assertEquals ("Wrong Answer !",expectedNumInHand,computerPlayerUnderTest_One.numInHand());
	}

	/**
	 * Test draw method
	 */
	@Test
	public void testDraw() {
		computerPlayerUnderTest_Two.draw(BONEYARD);
		int expectedBoneyardCount=27; //28 - 1
		assertEquals ("Wrong Answer !",expectedBoneyardCount,BONEYARD.size());
	}

	/**
	 * Test getName method 
	 * (Implicitly tests setName as well)
	 */
	@Test
	public void testGetName() {
		computerPlayerUnderTest_Two.setName("DARTH VADER");
		String expectedName = "DARTH VADER";
		assertEquals ("Wrong Answer !",expectedName,computerPlayerUnderTest_Two.getName());
	}
	
	/**
	 * Test getPoints method
	 * (Implicitly tests setPoints as well)
	 */
	@Test
	public void testGetPoints() {
		computerPlayerUnderTest_Two.setPoints(37);
		int expectedPoints=37;
		assertEquals ("Wrong Answer !",expectedPoints,computerPlayerUnderTest_Two.getPoints());
	}
	
	/**
	 * Test the makePlay method
	 * WHEN the player has bones and so does the table layout 
	 */
	@Test
	public void testMakePlay() throws InvalidPlayException, CantPlayException {
		//Play bone expected
		String playBoneExpected = "6|6";
		
		//call makePlay
		Play playReturned = computerPlayerUnderTest_One.makePlay(dominoTable);
		
		//Extract the left and right value of the Bone in Play
		int tempBoneRight = playReturned.bone().right();
		int tempBoneLeft = playReturned.bone().left();
		//create a String representation of the bone 
		String playBoneReturned = 	tempBoneLeft+"|"+tempBoneRight;
	
		//test - Bone returned against expected 
		assertEquals("Wrong Answer !",playBoneExpected, playBoneReturned);
		
		//test if the bone has been removed from the Players list
		int expectedNumInHand = 2; //(3 Bones originally - 1 Bone from makePlay) 
		assertEquals ("Wrong Answer !",expectedNumInHand,computerPlayerUnderTest_One.numInHand());
	}
	
	/**
	 * Test initialising a new round
	 */
	@Test
	public void testNewRound() {
		computerPlayerUnderTest_One.newRound();
		int expectedNumInHand = 0;
		assertEquals ("Wrong Answer !",expectedNumInHand,computerPlayerUnderTest_One.numInHand());
	}
	
	/**
	 * Test taking back a back a bone and
	 * adding to the players stack
	 */
	@Test
	public void testTakeBack() {
		Bone Bone_six = new Bone(5,4);
		//clear players hands
		computerPlayerUnderTest_One.newRound();
		computerPlayerUnderTest_One.takeBack(Bone_six);
		String expectedPlayerBones = "5|4";
		String playerBonesReturned = dominoesGlobal.convertBonestoString
													(computerPlayerUnderTest_One.bonesInHand());
		assertEquals (expectedPlayerBones,playerBonesReturned);
	}
}
