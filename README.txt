CIS 120 Game Project README
PennKey: judtin, 83182707
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. Collections
  
	  For this project, I specifically used Lists (LinkedList to be exact). The LinkedList
	  was particularly important to store the list of players and their respective hands,
	  as well as the Poker game's deck. For the hand and deck, it is specifically important to
	  store them in a list, as order does indeed matter. For the hand, I need to know the specific
	  element index to exchange, so that I can make sure that the player cannot exchange it again. For
	  the deck, I need to shuffle it every single round, and so the order of the deck does matter (it's random).
	  
	  For the players, I stored it in a LinkedList as well to differentiate the User from the random players 
	  that he/her is facing. To stay consistent, I always put the User as the first player, and by using a 
	  LinkedList, it was very easy to always retrieve the User.
	  
	  This collection is the best in this case because order always matters, at least in my implementation of poker.
	  It is also a lot easier to retrieve a particular user because of the indices given by a LinkedList.
	  
	  A Map and a Set would not work here because Set's do not care about order, and thus the deck would not truly
	  be shuffled. The Set won't work for players as well because it would be hard to differentiate the real User. The Map
	  wouldn't work for the deck because, again, there is no "sort" functionality in a Map, so I can't readily determine
	  a Poker straight when I see one. Map's don't work for players because there is no key, value pairing in my 
	  implementation of Player.


  2. File I/O

	I used I/O to store the most recent bet, as well as the number of chips each player has going into the next round.
	This file also signifies between the start of another round and the end of the game. 
	
	There is a file reader for the instructions button on the main screen. There is also a file reader and writer for the
	bet history, which stores things in the BetHistory.txt file. This is a running file, meaning it doesn't delete any past
	games. This is hopefully just to make things easier to analyze for a player. 
	
	The file stores to bet, the chips, and the player corresponding to that bet and chip combo. The file also catches and handles
	any IO errors that may occur. There is a lot of parsing, especially since there is a lot of data to read off for every new round, 
	it records the chips/bets and all players every time the method is called. It splits the data into an array of Strings, then parses
	for specific patterns (looks for the "Chips" and "Bets" string).
	
	The file is formatted in 4's, and is divided using a line of equal signs. Each line indicates the player, bet, and chip. 
	This is appropriate for history purposes. To analyze a game, or to double check the trends of how each game went, it is much
	easier to have a whole text file devoted to it. The BetHistory.txt will store all outcomes after every round, and will do this
	for every game the User plays. It essentially provides a full transcript of what happened in the game, even after the program
	stopped running.

  3. Inheritance/Sub typing for Dynamic Dispatch
  
  	I used a hierarchy in my Players: an ActualPlayer and a RandomPlayer, both of which extend the abstract class Player. This was necessary
  	because players have a lot in common (same fields, same methods), but the big difference is that the actual player is controlled by the user,
  	and the random player is controlled by randomness.
  	
  	The Player class was abstract because I needed to implement some methods in there so that Actual and Random player can share the same code for it. An example would
  	be the calculateValue method, where I calculate the value of a player's hand. This is extended to both Actual and Random player, as well as many other methods.
  	The differences in the classes were the implementations of newCard, bet, and match. This was important, newCard. bet, and match are determined by the user, and so
  	that implementation is much different then if these methods were determined by random actions. Thus, the way I wrote them were very different, but their actions are
  	the same (they both can get newCards, bet, and match). It's just a matter of HOW they do it, which is why I used inheritance and abstract classes for this. These
  	are nontrivial methods, as these are the core to how each player plays the Poker game.
  	
  	Dynamic dispatch was used a lot in the Poker class. I had a field called "players" which is a LinkedList of Players and CurrentPlayers. Every single time I needed to call "newCard, bet, and match" 
  	in the Poker class, I always referred to them as "players.get(x)" or "currentPlayers.get(x)". They are all Players, but the one I am referring to goes to that specific implementation of newCard, bet, 
  	and match.
  	
  	This is different than what I put in my proposal. After working on the game, I realized I didn't need classes to represent the player's hand, but rather I needed the inheritance to represent
  	the player themselves.


  4. Testable Component
  
  	I made three tests here: HandTest, PlayerTest, and PokerTest. They all test the core game state and the sequential rounds conditions. This was all JUnit tested, and the code was
  	designed so that I can test each individual thing. For example, HandTest tests the specifics hands in the Card class, just to make sure what I defined in the Card class is an
  	accurate representation of what the Hand represents. The PlayerTest tests the players and sees if they bet, match, and get the newCard correctly (based on Actual vs Random). It also
  	tests the shared method calculateValue, which just compares the hands and sees which hand from which player should actually in.
  	
  	Lastly, in PokerTest, I was testing the entire game state just to see if all the conditions held after every game, when a game ends, when a game starts, etc. 
  	
  	The edge cases are specifically in the HandTest and PlayerTest, where I make sure that the cards, no matter the distribution, should yield the same hand. This involved making sure
  	that a full house doesn't double count as a triple and a double, etc. 
  	
  	The test cases were divided up based on what class it tested. And the code was broken down into pieces, where each piece was testable.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  Player --> provides the necessary info to describe a player, including their hand, chips,
  how much they bet. There are 3 abstract methods (newCard, bet, willMatch) that are covered in
  the classes the extend Player. The other functionality includes getting/setting hands, making
  sure that players can't exchange a card twice, and calculating the Player's hand worth to see who wins.
  
  Actual Player --> Actual Player provides implementation newCard, bet, and willMatch.
  newCard checks to see if they can draw or not, then let's them draw.
  Bet just places the bet, making sure it accounts for any whacky cases the user throws.
  The willMatch matches the highest bet using the bet method.
  
  Random Player --> Random Player provides implementation newCard, bet, and willMatch. 
  newCard makes it so there's a 50% chance of exchanging each card only once
  Bet makes it so if he's running low on chips, there's a 50% of all in, and 50% of fold,
  otherwise there's an 80% of betting, and a 67% of increasing the anti
  willMatch makes it so that there's a 67% chance of matching, otherwise they fold.
  
  Card --> Card provides the details to define a card, specifically a suit and a value.
  It also provides the distinctions for types of hand built by multiple cards.
  
  Poker --> main bulk of the project, it models the state of the game. There are many features,
  but pretty much they allow for the User to update their player model, and then also update the 
  random players after the User enters his inputs. Poker plays the first turn and the final turn, 
  and also writes the data onto the BetHistory.txt. Poker also provides many methods for testing
  on the bottom.
  
  GameTable --> Allows for the GUI to interact with the poker game. The User has all of the
  functionality of a player, and is allowed to use them here. This Table also represents the
  status bar on the top of the screen, which updates with every move that they make. It also
  indicates to the user the state of the game, when there's a new game, etc. There is also a
  file reader function down below to update the game statuses for the GUI.
  
  Game --> the runnable portion of the GUI. Adds all of the necessary features for the User
  to interact with to play the game, and also has a file reader for the instructions of the game.
  This Game interacts only with the GameTable file. 


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  
  There was a lot of times where I was struggling on what to write first: the GameTable and Poker.
  I ended up (after a lot of time) specifically focusing on building the game model first, and then
  finding a way to represent it on the GUI. After finalizing how I wanted the Poker to be played, I then
  transitioned to implementing it on the front end for the User experience. 
 

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  
  I think the design is pretty good, but there's definitely a lot to work on. The functionality is definitely separated, but
  maybe sometimes it is a little too separated. I think something I could do in the future is move some of the methods in poker
  to the specific Player classes, and then write a method in Poker to run them all on the same time. (Ex. the bet and match and fold
  should all be taken care of in one method based upon User actions, that way it's more concise). But right now, it works just fine, and
  whenever someone calls on a bet, fold, match they are just directed to a specific method which is fine. 
  
  All of my stuff is encapsulated. There were many cases where I found myself writting getters and setters for the GUI and testing, 
  but otherwise the game itself is very protected. 



========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
  I used the Oracle Java Documentation to help me find certain functionalities of Java Swing.
  I also re-watched the Professors' videos on I/O.
