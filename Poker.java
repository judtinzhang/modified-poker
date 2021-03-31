import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.LinkedList;

public class Poker {
    
    /**
     * Poker game contains cards, players, and 
     * players still playing.
     * 
     * Also contains status checkers, such as the current
     * bet, if it's the player's turn, if the game is over,
     * and if the game is playable for the player
     * 
     */
    private LinkedList<Card> cards;
    private LinkedList<Player> players;
    private LinkedList<Player> currentPlayers;
    
    private int currentBet;
    private boolean playedTurn;
    private boolean gameOver;
    private boolean playable;
    
    
    public Poker() {
        newGame();
    }
    
    /**
     * Creates a new game, shuffled deck, and dealt hands.
     * Also creates new players
     */
    public void newGame() {
        
        cards = new LinkedList<Card>();
        
        // creates a new deck
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                Card c = new Card(i, j);
                cards.add(c);
            }
        }
               
        players = new LinkedList<Player>();
        currentPlayers = new LinkedList<Player>();
        
        // shuffles the deck
        Collections.shuffle(cards);
        
        LinkedList<Card> firstHand = new LinkedList<Card>();
        LinkedList<Card> secondHand = new LinkedList<Card>();
        LinkedList<Card> thirdHand = new LinkedList<Card>();
        LinkedList<Card> fourthHand = new LinkedList<Card>();
        
        // adds 5 cards to each player's hand
        for (int i = 0; i < cards.size(); i++) {
            if (i < 5) {
                firstHand.add(cards.get(i));
            } else if (i < 10) {
                secondHand.add(cards.get(i));
            } else if (i < 15) {
                thirdHand.add(cards.get(i));
            } else if (i < 20) {
                fourthHand.add(cards.get(i));
            }
        }
        
        // removes the cards given from the deck
        for (int i = 0; i < 20; i++) {
            cards.removeFirst();
        }
             
        // gives each player 100 chips
        players.add(new ActualPlayer(firstHand, 100));
        players.add(new RandomPlayer(secondHand, 100));
        players.add(new RandomPlayer(thirdHand, 100));
        players.add(new RandomPlayer(fourthHand, 100));
                
        gameOver = false;
        playable = true;
        playedTurn = false;
        currentBet = 0;

    }
    
    /**
     * Similar to New Game, but does not create new users.
     * Keeps the chips the same, and also removes any previous
     * Player history if necessary.
     * 
     */
    public void newRound() {
        
        // creates and shuffles the deck
        cards = new LinkedList<Card>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                Card c = new Card(i, j);
                cards.add(c);
            }
        }        
        Collections.shuffle(cards);
        
        LinkedList<Card> firstHand = new LinkedList<Card>();
        LinkedList<Card> secondHand = new LinkedList<Card>();
        LinkedList<Card> thirdHand = new LinkedList<Card>();
        LinkedList<Card> fourthHand = new LinkedList<Card>();
        
        // adds the cards to the player's hand
        for (int i = 0; i < cards.size(); i++) {
            if (i < 5) {
                firstHand.add(cards.get(i));
            } else if (i < 10) {
                secondHand.add(cards.get(i));
            } else if (i < 15) {
                thirdHand.add(cards.get(i));
            } else if (i < 20) {
                fourthHand.add(cards.get(i));
            }
        }
        
        // removes the cards given out from deck
        for (int i = 0; i < 20; i++) {
            cards.removeFirst();
        }
        
        players.get(0).setHand(firstHand);
        players.get(1).setHand(secondHand);
        players.get(2).setHand(thirdHand);
        players.get(3).setHand(fourthHand);
        
        // clears any repeated array that they may have
        // and resets the bet value
        currentPlayers.clear();
        for (Player p : players) {
            currentPlayers.add(p);
            p.deleteRepeated();
            p.setBet(-1);
        }
        
        playedTurn = false;
        currentBet = 0;
        gameOver = false;
        playable = true;

    }
    
    public boolean outOfChips() {
        return players.get(0).getChips() == 0;
    }
    
    /**
     * Users can change the specific card number
     * 
     * @param number: denotes the specific card
     * @return boolean based on whether or not this
     * action is valid
     */
    public boolean changeCard(int number) {
        // sees if player can exchange card
        if (!playedTurn) {
            return players.get(0).newCard(number, cards);
        }
        return false;
        
    }
    
    /**
     * Allows users to bet certain quantities
     * 
     * @param number denotes amount to bet
     * @return the bet amount after running checks,
     * returns -1 and -2 if the user folds or if 
     * it is not their turn, respectively
     */
    public int bet(int number) {
        if (playedTurn) {
            // returns if player cannot play now
            return -1;
        } else {
            int bet = players.get(0).bet(number);
            
            if (bet == 0) {
                // returns a folding hand
                return -2;
            } else {
                return bet;
            }
        }
    }
    
    /**
     * Allows users to match the bet of others
     * 
     * @return true or false depending whether or not
     *  the user is allowed to match at the time
     */
    public boolean match() {
        if (playedTurn) {
            // allows players to match after playing their first turn
            return players.get(0).willMatch(currentBet);
        } else {
            return false;
        }
    }
    
    /**
     * Allows users to fold
     */
    public void fold() {
        players.get(0).setBet(0);
    }
    
    /**
     * Accounts for the player's first turns, and completes
     * all of the random player's turns as well
     * Ends the game if everybody folds, or everybody
     * except for one person folds. If the user does not bet,
     * then the user will be denied access to any other button
     * other than "Done"
     * 
     * @return an integer denoting the outcome: -2 if the player
     *  didn't make a turn, -1 if already played this turn, 0 if 
     *  nobody wins, and the maximal bet otherwise. 
     */
    
    public int playFirstTurn() {
        // checks if the player already played the first  turn
        if (!playedTurn) {
            Player actualPlayer = players.get(0);
            int betVal = actualPlayer.getBet();
            
            // returns if the actual player didn't do anything
            if (betVal == -1) {
                return -2;
            }
            
            // 2 dynamic dispatches (players is a list of Players)
            for (Player p : players) {
                if (p.getClass().equals(RandomPlayer.class)) {
                    p.newCard(0, cards);
                    p.bet(betVal);
                }
            }

            // get's the maximum bet of these players
            int maxBet = 0;
            for (Player p : players) {            
                if (p.getBet() == 0) {
                    currentPlayers.remove(p);
                }
                if (p.getBet() > maxBet) {
                    maxBet = p.getBet();
                }
            }
            
            // IO writes on the BetHistory here
            initialStage();
            
                      
            // if there's only 0 or 1 person, then nobody wins
            if (currentPlayers.size() == 0 || currentPlayers.size() == 1) {
                gameOver = true;
                playable = false;                
                finalStage();
                return 0;
            }
            
            // turns the playable to false (they already submitted their answer)
            if (!currentPlayers.contains(players.get(0))) {
                playable = false;
            }
                        
            playedTurn = true;
            currentBet = maxBet;
                        
            return maxBet;
        }
        return -1;
    }
    
    /**
     * Plays the final turn and makes the calculations.
     * First sees if the random players want to match the maximum,
     * then adds to the prize pool, and subtracts the chips from
     * the user. 
     * 
     * This version of Poker is all-or-nothing, which means 1 person
     * going all-in is satisfactory for that user to win all of the
     * chips in the prize pool
     * 
     * After seeing who matches, this method will compare the hand
     * valuations and determine a winner.
     * 
     * @return a true or false of whether or not the real User won.
     */
    public boolean finalTurn() {
               
        // this is the prize the winners get
        int prize = 0;
        
        // decides for the players if they will match or not
        for (Player p : currentPlayers) {
            if (p.getClass().equals(RandomPlayer.class)) {
                if (p.getBet() != currentBet) {
                    p.willMatch(currentBet);
                }              
            }
            // removes their bet from their chips
            // and adds it to the prize
            p.setChips(p.getChips() - p.getBet());
            prize += p.getBet();
        }
        
        LinkedList<Player> holder = new LinkedList<Player>();
        
        // removes any player from currentPlayers
        // if they folded
        for (Player p : currentPlayers) {    
            if (p.getBet() != 0) {
                if (p.getClass().equals(ActualPlayer.class)) {
                    if (p.getBet() == currentBet) {
                        holder.add(p);
                    }
                } else {
                    holder.add(p);
                }
            }
        }       
        currentPlayers = holder;
        
        
        // makes sure the gameOver state is true here
        // before we start returning
        gameOver = true;
        
        // if there's only 1 current player, he's the winner
        if (currentPlayers.size() == 1) {
            currentPlayers.get(0).setChips(currentPlayers.get(0).getChips() + prize);
            finalStage();
            return !currentPlayers.get(0).getClass().equals(RandomPlayer.class);
        }
                  
        int max = 0;
        int highCard = 0;
        
        Player[] winner = new Player[1];
        winner[0] = players.get(0);
        Player[] cardWinner = new Player[1];
        
        // compares the card valuations
        for (Player p : currentPlayers) {
            int handValue = p.calculateValue(p.getHand());
            
            if (handValue > max) {
                max = handValue;
                winner[0] = p;
            } else if (handValue == max && winner[0] != null) {
                // if there is no real "hand", then we will look
                // at card valuation
                int c1 = Card.highCard(p.getHand());
                int c2 = Card.highCard(winner[0].getHand());
                
                if (c1 > c2) {
                    winner[0] = p;
                }
            }
            if (Card.highCard(p.getHand()) > highCard) {
                cardWinner[0] = p;
            }
            
        }
        
        // if no one has a real hand, then the person with
        // the highest card valuation wins
        if (winner[0] == null) {
            cardWinner[0].setChips(cardWinner[0].getChips() + prize);
            // does another IO writing here. Records final outcomes
            finalStage();
            return !cardWinner[0].getClass().equals(RandomPlayer.class); 
        } else {
            winner[0].setChips(winner[0].getChips() + prize);
            finalStage();
            return !winner[0].getClass().equals(RandomPlayer.class);
        }
    }
    
    /**
     * 
     * @return whether this poker
     * game is playable for the User.
     */
    public boolean playable() {
        return playable;
    }
    
    /**
     * 
     * @return whether this round
     * is over.
     */
    public boolean gameOver() {
        return gameOver;
    }
  
    /**
     * Sees if the random players have lost
     * 
     * @return a summation of the random player chips
     */
    public int randomChips() {
        int sum = 0;
        for (int i = 1; i < 4; i++) {
            sum += players.get(i).getChips();
        }
        return sum;
    }
           
    /**
     * IO Writer, writes down the initial stages of the game
     */
    public void initialStage() {       
        // writes in the BetHistory file
        String filePath = "BetHistory.txt";
        try {
            // utilizes writer and buffered writer
            Writer w = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(w);
                 
            int i = 1;
            for (Player p : players) {
                String message = "";
                int chips = p.getChips();
                int bet = p.getBet();
                
                // records both bet and chips, based on player
                if (i == 1) {
                    message += "User Initial Chips: " + chips + ". User Initial Bet: " + bet + ".";
                } else {
                    message += "Player " + i + " Initial Chips: " + chips + ". " 
                            +  "Player " + i + " Initial Bet: " + bet + ".";
                }
                i++;
                // writes the message
                bw.write(message);
                if (i == 5) {
                    // denotes that we are in the same round (unlike the end writer)
                    bw.newLine();
                    bw.write("=========================SAME=ROUND========================");
                }
                bw.newLine();                
            }
            bw.close();
          // accounts for any possible errors when writing
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid File Path.");
        }
    }
    
    public void finalStage() {
        // records in the BetHistory file
        String filePath = "BetHistory.txt";
        try {
            // utilizes both file writer and buffered writer
            Writer w = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(w);
                 
            int i = 1;
            for (Player p : players) {
                String message = "";
                int chips = p.getChips();
                int bet = p.getBet();
                if (i == 1) {
                    // gets the player, their chips, and their bet and records them
                    message += "User Final Chips: " + chips + ". User Final Bet: " + bet + ".";
                } else {
                    message += "Player " + i + " Final Chips: " + chips + ". " 
                            +  "Player " + i + " Final Bet: " + bet + ".";
                }
                i++;
                bw.write(message);
                
                // denotes if the game is done, or if there's more to add
                if (i == 5 && (randomChips() == 0 || players.get(0).getChips() == 0)) {
                    bw.newLine();
                    bw.write("=========================END=OF=GAME=======================");
                } else if (i == 5) {
                    bw.newLine();
                    bw.write("===========================================================");
                }
                bw.newLine();               
            }          
            bw.close();
          // accounts for possible errors while writing
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid File Path.");
        }
    }
    
    
    /**
    * I made the following methods purely for testing and GUIs.
    * 
    * It is not used anywhere else other than PokerTest and the
    * Paint Component in GameTable.
    * 
    * They're all just getters and setters.
    */
    
    public LinkedList<Card> getDeck() {
        return cards;
    }
    
    public LinkedList<Player> getPlayers() {
        return players;
    }
    
    public LinkedList<Player> getCurrentPlayers() {
        return currentPlayers;
    }
    
    public int getCurrentBet() {
        return currentBet;
    }
    
    public void setCurrentBet(int bet) {
        currentBet = bet;
    }
    
    public boolean getPlayedTurn() {
        return playedTurn;
    }
    
    public void setPlayedTurn(boolean b) {
        playedTurn = b;
    }
    
    public boolean getGameOver() {
        return gameOver;
    }
    
    public void setGameOver(boolean b) {
        gameOver = b;
    }
    
    public boolean getPlayable() {
        return playable;
    }
    
    public void setPlayable(boolean b) {
        playable = b;
    }  
}