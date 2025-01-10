class SymbolTable {
    private Node root;
    private int currentIndex;

    public SymbolTable() {
        this.root = null;
        this.currentIndex = 0;
    }

    // Adaugă un simbol în arbore
    public void add(String key, String type) {
        root = addRec(root, key);
    }

    // Caută un simbol și returnează indexul sau null dacă nu există
    public Integer get(String key) {
        Node node = search(root, key);
        return (node != null) ? node.index : null;
    }

    // Adaugă un nod în arbore recursiv
    private Node addRec(Node node, String key) {
        if (node == null) {
            return new Node(key, currentIndex++);
        }
        if (key.compareTo(node.key) < 0) {
            node.left = addRec(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            node.right = addRec(node.right, key);
        }
        return node;
    }

    // Caută un nod în arbore recursiv
    private Node search(Node node, String key) {
        if (node == null || node.key.equals(key)) {
            return node;
        }
        if (key.compareTo(node.key) < 0) {
            return search(node.left, key);
        }
        return search(node.right, key);
    }

    // Afișează TS în ordine lexicografică
    public void print() {
        System.out.println("Tabela de Simboluri:");
        printInOrder(root);
    }

    private void printInOrder(Node node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.println(node.key + " -> " + node.index);
            printInOrder(node.right);
        }
    }
}
