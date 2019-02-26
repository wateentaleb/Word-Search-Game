/*
Name: Wateen Taleb
Student Number: 250 841 825
Date: The 25th of January, 2019
 */

// since your hashchain dictionary class must implement all methods of the DictionaryADT interface,
// we need to add the "implements Dictionary ADT" below


public class HashChainDictionary implements DictionaryADT {


	// Setting the fixed size of 75,0000
	// size the load factor needs to be 0.75 and M = 75,000 then 23 need a number close to 56,250
	// using the website given for desirable prime numbers the closest number i could find was 56,263 which gave a load factor of 0.7501733333
	// very close to our 0.75 load factor
	private Node head, prev = new Node(null);
	private Word word;
	private Node dict[];
	private final static int SIZE = 75000;
	int M = 0;
	private int size;


	// initializing a hashtable of nodes of size "size" initialized all to null
	public HashChainDictionary(int size) {
	  dict = new Node[size];
		this.M = size;

		// making all the elements empty
	for (int i = 0; i < size; i++) {
		dict[i] = null;
	}

	}


		/*
		Method: put

		Parameters: word

		Description: The hashtable asked to implement followed a separate chaining method to avoid collisions,
					 if the element is already placed in the index computed by the hash function, then it will assign the word
					 to the next node of the last node placed (same behaviour as linked list)

		Function: Inserts the given Word object referenced by word into the dictionary.

		 */
	public int put(Word word) throws DictionaryException {
		int collision = 0;
		Word wrd;
		Word head;
		Node prev = null;
		this.word = word;

		// getting the index using our hash function
		int index = hashfunction(word.getKey());

		// assigning the head of that index to Node object "node"
		Node node = dict[index];

		// if the head is empty, then insert in that position.
		if (node == null) {
			// making a new node for word and setting it as head
			Node node1 = new Node(word);
			// now assigning that node to that location
			dict[index] = node1;
			// increment size of hashtable
			this.size++;
			// returning collisions
			return collision;
		}
		else {
			// checking if the head has the same key,
			head = node.getNode();
			// if the key already exists in the dictionary, throw Dictionary Exception
			if (head.getKey().equals(word.getKey())) {
				collision++;
				throw new DictionaryException("Dictionary Exception: Key already exists in hash table");
			}
			else {
				// while there head of that index is not empty
				while(node!=null) {
					// set prev to the current node
					// assign wrd to the word of the current node
				prev = node;
				wrd =  node.getNode();

				// if the node we are currently checking has the same key trying to be inserted, then we throw a DictionaryException
				if (wrd.getKey().equals(word.getKey())) {
					// increased collisons
					collision++;
					// throw DictionaryException
					throw new DictionaryException("Dictionary Exception: Key already exists in the hash table");
				}
				// if we reached here, then the node being analyzed currently
					// does not have the same key, checking next node
				node = node.getNext();

				}// end of while loop

				// if we reached here then there was no key that was the same, so lets insert it,
				Node newNode = new Node(word);
				// set the previous node to the next node
				prev.setNext(newNode);
				// increment the size
				this.size++;
				// no collision so return 0;
				return collision;
			}


		}


	}


	/*
    Method: get

    Parameters: inputWord

    Description: The hashtable asked to implement followed a separate chaining method to avoid collisions,
               	 Using the string given, the hashfunction was used to to find the index IF the word WOULD be inside the dictionary

    Function: returns Word object associated with given String, will return null if it is not in the dictionary

     */

	public Word get(String inputWord) {
		// finding the index if the word was in the dictionary
		int index = hashfunction(inputWord);
		Node node;
		// setting the head to Node object "node"
		node = dict[index];

		// if head empty return null;
		if (node == null)
			return null;

		// while the node is not empty
		while(node!=null){
			// if the node's key (Type String) being analyzed matches the string passed,
			// then we found the Word associated with the string and the Word object is returned
			if (node.getNode().getKey().equals(inputWord))
				return node.getNode();
			// the node being analyzed does not match the string passed down, so increment node and look at the next one
			else {
				node = node.getNext();
			}
		}
		// if reached here then the string given could not be matched by any Word object in the dictionary
		// therefore we return null
		return null;
	}


		/*
		Method: remove

		Parameters: inputWord

		Description: The hashtable asked to implement followed a separate chaining method to avoid collisions,
					 the following algorithm firsts finds if the key is in the dictionary, if it isn't it throws a NoKeyException
					 if the key is found, then we need to find out if its the head of the index in the dictionary or not since
					 the removal process will differ. In the code bellow i will illustrate the different methods used to remove the node
					 depending if its a head or not.

		Function: Removes the Word record with String parameter inputWord as it's key from the dictionary, and returns it.

		 */

	public Word remove(String inputWord) throws NoKeyException {
		// getting the index of the string given
		int index = hashfunction(inputWord);
		// assigning the head of that location of the array to Node object p;
		Node p = dict[index];
		// setting a variable prev that will be used to hold the previous value of Node to null
		Node  prev = null;

		// while the head is not empty, loop
		while (p != null)
			// if the head is equal to the Word's key that wants to be removed then
			if (p.getNode().getKey().equals(inputWord)) {
				// if the previous node is not empty
				if (prev != null) {
					// set the previous node to the head's next node.
					prev.setNext(p.getNext());
				}
				// if the previous node is empty
				else {
					// then assign the head to the head's next value
					dict[index] = p.getNext();
					// decrement the size
					this.size--;
					// then return the Word object that was removed from the dictionary
					return p.getNode();

				}

			} else
				{
					// set the previous node to the current node
			prev = p;
			// get the node's next node
			p = p.getNext();
			// this is basically traversing through with the same behaviour as a linked list.
			}
		// if we reached here, then the key is not found, Since we can't remove a key that is not in the dictionary we will throw
		// a NoKeyException
		throw new NoKeyException("NoKeyException thrown: cannot not remove word with key given because its not in the table");
	}


	// returns the size of the
	public int size() {
		return this.size;
	}

	// using horners method to hash a string into an int
	private int hashfunction(String str) {
		int h = 0, hash = 0;
		// looping from 0 to the length of the string -1
		for (int i = 0; i<str.length(); i++)
			h = (31 * hash) + str.charAt(i);
		hash = h % M;
		return hash;
	}
}
