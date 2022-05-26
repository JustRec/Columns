public class DoubleLinkedList {
	DoubleLinkedListNode head;

	public DoubleLinkedListNode getHead() {
		return this.head;
	}

	public void setHead(DoubleLinkedListNode head) {
		this.head = head;
	}

	DoubleLinkedListNode tail;
	DoubleLinkedListNode head2;
	DoubleLinkedListNode tail2;
	
	public DoubleLinkedList() {
		head = null;
		tail = null;
		head2 = null;
		tail2 = null;
	}
	
	public void add(Object data) {
		DoubleLinkedListNode newNode = new DoubleLinkedListNode(data);
		if(head == null) {
			head = newNode;
			tail = newNode;
		}
		else {
			newNode.setPrev(tail);
			tail.setNext(newNode);
			tail = newNode;
		}
	}
	
	public void sortedAdd(Object dataToAdd,Object dataToAdd2) {

        if(head==null) 
        {
        	DoubleLinkedListNode newNode=new DoubleLinkedListNode(dataToAdd);
            head=newNode;
            tail=newNode;
            DoubleLinkedListNode newNode2=new DoubleLinkedListNode(dataToAdd2);
            head2=newNode2;
            tail2=newNode2;
        }
        
        else 
        {
        	DoubleLinkedListNode newNode=new DoubleLinkedListNode(dataToAdd);
        	DoubleLinkedListNode newNode2=new DoubleLinkedListNode(dataToAdd2);
        	if((int)dataToAdd < (int)head.getData())  
        	{       	
            newNode.setNext(head);
            head.setPrev(newNode);
            head=newNode;
            newNode2.setNext(head2);
            head2.setPrev(newNode2);
            head2=newNode2;
        	}
        	else 
        	{
        		DoubleLinkedListNode temp = head;
        		DoubleLinkedListNode temp2 = head2;
        		while(temp.getNext() != null && (int)dataToAdd >= (int)temp.getNext().getData()) {
        			temp=temp.getNext();
        			temp2=temp2.getNext();
        		}
        		newNode.setPrev(temp);
        		newNode.setNext(temp.getNext());
        		newNode2.setPrev(temp2);
        		newNode2.setNext(temp2.getNext());
        		if(temp.getNext() != null) {
        			temp.getNext().setPrev(newNode);
        			temp2.getNext().setPrev(newNode2);
        		}
        		else {
        			tail=newNode;
        			tail2=newNode2;
        		}
        		temp.setNext(newNode);
        		temp2.setNext(newNode2);
        	}   	      	
        } 
	}
	
	public int size() {
		int count = 0;
		if(head == null)
			return count;
		else {
			DoubleLinkedListNode temp = head;
			while(temp != null) {
				count++;
				temp = temp.getNext();
			}
		}
		return count;
	}
	
	public boolean search(int number) {
		if(head == null)
		{
			System.out.println("List is empty");
		}
		else {
			DoubleLinkedListNode temp = tail;
			while(temp != null) {
				if((int)temp.getData() == number) {
					return true;
				}
				temp = temp.getPrev();
			}
		}
		return false;
	}
	
	public void display1() {
		if(head == null)
			System.out.println("List is empty");
		else {
			DoubleLinkedListNode temp = head;
			while(temp != null) {
				System.out.print(temp.getData() + " ");
				temp = temp.getNext();
			}
			System.out.println();
		}
	}
	
	public void display2() {
		if(head == null)
			System.out.println("List is empty");
		else {
			DoubleLinkedListNode temp = tail;
			while(temp != null) {
				System.out.print(temp.getData() + " ");
				temp = temp.getPrev();
			}
			System.out.println();
		}
	}
	
	public void display_names() {
		if(head2 == null)
			System.out.println("List is empty");
		else {
			DoubleLinkedListNode temp2 = tail2;
			while(temp2 != null) {
				System.out.print(temp2.getData() + " ");
				temp2 = temp2.getPrev();
			}
			System.out.println();
		}
	}
	
	public Object getElement(int x) {
		if(head == null)
		{
			System.out.println("List is empty");
			return null;
		}
		else if(x > size() || x < 0 ){
			System.out.println("Index is out of range");
			return null;
		}
		else {
			DoubleLinkedListNode temp = head;
			int count = 1;
			while(temp != null) {
				if(x == count)
					return temp.getData();
				temp = temp.getNext();
				count++;
			}
			return null;
		}
	}
	
	public void swap(int x) {
		if(head == null)
		{
			System.out.println("List is empty");
		}
		else if(x > size() || x < 0 ){
			System.out.println("Index is out of range");
		}
		else {
			int element1 = -1;
			DoubleLinkedListNode temp1 = head;
			int count = 1;
			while(temp1 != null) {
				if(count == x) {
					element1 = (int)temp1.getData();
					break;
				}
				temp1 = temp1.getNext();
				count++;
			}
			int element2 = -1;
			DoubleLinkedListNode temp2 = tail;
			count = 1;
			while(temp2 != null) {
				if(count == x) {
					element2 = (int)temp2.getData();
					temp2.setData(element1);
					break;
				}
				temp2 = temp2.getPrev();
				count++;
			}			
			temp1.setData(element2);			
		}
	}

	public int deleteBoardData(Object data) { // Delete one scoreboard element from list
        if (head == null) {
            System.out.println("List is empty");
        } 
        else {
            while ((ScoreboardData) head.getData() == (ScoreboardData) data) {
                head = head.getNext();
                return 0;
            }

            DoubleLinkedListNode previous = null;
            DoubleLinkedListNode temp = head;
            while (temp != null) {
                if ((ScoreboardData) temp.getData() == (ScoreboardData) data) {
                    previous.setNext(temp.getNext());
                    return 0;
                }
                previous = temp;
                temp = temp.getNext();
            }
        }
        return 0;
    }
}
