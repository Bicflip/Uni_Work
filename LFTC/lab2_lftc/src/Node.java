public class Node {
    String key;
    int index;
    Node left, right;

    public Node(String key, int index) {
        this.key = key;
        this.index = index;
        this.left = null;
        this.right = null;
    }
}
