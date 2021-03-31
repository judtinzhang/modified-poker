import java.util.LinkedList;

public class RandomPlayer extends Player {

    public RandomPlayer(LinkedList<Card> hand, int chips) {
        super(hand, chips);
    }

    @Override
    /**
     * Decides whether or not to draw a new Card
     */
    public boolean newCard(int cardNumber, LinkedList<Card> deck) {

        LinkedList<Card> hand = this.getHand();
        
        for (int i = 0; i < hand.size(); i++) {
            
            // 50% chance of changing each card in the player hand
            double randomNum = Math.random();
            if (randomNum >= 0.5) {
                this.getHand().set(i, deck.get(0));
                deck.removeFirst();
            }
        }
        
        // always returns true because this method never changes a card more than once
        return true;     
    }

    @Override
    /**
     * Decides whether or not to place bets
     */
    public int bet(int value) {
        int bet = 0;
        int chips = this.getChips();
        
        if (value > chips) {
            // 50% change of folding, and 50% chance of going all-in
            // if the bet value player makes is greater than
            // what the random player has
            double randomNum = Math.random();
            if (randomNum >= 0.5) {
                bet = chips;
            }
        } else {
            double randomNum1 = Math.random();
            // 80% of playing, 20% of folding
            if (randomNum1 >= 0.2) {
                double randomNum2 = Math.random();
                // within that 80% chance, there's a 
                // 67% of increasing, and 33% of staying
                if (randomNum2 >= 0.33) {
                    int diff = chips - value;
                    double increase = Math.random() * diff + 1 + value;
                    if (increase > chips) {
                        bet = chips;
                    } else {
                        bet = (int) increase;
                    }
                } else {
                    bet = value;
                }   
            }
        }
        
        this.setBet(bet);
        return bet;
    }

    @Override
    /**
     * Decides whether or not to match the bet
     */
    public boolean willMatch(int high) {
        
        // 67% chance of matching or all in
        // 33% chance of folding
        double randomNum = Math.random();
        if (randomNum >= 0.33) {
            
            int chips = this.getChips();
            
            if (high > chips) {
                this.setBet(chips);
            } else {
                this.setBet(high);
            }
            return true;
        } else {
            this.setBet(0);
            return false;
        }
    }
}