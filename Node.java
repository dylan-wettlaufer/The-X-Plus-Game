/* This class represents a node. each node has a pointer called next, hashNext, and hashPrev. Each node stores a key */

public class Node<T> {

    private Node<T> next;
    private Node<T> hashNext;
    private Node<T> hashPrev;
    private T key;

    /* Constructor: creates node with each variable equal to null  */
    public Node() {
        next = null;
        hashNext = null;
        hashPrev = null;
        key = null;
    }
    /* Constructor: creates node with each variable equal to null except for key */
    public Node(T key) {
        next = null;
        hashNext = null;
        hashPrev = null;
        this.key = key;
    }

    /* Gets the next node in the linked list */
    public Node<T> getNext() {
        return next;
    }

    /* Sets the next node in the linked list */
    public void setNext(Node<T> next) {
        this.next = next;
    }

    /* Gets the next node in the linked list that stores the data */
    public Node<T> getHashNext() {
        return hashNext;
    }

    // Sets the next node in the linked list that stores the data
    public void setHashNext(Node<T> hashNext) {
        this.hashNext = hashNext;
    }

    /* Gets the previous node in the linked list that stores the data */
    public Node<T> getHashPrev() {
        return hashPrev;
    }
    // Sets the previous node in the linked list that stores the data
    public void setHashPrev(Node<T> hashPrev) {
        this.hashPrev = hashPrev;
    }
    /* Gets the key */
    public T getKey() {
        return key;
    }
}
