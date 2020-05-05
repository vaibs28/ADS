//max fibonacci heap
public interface IFibonacciHeap {

    Node insert(int hashtagFrequency, String hashtag);
    
    Node removeMax();
    
    void increaseKey(Node node, int value);
    
    void cascadeCut(Node node);
    
    void cut(Node node1, Node node2);
}
