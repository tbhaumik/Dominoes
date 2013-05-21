package dominoes.players;
/**
 * Tests for the interactivePlayer class
 * @author Tirthankar Bhaumik
 */
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dominoes.Bone;
import dominoes.BoneYard;
import dominoes.CantPlayException;
import dominoes.DominoUI;
import dominoes.Dominoes;
import dominoes.Play;
import dominoes.Table;
import dominoes.dominoesGlobal;
import dominoes.dominoesUserInterfaceFacade;

public class interactivePlayerTest {
	private static interactivePlayer interactivePlayerUnderTest_One;
	private static interactivePlayer interactivePlayerUnderTest_Two;
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
		interactivePlayerUnderTest_One = new interactivePlayer();
		interactivePlayerUnderTest_Two =  new interactivePlayer();
		//get UI instance
		dominoesUserInterfaceFacade dominoGameUI = dominoesUserInterfaceFacade.getInstance();
		//create a game
		Dominoes dominoGame = new Dominoes((DominoUI) dominoGameUI, interactivePlayerUnderTest_One, interactivePlayerUnderTest_Two
										   ,dominoesGlobal.POINTS_GOAL, dominoesGlobal.MAX_DOTS);
		
		//create 5 Bones
		Bone bone_one = new Bone(4,3);
		Bone bone_two = new Bone(6,6);
		Bone bone_three = new Bone(2,1);
		Bone bone_four = new Bone(6,5);
		Bone bone_five = new Bone(5,1);

		//add 3 of them to the players list, use takeBack method
		interactivePlayerUnderTest_One.takeBack(bone_one);
		interactivePlayerUnderTest_One.takeBack(bone_two);
		interactivePlayerUnderTest_One.takeBack(bone_three);

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
		Bone[] tempBone = interactivePlayerUnderTest_One.bonesInHand();
		//convert the array of Bones[] into a string
		String playerBonesReturned = dominoesGlobal.convertBonestoString(tempBone);
		assertEquals (expectedPlayerBones,playerBonesReturned);
	}
	
	/**
	 * Test draw method
	 */
	@Test
	public void testDraw() {
		interactivePlayerUnderTest_Two.draw(BONEYARD);
		int expectedBoneyardCount=27; //28 - 1
		assertEquals ("Wrong Answer !",expectedBoneyardCount,BONEYARD.size());
	}

	/**
	 * Test getName method 
	 * (Implicitly tests setName as well)
	 */
	@Test
	public void testGetName() {
		interactivePlayerUnderTest_Two.setName("JOE BLOGGS");
		String expectedName = "JOE BLOGGS";
		assertEquals ("Wrong Answer !",expectedName,interactivePlayerUnderTest_Two.getName());
	}

	/**
	 * Test getPoints method
	 * (Implicitly tests setPoints as well)
	 */
	@Test
	public void testGetPoints() {
		interactivePlayerUnderTest_Two.setPoints(37);
		int expectedPoints=37;
		assertEquals ("Wrong Answer !",expectedPoints,interactivePlayerUnderTest_Two.getPoints());
	}

	/**
	 * Test initialising a new round
	 */
	@Test
	public void testNewRound() {
		interactivePlayerUnderTest_One.newRound();
		int expectedNumInHand = 0;
		assertEquals ("Wrong Answer !",expectedNumInHand,interactivePlayerUnderTest_One.numInHand());
	}
	
	/**
	 * Test NumInHand method
	 */
	@Test
	public void testNumInHand() {
		int expectedNumInHand = 3;
		assertEquals ("Wrong Answer !",expectedNumInHand,interactivePlayerUnderTest_One.numInHand());
	}

	/**
	 * Test TakeBack method
	 */
	@Test
	public void testTakeBack() {
		Bone Bone_six = new Bone(5,4);
		//clear players hands
		interactivePlayerUnderTest_One.newRound();
		//add Bone to players stack
		interactivePlayerUnderTest_One.takeBack(Bone_six);
		
		String expectedPlayerBones = "5|4";
		String playerBonesReturned = dominoesGlobal.convertBonestoString
													(interactivePlayerUnderTest_One.bonesInHand());
		
		assertEquals (expectedPlayerBones,playerBonesReturned);
	}
	
	/**
	 * Test WeightInHand method
	 */	
	@Test
	public void testWeightInHand() {
		//clear out hand
		interactivePlayerUnderTest_Two.newRound();
		
		//test if it returns 0 (hand is clear - no bones in player stack)
		int expectedWeightInHand = 0;
		assertEquals ("Wrong Answer !",expectedWeightInHand,interactivePlayerUnderTest_Two.weightInHand());
		
		//add Bone to players stack
		Bone Bone_seven = new Bone(5,2);
		interactivePlayerUnderTest_Two.takeBack(Bone_seven);
		
		//should return 7 =  5 + 2
		expectedWeightInHand = 7;
		assertEquals ("Wrong Answer !",expectedWeightInHand,interactivePlayerUnderTest_Two.weightInHand());
	}

	/**
	 * Test GetLastAction method
	 */	
	@Test
	public void testGetLastAction() {
		interactivePlayerUnderTest_Two.setLastAction(abstractPlayer.PLAY);
		int expectedValue = abstractPlayer.PLAY;
		assertEquals ("Wrong Answer !",expectedValue,interactivePlayerUnderTest_Two.getLastAction());
	}
		
	/**
	 * Test UpdatePlayerBones method
	 */	
	@Test
	public void testUpdatePlayerBones() {
		
		//create an array of bones
		Bone[] boneArray = new Bone[3];
		boneArray[0] = new Bone(4,3);
		boneArray[1] = new Bone(6,6);
		boneArray[2] = new Bone(2,1);
		
		//clear hand
		interactivePlayerUnderTest_Two.newRound();
		
		interactivePlayerUnderTest_Two.updatePlayerBones(boneArray);
				
		String expectedPlayerBones = "4|3 6|6 2|1";
		Bone[] tempBone = interactivePlayerUnderTest_Two.bonesInHand();
		//convert the array of Bones[] into a string
		String playerBonesReturned = dominoesGlobal.convertBonestoString(tempBone);
		assertEquals (expectedPlayerBones,playerBonesReturned);
	}
	
	/**
	 * Test MakePlay method
	 * (implicitly tests setPlayBone method)
	 */	
	@Test
	public void testMakePlay() throws CantPlayException {
		//clear hand
		interactivePlayerUnderTest_Two.newRound();
		
		Bone pBone=new Bone(5,4);
		int itableEnd = Play.RIGHT;
		
		interactivePlayerUnderTest_Two.setPlayBone(pBone, itableEnd);
		
		Play retunedPlay = interactivePlayerUnderTest_Two.makePlay(dominoTable);
		
		Bone returnedBone = retunedPlay.bone();
		int returnedEnd= retunedPlay.end();
		String expectedBone = "5|4";
		String returnedPlayerBones = returnedBone.left()+"|"+returnedBone.right();
		//test the bone returned
		assertEquals ("Wrong Answer !",expectedBone,returnedPlayerBones);
		//test the end returned
		assertEquals ("Wrong Answer !",itableEnd,returnedEnd);
	}
}
