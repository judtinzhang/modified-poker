import java.util.LinkedList;

/**
 * Player class to identify each player
 */
public abstract class Player {
    
    /**
     * Each player has a hand, chips, a bet value, and a list
     * of repeated cards that they replaced
     */
    private LinkedList<Card> hand;
    private int chips;
    private int bet;
    private LinkedList<Integer> repeated;
    
    public Player(LinkedList<Card> hand, int chips) {
        this.hand = hand;
        this.chips = chips;
        this.bet = -1;
        repeated = new LinkedList<Integer>();
        
    }
    
    /**
     * Decides whether or not to draw a new Card
     */
    public abstract boolean newCard(int cardNumber, LinkedList<Card> deck);
    
    /**
     * Decides whether or not to place bets
     */
    public abstract int bet(int value);
   
    /**
     * Decides whether or not to match the bet
     */
    public abstract boolean willMatch(int high);
       
    /**
     * @return the player hand
     */
    public LinkedList<Card> getHand() {
        return hand;
    }
    
    /**
     * Creates a new hand for the player
     * 
     * @param c: the hand you want to replace with
     */
    public void setHand(LinkedList<Card> c) {
        hand = c;
    }
    
    /**
     * Checks for repeated card swaps
     * 
     * @param repeat: the number in question
     * @return determines if there's a repeat
     */
    public boolean hasRepeat(int repeat) {
        return repeated.contains(repeat);
    }
    
    /**
     * Add a card number to the repeated array
     * 
     * @param repeat: the card number
     */
    public void addRepeated(int repeat) {
        repeated.add(repeat);
    }
        
    /**
     * Clear the repeated array for the next round
     */
    public void deleteRepeated() {
        repeated.clear();
    }
    
    /**
     * 
     * @return the player chips
     */
    public int getChips() {
        return chips;
    }
    
    /**
     * Sets the chips to a value
     * 
     * @param chips: the number of chips remaining
     */
    public void setChips(int chips) {
        this.chips = chips;
    }
        
    /**
     * Set's the bet to a quantity
     * 
     * @param bet: the quantity of bet
     */
    public void setBet(int bet) {
        this.bet = bet;
    }
    
    /**
     * @return get's the bet of a player
     */
    public int getBet() {
        return this.bet;      
    }
    
    /**
     * Calculates the value of a player's hand
     * 
     * @param hand: the player's hand
     * @return: the total valuation of the hand
     */
    public int calculateValue(LinkedList<Card> hand) {
        // essentially just checks all of the
        // card hands described in the
        // Card class
        // returns 0 if there is no hand
        if (Card.isFlush(hand)) {
            if (Card.isRoyal(hand)) {
                return 20;
            } else if (Card.isStraight(hand)) {
                return 19;
            } else {
                return 16;
            }
        } else if (Card.quadruple(hand)) {
            return 18;
        } else if (Card.isFullHouse(hand)) {
            return 17;
        } else if (Card.isStraight(hand)) {
            return 15;
        } else if (Card.isTriple(hand)) {
            return 14;
        } else if (Card.twoDoubles(hand)) {
            return 12;
        } else if (Card.oneDouble(hand)) {
            return 10;
        } else {
            return 0;
        }
    }
}
