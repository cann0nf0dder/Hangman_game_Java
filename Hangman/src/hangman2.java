

import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
* @author cann0nF00der
* ispired by System of a Down mix albums playlist
*/
public class hangman2 {

//list of hidden words.

private List wordList = new ArrayList();

// list of labels

private List labelList = new ArrayList();

// The secret word.

private String hiddenWord;

// fiel for input.

private JTextField inputLetterField;

// Frame

private JFrame frame;

// panel to display the game

private JPanel gamePanel;

// panel to dispay the label

private JPanel labelPanel;

// panel to display the lives

private JPanel livesPanel;

// Panel for cathegory

private JPanel cathegoryPanel;
//Label for cathegory

private JLabel catLabel;

// index from list for keeping track of secret hidden word.

private int previousIndex;

// lives.

private int lives;

// Label for lives.

private JLabel livesLabel;

//Main method

public static void main(String[] args) {
hangman2 hangman = new hangman2();
hangman.addWords(); // add secret words to list
hangman.getWord(); // make secret word
hangman.display(); // display game
}

// List of the premiership teams to make it easier to guess

public void addWords() {
wordList.add("manchesterunited");
wordList.add("liverpool");
wordList.add("chelsea");
wordList.add("arsenal");
wordList.add("astonvilla");
wordList.add("everton");
wordList.add("fulham");
wordList.add("westham");
wordList.add("manchestercity");
wordList.add("tottenham");
wordList.add("wigan");
wordList.add("stoke");
wordList.add("bolton");
wordList.add("portsmouth");
wordList.add("blackburn");
wordList.add("hull");
wordList.add("newcastle");
wordList.add("middlesbourgh");
wordList.add("westbrom");

}
//method for random word
public void getWord() {
Random random = new Random();
int index = random.nextInt(wordList.size());
while (index == previousIndex) {
index = random.nextInt(wordList.size());
}
hiddenWord = wordList.get(index);
previousIndex = index;
}

// gui

public void display() {
frame = new JFrame("HaNgMaN");
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setJMenuBar(new GameBar());
gamePanel = new JPanel(new BorderLayout());
livesPanel = new JPanel();
lives = 6;
livesLabel = new JLabel(lives + " lives remaining");
livesPanel.add(livesLabel);
gamePanel.add(livesPanel, BorderLayout.WEST);
cathegoryPanel = new JPanel();
catLabel = new JLabel("Cathegory: Premiership Football Teams 2008/09");
cathegoryPanel.add(catLabel);
gamePanel.add(cathegoryPanel, BorderLayout.NORTH);

JPanel fieldPanel = new JPanel();
inputLetterField = new JTextField(1);
inputLetterField.addKeyListener(new LetterChecker());
fieldPanel.add(inputLetterField);
gamePanel.add(fieldPanel, BorderLayout.CENTER);

labelPanel = new JPanel();
setupLabels();
frame.add(gamePanel, BorderLayout.CENTER);

frame.setSize(300,150);
frame.setResizable(false);
frame.setVisible(true);
}

//lebel to display dashes
private void setupLabels() {
for (int i=0; i < hiddenWord.length(); i++) {
JLabel label = new JLabel("-");
labelList.add(label);
labelPanel.add(label);
}
gamePanel.add(labelPanel, BorderLayout.SOUTH);
}

//newgame method ,sets lives to 7 and refresh panels
private void newGame() {

lives = 6; // reset lives
livesLabel.setText(lives + " lives remaining");
livesPanel.validate(); // refresh lives panel
// remove all labels
for (JLabel label : labelList) {
labelPanel.remove(label);
}
labelPanel.validate(); // refresh label panel
gamePanel.remove(labelPanel); // remove label panel
gamePanel.validate(); // refresh game panel
labelList.clear();
getWord();
setupLabels();
frame.validate(); // refresh frame
}

//inner class for menu bar
private class GameBar extends JMenuBar {
private GameBar() {
super();
generateGameMenu();
}

//gui for menu
private void generateGameMenu() {
JMenu gameMenu = new JMenu("Game");
gameMenu.setMnemonic(KeyEvent.VK_G);
JMenuItem newGameItem = new JMenuItem(new GameAction());
newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
InputEvent.ALT_DOWN_MASK)); //shortcut
gameMenu.add(newGameItem);
gameMenu.addSeparator();
JMenuItem exitItem = new JMenuItem(new ExitAction());
exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
InputEvent.ALT_DOWN_MASK)); // shortcut ;)
gameMenu.add(exitItem);
add(gameMenu);
}

} // end inner class

//inner class for action
private class GameAction extends AbstractAction {
private GameAction() {
putValue(AbstractAction.NAME, "New");
putValue(AbstractAction.SHORT_DESCRIPTION, "New game");
putValue(AbstractAction.MNEMONIC_KEY, KeyEvent.VK_N);
}
//new game
@Override
public void actionPerformed(ActionEvent e) {
newGame();
}

} // end inner class
//inner class for another action
private class ExitAction extends AbstractAction {
private ExitAction() {
putValue(AbstractAction.NAME, "Exit");
putValue(AbstractAction.SHORT_DESCRIPTION, "Exit game");
putValue(AbstractAction.MNEMONIC_KEY, KeyEvent.VK_X);
}
//for exit
@Override
public void actionPerformed(ActionEvent e) {
System.exit(0);
}

} // end inner class

//checks user input

private class LetterChecker extends KeyAdapter {
private boolean isComplete() {
int length = 0;
for (JLabel label : labelList) {
if (label.getText().charAt(0) != '-') {
length++;
}
}
if (hiddenWord.length() != length) {
return false;
}
else {
return true;
}
}
//validate user inpit
@Override
public void keyTyped(KeyEvent e) {
char ch = e.getKeyChar(); // get input
// check if input is numeric
if (e.isAltDown() || e.isShiftDown() || Character.isDigit(ch)) {}
else {
char[] ary = new char[hiddenWord.length()];
ary = hiddenWord.toCharArray();
boolean noMatch = true; // assume incorrect input
for (int i=0; i < ary.length; i++) {
if (ch == ary[i]) {
// get label index
JLabel charLabel = labelList.get(i);
// update label
charLabel.setText(Character.toString(ch));
noMatch = false;
if (isComplete()) { // check if word is completed
//shows dialog
int option = JOptionPane.showConfirmDialog(null,
"Congratulations ! New game??",
"Gooooooooooooooooood!",
JOptionPane.OK_CANCEL_OPTION,
JOptionPane.PLAIN_MESSAGE);
if (option == JOptionPane.OK_OPTION) {
newGame();
}
}
}
}
if (noMatch) { // incorrect input
lives--; // decrement lives
// update lives label
livesLabel.setText(lives + " lives remaining");
livesPanel.validate(); // refresh lives panel
if (lives == 0) {
int option = JOptionPane.showConfirmDialog(null,
"Game over! New game?",
"Buuuuuuuuuu!",
JOptionPane.OK_CANCEL_OPTION,
JOptionPane.PLAIN_MESSAGE);
if (option == JOptionPane.OK_OPTION) {
newGame(); // restart game
}
}
else {
JOptionPane.showMessageDialog(null, "No match :-P",
"Wrong", JOptionPane.PLAIN_MESSAGE);
gamePanel.validate(); // refresh game panel
frame.validate(); // refresh window
}
}
inputLetterField.setText(""); // display only one letter
}
}

} // end inner class
}