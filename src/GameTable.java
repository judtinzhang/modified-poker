import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.*;

@SuppressWarnings("serial")
public class GameTable extends JPanel {
    
    private JLabel status;
    private Poker poker;
    
    // Game constants
    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGHT = 500;
    
    
    /**
     * Creates the Game Table with it's own Poker game
     * 
     * @param status: takes in a status to begin
     */
    public GameTable(JLabel status) {
        
        poker = new Poker();
        this.status = status;
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);

    }
    
    /**
     * Play's a new round or game, depending on if the game is over.
     * 
     */
    public void play() {
        // checks if you lost, won, or just started a new round
        if (poker.outOfChips()) {
            status.setText("You lost the game. It's Your Turn!");
            poker = new Poker();
        } else if (poker.randomChips() == 0) {
            status.setText("You won the game! It's Your Turn!");
            poker = new Poker();
        } else {
            // creates round here, not a new game
            status.setText("It's Your Turn!");
            poker.newRound();
        }
                
        repaint();
        requestFocusInWindow();
    }
    
    /**
     * Allows players to exchange a card one time, checks if the 
     * player is allowed to play first.
     *  
     * @param cardNumber: the particular number corresponding to a card 
     */
    public void exchange(int cardNumber) {
        // checks if the game is over, and if you are allowed to play
        if (!poker.gameOver()) {
            if (poker.playable()) {
                // tells you that you changed your card
                if (poker.changeCard(cardNumber)) {
                    status.setText("Card " + cardNumber + " successfully exchanged!");
                    repaint();
                  // tells you that you already changed the card
                } else {
                    status.setText("This action isn't unavailable.");  
                }
                // let's you know that you folded
            } else {
                status.setText("You've Folded already!");   
            }   
        } else {
            status.setText("This round is done! Again?"); 
        }   
    }

    /**
     * Allows users to bet only at given times.
     * Users are not allowed to bet after the submitted,
     * and are not allowed to bet after they folded.
     * 
     * @param number: the number that they bet. The Bet method in
     * Player will run checks to see if this number is valid.
     */
    public void placeBet(int number) {
        if (!poker.gameOver()) {
            // places the bet and get's responses
            int betValue = poker.bet(number);
            if (poker.playable()) {
                if (betValue == -1) {
                    // already placed bet here
                    status.setText("You can only Match or Fold!");
                } else if (betValue == -2) {
                    // already folded here
                    status.setText("You have Folded!");
                } else {
                    // tells you how much you bet
                    status.setText("You have bet " + betValue + " chips!");
                }
            } else {
                status.setText("You've Folded already!");   
            }
        } else {
            status.setText("This round is done! Again?"); 
        }
    }
    
    /**
     * Allows players to match at a given time.
     * Users are not allowed to match before they 
     * bet, or after they already fold.
     */
    public void match() {
        if (!poker.gameOver()) {
            if (poker.playable()) {
                if (poker.match()) {
                    // lets you know you matched
                    status.setText("You have Matched!");
                } else {
                    // won't let you match until you played
                    status.setText("People Haven't Gone Yet!");
                }
            } else {
                // tells you that you folded
                status.setText("You've Folded already!");  
            }
        } else {
            status.setText("This round is done! Again?"); 
        }
    }
    
    /**
     * Allows users to Fold, doesn't allow
     * folding after user already folds.
     */
    public void fold() {
        if (!poker.gameOver()) {
            if (poker.playable()) {
                poker.fold();
                status.setText("You have Folded!");      
            } else {
                // you're not allowed to fold twice
                status.setText("You've Folded already!");      
            }   
        } else {
            status.setText("This round is done! Again?");  
        }
        
    }
    
    /**
     * Plays the 2 turns of the user. User cannot submit
     * their turn if they haven't done anything yet.
     * Users are not allowed to do anything the second turn
     * if they already folded in the first.
     */
    public void playTurn() {
        if (!poker.gameOver()) {
            int firstTurnOutcome = poker.playFirstTurn();
            
            if (firstTurnOutcome == -1) {
                // this checks for the second turn (match or fold)
                // releases the win or loss
                if (!poker.finalTurn()) {
                    status.setText("You lost this round! Another?"); 
                    repaint();
                } else {
                    status.setText("You won this round! Another?");
                    repaint();
                }
            } else if (firstTurnOutcome == -2) {
                // can't click done until you take an action
                status.setText("You have not done anything yet!"); 
            } else if (firstTurnOutcome == 0) {
                // if there's not enough people for a winner, nobody wins
                status.setText("Nobody loses this round. Play again!");
                repaint();
            } else {
                if (poker.playable()) {
                    // tells you the maximum bet and what you have to match
                    // NOTE: if you click Done without matching, you automatically Fold
                    status.setText("Max Bet is " + firstTurnOutcome + ". Fold, Match, or Done?");
                    repaint();
                } else {
                    // Despite folding, you still need to click Done to proceed
                    status.setText("You've Folded, please click Done!");
                    repaint();
                }           
            } 
        } else {
            status.setText("This round is done! Again?"); 
        }       
    }

    @Override
    /**
     * Paints the table and the cards.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Monaco", Font.PLAIN, 20));     
        
        // draws the Actual Player card outline
        g.drawLine(100, 350, 700, 350);
        g.drawLine(100, 350, 700, 350);
        g.drawLine(100, 500, 100, 350);
        g.drawLine(700, 500, 700, 350);
        
        g.drawLine(220, 500, 220, 350);
        g.drawLine(340, 500, 340, 350);
        g.drawLine(460, 500, 460, 350);
        g.drawLine(580, 500, 580, 350);
        
        
        // draws the Chip and Bet values and rectangles
        g.setFont(new Font("Monaco", Font.PLAIN, 14));
        
        String [] history = historyReader();
        
        g.drawRect(400, 290, 120, 60);
        g.drawRect(280, 290, 120, 60);
        
        g.drawRect(80, 175, 60, 80);
        g.drawRect(80, 150 - 55, 60, 80);
        
        g.drawRect(800 - 140, 175, 60, 80);
        g.drawRect(800 - 140, 150 - 55, 60, 80);
        
        g.drawRect(400, 80, 100, 50);
        g.drawRect(300, 80, 100, 50);     
        
        g.drawString("Chips", 410, 310);
        g.drawString("Bet", 290, 310);
        
        g.drawString("Chips", 84, 195);
        g.drawString("Bet", 84, 150 - 38);
        
        g.drawString("Chips", 800 - 135, 195);
        g.drawString("Bet",800 - 135, 150 - 38);
        
        g.drawString("Chips", 410, 100);
        g.drawString("Bet", 310, 100);
 
        // checks to make sure there's something to read
        if (history[0] != null) {
            // checks if this is a new round or new game
            if (history[8].equals("START")) {
                g.drawString(history[0], 410, 330);
                g.drawString(history[1], 290, 330);

                g.drawString(history[2], 84, 225);
                g.drawString(history[3], 84, 150 - 38 + 30);               
                
                g.drawString(history[4], 800 - 135, 225);
                g.drawString(history[5], 800 - 135, 150 - 38 + 30);

                g.drawString(history[6], 410, 120);
                g.drawString(history[7], 310, 120);
            } else {
                g.drawString("-", 410, 330);
                g.drawString("-", 290, 330);

                g.drawString("-", 84, 225);
                g.drawString("-", 84, 150 - 38 + 30);               
                
                g.drawString("-", 800 - 135, 225);
                g.drawString("-", 800 - 135, 150 - 38 + 30);

                g.drawString("-", 410, 120);
                g.drawString("-", 310, 120);
            }
        } else {
            g.drawString("-", 410, 330);
            g.drawString("-", 290, 330);

            g.drawString("-", 84, 225);
            g.drawString("-", 84, 150 - 38 + 30);               
            
            g.drawString("-", 800 - 135, 225);
            g.drawString("-", 800 - 135, 150 - 38 + 30);

            g.drawString("-", 410, 120);
            g.drawString("-", 310, 120);
        }

        g.setFont(new Font("Monaco", Font.PLAIN, 20));
        
        
        // draws the individual card: suit and value
        LinkedList<Player> players = poker.getPlayers();
        LinkedList<Card> playerHand = players.get(0).getHand();
        
        int counter = 0;
        for (Card c : playerHand) {
            
            // converts the value (0 --> 12 scale) to a (A --> K scale).
            String value = Integer.toString(c.getValue() + 1);
            
            if (value.equals("1")) {
                value = "A";
            } else if (value.equals("11")) {
                value = "J";
            } else if (value.equals("12")) {
                value = "Q";
            } else if (value.equals("13")) {
                value = "K";
            }
            
            // converts the numerical suit to the String version
            String suitString = "";
            int suit = c.getSuit();
            
            if (suit == 0) {
                suitString = "Diamond";
            } else if (suit == 1) {
                suitString = "Club";
            } else if (suit == 2) {
                suitString = "Heart";
            } else {
                suitString = "Spade";
            }
            
            g.drawString(suitString, 110 + 120 * counter, 375);
            g.drawString(value, 110 + 120 * counter, 450);
            counter++;   
        }
        
        // draws the remaining Random Player card outlines
        // then puts question marks on those cards (so they don't
        // see the other players' cards)
        g.drawLine(80, 50, 0, 50);
        g.drawLine(80, 100, 0, 100);
        g.drawLine(80, 150, 0, 150);
        g.drawLine(80, 200, 0, 200);
        g.drawLine(80, 250, 0, 250);
        g.drawLine(80, 300, 0, 300);
        g.drawLine(80, 300, 80, 50);   
        
        g.drawLine(720, 50,  800, 50);
        g.drawLine(720, 100, 800, 100);
        g.drawLine(720, 150, 800, 150);
        g.drawLine(720, 200, 800, 200);
        g.drawLine(720, 250, 800, 250);
        g.drawLine(720, 300, 800, 300);
        g.drawLine(720, 300, 720, 50);
               
        g.drawLine(250, 0,  250, 80);
        g.drawLine(310, 0, 310, 80);
        g.drawLine(370, 0, 370, 80);
        g.drawLine(430, 0, 430, 80);
        g.drawLine(490, 0, 490, 80);
        g.drawLine(550, 0, 550, 80);
        g.drawLine(250, 80, 550, 80);
        
        // if the poker game is over then
        // the cards will present themselves
        if (!poker.gameOver()) {
            
            for (int i = 0; i < 5; i++) {
                g.drawString("?", 35, 82 + 50 * i);
            }
            
            for (int i = 0; i < 5; i++) {
                g.drawString("?", 800 - 45, 82 + 50 * i);
            }
            
            for (int i = 0; i < 5; i++) {
                g.drawString("?", 275 + 60 * i, 45);
            }
            
        } else {
            // makes the cards show
            // this is pretty long, but had to be done
            // because each player needed to go in a different
            // location 
            g.setFont(new Font("Monaco", Font.PLAIN, 12));
            
            LinkedList<Card> h1 = players.get(1).getHand();
            LinkedList<Card> h2 = players.get(2).getHand();
            LinkedList<Card> h3 = players.get(3).getHand();
            
            LinkedList<Player> currents = poker.getCurrentPlayers();
            
            if (currents.contains(players.get(1))) {
                for (int i = 0; i < h1.size(); i++) {
                    String value = Integer.toString(h1.get(i).getValue() + 1);
                    if (value.equals("1")) {
                        value = "A";
                    } else if (value.equals("11")) {
                        value = "J";
                    } else if (value.equals("12")) {
                        value = "Q";
                    } else if (value.equals("13")) {
                        value = "K";
                    }
                    
                    String suitString = "";
                    int suit = h1.get(i).getSuit();
                    
                    if (suit == 0) {
                        suitString = "Diamond";
                    } else if (suit == 1) {
                        suitString = "Club";
                    } else if (suit == 2) {
                        suitString = "Heart";
                    } else {
                        suitString = "Spade";
                    }
                    
                    g.drawString(suitString, 5, 65 + 50 * i);
                    g.drawString(value, 60, 92 + 50 * i);
                    
                }
            }
            
            if (currents.contains(players.get(2))) {
                for (int i = 0; i < h2.size(); i++) {
                    String value = Integer.toString(h2.get(i).getValue() + 1);
                    if (value.equals("1")) {
                        value = "A";
                    } else if (value.equals("11")) {
                        value = "J";
                    } else if (value.equals("12")) {
                        value = "Q";
                    } else if (value.equals("13")) {
                        value = "K";
                    }
                    
                    String suitString = "";
                    int suit = h2.get(i).getSuit();
                    
                    if (suit == 0) {
                        suitString = "Diamond";
                    } else if (suit == 1) {
                        suitString = "Club";
                    } else if (suit == 2) {
                        suitString = "Heart";
                    } else {
                        suitString = "Spade";
                    }
                    
                    g.drawString(suitString, 800 - 75, 65 + 50 * i);
                    g.drawString(value, 800 - 20, 92 + 50 * i);     
                }
            }
            
            if (currents.contains(players.get(3))) {
                for (int i = 0; i < h3.size(); i++) {
                    String value = Integer.toString(h3.get(i).getValue() + 1);
                    if (value.equals("1")) {
                        value = "A";
                    } else if (value.equals("11")) {
                        value = "J";
                    } else if (value.equals("12")) {
                        value = "Q";
                    } else if (value.equals("13")) {
                        value = "K";
                    }
                    
                    String suitString = "";
                    int suit = h3.get(i).getSuit();
                    
                    if (suit == 0) {
                        suitString = "Diamond";
                    } else if (suit == 1) {
                        suitString = "Club";
                    } else if (suit == 2) {
                        suitString = "Heart";
                    } else {
                        suitString = "Spade";
                    }
                    
                    g.drawString(suitString, 255 + 60 * i, 15);
                    g.drawString(value, 255 + 60 * i, 50);     
                }
            } 
        }       
    }
    
    /**
     * IO: reads BetHistory.txt and generates strings to present
     * on the table
     * 
     * @return and String array of the information necessary to
     * put on the table
     */
    public static String [] historyReader() {
        String filePath = "BetHistory.txt";
        
        // this is what will be returned
        String [] values = new String [9];

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            Iterator<String> lineReader = br.lines().iterator();
            
            boolean dottedLine = false;
            int index = 0;
            
            // iterates through the text file
            while (lineReader.hasNext()) {
                String line = lineReader.next();
                // ignore the "====="
                if (line.contains("==")) {
                    dottedLine = true;
                    // let's us know if this is a new game or round
                    if (!lineReader.hasNext()) {
                        if (line.contains("END")) {
                            values[8] = "END";
                        } else {
                            values[8] = "START";
                        }
                    }  
                }
                if (!dottedLine) {
                    String [] score = line.split("\\s+");
                    
                    // finds the chips and the bets
                    for (int i = 0; i < score.length; i++) {
                        if (score[i].equals("Chips:")) {
                            String chips = score[i + 1].replace(".", "");
                            values[index] = chips;
                            index++;
                        }
                        if (score[i].equals("Bet:")) {
                            String bet = score[i + 1].replace(".", "");
                            values[index] = bet;
                            index++;
                        }
                    }
                    
                }
                dottedLine = false;
                
                // resets if there is another round in the history
                if (index == 8) {
                    index = 0;
                }
            }
            // closes the reader
            br.close();
            
          // accounts for any errors
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid File Path.");
        }
        return values;
    }
       
    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {        
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}