public class SingleLinkedListNode {

    private Object data;
    private SingleLinkedListNode link;

    SingleLinkedListNode(Object data) {
        this.data = data;
        link = null;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public SingleLinkedListNode getLink() {
        return link;
    }

    public void setLink(SingleLinkedListNode link) {
        this.link = link;
    }


}
