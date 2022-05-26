public class MultiLinkedList {

    ColumnNode head;
    public void addColumn(String dataToAdd) {

        if (head == null) {
            ColumnNode newNode = new ColumnNode(dataToAdd);
            head = newNode;
        }

        else {
            ColumnNode tempNode = head;

            while (tempNode.getDown() != null) {

                tempNode = tempNode.getDown();

            }

                ColumnNode newNode = new ColumnNode(dataToAdd);
                tempNode.setDown(newNode);


        }
    }

    public void addCard(String column, int card) {

        if (head == null) {
            System.out.println("Please add a column before card");
        }

        else {

            ColumnNode tempNode = head;

            while (tempNode != null) {

                if (column.equals(tempNode.getColumnName())) {

                    CardNode tempNode2 = tempNode.getRight();

                    if (tempNode2 == null) {

                        CardNode newNode = new CardNode(card);

                        tempNode.setRight(newNode);

                    }

                    else {

                        while (tempNode2.getNext() != null) {

                            tempNode2 = tempNode2.getNext();
                        }

                        CardNode newNode = new CardNode(card);
                        tempNode2.setNext(newNode);
                    }
                }

                tempNode = tempNode.getDown();
            }
        }
    }

    public void printMultiLinkedList(enigma.console.Console console) {

        if (head == null) {

            System.out.println("MLL is empty");
        }

        else {

            ColumnNode tempNode = head;
            int cursorXPosition = 7;
            int cursorYPosition= 3;

            while (tempNode != null) {

                console.getTextWindow().setCursorPosition(cursorXPosition, cursorYPosition);
                System.out.println(tempNode.getColumnName());
                cursorYPosition += 2;
                CardNode tempNode2 = tempNode.getRight();

                while (tempNode2 != null) {

                    console.getTextWindow().setCursorPosition(cursorXPosition, cursorYPosition);
                    System.out.print(tempNode2.getCardName());
                    cursorYPosition+=1;
                    tempNode2 = tempNode2.getNext();

                }

                cursorXPosition += 3;
                cursorYPosition = 3;

                tempNode = tempNode.getDown();
                System.out.println();
            }
        }

    }

    public void deleteNode(int columnIndex, int cardIndex) {

        ColumnNode tempNode = head;


        for (int i = 0; i < columnIndex - 1; i++) {

            tempNode = tempNode.getDown();

        }

        CardNode tempNode2 = tempNode.getRight();
        CardNode previousNode = null;

        for (int i = 0; i < cardIndex - 1; i++) {

            previousNode = tempNode2;
            tempNode2 = tempNode2.getNext();
        }

        if(previousNode == null)
            tempNode.setRight(null);
        else
            previousNode.setNext(tempNode2.getNext());
    }

    public int columnSize (int columnIndex) {
        int cardCount = 0;

        ColumnNode tempColumnNode = head;

        for (int i = 0; i < columnIndex; i++) {

            tempColumnNode = tempColumnNode.getDown();
        }

        CardNode tempCardNode = tempColumnNode.getRight();

        while (tempCardNode != null) {

            cardCount += 1;
            tempCardNode = tempCardNode.getNext();
        }

        return cardCount;
    }

    public boolean isColumnOrderedSet(int columnIndex) {

        boolean isOrdered = true;

        ColumnNode tempColumnNode = head;

        for (int i = 0; i < columnIndex; i++) {

            tempColumnNode = tempColumnNode.getDown();
        }

        CardNode tempCardNode = tempColumnNode.getRight();

        for (int i = 1; i <= 10; i++) {

            if (!(Integer.toString(tempCardNode.getCardName())).equals(Integer.toString(i))) {

                isOrdered = false;

                tempCardNode = tempCardNode.getNext();
            }
        }

        tempCardNode = tempColumnNode.getRight();

        for (int i = 10; i >= 1 ; i++) {

            if (!(Integer.toString(tempCardNode.getCardName())).equals(Integer.toString(i))) {

                isOrdered = false;
            }
        }

        return isOrdered;
    }
}
