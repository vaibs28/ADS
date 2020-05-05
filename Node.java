/**
 * @author vaibhav 
 * 
 */
public class Node {
  /**
   * Class representing a fibonacci heap node
   */
    private boolean isMarked;
    private Node parent;
    private Node child;
    private Node left;
    private Node right;
    private String hashtag;
    private int frequency;
    private int degree;

    Node() {
    }

    /**
     * Parameterized constructor for initializing the node
     * @param frequency the count of the hashtag from the input file
     * @param hashtag   the hashtag
     */
    public Node(int frequency, String hashtag) {
    isMarked = false;
    parent = null;
    child = null;
    left = this;
    right = this;
    this.frequency = frequency;
    this.hashtag = hashtag;
    }

    /**
     * Returns the parent of the node
     * @return  parent node
     */
    public Node getParent() {
    return parent;
    }

    /**
     * Sets the parent in node to the passed param
     * @param parent parent node
     */
    public void setParent(Node parent) {
    this.parent = parent;
    }

    /**
     * Returns the value of child.
     * @return child node
     */
    public Node getChild() {
    return child;
    }

    /**
     * Sets the value of child to the passed param.
     * @param child child node
     */
    public void setChild(Node child) {
    this.child = child;
    }

    /**
     * Returns the left sibling.
     * @return left sibling node
     */
    public Node getLeft() {
    return left;
    }

    /**
     * Sets the left sibling.
     * @param left left sibling node
     */
    public void setLeft(Node left) {
    this.left = left;
    }

    /**
     * Returns the right sibling.
     * @return right sibling node
     */
    public Node getRight() {
    return right;
    }

    /**
     * Sets the right sibling.
     * @param right right sibling node
     */
    public void setRight(Node right) {
    this.right = right;
    }

    /**
     * returns the frequency of the hashtags.
     * @return frequency
     */
    public int getFrequency() {
    return frequency;
    }

    /**
     * Sets the frequency.
     * @param frequency frequency of hashtag
     */
    public void setFrequency(int frequency) {
    this.frequency = frequency;
    }

    /**
     * Returns the degree of the node.
     * @return degree
     */
    public int getDegree() {
    return degree;
    }

    /**
     * Sets the degree.
     * @param degree degree
     */
    public void setDegree(int degree) {
    this.degree = degree;
    }

    /**
     * Returns the hashtag stored in the node.
     * @return hashtag
     */
    public String getHashtag() {
    return hashtag;
    }

    /**
     * sets the hashtag.
     * @param hashtag hashtag
     */
    public void setHashtag(String hashtag) {
    this.hashtag = hashtag;
    }

    /**
     * returns the value of the cut field.
     * @return boolean
     */
    public boolean isMarked() {
    return isMarked;
    }

    /**
     * sets the value of cut field.
     * @param isMarked boolean
     */
    public void setMarked(boolean isMarked) {
    this.isMarked = isMarked;
    }

}
