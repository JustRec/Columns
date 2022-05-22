public class CardNode {

    private int cardName;
    private CardNode next;

    public CardNode(int dataToAdd) {
        cardName = dataToAdd;
        next = null;
    }

    public int getCardName() {
        return cardName;
    }

    public void setCardName(int cardName) {
        this.cardName = cardName;
    }

    public CardNode getNext() {
        return next;
    }

    public void setNext(CardNode next) {
        this.next = next;
    }
}
