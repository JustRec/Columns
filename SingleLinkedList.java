import enigma.core.Enigma;

import java.io.Console;

public class SingleLinkedList {
    enigma.console.Console cn = Enigma.getConsole("Star Trek Warp Wars");

    SingleLinkedListNode head;

    public void add(Object data) {

        if (head == null) {
            head = new SingleLinkedListNode(data);
        }

        else {

            SingleLinkedListNode tempNode = head;

            while (tempNode.getLink() != null) {
                tempNode = tempNode.getLink();
            }

            SingleLinkedListNode newNode = new SingleLinkedListNode(data);
            tempNode.setLink(newNode);
        }

    }

    // Function for printing single linked list with respect to column name and cursor position
    public void printSingleLinkedList(String columnNumber, int cursorXPosition, int cursorYPosition) {

        cn.getTextWindow().setCursorPosition(cursorXPosition,cursorYPosition);
        cn.getTextWindow().output(columnNumber);
        cursorYPosition+=1;
        cn.getTextWindow().setCursorPosition(cursorXPosition,cursorYPosition);
        cn.getTextWindow().output("--");
        cursorYPosition+=1;


        if (head == null) {
            System.out.println("Linked list is empty");
        }

        else {

            SingleLinkedListNode tempNode = head;

            while (tempNode != null) {

                cn.getTextWindow().setCursorPosition(cursorXPosition,cursorYPosition);
                cn.getTextWindow().output(String.valueOf((int)tempNode.getData()));
                cursorYPosition+=1;

                tempNode = tempNode.getLink();
            }
        }
    }

    public int size() {

        int count = 0;


        SingleLinkedListNode tempNode = head;

        while (tempNode != null) {
            count += 1;
            tempNode = tempNode.getLink();
        }

        return count;
    }




}
