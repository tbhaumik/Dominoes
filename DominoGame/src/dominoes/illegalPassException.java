package dominoes;
/**
 * 
 * This exception object will be thrown 
 * if a player attempts to pass when the
 * boneyard is not empty. 
 * 
 * @author Tirthankar Bhaumik
 */
public class illegalPassException extends Exception  {

	/**
	 * Parameterless Constructor
	 */
	public illegalPassException() {}

	/**
	 * Constructor that accepts a message
	 * 
	 * @param String 
	 */
	public illegalPassException(String message)
	{
		super(message);
	}
}
