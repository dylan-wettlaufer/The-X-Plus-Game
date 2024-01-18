/* This class represents a hash dictionary. each hash dictionary contains a size, a first pointer and a hashFirst pointer. Implements the dictionary ADT */
public class HashDictionary implements DictionaryADT {

    private int size; /* the size of the dictionary */
    private Node<Data> first; /* the first node in the dictionary. These nodes point to other nodes that Data can be stored in */
    private Node<Data> hashFirst; /* The first node in each of the nodes separate lists that store the data. These nodes exist to handle collisions in case multiple data objects have the same location in the hash table */

    /* Constructor: creates a dictionary with the given size by creating empty nodes and connecting them together. Each node's individual list the holds the data is also initialized  */
    public HashDictionary(int size) {
        this.size = size;
        first = new Node<Data>();
        Node<Data> hashCurrent = hashFirst;
        Node<Data> current = first;
        for (int i = 0; i < size; i++) { // loops to create enough nodes so the size of the liked lists is equal to size
            Node<Data> nextNode =  new Node<Data>();
            hashCurrent = new Node<Data>(); // hashCurrent is initialized every loop and does not point to anything as it only holds the data put into the hash table
            current.setNext(nextNode);
            current = current.getNext();
            hashCurrent = current.getNext();
            }
        }

        /* Finds the node at the given index */
    private Node<Data> findNode(int index) {
        Node<Data> current = first;
        int pos = 0;
        while(current != null && pos != index) { // loops until pos equals the index given
            current = current.getNext();
            pos+=1;
        }

        return current;
    }

    /* Hash function to place the data in the hash dictionary */
    private int hashFunction(String key, int size) {
        int value = 0;
        for (int i = 0; i < key.length(); i++) {
            value = (value * 23 + (int)key.charAt(i)) % size;
        }
        return value;
    }
    /* Puts the given data object into the hash dictionary. Throws a DictionaryException if the Data object is already in the hash dictionary */
    @Override
    public int put(Data record) throws DictionaryException {
        Node<Data> current = first;
        while(current != null) { // loops through the list to see if the given Data is in the dictionary already
            if (current.getHashNext() != null) {
                Node<Data> hashCurrent = current.getHashNext();
                if (hashCurrent.getKey().getConfiguration().equals(record.getConfiguration())) throw new DictionaryException();
                while (hashCurrent.getHashNext() != null) {
                    if (hashCurrent.getKey().getConfiguration().equals(record.getConfiguration())) throw new DictionaryException();
                    hashCurrent = hashCurrent.getHashNext();
                }
            }
            current = current.getNext();
        }
        int numOfCollisions = 0; // Used to check if a collision occurs
        Node<Data> hashNode = new Node<>(record);
        int hashValue = hashFunction(record.getConfiguration(), size); // uses the hashFunction method to get the records location in the hash table
        Node<Data> tablePos = findNode(hashValue); // gets the location for the node storing record
        if (tablePos.getHashNext() == null) { // if there is not already a record in this node of the hash table, no collision occurs and hash next is set to the node with the new record in it.
            tablePos.setHashNext(hashNode);
            Node<Data> next = tablePos.getHashNext();
            next.setHashPrev(tablePos);
        }
        else { // if the node in the hashtable already has Data stored in it, a collision is recorded and the new record is added to the list with the other Data
            current = tablePos.getHashNext();
            while (current.getHashNext() != null) {
                current = current.getHashNext();
            }
            current.setHashNext(hashNode);
            current.getHashNext().setHashPrev(current);
            numOfCollisions = 1;
        }
        return numOfCollisions;
    }

    @Override
    /* Removes the record with the given configuration */
    public void remove(String config) throws DictionaryException {
        boolean noConfig = true; // must become false or an exception is thrown
        Node<Data> current = first;
        while (current != null) { // loops through the dictionary
            if (current.getHashNext() != null) { // if this equals null we go to the next node, if not, we explore the linked list attached to the node
                Node<Data> hashCurrent = current.getHashNext();
                while (hashCurrent != null) {
                    if (hashCurrent.getKey().getConfiguration().equals(config)) { // checks if the record has the same config as the one we need to remove
                        Node<Data> prev = hashCurrent.getHashPrev(); // sets the previous node
                        prev.setHashNext(hashCurrent.getHashNext()); // the previous node next pointer is set to the node the current node is pointing to, this removes it from the list
                        noConfig = false; // becomes false as record was removed so no exception needs to be thrown
                    }
                    hashCurrent = hashCurrent.getHashNext();
                }
            }
            current = current.getNext();
        }
        if (noConfig) throw new DictionaryException();
    }

    @Override
    /* Get the record with the given configuration */
    public int get(String config) {
        Node<Data> current = first;
        while (current != null) { // loops through the dictionary
            if (current.getHashNext() != null) { // if this equals null we go to the next node, if not, we explore the linked list attached to the node
                Node<Data> hashCurrent = current.getHashNext();
                while (hashCurrent != null) { // loops through the linked list storing the Data objects
                    if (hashCurrent.getKey().getConfiguration().equals(config)) { //checks if the nodes string is equal to config
                        return hashCurrent.getKey().getScore(); // returns the score if the if statement is true
                    }
                    hashCurrent = hashCurrent.getHashNext();
                }
            }
            current = current.getNext();
        }
        return -1; // returns -1 if the config isn't found
    }
    @Override
    /* counts the number of records in the hash dictionary */
    public int numRecords() {
        int numOfData = 0;
        Node<Data> current = first;
        while (current != null) { // loops through the dictionary
            if (current.getHashNext() != null) {  // if this equals null we go to the next node, if not, we explore the linked list attached to the node
                Node<Data> hashCurrent = current.getHashNext();
                while (hashCurrent != null) {
                    hashCurrent = hashCurrent.getHashNext();
                    numOfData++; // counts each node in the linked list containing the data objects
                }
            }
            current = current.getNext();
        }
        return numOfData;
    }
}
