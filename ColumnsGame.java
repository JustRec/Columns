import enigma.console.Console;
import enigma.console.TextAttributes;
import enigma.core.Enigma;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class ColumnsGame {

	enigma.console.Console cn = Enigma.getConsole("Columns", 70, 25, 17);

	MultiLinkedList gameScreen = new MultiLinkedList();

	SingleLinkedList box = new SingleLinkedList();

	DoubleLinkedList dll_scores = new DoubleLinkedList();
	DoubleLinkedList dll_names = new DoubleLinkedList();
	DoubleLinkedList dll_highscore = new DoubleLinkedList();

	int transferCount = 0;
	int playerScore = 0;
	int boxNumber = 0;

	int setCount = 0;

	int endGameScore = 0;

	boolean isBoxNumberSelected = false;
	boolean isBoxNumberPlaced = true;

	boolean isGameContinue = true;

	CardNode boxCard;

	Cursor cursor = new Cursor();

	private int keypr;
	private int rkey;

	private int selected_column = 0;
	private int selected_index = 0;
	private int target_column = 0;

	private Color PURPLE = new Color(189, 147, 255);
	Random rnd = new Random();

	int randomCardIndex;

	int[] cards = new int[] { 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6,
			7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10 };

	public void gameRun(){


		gameScreen.addColumn("C1");
		gameScreen.addColumn("C2");
		gameScreen.addColumn("C3");		// Creating Columns
		gameScreen.addColumn("C4");
		gameScreen.addColumn("C5");




		
		while (cards.length > 0){
		  

			randomCardIndex = rnd.nextInt(cards.length);

			if (gameScreen.columnSize(0) < 6) {

				gameScreen.addCard("C1", cards[randomCardIndex]);
				}

			else if (gameScreen.columnSize(1) < 6) {

				gameScreen.addCard("C2", cards[randomCardIndex]);
			}																// Adding cards to columns randomly until each one has 6 cards

			else if (gameScreen.columnSize(2) < 6) {

				gameScreen.addCard("C3", cards[randomCardIndex]);
			}

			else if (gameScreen.columnSize(3) < 6) {

				gameScreen.addCard("C4", cards[randomCardIndex]);
			}

			else if (gameScreen.columnSize(4) < 6) {

				gameScreen.addCard("C5", cards[randomCardIndex]);
			}

			else { box.add(cards[randomCardIndex]); }		// Adding remaining cards to box



			cards = removeItemFromArray(cards, randomCardIndex);

		}

		printGameScreen(cn);

		
		Play();
		Scoreboard();

	}

	private void Play(){
        KeyListener klistener = new KeyListener() { // Keyboard event listener
            public void keyTyped(KeyEvent e) {}
            public void keyPressed(KeyEvent e) {
                if (keypr == 0) {
                    keypr = 1;
                    rkey = e.getKeyCode();
                }
            }
            public void keyReleased(KeyEvent e) {
            }
        };
        cn.getTextWindow().addKeyListener(klistener);
		updateCursor(cursor.getX(), cursor.getY(), PURPLE);

		while(isGameContinue){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			if(keypr == 1){
				if(rkey == KeyEvent.VK_RIGHT){
					if(isMoveLegal(1,0)){
						cursor.Move(1);
						updateCursor(cursor.getX(), cursor.getY(), PURPLE); //Move cursor to the next number
					}
				}
				else if(rkey == KeyEvent.VK_LEFT){
					if(isMoveLegal(-1,0)){
						cursor.Move(2);
						updateCursor(cursor.getX(), cursor.getY(), PURPLE);
					}
				}
				else if(rkey == KeyEvent.VK_UP){
					if(isMoveLegal(0,-1)){
						cursor.Move(3);
						updateCursor(cursor.getX(), cursor.getY(), PURPLE);
					}
				}
				else if(rkey == KeyEvent.VK_DOWN){
					ColumnNode temp = gameScreen.head;

					while (temp != null){ //Check the number under the cursor
						String column = "C" + cursor.getX();
						if (column.equals(temp.getColumnName())){
							CardNode temp2 = temp.getRight();
							if(temp2 != null){
								int element = 1;

								while(temp2.getNext() != null){
									if(element == cursor.getY()){
										if(isMoveLegal(0,+1)){
											cursor.Move(4);
											updateCursor(cursor.getX(), cursor.getY(), PURPLE);
										}
										break;
									}
									temp2 = temp2.getNext();
									element++;
								}
							}
							break;
						}
						temp = temp.getDown();
					}
				}

				else if(rkey == KeyEvent.VK_Z){

					isBoxNumberSelected = false;

					if(selected_column != 0){ //Reset the previous selection
						for (int i = 1; i < 10; i++) {
							if(!updateCursor(selected_column, i, Color.WHITE))
								break;
						}
					}

					selected_column = cursor.getX();//Color the new selection
					selected_index = cursor.getY();
					for (int i = cursor.getY(); i < 10; i++) { 
						if(!updateCursor(cursor.getX(), i, Color.red))
							break;
					}
					
					if(cursor.getY() == 1) //Cursors new random location after selection
						cursor.setX(cursor.getX() - 1);
					else{
						cursor.setY(cursor.getY() - 1);
					}
					if(cursor.getX() == 0)
						cursor.setX(cursor.getX() + 2);
					
					updateCursor(cursor.getX(), cursor.getY(), PURPLE);
				}

				else if(rkey == KeyEvent.VK_X){

					if (!isBoxNumberSelected) {

						target_column = cursor.getX();
						if(selected_column != target_column && selected_column != 0){
							Transfer();
							selected_column = 0;
							target_column = 0;

							checkColumnForSets(gameScreen);

							printGameScreen(cn);
						}
					}

					else {	// Adding box card to game screen with respect to cursor position

						if (cursor.getX() == 1) {

							if (gameScreen.lastNodeNumber(0) == 0) {
								if (boxNumber == 1 || boxNumber == 10) {

									gameScreen.addCard("C1", boxNumber);
									transferCount += 1;
									isBoxNumberPlaced = true;
									boxNumber = 0;

									printGameScreen(cn);

								}
							}

							else if (gameScreen.lastNodeNumber(0) + 1 == boxNumber || gameScreen.lastNodeNumber(0) - 1 == boxNumber
									|| gameScreen.lastNodeNumber(0) == boxNumber) {

								gameScreen.addCard("C1", boxNumber);
								transferCount += 1;
								isBoxNumberPlaced = true;
								boxNumber = 0;

								printGameScreen(cn);
							}


						}

						else if (cursor.getX() == 2) {

							if (gameScreen.lastNodeNumber(0) == 0) {
								if (boxNumber == 1 || boxNumber == 10) {

									gameScreen.addCard("C2", boxNumber);
									transferCount += 1;
									isBoxNumberPlaced = true;
									boxNumber = 0;

									printGameScreen(cn);

								}
							}

							else if (gameScreen.lastNodeNumber(1) + 1 == boxNumber || gameScreen.lastNodeNumber(1) - 1 == boxNumber
									|| gameScreen.lastNodeNumber(1) == boxNumber) {

								gameScreen.addCard("C2", boxNumber);
								transferCount += 1;
								isBoxNumberPlaced = true;
								boxNumber = 0;

								printGameScreen(cn);
							}


						}

						else if (cursor.getX() == 3) {

							if (gameScreen.lastNodeNumber(0) == 0) {
								if (boxNumber == 1 || boxNumber == 10) {

									gameScreen.addCard("C3", boxNumber);
									transferCount += 1;
									isBoxNumberPlaced = true;
									boxNumber = 0;

									printGameScreen(cn);

								}
							}

							else if (gameScreen.lastNodeNumber(2) + 1 == boxNumber || gameScreen.lastNodeNumber(2) - 1 == boxNumber
									|| gameScreen.lastNodeNumber(2) == boxNumber) {

								gameScreen.addCard("C3", boxNumber);
								transferCount += 1;
								isBoxNumberPlaced = true;
								boxNumber = 0;

								printGameScreen(cn);
							}


						}

						else if (cursor.getX() == 4) {

							if (gameScreen.lastNodeNumber(0) == 0) {
								if (boxNumber == 1 || boxNumber == 10) {

									gameScreen.addCard("C4", boxNumber);
									transferCount += 1;
									isBoxNumberPlaced = true;
									boxNumber = 0;

									printGameScreen(cn);

								}
							}

							else if (gameScreen.lastNodeNumber(3) + 1 == boxNumber || gameScreen.lastNodeNumber(3) - 1 == boxNumber
									|| gameScreen.lastNodeNumber(3) == boxNumber) {

								gameScreen.addCard("C4", boxNumber);
								transferCount += 1;
								isBoxNumberPlaced = true;
								boxNumber = 0;

								printGameScreen(cn);
							}


						}

						else if (cursor.getX() == 5) {

							if (gameScreen.lastNodeNumber(0) == 0) {
								if (boxNumber == 1 || boxNumber == 10) {

									gameScreen.addCard("C5", boxNumber);
									transferCount += 1;
									isBoxNumberPlaced = true;
									boxNumber = 0;

									printGameScreen(cn);

								}
							}

							else if (gameScreen.lastNodeNumber(4) + 1 == boxNumber || gameScreen.lastNodeNumber(4) - 1 == boxNumber
									|| gameScreen.lastNodeNumber(4) == boxNumber) {

								gameScreen.addCard("C5", boxNumber);

								transferCount += 1;
								isBoxNumberPlaced = true;
								boxNumber = 0;

								printGameScreen(cn);
							}


						}

					}

				}
				// revealing the new card
				else if(rkey == KeyEvent.VK_B){

					isBoxNumberSelected = true;

					if (isBoxNumberPlaced) {

						boxNumber = (int)box.getFirstNode().getData();

						printGameScreen(cn);

						isBoxNumberPlaced = false;
					}







				}

				else if(rkey == KeyEvent.VK_E) {

					isGameContinue = false;
				}

				updateInfo();

			}
			keypr = 0;

			if (setCount == 5) {

				isGameContinue = false;
			}
		}

		endGameScore = 100*setCount + (playerScore/transferCount);
	}
	private boolean isMoveLegal(int modifX, int modifY){
		updateCursor(cursor.getX(), cursor.getY(), currentTileColor()); //Reset the current cursor location

		if(Search("C" + (cursor.getX() + modifX), cursor.getY()) == 404){ //Skip the empty column
			if(Math.abs(modifX) == 1){
				if(Search("C" + (cursor.getX() + (2 * modifX)), cursor.getY()) != 404){
					cursor.setX(cursor.getX() + modifX);
					updateCursor(cursor.getX(), cursor.getY(), Color.PINK);
				}
			}
			updateCursor(cursor.getX(), cursor.getY(), PURPLE);
			return false;
		}
		return true;
	}

	private void updateInfo(){
		cn.getTextWindow().setCursorPosition(40, 3);
		System.out.println(transferCount);
	}

	private void checkColumnForSets(MultiLinkedList gameScreen) {

		for (int i = 0; i < 5; i++) {

			if (gameScreen.columnSize(i) == 10) {

				if (gameScreen.isColumnOrderedSet(i)) {						// Deleting columns if there is set

					for (int j = 0; j < 10; j++) {

						gameScreen.deleteNode(i+1, 1);
					}

					playerScore += 1000;
				}
			}
		}

	}

	private Color currentTileColor(){
		if(cursor.getX() == selected_column && cursor.getY() >= selected_index)
			return Color.red;
		return Color.white;
	}

	private int Search(String column, int index){

		if(column.equals("C-1") || column.equals("C7")) //Return error code if there is no number
			return 404;
		
		ColumnNode temp = gameScreen.head;
		CardNode temp2 = temp.getRight();
		while (temp != null){
			if (column.equals(temp.getColumnName())){
				for (int i = 0; i < index - 1; i++) {
					if(temp2 == null)
						return 404;
					temp2 = temp2.getNext();
				}
				break;
			}
			temp = temp.getDown();
			if(temp!= null)
				temp2 = temp.getRight();
		}
		if(temp2 == null)
			return 404; //null error code
		return (Integer) temp2.getCardName();
	}

	private boolean updateCursor(int column, int index, Color color){
		String number = String.valueOf(Search("C" + column, index));	// Updating cursor

		if(color == Color.PINK){ //Empty column selection
			number = "|";
		}
		if(number.equals("404")) //Escape error
			return false;	
		int x = 7 + ((column - 1) * 3);
		int y = 5 + (index - 1 );

		TextAttributes ta = new TextAttributes(color);
		cn.setTextAttributes(ta);
		cn.getTextWindow().setCursorPosition(x, y);
		System.out.print(number);
		ta = new TextAttributes(Color.white, Color.BLACK);
		cn.setTextAttributes(ta);
		return true;
	}

	private void Transfer(){
		ColumnNode temp = gameScreen.head;
		int last_node = 0;
		int queue_size = 0;
		Queue queue = new Queue(10);

		while (temp != null){
			String column = "C" + selected_column;
			if (column.equals(temp.getColumnName())){
				CardNode temp2 = temp.getRight();
				for (int i = 0; i < selected_index - 1; i++) {
					temp2 = temp2.getNext();
				}
				last_node = temp2.getCardName();
				while(temp2 != null){
					queue.enqueue(temp2.getCardName());//Load the queue
					queue_size++;
					temp2 = temp2.getNext();
				}
				break;
			}
			temp = temp.getDown();
		}
		temp = gameScreen.head;

		while (temp != null){
			String column = "C" + target_column;
			int previous = 0;
			if (column.equals(temp.getColumnName())){
				CardNode temp2 = temp.getRight();
				while(temp2 != null){
					previous = temp2.getCardName();
					temp2 = temp2.getNext();
				}
				if(Math.abs(last_node - previous) == 1){ // Last if check
					while(!queue.isEmpty()){
						gameScreen.addCard("C" + target_column, (Integer)queue.dequeue());
					}
					for (int i = 0; i < queue_size; i++) {
						gameScreen.deleteNode(selected_column, selected_index);
					}
					transferCount++;
					break;
				}
				
			}
			temp = temp.getDown();
		}
		printGameScreen(cn);
	}

	// Function for removing item from array
	private int[] removeItemFromArray(int[] array, int indexOfTheElementToBeDeleted) {

		int[] tempArray = new int[array.length - 1];

		for (int k = 0; k < array.length; k++) {

			if (k < indexOfTheElementToBeDeleted) {
				tempArray[k] = array[k];
			}

			else if (k > indexOfTheElementToBeDeleted) {
				tempArray[k - 1] = array[k];
			}
		}

		array = tempArray;

		return array;
	}

	// Function for printing game screen
	private void printGameScreen(Console console) {

		deleteScreen();
		
		gameScreen.printMultiLinkedList(console);

		console.getTextWindow().setCursorPosition(30, 3);
		console.getTextWindow().output("Transfer: " + transferCount);
		console.getTextWindow().setCursorPosition(30, 4);
		console.getTextWindow().output("Score:    " + playerScore);

		console.getTextWindow().setCursorPosition(30, 8);
		console.getTextWindow().output("Box");
		console.getTextWindow().setCursorPosition(30, 9);
		console.getTextWindow().output("+--+");
		console.getTextWindow().setCursorPosition(30, 10);

		if (boxNumber != 0) {
			console.getTextWindow().output("| " + boxNumber + "|");
		}

		else {
			console.getTextWindow().output("|  |");
		}

		console.getTextWindow().setCursorPosition(30, 11);
		console.getTextWindow().output("+--+");

		//box.printSingleLinkedList("Box", 50, 3);

	}


	private void deleteScreen() {


		for (int i = 0; i < 90; i++) {
			cn.getTextWindow().setCursorPosition(0,i);
			cn.getTextWindow().output("\t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t");
		}

		cn.getTextWindow().setCursorPosition(0,0);
	}

	private void Scoreboard(){
		DoubleLinkedList scores = new DoubleLinkedList();
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader("highscore.txt"));
			String[] name = (reader.readLine()).split(" ");
			String[] names = new String[3];
			names[0] = name[0];
			names[1] = name[1];
			for (int i = 2; i < name.length; i++) {
				if(!name[i].equals(""))
					names[2] = name[i];
			}

			while (name != null) { // Add the current line into list until there is no new line
				scores.add(new ScoreboardData(names[0] + names[1], Double.parseDouble(names[2])));
				String str = reader.readLine();
				if (str == null)
					break;
				name = str.split(" ");
				names[0] = name[0];
				names[1] = name[1];
				for (int i = 2; i < name.length; i++) {
					if(!name[i].equals(""))
						names[2] = name[i];
				}
			}
			reader.close();

			DoubleLinkedListNode previous = scores.getHead();
			DoubleLinkedListNode temp = previous.getNext();
			boolean flag = false;

			while (!flag) {
				previous = scores.getHead();
				temp = previous.getNext();
				flag = true;
				while (temp != null) {
					double temp_score = ((ScoreboardData) temp.getData()).getScore();
					double previous_score = ((ScoreboardData) previous.getData()).getScore();

					if (previous_score < temp_score) { // Move the previous number to the last if it's lesser than its link
						scores.deleteBoardData((previous.getData()));
						scores.add(previous.getData());
						flag = false; // Save the loop if there is still some changes
						break;
					}
					previous = temp;
					temp = temp.getNext();
				}
			}
			File writer = new File("highscore.txt");
			writer.delete();
			writer.createNewFile();
			FileWriter fw = new FileWriter("highscore.txt", true);

			temp = scores.getHead();
			while(temp!= null){
				double user_score = ((ScoreboardData) temp.getData()).getScore();
				String user_name = ((ScoreboardData) temp.getData()).getName();

				fw.write(user_name + ":  " + user_score + "\n");
				temp = temp.getNext();
			}
			fw.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	
}
