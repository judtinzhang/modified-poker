import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.LinkedList;

/**
 *  This tests the main Poker Game
 *
 */
public class PokerTest {
    
    @Test
    public void testNewGame() {
        // creates a new game
        Poker p = new Poker();
        
        LinkedList<Card> deck = p.getDeck();
        LinkedList<Player> players = p.getPlayers();
        LinkedList<Player> currentPlayers = p.getCurrentPlayers();
        
        // makes sure the player hands are filled
        assertEquals(deck.size(), 32);
        assertEquals(players.size(), 4);
        assertEquals(currentPlayers.size(), 0);
        
        // makes sure that the players are 
        // instantiated correctly
        for (Player player : players) {
            assertEquals(player.getBet(), -1);
            assertEquals(player.getChips(), 100);
            assertEquals(player.getHand().size(), 5);
        }        
    }
    
    @Test
    public void testNewRound() {
        
        // makes sure the new Poker works correctly
        Poker p = new Poker();
        
        LinkedList<Card> deck = p.getDeck();
        LinkedList<Player> players = p.getPlayers();
        LinkedList<Player> currentPlayers = p.getCurrentPlayers();
        
        assertEquals(deck.size(), 32);
        assertEquals(players.size(), 4);
        assertEquals(currentPlayers.size(), 0);
        
        for (Player player : players) {
            assertEquals(player.getBet(), -1);
            assertEquals(player.getChips(), 100);
            assertEquals(player.getHand().size(), 5);
        }
        
        // lets the player play a round
        players.get(0).bet(80);
        p.playFirstTurn();
        p.finalTurn();
        
        // creates a new round
        p.newRound();
        
        // makes sure that round is created correctly
        assertEquals(p.getDeck().size(), 32);
        assertEquals(p.getPlayers().size(), 4);
        assertEquals(p.getCurrentPlayers().size(), 4);
        
        for (Player player : players) {
            assertEquals(player.getBet(), -1);
            assertEquals(player.getHand().size(), 5);
        }        
    }

    @Test
    public void testBetAndFold() {
        Poker p = new Poker();
        p.newRound();

        LinkedList<Player> players = p.getPlayers();

        // plays with the conditions of bet and fold
        p.setPlayedTurn(true);
        assertEquals(p.bet(80), -1);
        
        // allows the bet this time
        p.setPlayedTurn(false);
        assertEquals(p.bet(80), 80);
        assertEquals(players.get(0).getBet(), 80);
   
        // makes sure bet is returning the 
        // right thing when someone folds
        assertEquals(p.bet(0), -2);
        
        // makes sure the direct fold works
        p.fold();
        assertEquals(players.get(0).getBet(), 0);
    }
    
    @Test
    public void testMatch() {
        Poker p = new Poker();
        p.newRound();
        
        // allows players to match
        p.setPlayedTurn(true);
        p.setCurrentBet(80);
        assertEquals(p.match(), true);
        
        // does not allow player to match
        // before someone bets
        p.setPlayedTurn(false);
        assertEquals(p.match(), false);
    }
    
    @Test
    public void testFirstTurnAndGameOver() {
        Poker p = new Poker();
        
        // makes sure they can't play the first
        // turn twice
        p.setPlayedTurn(true);
        assertEquals(p.playFirstTurn(), -1);
        
        p.setPlayedTurn(false);
        
        // make sure they can't play before the
        // round is instantiated
        p.getPlayers().get(0).setBet(-1);
        assertEquals(p.playFirstTurn(), -2);
        
        
        // plays a new game
        Poker p1 = new Poker();
        p1.newRound();
        p1.bet(80);
        
        assertEquals(p1.getPlayedTurn(), false);
        
        // checks to see what the return value
        // is if the game cuts off early (nobody wins)
        int value = p1.playFirstTurn();
        
        if (p1.getCurrentPlayers().size() > 1) {
            assertEquals(p1.getPlayedTurn(), true);
        } else {    
            assertEquals(p1.getGameOver(), true);
            assertEquals(p1.getPlayable(), false);
            assertEquals(value, 0);
        }
    }
    
    @Test
    public void testFinalTurnAndGameOver() {
        Poker p = new Poker();
        p.newRound();
        
        p.playFirstTurn();
        p.finalTurn();
        
        // makes sure the game closes
        assertEquals(p.getGameOver(), true);
        
        assertTrue(p.getCurrentPlayers().size() <= 4);
        
        // makes sure that the chip numbers and
        // hand numbers remain constant after
        // distributing them during FinalTurn
        int chipNum = 0;
        int handNum = 0;
        for (Player player : p.getPlayers()) {
            chipNum += player.getChips();
            handNum += player.getHand().size();
        }
        
        assertEquals(chipNum, 400);
        assertEquals(handNum, 20);
        assertEquals(p.getDeck().size(), 32);
    } 
}
