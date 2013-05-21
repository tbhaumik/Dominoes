package dominoes.players;

import dominoes.players.interactivePlayer;
/**
 * 
 *This class is the Interactive player Concrete Creator.
 *
 *@author Tirthankar Bhaumik
 *@see abstractPlayerFactory
 */
public class interactivePlayerFactory extends abstractPlayerFactory{
	/**
	 * Overrides the factory method to return an instance of a
	 * concrete product.
	 * 
	 * @param Player Type enumeration
	 * @return DominoPlayer interactiveOlayer
	 */
	@Override
	protected abstractPlayer selectPlayer(PlayerType ptype) {

		return new interactivePlayer();

	}
}
