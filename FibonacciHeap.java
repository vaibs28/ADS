import java.util.ArrayList;
import java.util.List;

/**Basic max fibonacci heap implementation
 * 
 * @author vaibhav
 *
 */
public class FibonacciHeap implements IFibonacciHeap {

  
    private Node max = null; // to store the maximum node after every operation
    private int heapSize = 0;

    /**
     * Creates the new node from the hashtagFrequency and inserts it as a single
     * tree in the heap, Adds the singleton tree to the right of the existing max. Updates the max pointer if required.
     * 
     * @param hashtagFrequency 
     * @param hashtag
     * 
     * @return node
     */
    @Override
    public Node insert(int hashtagFrequency, String hashtag) {

        // create a single tree with the node
        Node node = new Node(hashtagFrequency, hashtag);
        heapSize++;

        if (max == null) {
            // first insertion
            max = node;
        } else {
            // subsequent insertions
            addToRootList(node);
            if (node.getFrequency() > max.getFrequency())
                max = node;
        }

        return node;
    }

    /**
     * Adds the root of the singleton tree to the right of max and changes the existing links to achieve it.
     * 
     *                           
     * @param node Takes in the node of the new singleton tree
     * @return
     * 
     * 
     */
    public void addToRootList(Node node) {
        node.setLeft(max);              // set node's left to max
        Node maxNext = max.getRight();  // get the node next to max
        node.setRight(maxNext);         // set the node's right to maxNext
        max.setRight(node);             // set max's right to node
        maxNext.setLeft(node);          // set maxNext's left to node
    }

    /**
     * Increases the key of the node.
     * 
     * 
     * 
     * @param node node for which increaseKey has to be executed.
     * @param value value by which the existing value has to be increased
     */
    @Override
    public void increaseKey(Node node, int value) {
        // increment the node's existing value by adding the passed value
        node.setFrequency(node.getFrequency() + value);
        Node x = node;
        Node y = node.getParent();

        // cut x and y and cascade to the top
        if ((y != null) && (x.getFrequency() > y.getFrequency())) {
            cut(x, y);
            cascadeCut(y);
        }

        //update max if required
        if (x.getFrequency() > max.getFrequency()) {
            max = x;
        }
    }

    /**
     * Recursively cuts the heap till it doesn't find a node marked F
     * 
     * 
     * @param node the node being cut
     */
    @Override
    public void cascadeCut(Node node) {
        Node z = node.getParent();
        if (z != null) {
            if (!node.isMarked()) {
                //if false , mark as true and stop
                node.setMarked(true);
            } else {
                //cut and cascade up
                cut(node, z);
                cascadeCut(z);
            }
        }

    }

    /**
     *
     * Cuts the link between node and parent.
     * 
     *
     * 
     * @param x Node
     * @param y Parent
     */
    @Override
    public void cut(Node x, Node y) {
    // cut x from y
    Node xPrev = x.getLeft();
    xPrev.setRight(x.getRight());
    Node xNext = x.getRight();
    xNext.setLeft(x.getLeft());

    // decrement the degree of y since x is not cut from y
    y.setDegree(y.getDegree() - 1);

    if (y.getDegree() == 0) {
      y.setChild(null);
    }

    else if (y.getChild() == x) {
      y.setChild(xNext);
    }

    //add x to root list

    x.setLeft(max);
    x.setRight(max.getRight());
    max.setRight(x);
    if (x.getRight() != null)
      x.getRight().setLeft(x);

    x.setParent(null);

    x.setMarked(false);
  }

    /**
     * Returns the maximum node from the heap.
     * The operation is followed by a degreewise merge of the trees.
     * 
     * @return maximum node
     */
    @Override
    public Node removeMax() {
        Node z = max;
        if(z==null)     //empty heap
          return null;  
        
        else {
          
            int numChildren = z.getDegree();
            Node x = z.getChild();
            Node tempRight;

            while (numChildren > 0) {
                tempRight = x.getRight();

                // remove x from child list
                x.getLeft()
                    .setRight(x.getRight());
                x.getRight()
                    .setLeft(x.getLeft());

                // add x to root list of heap
                x.setLeft(max);
                x.setRight(max.getRight());
                max.setRight(x);
                x.getRight()
                    .setLeft(x);

             
                x.setParent(null);
                x = tempRight;
                
                //decrement numChildren
                numChildren--;

            }

            // remove z from root list of heap
            z.getLeft()
                .setRight(z.getRight());
            z.getRight()
                .setLeft(z.getLeft());

            if (z == z.getRight()) {
                max = null;

            } else {
                max = z.getRight();
                consolidate();
            }
            heapSize--;
            return z;
        }
    }

    /**
     * Combines the trees degreewise after removeMax
     */
    public void consolidate() {
        //arbitrary size
        int size = 50;

        List<Node> degreeTable = new ArrayList<Node>(size);

        // Initialize degree table
        for (int i = 0; i < size; i++) {
            degreeTable.add(null);
        }

        int numRoots = 0;
        Node x = max;

        //get the numRoots
        if (x != null) {
            numRoots++;
            x = x.getRight();

            while (x != max) {
                numRoots++;
                x = x.getRight();
            }
        }

        //combine all the roots with same degree
        while (numRoots > 0) {

            int d = x.getDegree();
            Node next = x.getRight();

            
            while (true) {
                Node y = degreeTable.get(d);
                if (y == null) {
                    break;
                }

                if (x.getFrequency() < y.getFrequency()) {
                    Node temp = y;
                    y = x;
                    x = temp;
                }

                //link y with x
                link(y, x);

                // reset the degree table
                degreeTable.set(d, null);
                d++;
            }
            degreeTable.set(d, x);
            x = next;
            numRoots--;
        }

        max = null;

        // combine nodes
        for (int i = 0; i < size; i++) {
            Node y = degreeTable.get(i);
            if (y == null) {
                continue;
            }

            if (max != null) {

                
                y.getLeft()
                    .setRight(y.getRight());
                y.getRight()
                    .setLeft(y.getLeft());

                
                y.setLeft(max);
                y.setRight(max.getRight());
                max.setRight(y);
                y.getRight()
                    .setLeft(y);

                // update max if required
                if (y.getFrequency() > max.getFrequency()) {
                    max = y;
                }
            } else {
                max = y;
            }
        }
    }

   
    /**
     * Links node y with x
     * @param y The root to be linked
     * @param x The root that y is linked to.
     */
    public void link(Node y, Node x) {
        // remove y from root list 
        y.getLeft()
            .setRight(y.getRight());
        y.getRight()
            .setLeft(y.getLeft());

        // make y a child of x
        y.setParent(x);

        if (x.getChild() == null) {
            x.setChild(y);
            y.setRight(y);
            y.setLeft(y);
        } else {
            y.setLeft(x.getChild());
            y.setRight(x.getChild()
                .getRight());
            x.getChild()
                .setRight(y);
            y.getRight()
                .setLeft(y);
        }

        // increase degree of x by 1
        x.setDegree(x.getDegree() + 1);

        // make mark of y as false
        y.setMarked(false);
    }

    /**Returns the current maximum node in heap.
     * @return
     */
    public Node returnMax() {
        return max;
    }

    /**Returns the current heap size.
     * @return
     */
    public int getHeapSize() {
        return heapSize;
    }

    /**Returns true if heap is empty, otherwise false
     * @return
     */
    public boolean isEmpty() {
        return (max == null);
    }
}
