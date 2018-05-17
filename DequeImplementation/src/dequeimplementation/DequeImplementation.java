/*
 * Jeanine Rioux
 * CSC 18C  5/14/18
 * Using the provided Deque Interface, Create a Deque class
 * and demonstrate functionality with a driver.
 */
package dequeimplementation;


public class DequeImplementation implements DequeInterface<T>{

    // returns true if the deque is empty (no items in deque) 
    // false if deque is (has at least one or more items in deque)
    public boolean isEmpty();
	
    // return the number of items currently in the deque
    public int size();
 
    // returns the value of the item currently at front of deque
    public T front();
	
    // returns the value of the item currently at the end of the deque
    public T back();
	
    // places parameter newItem onto the front of the deque
    public void enqueue_front(T newItem);

    // places parameter newItem onto the end of the deque
    public void enqueue_back(T newItem);

    // returns and removes the current item at the front of the deque
    // the item that is in the deque behind the item becomes the new front item
    public T dequeue_front();

    // returns and removes the current item at the front of the deque
    // the item that is in the deque behind the item becomes the new front item
    public T dequeue_back();

    // display all of the contents in the deque from front to back
    public void display();
    
}
