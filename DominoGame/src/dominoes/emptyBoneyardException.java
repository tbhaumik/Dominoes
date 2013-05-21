package dominoes;
/**
 * 
 * This exception object will be thrown 
 * if a player attempts to draw from an
 * empty boneyard. 
 * 
 * @author Tirthankar Bhaumik
 */
public class emptyBoneyardException extends Exception {

	/**
	 * Parameterless Constructor
	 */
	public emptyBoneyardException() {}

	/**
	 * Constructor that accepts a message
	 * 
	 * @param String 
	 */
	public emptyBoneyardException(String message)
	{
		super(message);
	}
}
