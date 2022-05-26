import enigma.console.Console;
import enigma.console.TextAttributes;
import enigma.core.Enigma;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class ColumnsGame {

	enigma.console.Console cn = Enigma.getConsole("Columns", 70, 25, 17);

	MultiLinkedList gameScreen = new MultiLinkedList();

	SingleLinkedList box = new SingleLinkedList();

	SingleLinkedList column1 = new SingleLinkedList();
	SingleLinkedList column2 = new SingleLinkedList();
	SingleLinkedList column3 = new SingleLinkedList();		// Creating Columns
	SingleLinkedList column4 = new SingleLinkedList();
	SingleLinkedList column5 = new SingleLinkedList();

	DoubleLinkedList dll_scores = new DoubleLinkedList();
	DoubleLinkedList dll_names = new DoubleLinkedList();
	DoubleLinkedList dll_highscore = new DoubleLinkedList();

	int transferCount = 0;
	int playerScore = 0;
	int boxNumber = 0;

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
		gameScreen.addColumn("C3");
		gameScreen.addColumn("C4");
		gameScreen.addColumn("C5");




		
		while (cards.length > 0){
		  

			randomCardIndex = rnd.nextInt(cards.length);

			if (column1.size() < 6) {

				gameScreen.addCard("C1", cards[randomCardIndex]);
				column1.add(cards[randomCardIndex]); }

			else if (column2.size() < 6) {

				gameScreen.addCard("C2", cards[randomCardIndex]);
				column2.add(cards[randomCardIndex]); }		// Adding cards to columns randomly until each one has 6 cards

			else if (column3.size() < 6) {

				gameScreen.addCard("C3", cards[randomCardIndex]);
				column3.add(cards[randomCardIndex]); }

			else if (column4.size() < 6) {

				gameScreen.addCard("C4", cards[randomCardIndex]);
				column4.add(cards[randomCardIndex]); }

			else if (column5.size() < 6) {

				gameScreen.addCard("C5", cards[randomCardIndex]);
				column5.add(cards[randomCardIndex]); }

			else { box.add(cards[randomCardIndex]); }		// Adding remaining cards to box



			cards = removeItemFromArray(cards, randomCardIndex);

		}

		printGameScreen(cn);

		
		Play();

		reading_scores(dll_scores, "highscore.txt");
		reading_names(dll_names, "highscore.txt");
		DoubleLinkedListNode temp = dll_scores.head;
		DoubleLinkedListNode temp2 = dll_names.head;
		while (temp != null) {
			dll_highscore.sortedAdd(temp.getData(), temp2.getData());
			temp = temp.getNext();
			temp2 = temp2.getNext();
		}
		writing(dll_highscore,"highscore.txt");

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

		while(true){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			if(keypr == 1){
				if(rkey == KeyEvent.VK_RIGHT){
					if(isMoveLegal(1,0)){
						cursor.Move(1);
						updateCursor(cursor.getX(), cursor.getY(), PURPLE);
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

					while (temp != null){
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
					if(selected_column != 0){
						for (int i = 1; i < 10; i++) {
							if(!updateCursor(selected_column, i, Color.WHITE))
								break;
						}
					}
					selected_column = cursor.getX();
					selected_index = cursor.getY();
					for (int i = cursor.getY(); i < 10; i++) {
						if(!updateCursor(cursor.getX(), i, Color.red))
							break;
					}
					
					if(cursor.getY() == 1)
						cursor.setX(cursor.getX() - 1);
					else{
						cursor.setY(cursor.getY() - 1);
					}
					if(cursor.getX() == 0)
						cursor.setX(cursor.getX() + 2);
					
					updateCursor(cursor.getX(), cursor.getY(), PURPLE);
				}

				else if(rkey == KeyEvent.VK_X){
					target_column = cursor.getX();
					if(selected_column != target_column && selected_column != 0){
						Transfer();
						selected_column = 0;
						target_column = 0;

						checkColumnForSets(gameScreen);
					}
				}
				else if(rkey == KeyEvent.VK_B){}
				updateInfo();

			}
			keypr = 0;
			
		}
	}
	private boolean isMoveLegal(int modifX, int modifY){
		updateCursor(cursor.getX(), cursor.getY(), currentTileColor());

		if(Search("C" + (cursor.getX() + modifX), cursor.getY()) == 404){
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

				if (gameScreen.isColumnOrderedSet(i)) {

					for (int j = 0; j <= 10; j++) {

						gameScreen.deleteNode(i+1, j+1);
					}
				}
			}
		}

	}

	private Color currentTileColor(){
		if(cursor.getX() == selected_column && cursor.getY() >= selected_index)
			return Color.red;
		/*else if(cursor.getX() == target_column)
			return Color.blue;*/
		return Color.white;
	}

	private int Search(String column, int index){

		if(column.equals("C-1") || column.equals("C7"))
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
		String number = String.valueOf(Search("C" + column, index));
		if(color == Color.PINK){
			number = "|";
		}
		if(number.equals("404"))
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
					queue.enqueue(temp2.getCardName());
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
				if(Math.abs(last_node - previous) == 1){
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

	public DoubleLinkedList reading_names(DoubleLinkedList dll, String string) {
		try {
			File file = new File(string);
			Scanner scan = new Scanner(file);

			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				line = line.toUpperCase(Locale.ENGLISH);
				int space = (line.indexOf(" "));
				dll.add(line.substring(0, space));
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		return dll;
	}

	public DoubleLinkedList reading_scores(DoubleLinkedList dll, String string) {

		try {
			File file = new File(string);
			Scanner scan = new Scanner(file);

			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				line = line.toUpperCase(Locale.ENGLISH);
				int space = (line.indexOf(" "));
				dll.add(Integer.parseInt(line.substring(space + 1)));
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		return dll;
	}
	public void writing(DoubleLinkedList dll, String string) {
		File file = new File(string);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file, false);
			BufferedWriter bw = new BufferedWriter(fw);

			DoubleLinkedListNode temp = dll.tail;
			DoubleLinkedListNode temp2 = dll.tail2;
			int count = 0;
			while (count < dll.size()) {

				String table_score = String.valueOf(temp.getData());
				temp = temp.getPrev();
				String table_name = temp2.getData().toString();
				temp2 = temp2.getPrev();

				bw.write(table_name + " " + table_score + "\n");
				count++;

			}
			bw.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private void deleteScreen() {


		for (int i = 0; i < 90; i++) {
			cn.getTextWindow().setCursorPosition(0,i);
			cn.getTextWindow().output("\t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t");
		}

		cn.getTextWindow().setCursorPosition(0,0);
	}

	
}
