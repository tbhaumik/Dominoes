package dominoes.players;

import dominoes.players.computerPlayer;
/**
 * 
 *This class is the Computer player Concrete Creator.
 *
 *@author Tirthankar Bhaumik
 *@see abstractPlayerFactory
 */
public class computerPlayerFactory extends abstractPlayerFactory{
	/**
	 * Overrides the factory method to return an instance of a
	 * concrete product.
	 * 
	 * @param Player Type enumeration
	 * @return DominoPlayer computerPlayer
	 */
	@Override
	protected abstractPlayer selectPlayer(PlayerType ptype) {

		return new computerPlayer();
	}
}
