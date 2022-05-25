import enigma.console.Console;
import enigma.core.Enigma;
import enigma.console.TextAttributes;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class ColumnsGame {

	enigma.console.Console cn = Enigma.getConsole("Columns");

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

	Random rnd = new Random();

	int randomCardIndex;

	int[] cards = new int[] { 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6,
			7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10 };

	public void gameRun() {


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
		gameScreen.deleteNode(1,1);
		gameScreen.deleteNode(2,3);
		gameScreen.deleteNode(4,3);
		gameScreen.deleteNode(2,2);
		gameScreen.deleteNode(3,4);
		printGameScreen(cn);

		 

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
