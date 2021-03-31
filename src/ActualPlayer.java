import java.util.LinkedList;

public class ActualPlayer extends Player {

    public ActualPlayer(LinkedList<Card> hand, int chips) {
        super(hand, chips);
    }

    @Override
    /**
     * Decides whether or not to draw a new Card
     */
    public boolean newCard(int cardNumber, LinkedList<Card> deck) {
        // doesn't allow you to get a new card from a repeat
        if (super.hasRepeat(cardNumber)) {
            return false;
        } else {
            // replaces the card with the deck's first card
            this.getHand().set(cardNumber - 1, deck.get(0));
            
            // removes from deck, and makes sure
            // you can't take another card
            deck.removeFirst();
            this.addRepeated(cardNumber);
            return true;
        }        
    }

    @Override
    /**
     * Decides whether or not to place bets
     */
    public int bet(int value) {
        int chips = this.getChips();
        
        // checks value entries
        if (value < 0) {
            value = 0;
        }
        
        if (value > chips) {
            value = chips;
        }
        
        this.setBet(value);
        
        return value;
    }

    @Override
    /**
     * Decides whether or not to match the bet
     */
    public boolean willMatch(int high) {  
        // matches based on bet
        this.bet(high);
        return true;
    }
}
