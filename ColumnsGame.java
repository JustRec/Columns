import enigma.console.Console;
import enigma.core.Enigma;
import enigma.console.TextAttributes;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class ColumnsGame {

    enigma.console.Console cn = Enigma.getConsole("Star Trek Warp Wars");

    SingleLinkedList box = new SingleLinkedList();

    SingleLinkedList column1 = new SingleLinkedList();
    SingleLinkedList column2 = new SingleLinkedList();
    SingleLinkedList column3 = new SingleLinkedList();
    SingleLinkedList column4 = new SingleLinkedList();
    SingleLinkedList column5 = new SingleLinkedList();

    int transferCount = 0;
    int playerScore = 0;
    int boxNumber = 0;


    Random rnd = new Random();

    int randomCardIndex;

    int[] cards = new int[] {1,1,1,1,1,2,2,2,2,2,3,3,3,3,3,4,4,4,4,4,5,5,5,5,5,6,6,6,6,6,7,7,7,7,7,8,8,8,8,8,9,9,9,9,9,10,10,10,10,10};

    public void gameRun() {

        while (cards.length > 0){


            randomCardIndex = rnd.nextInt(cards.length);

            if (column1.size() < 6) {

                column1.add(cards[randomCardIndex]);
            }

            else if (column2.size() < 6) {

                column2.add(cards[randomCardIndex]);
            }

            else if (column3.size() < 6) {

                column3.add(cards[randomCardIndex]);
            }

            else if (column4.size() < 6) {

                column4.add(cards[randomCardIndex]);
            }

            else if (column5.size() < 6) {

                column5.add(cards[randomCardIndex]);
            }

            else {
                box.add(cards[randomCardIndex]);
            }



            cards = removeItemFromArray(cards, randomCardIndex);

        }

        /* box.printSingleLinkedList();
        System.out.println();
        column1.printSingleLinkedList();
        System.out.println();
        column2.printSingleLinkedList();
        System.out.println();
        column3.printSingleLinkedList();
        System.out.println();
        column4.printSingleLinkedList();
        System.out.println();
        column5.printSingleLinkedList(); */

        printGameScreen(cn);

    }

    private int[] removeItemFromArray (int[] array, int indexOfTheElementToBeDeleted) {

        int[] tempArray = new int[array.length-1];

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

    private void printGameScreen(Console console) {

        column1.printSingleLinkedList("C1", 7,3);
        column2.printSingleLinkedList("C2", 11,3);
        column3.printSingleLinkedList("C3", 15,3);
        column4.printSingleLinkedList("C4", 19,3);
        column5.printSingleLinkedList("C5", 23,3);

        console.getTextWindow().setCursorPosition(30,3);
        console.getTextWindow().output("Transfer: " + transferCount);
        console.getTextWindow().setCursorPosition(30,4);
        console.getTextWindow().output("Score:    " + playerScore);


        console.getTextWindow().setCursorPosition(30,8);
        console.getTextWindow().output("Box");
        console.getTextWindow().setCursorPosition(30,9);
        console.getTextWindow().output("+--+");
        console.getTextWindow().setCursorPosition(30,10);

        if (boxNumber != 0) {
            console.getTextWindow().output("| " + boxNumber + "|");
        }

        else {
            console.getTextWindow().output("|  |");
        }

        console.getTextWindow().setCursorPosition(30,11);
        console.getTextWindow().output("+--+");


    }
}
