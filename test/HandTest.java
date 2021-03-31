import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.LinkedList;

/** 
 *  Tests the types of hands players can get
 *  
 *  Ordering:
 *  0: Diamonds
 *  1: Clubs
 *  2: Hearts 
 *  3: Spades
 *  
 *  0 --> 13 for A --> K
 *  
 */

public class HandTest {
    
    // all of these tests just test the hand type, and makes sure
    // they line up with how I defined them in the Card class.
    
    @Test
    public void testValueList() {
        LinkedList<Card> hand = new LinkedList<Card>();
        hand.add(new Card(0, 1));
        hand.add(new Card(0, 12));
        hand.add(new Card(0, 3));
        hand.add(new Card(0, 8));
        hand.add(new Card(0, 5));
        
        LinkedList<Integer> valueHand = Card.valueList(hand);
        
        LinkedList<Integer> actual = new LinkedList<Integer>();
        actual.add(1);
        actual.add(12);
        actual.add(3);
        actual.add(8);
        actual.add(5);
        
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(actual.get(i), valueHand.get(i));
        }
        
        assertEquals(valueHand.size(), 5);
    }
    
    @Test
    public void testOneDouble() {
        LinkedList<Card> hand = new LinkedList<Card>();
        hand.add(new Card(0, 1));
        hand.add(new Card(0, 12));
        hand.add(new Card(0, 3));
        hand.add(new Card(0, 8));
        hand.add(new Card(0, 5));
        
        assertFalse(Card.oneDouble(hand));
        
        hand.set(0, new Card(1, 8));
        
        assertTrue(Card.oneDouble(hand));
        
        hand.set(4, new Card(3, 12));
        
        assertTrue(Card.oneDouble(hand));
    }
    
    @Test
    public void testTwoDoubles() {
        LinkedList<Card> hand = new LinkedList<Card>();
        hand.add(new Card(0, 12));
        hand.add(new Card(0, 12));
        hand.add(new Card(0, 3));
        hand.add(new Card(0, 8));
        hand.add(new Card(0, 5));
        
        assertFalse(Card.twoDoubles(hand));
        
        hand.set(2, new Card(1, 8));
        
        assertTrue(Card.twoDoubles(hand));
        
        LinkedList<Card> hand1 = new LinkedList<Card>();
        hand1.add(new Card(1, 1));
        hand1.add(new Card(2, 2));
        hand1.add(new Card(3, 3));
        hand1.add(new Card(0, 2));
        hand1.add(new Card(1, 3));
        
        assertTrue(Card.twoDoubles(hand1));
        
        hand1.set(2, new Card(1, 4));
        
        assertFalse(Card.twoDoubles(hand1));
        
        LinkedList<Card> hand2 = new LinkedList<Card>();
        hand2.add(new Card(1, 1));
        hand2.add(new Card(2, 4));
        hand2.add(new Card(3, 4));
        hand2.add(new Card(0, 4));
        hand2.add(new Card(1, 4));
        
        assertFalse(Card.twoDoubles(hand2));
        
        hand2.set(1, new Card(1, 5));
        hand2.set(2, new Card(2, 5));
        
        assertTrue(Card.twoDoubles(hand2));     
    }
    
    @Test
    public void testQuad() {
        LinkedList<Card> hand = new LinkedList<Card>();
        hand.add(new Card(4, 12));
        hand.add(new Card(0, 12));
        hand.add(new Card(2, 12));
        hand.add(new Card(0, 8));
        hand.add(new Card(1, 12));
        
        LinkedList<Card> hand1 = new LinkedList<Card>();
        hand1.add(new Card(1, 1));
        hand1.add(new Card(2, 2));
        hand1.add(new Card(3, 1));
        hand1.add(new Card(0, 2));
        hand1.add(new Card(1, 3));
        
        LinkedList<Card> hand2 = new LinkedList<Card>();
        hand2.add(new Card(1, 1));
        hand2.add(new Card(2, 4));
        hand2.add(new Card(3, 4));
        hand2.add(new Card(0, 4));
        hand2.add(new Card(1, 4));
        
        LinkedList<Card> hand3 = new LinkedList<Card>();
        hand3.add(new Card(1, 3));
        hand3.add(new Card(2, 3));
        hand3.add(new Card(3, 3));
        hand3.add(new Card(0, 4));
        hand3.add(new Card(1, 3));
        
        LinkedList<Card> hand4 = new LinkedList<Card>();
        hand4.add(new Card(1, 1));
        hand4.add(new Card(2, 4));
        hand4.add(new Card(3, 1));
        hand4.add(new Card(0, 4));
        hand4.add(new Card(1, 4));
        
        assertTrue(Card.quadruple(hand));
        assertFalse(Card.quadruple(hand1));
        assertTrue(Card.quadruple(hand2));   
        assertTrue(Card.quadruple(hand3));  
        assertFalse(Card.quadruple(hand4));  
    }
    
    @Test
    public void testFlush() {
        LinkedList<Card> hand = new LinkedList<Card>();
        hand.add(new Card(4, 12));
        hand.add(new Card(4, 12));
        hand.add(new Card(4, 12));
        hand.add(new Card(4, 8));
        hand.add(new Card(4, 12));
        
        LinkedList<Card> hand1 = new LinkedList<Card>();
        hand1.add(new Card(1, 1));
        hand1.add(new Card(2, 2));
        hand1.add(new Card(1, 1));
        hand1.add(new Card(1, 2));
        hand1.add(new Card(1, 3));
        
        LinkedList<Card> hand2 = new LinkedList<Card>();
        hand2.add(new Card(1, 1));
        hand2.add(new Card(1, 4));
        hand2.add(new Card(1, 4));
        hand2.add(new Card(1, 4));
        hand2.add(new Card(1, 4));
        
        assertTrue(Card.isFlush(hand));
        assertFalse(Card.isFlush(hand1));
        assertTrue(Card.isFlush(hand2));   
    }
    
    @Test
    public void testRoyal() {
        LinkedList<Card> hand = new LinkedList<Card>();
        hand.add(new Card(4, 12));
        hand.add(new Card(4, 12));
        hand.add(new Card(4, 12));
        hand.add(new Card(4, 0));
        hand.add(new Card(4, 12));
        
        LinkedList<Card> hand1 = new LinkedList<Card>();
        hand1.add(new Card(1, 12));
        hand1.add(new Card(2, 12));
        hand1.add(new Card(1, 12));
        hand1.add(new Card(1, 11));
        hand1.add(new Card(1, 3));
        
        LinkedList<Card> hand2 = new LinkedList<Card>();
        hand2.add(new Card(1, 13));
        hand2.add(new Card(1, 12));
        hand2.add(new Card(1, 11));
        hand2.add(new Card(1, 4));
        hand2.add(new Card(1, 4));
        
        assertTrue(Card.isRoyal(hand));
        assertFalse(Card.isRoyal(hand1));
        assertFalse(Card.isRoyal(hand2));   
    }
    
    @Test
    public void testStraight() {
        LinkedList<Card> hand = new LinkedList<Card>();
        hand.add(new Card(4, 1));
        hand.add(new Card(0, 5));
        hand.add(new Card(4, 4));
        hand.add(new Card(1, 2));
        hand.add(new Card(4, 3));
        
        LinkedList<Card> hand1 = new LinkedList<Card>();
        hand1.add(new Card(1, 12));
        hand1.add(new Card(2, 1));
        hand1.add(new Card(1, 13));
        hand1.add(new Card(1, 10));
        hand1.add(new Card(3, 9));
        
        LinkedList<Card> hand2 = new LinkedList<Card>();
        hand2.add(new Card(1, 1));
        hand2.add(new Card(1, 2));
        hand2.add(new Card(1, 13));
        hand2.add(new Card(1, 4));
        hand2.add(new Card(1, 4));
        
        assertTrue(Card.isStraight(hand));
        assertFalse(Card.isStraight(hand1));
        assertFalse(Card.isStraight(hand2));   
    }
    
    @Test
    public void testTriple() {
        LinkedList<Card> hand = new LinkedList<Card>();
        hand.add(new Card(4, 12));
        hand.add(new Card(0, 12));
        hand.add(new Card(2, 12));
        hand.add(new Card(0, 8));
        hand.add(new Card(1, 1));
        
        LinkedList<Card> hand1 = new LinkedList<Card>();
        hand1.add(new Card(1, 1));
        hand1.add(new Card(2, 2));
        hand1.add(new Card(3, 1));
        hand1.add(new Card(0, 2));
        hand1.add(new Card(1, 1));
        
        LinkedList<Card> hand2 = new LinkedList<Card>();
        hand2.add(new Card(1, 1));
        hand2.add(new Card(2, 4));
        hand2.add(new Card(3, 4));
        hand2.add(new Card(0, 4));
        hand2.add(new Card(1, 4));
        
        LinkedList<Card> hand3 = new LinkedList<Card>();
        hand3.add(new Card(1, 1));
        hand3.add(new Card(2, 1));
        hand3.add(new Card(3, 3));
        hand3.add(new Card(0, 3));
        hand3.add(new Card(1, 3));
        
        LinkedList<Card> hand4 = new LinkedList<Card>();
        hand4.add(new Card(1, 1));
        hand4.add(new Card(2, 4));
        hand4.add(new Card(3, 1));
        hand4.add(new Card(0, 3));
        hand4.add(new Card(1, 4));
        
        LinkedList<Card> hand5 = new LinkedList<Card>();
        hand5.add(new Card(1, 1));
        hand5.add(new Card(2, 3));
        hand5.add(new Card(3, 1));
        hand5.add(new Card(0, 4));
        hand5.add(new Card(1, 4));
        
        assertTrue(Card.isTriple(hand));
        assertTrue(Card.isTriple(hand1));
        assertFalse(Card.isTriple(hand2));   
        assertTrue(Card.isTriple(hand3));  
        assertFalse(Card.isTriple(hand4));  
        assertFalse(Card.isTriple(hand5));  
    }
    
    @Test
    public void testFullHouse() {
        LinkedList<Card> hand = new LinkedList<Card>();
        hand.add(new Card(4, 12));
        hand.add(new Card(0, 12));
        hand.add(new Card(2, 12));
        hand.add(new Card(0, 8));
        hand.add(new Card(1, 1));
        
        LinkedList<Card> hand1 = new LinkedList<Card>();
        hand1.add(new Card(1, 2));
        hand1.add(new Card(2, 12));
        hand1.add(new Card(3, 2));
        hand1.add(new Card(0, 12));
        hand1.add(new Card(1, 2));
        
        LinkedList<Card> hand2 = new LinkedList<Card>();
        hand2.add(new Card(1, 1));
        hand2.add(new Card(2, 4));
        hand2.add(new Card(3, 4));
        hand2.add(new Card(0, 4));
        hand2.add(new Card(1, 4));
        
        LinkedList<Card> hand3 = new LinkedList<Card>();
        hand3.add(new Card(1, 1));
        hand3.add(new Card(2, 1));
        hand3.add(new Card(3, 3));
        hand3.add(new Card(0, 3));
        hand3.add(new Card(1, 3));
        
        LinkedList<Card> hand4 = new LinkedList<Card>();
        hand4.add(new Card(1, 4));
        hand4.add(new Card(2, 4));
        hand4.add(new Card(3, 1));
        hand4.add(new Card(0, 1));
        hand4.add(new Card(1, 4));
        
        LinkedList<Card> hand5 = new LinkedList<Card>();
        hand5.add(new Card(1, 1));
        hand5.add(new Card(2, 3));
        hand5.add(new Card(3, 1));
        hand5.add(new Card(0, 4));
        hand5.add(new Card(1, 4));
        
        assertFalse(Card.isFullHouse(hand));
        assertTrue(Card.isFullHouse(hand1));
        assertFalse(Card.isFullHouse(hand2));   
        assertTrue(Card.isFullHouse(hand3));  
        assertTrue(Card.isFullHouse(hand4));  
        assertFalse(Card.isFullHouse(hand5));  
    }
}
