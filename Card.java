import java.util.Collections;
import java.util.LinkedList;

public class Card {
    
    /**
     * Using integers to represent suits.
     *  
     * Reasoning: it is easier to iterate over
     * 
     * Ordering:
     * 0: Diamonds
     * 1: Clubs
     * 2: Hearts 
     * 3: Spades
     */
    private int suit;
    private int value;
    
    public Card(int suit, int value) {
        this.suit = suit;
        this.value = value;
    }
    
    /**
     * Makes a new copy of Card
     * @param c for the Card you want to copy
     */
    public Card(Card c) {
        this.suit = c.getSuit();
        this.value = c.getValue();
    }
    
    /**
     * @return the Card's suit
     */
    public int getSuit() {
        return this.suit;
    }
    
    /**
     * @return the Card's value
     */
    public int getValue() {
        return this.value;
    }
    
    /**
     * Gives a list of the values from the hand
     * 
     * @param hand: takes in a player hand
     * @return the value list
     */
    public static LinkedList<Integer> valueList(LinkedList<Card> hand) {
        LinkedList<Integer> values = new LinkedList<Integer>();
        for (Card c : hand) {
            values.add(c.getValue());
        }
        return values;
    }
    
    /**
     * Checks if hand has a double
     * 
     * @param hand: takes in the player hand
     * @return whether or not there is a double
     */
    public static boolean oneDouble(LinkedList<Card> hand) {
        
        LinkedList<Integer> valueHand = valueList(hand);
        LinkedList<Integer> compareHand = valueList(hand);

        // if there are two of the same return
        for (Integer c : valueHand) {
            compareHand.remove(c);
            if (compareHand.contains(c)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if hand has two doubles
     * 
     * @param hand: takes in the player hand
     * @return whether or not there are two doubles
     */
    public static boolean twoDoubles(LinkedList<Card> hand) {
        
        LinkedList<Integer> valueHand = valueList(hand);
        LinkedList<Integer> compareHand = valueList(hand);
        
        for (Integer c : valueHand) {
            compareHand.remove(c);
            // if there's a double, look for another
            if (compareHand.contains(c)) {
                compareHand.remove(c);
                
                LinkedList<Integer> newCompareHand = new LinkedList<Integer>(compareHand);
                // if there's a double and it's not the same as the one
                // from before, return true; otherwise false
                for (Integer c1 : compareHand) {
                    newCompareHand.remove(c1);
                    if (newCompareHand.contains(c1) && c1 != c) {
                        return true;
                    }
                }         
            }
        }
        return false;
    }
    
    /**
     * Checks if hand has a quadruple
     * 
     * @param hand: takes in the player hand
     * @return whether or not there is a quadruple
     */
    public static boolean quadruple(LinkedList<Card> hand) {
        Card c1 = hand.get(0);
        Card c2 = hand.get(1);
        
        int counter1 = 0;
        int counter2 = 0;
        
        // checks the iterations, makes sure there 
        // is only one different card
        for (Card card : hand) {
            if (card.getValue() == c1.getValue()) {
                counter1++;
            }
            if (card.getValue() == c2.getValue()) {
                counter2++;
            }
        }
        
        if (counter1 < 4 && counter2 < 4) {
            return false;
        }        
        return true;
    }
    
    /**
     * Checks if hand has a flush
     * 
     * @param hand: takes in the player hand
     * @return whether or not there is a flush
     */
    public static boolean isFlush(LinkedList<Card> hand) {
        int suit = hand.get(0).getSuit();
        
        // just checks if the suits are same
        for (Card c : hand) {
            if (c.getSuit() != suit) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Checks if hand is royal
     * 
     * @param hand: takes in the player hand
     * @return whether or not the hand is royal
     */
    public static boolean isRoyal(LinkedList<Card> hand) {
        
        // checks if card values are greater than 9 (a 10)
        for (Card c : hand) {
            if (c.getValue() < 9 && c.getValue() != 0) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Checks if hand has a straight
     * 
     * @param hand: takes in the player hand
     * @return whether or not there is a straight
     */
    public static boolean isStraight(LinkedList<Card> hand) {
        LinkedList<Integer> values = new LinkedList<Integer>();
        
        for (Card c : hand) {
            values.add(c.getValue());
        }
        
        Collections.sort(values);
        
        // sorts them and makes sure that they're
        // 1 apart
        int firstVal = values.get(0) - 1;
        for (Integer val : values) {
            if (val != firstVal + 1) {
                return false;
            }
            firstVal++;
        }
        
        return true;
    }
    
    /**
     * Checks if hand has a triple
     * 
     * @param hand: takes in the player hand
     * @return whether or not there is a triple
     */
    public static boolean isTriple(LinkedList<Card> hand) {
      
        int counter = 0;
        
        // checks that counter is 3
        for (Card c1 : hand) {
            for (Card c2 : hand) {
                if (c1.getValue() == c2.getValue()) {
                    counter++;
                }
            }
            if (counter == 3) {
                return true;
            }
            counter = 0;
        }        
        return false;
    }
    
    /**
     * Checks if hand has a full house
     * 
     * @param hand: takes in the player hand
     * @return whether or not there is a full house
     */
    public static boolean isFullHouse(LinkedList<Card> hand) {
        
        int triplet = -1;
        int counter = 0;
        
        // checks for triple
        LinkedList<Integer> valueHand = valueList(hand);
        
        for (Integer i : valueHand) {
            for (Integer j : valueHand) {
                if (i == j) {
                    counter++;
                }
            }
            if (counter == 3) {
                triplet = i;
            }
            counter = 0;
        }
        
        // checks for a double 
        if (triplet != -1) {
            
            int doub = -1;
            
            for (Integer i : valueHand) {
                if (i != triplet) {
                    doub = i;
                }
            }
            
            // make sure that doub appears twice
            for (Integer i : valueHand) {
                if (i == doub) {
                    counter++;
                }
            }
            
            return counter == 2;
        }
        
        return false;

    }
    
    /**
     * Returns value of the hand
     * 
     * @param hand: takes in the player hand
     * @return the value of the hand
     */
    public static int highCard(LinkedList<Card> hand) {
        LinkedList<Integer> valueHand = valueList(hand);
        int sum = 0;
        
        // returns sum
        for (Integer c : valueHand) {
            sum += c;
        }
        return sum;
    }
    
    
    
}