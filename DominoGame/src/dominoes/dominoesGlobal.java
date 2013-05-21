package dominoes;

/**
 * Global constants and useful generic functions
 * 
 * @author Tirthankar Bhaumik
 */
public final class dominoesGlobal {

	//Construct bones with maximum number of dots
	public final static int MAX_DOTS = 6;

	//POINTS_GOAL only used in testing
	//see computerPlayerTest.java
	//see interactivePlayerTest.java
	public final static int POINTS_GOAL = 100;

	public final static int NO_OF_PLAYERS = 2;

	//Main game screen size
	public final static int SCREEN_WIDTH = 700;
	public final static int SCREEN_HEIGHT = 600;

	//Message constants
	public final static String flipMsg="Please select a Bone to flip."; 
	public static final String makePlayMsg = "Player has not selected a Bone to play";
	public final static String boneyardEmptyMsg="Boneyard is empty.";
	public final static String illegalPassMsg="Pass not allowed. Please draw a bone from the Boneyard.";
	public final static String noPlayerBones="There are no bones available for player to play";
	public final static String drawMsg="This round is a draw";
	public final static String emptyTxtBoxes = "Fields cannot be left blank.";
	public final static String noBonesOntable = "<no bones played yet>";

	/**
	 * Function to convert an array of Bones into a single string,
	 * such as "4|5 5|3 3|7"
	 * 
	 * @param array of Bones
	 * @return String
	 */
	public final static String convertBonestoString(Bone[] boneArray){
		//create a string to store bone left and right values
		String strTempBones="";
		//loop through to extract l and r values 
		for(int i=0;i<=boneArray.length-1;i++)
		{
			strTempBones =strTempBones+boneArray[i].left() +"|" + boneArray[i].right()+" ";
		}
		return strTempBones.trim();
	}

	/**
	 * Function to convert an array of Bones into a array of string,
	 * 
	 * @param array of Bones
	 * @return String[]
	 */
	public final static String[] convertBonestoStringArray (Bone[] boneArray){
		//create a string array to store bone left and right values
		String strTempBones [] = new String[boneArray.length];
		//loop through to extract l and r values 
		for(int i=0;i<=boneArray.length-1;i++)
		{
			strTempBones[i] = boneArray[i].left() +" | " + boneArray[i].right();
		}
		return strTempBones;
	}

	/**
	 * Function to convert a string to a Bone
	 * Convert 3|4 to a Bone
	 * 
	 * @param String
	 * @return Bone
	 */
	public final static Bone convertStringToBone(String strBone){
		//retrieve l and r values
		int l = Integer.parseInt(strBone.substring(0,1));
		int r = Integer.parseInt(strBone.substring(strBone.length()-1, strBone.length()));
		//create bone	
		Bone nBone = new Bone(l,r);
		return nBone;
	}
}
