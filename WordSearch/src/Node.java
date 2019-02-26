
/*
Name: Wateen Taleb
Student Number: 250 841 825
Date: The 25th of January, 2019
 */

public class Node {

private Word word;
private Node next;


/*
Constructor for Node, takes a parameter word and
assigns that word to a word element and the next element as a Node

This implementation is easier to deal with then with linklist, mimicking the same behaviour
 */
public Node (Word word) {
this.word = word;
this.next = null;
}


// function the returns the next node
public Node getNext() {
	return this.next;
}

// function that initializes the next node
public void setNext(Node node) {
//	node.next = new Node(node.word);
	//node.next = node;
	next = node;
}

// function that returns the word inside of node.
public Word getNode(){
	return word;
}




}