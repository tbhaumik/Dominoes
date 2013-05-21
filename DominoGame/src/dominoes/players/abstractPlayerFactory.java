package dominoes.players;

/**
 * 
 *This class is the abstract player Creator.
 *
 *@author Tirthankar Bhaumik
 *@see computerPlayerFactory
 *@see interactivePlaterFactory 
 */
public abstract class abstractPlayerFactory {
	//types of players
	public enum PlayerType {COMPUTER, INTERACTIVE};

	/**
	 * Sets the number of points required to win the game.
	 * 
	 * @param Player Type enumeration
	 * @return DominoPlayer
	 */
	public abstractPlayer createPlayer(PlayerType ptype){
		abstractPlayer player = selectPlayer(ptype);
		return player;
	}

	/**
	 * This is the factory method- that only subclasses will utilise.
	 * 
	 * @param Player Type enumeration
	 * @return DominoPlayer
	 */
	protected abstract abstractPlayer selectPlayer(PlayerType ptype);
}
