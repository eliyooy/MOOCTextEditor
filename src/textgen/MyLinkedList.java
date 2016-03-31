package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		size = 0;
		head = new LLNode<>(null);
		tail = new LLNode<>(null);
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add( E element )
	{
		// TODO: Implement this method
		LLNode<E> newElement = new LLNode<>(element);

		LLNode<E> prevElement = tail.prev;
		prevElement.next = newElement;
		tail.prev = newElement;
		newElement.prev = prevElement;
		newElement.next = tail;
		size++;

		return false;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		LLNode<E> currentElement = head.next;

		if(size == 0 || index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Not a valid index for this LinkedList.");
		}

		if(index == 0) {
			return head.next.data;
		}

		for(int i=1; i<(index+1); i++) {
			currentElement = currentElement.next;

			if(i == index) {
				return currentElement.data;
			}
		}
		return null;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		LLNode<E> currentElement = head;
		LLNode<E> newElement = new LLNode<>(element);

		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Not a valid index for this LinkedList.");
		}

		if(index == 0) {
			head.next = newElement;
			newElement.prev = head;
			newElement.next = head.next.next;
			//newElement.next.prev = newElement;
			size++;
			return;
		}

		for(int i=0; i<(index+1); i++) {
			currentElement = currentElement.next;
			LLNode<E> placeholderElement = head;

			if(i == index) {
				placeholderElement.data = currentElement.data;
				currentElement.data = newElement.data;
				currentElement.prev = placeholderElement.prev;
				currentElement.next = placeholderElement;
				placeholderElement.prev = currentElement;
				size++;
			}
		}
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method

		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		LLNode<E> currentElement = head;
		E EValue = head.data;

		if(size == 0 || index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Not a valid index for this LinkedList.");
		}

		if(index == 0) {
			EValue = head.next.data;
			LLNode<E> targetElement = head.next;
			head.next = targetElement.next;
			targetElement.next.prev = head;
			size--;
			return EValue;
		}

		for(int i=0; i<(index+1); i++) {
			currentElement = currentElement.next;
			LLNode<E> previousElement = currentElement.prev;

			if(i == index && index != (size + 1)) {
				EValue = currentElement.data;
				//previousElement.next = currentElement.next;
				currentElement.next.prev = previousElement;
				size--;
				return EValue;
			}
		}
		return EValue;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		LLNode<E> currentElement = head;
		LLNode<E> newElement = new LLNode<E>(element);

		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Not a valid index for this LinkedList.");
		}

		if(index == 0) {
			head.next = newElement;
			return newElement.data;
		}

		for(int i=1; i<(index+1); i++) {
			currentElement = currentElement.next;
			LLNode<E> previousElement = currentElement.prev;

			if(i == index) {
				currentElement = newElement;
			}
		}
		return newElement.data;
	}
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}


