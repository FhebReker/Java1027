
public class DoubleNode<T>{

    private DoubleNode<T> next;
    private DoubleNode<T> previous;
    private T element;
    
    public DoubleNode() {
        next = null;
        previous = null;
        element = null;
    }

    public DoubleNode (T elem) {
        next = null;
        previous = null;
        element = elem;
    }

    public DoubleNode<T> getNext() {
        return next;
    }

    public DoubleNode<T> getPrevious() {
        return previous;
    }

    public void setNext(DoubleNode<T> node) {
        next = node;
    }

    public void setPrevious(DoubleNode<T> node) {
        previous = node;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T elem) {
        element = elem;
    }

    public String toString() {
    	return "'" + element + "'";
    }
    
}
