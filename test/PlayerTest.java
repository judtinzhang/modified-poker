import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.LinkedList;

/**
 * Tests the important, not one-line methods in Player
 * 
 * Does not test Match because that is purely random
 * 
 */
public class PlayerTest {

    @Test
    public void testNewCard() {
        // adds cards to the list
        LinkedList<Card> cards = new LinkedList<Card>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                Card c = new Card(i, j);
                cards.add(c);
            }
        }  
        
        // creates the hand
        LinkedList<Card> hand = new LinkedList<Card>();
        hand.add(new Card(0, 1));
        hand.add(new Card(0, 12));
        hand.add(new Card(0, 3));
        hand.add(new Card(0, 8));
        hand.add(new Card(0, 5));
        
        Player p1 = new ActualPlayer(hand, 100);
        Player p2 = new RandomPlayer(hand, 100);
           
        // draws new cards and makes sure they can't
        // draw again for the same card replacement
        assertTrue(p1.newCard(1, cards));
        assertEquals(cards.size(), 51);  
        assertFalse(p1.newCard(1, cards));
        assertEquals(cards.size(), 51); 
        assertTrue(p2.newCard(4, cards));  
    }
    
    @Test
    public void testBet() {
        // adds cards to hand
        LinkedList<Card> hand = new LinkedList<Card>();
        hand.add(new Card(0, 1));
        hand.add(new Card(0, 12));
        hand.add(new Card(0, 3));
        hand.add(new Card(0, 8));
        hand.add(new Card(0, 5));
        
        Player p1 = new ActualPlayer(hand, 100);
        Player p2 = new RandomPlayer(hand, 100);
        
        // makes sure that they can bet what they
        // want and change before clicking done
        assertEquals(p1.bet(100), 100);
        assertEquals(p1.bet(50), 50);
        
        // asserts that the randoms can't over bet
        assertTrue(p2.bet(100) <= 100);
        assertTrue(p2.bet(10000) <= 100);
    }
    
    @Test
    public void calculateValue() {
        
        // the following is creating hands to test the 
        // comparison of hands described in the
        // Player class.
        // I put comments on the name of the hand
        
        
        // nothing
        LinkedList<Card> hand = new LinkedList<Card>();
        hand.add(new Card(0, 1));
        hand.add(new Card(1, 12));
        hand.add(new Card(0, 3));
        hand.add(new Card(0, 8));
        hand.add(new Card(0, 5));
        Player p = new ActualPlayer(hand, 100);
        
        // double
        LinkedList<Card> hand1 = new LinkedList<Card>();
        hand1.add(new Card(1, 1));
        hand1.add(new Card(0, 12));
        hand1.add(new Card(3, 3));
        hand1.add(new Card(0, 1));
        hand1.add(new Card(1, 5));
        Player p1 = new ActualPlayer(hand1, 100);
        
        
        // two doubles
        LinkedList<Card> hand2 = new LinkedList<Card>();
        hand2.add(new Card(2, 12));
        hand2.add(new Card(0, 12));
        hand2.add(new Card(3, 3));
        hand2.add(new Card(0, 8));
        hand2.add(new Card(0, 8));
        Player p2 = new ActualPlayer(hand2, 100);
        
        // triple
        LinkedList<Card> hand3 = new LinkedList<Card>();
        hand3.add(new Card(0, 8));
        hand3.add(new Card(0, 12));
        hand3.add(new Card(0, 3));
        hand3.add(new Card(1, 8));
        hand3.add(new Card(0, 8));
        Player p3 = new ActualPlayer(hand3, 100);
        
        // full house
        LinkedList<Card> hand4 = new LinkedList<Card>();
        hand4.add(new Card(0, 8));
        hand4.add(new Card(1, 3));
        hand4.add(new Card(0, 8));
        hand4.add(new Card(0, 8));
        hand4.add(new Card(0, 3));
        Player p4 = new ActualPlayer(hand4, 100);
        
        // quadruple
        LinkedList<Card> hand5 = new LinkedList<Card>();
        hand5.add(new Card(0, 12));
        hand5.add(new Card(0, 12));
        hand5.add(new Card(0, 12));
        hand5.add(new Card(0, 8));
        hand5.add(new Card(3, 12));
        Player p5 = new ActualPlayer(hand5, 100);
        
        // straight
        LinkedList<Card> hand6 = new LinkedList<Card>();
        hand6.add(new Card(0, 1));
        hand6.add(new Card(1, 2));
        hand6.add(new Card(0, 4));
        hand6.add(new Card(0, 3));
        hand6.add(new Card(0, 5));
        Player p6 = new ActualPlayer(hand6, 100);
        
        // flush
        LinkedList<Card> hand7 = new LinkedList<Card>();
        hand7.add(new Card(0, 1));
        hand7.add(new Card(0, 12));
        hand7.add(new Card(0, 3));
        hand7.add(new Card(0, 8));
        hand7.add(new Card(0, 5));
        Player p7 = new ActualPlayer(hand7, 100);
        
        // royal flush
        LinkedList<Card> hand8 = new LinkedList<Card>();
        hand8.add(new Card(0, 9));
        hand8.add(new Card(0, 12));
        hand8.add(new Card(0, 10));
        hand8.add(new Card(0, 11));
        hand8.add(new Card(0, 9));
        Player p8 = new ActualPlayer(hand8, 100);
        
        // nothing
        LinkedList<Card> hand9 = new LinkedList<Card>();
        hand9.add(new Card(3, 7));
        hand9.add(new Card(0, 12));
        hand9.add(new Card(2, 3));
        hand9.add(new Card(0, 8));
        hand9.add(new Card(1, 5));
        Player p9 = new ActualPlayer(hand9, 100);
        
        // a huge comparison between hands
        assertTrue(p.calculateValue(hand) < p1.calculateValue(hand1));
        assertEquals(p.calculateValue(hand), 0);
        
        assertTrue(p1.calculateValue(hand1) < p2.calculateValue(hand2));
        
        assertTrue(p2.calculateValue(hand2) < p3.calculateValue(hand3));
        assertEquals(p3.calculateValue(hand3), 14);
        
        assertTrue(p3.calculateValue(hand3) < p6.calculateValue(hand6));
        assertEquals(p6.calculateValue(hand6), 15);
        
        assertTrue(p6.calculateValue(hand6) < p4.calculateValue(hand4));
        
        assertTrue(p4.calculateValue(hand4) < p5.calculateValue(hand5));
        assertEquals(p5.calculateValue(hand5), 18);
        
        assertTrue(p7.calculateValue(hand7) < p5.calculateValue(hand5));
        assertEquals(p7.calculateValue(hand7), 16);
        
        assertTrue(p7.calculateValue(hand7) < p8.calculateValue(hand8));
        assertEquals(p8.calculateValue(hand8), 20);
        
        assertTrue(p9.calculateValue(hand9) < p2.calculateValue(hand2));       
    }
}
