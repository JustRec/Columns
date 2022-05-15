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

	enigma.console.Console cn = Enigma.getConsole("Star Trek Warp Wars");

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

		
		  while (cards.length > 0){
		  
		  
		  randomCardIndex = rnd.nextInt(cards.length);
		  
		  if (column1.size() < 6) {
		  
		  column1.add(cards[randomCardIndex]); }
		  
		  else if (column2.size() < 6) {
		  
		  column2.add(cards[randomCardIndex]); }		// Adding cards to columns randomly until each one has 6 cards
		  
		  else if (column3.size() < 6) {
		  
		  column3.add(cards[randomCardIndex]); }
		  
		  else if (column4.size() < 6) {
		  
		  column4.add(cards[randomCardIndex]); }
		  
		  else if (column5.size() < 6) {
		  
		  column5.add(cards[randomCardIndex]); }
		  
		  else { box.add(cards[randomCardIndex]); }		// Adding remaining cards to box
		  
		  
		  
		  cards = removeItemFromArray(cards, randomCardIndex);
		  
		  }

		  
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

		column1.printSingleLinkedList("C1", 7, 3);
		column2.printSingleLinkedList("C2", 11, 3);
		column3.printSingleLinkedList("C3", 15, 3);
		column4.printSingleLinkedList("C4", 19, 3);
		column5.printSingleLinkedList("C5", 23, 3);

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

	
}
