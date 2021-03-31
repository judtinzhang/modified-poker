import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 * There are several features, including instructions, a new game,
 * possibilities to change your card, a fold, a match, and a bet button
 * as well as a submit feature.
 */
public class Game implements Runnable {
    public void run() {
        
        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Poker");
        frame.setLocation(800, 500);

        // Status for the game
        final JLabel status = new JLabel("Setting up...");
        
        // Game board
        final GameTable table = new GameTable(status);
        frame.add(table, BorderLayout.CENTER);
        
        // Control Panel
        final JPanel control_panel = new JPanel(new GridLayout(1, 0, 10, 0));
        frame.add(control_panel, BorderLayout.NORTH);

        
        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JButton play = new JButton("Play Round");
        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                table.play();
            }
        });
        
        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, fileIterator());  
            }
        });
         
        control_panel.add(play);
        control_panel.add(status);
        control_panel.add(instructions);
 
                
        // Card buttons on bottom
        // There are a lot of buttons
        final JButton b1 = new JButton("Card 1");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.exchange(1);
            }
        });
        final JButton b2 = new JButton("Card 2");
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.exchange(2);
            }
        });
        final JButton b3 = new JButton("Card 3");
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.exchange(3);
            }
        });
        final JButton b4 = new JButton("Card 4");
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.exchange(4);
            }
        });
        final JButton b5 = new JButton("Card 5");
        b5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.exchange(5);
            }
        });
        final JButton bet = new JButton("Bet!");
        bet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = JOptionPane.showInputDialog("Enter the amount you would like to bet.");
                int bet = 0;
                try {
                    bet = Integer.parseInt(s);
                } catch (NumberFormatException nfe) {

                }
                table.placeBet(bet);
            }
        });
        
        final JButton match = new JButton("Match!");
        match.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.match();
            }
        });
        final JButton fold = new JButton("Fold!");
        fold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.fold();
            }
        });
        final JButton done = new JButton("Done!");
        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.playTurn();
            }
        });
        
        // add all the buttons onto the bottom panel
        final JPanel card_buttons = new JPanel();
        frame.add(card_buttons, BorderLayout.SOUTH);
        card_buttons.add(b1);
        card_buttons.add(b2);
        card_buttons.add(b3);
        card_buttons.add(b4);
        card_buttons.add(b5);  
        card_buttons.add(bet);
        card_buttons.add(match);
        card_buttons.add(fold);
        card_buttons.add(done);
        

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        // Start the game
        table.play();

    }
    
    
    /**
     * Uses IO to read the Instruction.txt file so that the code can be a little bit more
     * reader friendly (no huge paragraphs in the Game.java file).
     * 
     * @return a String'd version of what is written in the Instructions File
     */
    public static String fileIterator() {
        String filePath = "Instructions.txt";
        
        String value = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            Iterator<String> lineReader = br.lines().iterator();
            // iterates through the text file
            while (lineReader.hasNext()) {
                String line = lineReader.next();
                value += line + "\n";
            }
            // closes reader when done
            br.close();
          // accounts for errors
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid File Path.");
        }
        
        return value;
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}