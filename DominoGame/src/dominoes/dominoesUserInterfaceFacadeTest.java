package dominoes;

import static org.junit.Assert.*;

/**
 * Tests for the dominoesUserInterfaceFacade class
 * @author Tirthankar Bhaumik
 */

import org.junit.Before;
import org.junit.Test;

import dominoes.players.abstractPlayer;
import dominoes.players.abstractPlayerFactory.PlayerType;
import dominoes.players.interactivePlayer;

public class dominoesUserInterfaceFacadeTest {
	private static dominoesUserInterfaceFacade instance;
	private static int intPointsGoal=117;
	private static abstractPlayer Player1 = new interactivePlayer();
	private static abstractPlayer Player2= new interactivePlayer();


	@Before
	public void setUp() throws Exception {
		//Tests getInstance and startNewGame methods
		instance = dominoesUserInterfaceFacade.getInstance();
		instance.startNewGame(intPointsGoal, Player1, Player2);
	}

	/**
	 * Test getTargetPoints method 
	 * Implicitly also tests setTargetPoints method
	 */
	@Test
	public void testGetTargetPoints() {
		instance.setTargetPoints(999);
		int expectedTargetPoints=999; 
		assertEquals ("Wrong Answer !",expectedTargetPoints,instance.getTargetPoints());
	}

	/**
	 * Test getRound method 
	 * Implicitly also tests setRound method
	 */
	@Test
	public void testGetRound() {
		instance.setRound(779);
		int expectedRound=779;
		assertEquals ("Wrong Answer !",expectedRound,instance.getRound());
	}

	/**
	 * Test drawBone method 
	 * Implicitly also tests getBoneyardCount and countPlayerBones methods
	 */
	@Test
	public void testDrawBone() throws emptyBoneyardException {
		int bcount = instance.getBoneyardCount();
		int playerBoneCount = instance.countPlayerBones(Player1);

		instance.drawBone(Player1);
		int expectedBoneyardCount = bcount-1;
		int expectedPlayerBoneCount = playerBoneCount+1;

		assertEquals ("Wrong Answer !",expectedBoneyardCount,instance.getBoneyardCount());
		assertEquals ("Wrong Answer !",expectedPlayerBoneCount,instance.countPlayerBones(Player1));
	}

	/**
	 * Test getBonesOnTable method 
	 * Implicitly also tests playToTable method
	 */
	@Test
	public void testGetBonesOnTable() throws InvalidPlayException {
		//create Bones
		Bone bone_one = new Bone(6,5);
		Bone bone_two = new Bone(5,1);

		//create Play objects
		//IMPORTANT NOTE - it is not clear from the DOMINOES documentation supplied, how to play 
		//the very FIRST bone on the table. Playing with PLAY.LEFT or PLAY.RIGHT throws an exception 
		//The PLAY class has a constant 'PLAY.START' but sometimes it is not visible. 
		//So I have got around that problem with using integer 2 for the first play.(I guess 
		//2 is the value of START)
		Play play_first = new Play(bone_one,2); //Bone 6,5
		Play play_second = new Play(bone_two, Play.RIGHT); //Bone 5,1

		//Play bone to the table
		instance.playToTable(play_first);
		instance.playToTable(play_second);

		String expectedValue = "6|5 5|1";
		assertEquals ("Wrong Answer !",expectedValue,dominoesGlobal.convertBonestoString(instance.getBonesOnTable()));
	}

	/**
	 * Test IsTableEmpty method 
	 */
	@Test
	public void testIsTableEmpty() throws InvalidPlayException {
		//create Bones
		Bone bone_one = new Bone(6,5);
		Bone bone_two = new Bone(5,1);

		//create Play objects
		//IMPORTANT NOTE - it is not clear from the DOMINOES documentation supplied, how to play 
		//the very FIRST bone on the table. Playing with PLAY.LEFT or PLAY.RIGHT throws an exception 
		//The PLAY class has a constant 'PLAY.START' but sometimes it is not visible. 
		//So I have got around that problem with using integer 2 for the first play.(I guess 
		//2 is the value of START)
		Play play_first = new Play(bone_one,2); //Bone 6,5
		Play play_second = new Play(bone_two, Play.RIGHT); //Bone 5,1

		//Play bone to the table
		instance.playToTable(play_first);
		instance.playToTable(play_second);	

		boolean expectedValue = false;
		assertEquals ("Wrong Answer !",expectedValue,instance.isTableEmpty());
	}

	/**
	 * Test GetName method 
	 * Implicitly also tests setName method
	 */
	@Test
	public void testGetName() {
		Player1.setName("De Niro");
		String expectedName = "De Niro";
		assertEquals ("Wrong Answer !",expectedName,instance.getName(Player1));
	}

	/**
	 * Tests showPlayerBones method 
	 */
	@Test
	public void testShowPlayerBones() {
		//create 3 Bones
		Bone bone_one = new Bone(4,3);
		Bone bone_two = new Bone(6,6);
		Bone bone_three = new Bone(2,1);

		//add 3 of them to the players list, use takeBack method
		Player1.takeBack(bone_one);
		Player1.takeBack(bone_two);
		Player1.takeBack(bone_three);

		String expectedPlayerBones = "4|3 6|6 2|1";
		Bone[] tempBone = instance.showPlayerBones(Player1);
		//convert the array of Bones[] into a string
		String playerBonesReturned = dominoesGlobal.convertBonestoString(tempBone);

		assertEquals (expectedPlayerBones,playerBonesReturned);
	}

	/**
	 * Tests GetPlayerPoints method 
	 */
	public void testGetPlayerPoints() {
		Player1.setPoints(37);
		int expectedvalue = 37;
		assertEquals ("Wrong Answer !",expectedvalue,instance.getPlayerPoints(Player1));
	}

	/**
	 * TestsPassTurn method 
	 */
	@Test
	public void testPassTurn() throws illegalPassException, emptyBoneyardException {
		Player1.setName("De Niro");
		Player2.setName("Al Pacino");

		//draw all the Bones from the Boneyard
		//else this throws an exception - does not let you pass
		for (int i=0; i<=27; i++)
		{
			instance.drawBone(Player1);
		}

		//Player 2 PASS'ES his turn
		instance.passTurn("Al Pacino", Player1, Player2);

		int expectedvalue = abstractPlayer.PASS;

		assertEquals ("Wrong Answer !",expectedvalue,Player2.getLastAction());
	}

	/**
	 * Tests testPassTurn method 
	 */
	@Test
	public void testCreatePlayers() {
		abstractPlayer COMPUTER_player;
		abstractPlayer INTERACTIVE_player;

		//call to Player factory
		abstractPlayer dominoPlayers[] = instance.createPlayers(PlayerType.COMPUTER, PlayerType.INTERACTIVE);
		//allocate the players returned from factory
		COMPUTER_player = dominoPlayers[0];
		INTERACTIVE_player = dominoPlayers[1];

		//Set Player names
		instance.setName(COMPUTER_player,"Computer Player");
		instance.setName(INTERACTIVE_player,"Interactive Player");

		String expectedName1 = "Computer Player";
		String expectedName2 = "Interactive Player";

		assertEquals ("Wrong Answer !",expectedName1,instance.getName(COMPUTER_player));
		assertEquals ("Wrong Answer !",expectedName2,instance.getName(INTERACTIVE_player));
	}

	/**
	 * Tests SetLastPlayerAction method 
	 */
	@Test
	public void testSetLastPlayerAction() {
		instance.setLastPlayerAction(Player2, abstractPlayer.PLAY);
		int expectedvalue = abstractPlayer.PLAY;
		assertEquals ("Wrong Answer !",expectedvalue,Player2.getLastAction());
	}

	@Test
	public void testFlipPlayerBone() {
		//create a Bone
		Bone boneToFlip = new Bone(4,1);

		//add it to players stack
		Player2.takeBack(boneToFlip);

		//create a string representation of the bone
		String strBoneToFlip = "4|1";

		// FLIP it
		instance.flipPlayerBone(Player2, strBoneToFlip);

		//test if flipped corectly
		//get the bone from the players stack
		String playerBones = dominoesGlobal.convertBonestoString(instance.showPlayerBones(Player2));
		//set expected value
		String expectedvalue = "1|4";
		//test
		assertEquals ("Wrong Answer !",expectedvalue,playerBones);
	}

	/**
	 * Test MakePlayerPlay method
	 * (implicitly tests createPlay method)
	 */	
	@Test
	public void testMakePlay() throws CantPlayException {
		//clear hand
		Player2.newRound();
		
		Bone pBone=new Bone(5,4);
		int itableEnd = Play.RIGHT;
		
		instance.createPlay(Player2, pBone, itableEnd);
				
		Play retunedPlay=instance.makePlayerPlay(Player2);
		
		Bone returnedBone = retunedPlay.bone();
		int returnedEnd= retunedPlay.end();
		String expectedBone = "5|4";
		String returnedPlayerBones = returnedBone.left()+"|"+returnedBone.right();
		//test the bone returned
		assertEquals ("Wrong Answer !",expectedBone,returnedPlayerBones);
		//test the end returned
		assertEquals ("Wrong Answer !",itableEnd,returnedEnd);
	}
	
	/**
	 * Test RemovePlayerBone method
	 */	
	@Test
	public void testRemovePlayerBone() {
		//clear hand
		Player2.newRound();
		//create an array of bones
		Bone[] boneArray = new Bone[3];
		boneArray[0] = new Bone(4,3);
		boneArray[1] = new Bone(6,6);
		boneArray[2] = new Bone(2,1);
		//update player bones
		Player2.updatePlayerBones(boneArray);
		
		Bone boneToDelete = new Bone(6,6);
		
		instance.removePlayerBone(Player2, boneToDelete);
		
		String expectedPlayerBones = "4|3 2|1";
		
		Bone[] tempBone = instance.showPlayerBones(Player2);
		//convert the array of Bones[] into a string
		String playerBonesReturned = dominoesGlobal.convertBonestoString(tempBone);

		assertEquals (expectedPlayerBones,playerBonesReturned);
	}
}
