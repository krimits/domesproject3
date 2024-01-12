private class TreeNode {
    private LargeDepositor item; // Το στοιχείο του κόμβου
    private TreeNode left; // Δείκτης στο αριστερό υποδέντρο
    private TreeNode right; // Δείκτης στο δεξιό υποδέντρο
    private int N; // Αριθμός κόμβων στο υποδέντρο που ριζώνει σε αυτόν τον κόμβο

    // Κατασκευαστής με τέσσερις παραμέτρους
    public TreeNode(LargeDepositor item, TreeNode left, TreeNode right, int N) {
        if (N < 0) {
            throw new IllegalArgumentException("Ο αριθμός των κόμβων δεν μπορεί να είναι αρνητικός");
        }
        this.item = item;
        this.left = left;
        this.right = right;
        this.N = N;
    }

    // Getters και setters με έλεγχο για έγκυρες τιμές
    public LargeDepositor getItem() {
        return item;
    }

    public void setItem(LargeDepositor item) {
        this.item = item;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public int getN() {
        return N;
    }

    public void setN(int N) {
        if (N < 0) {
            throw new IllegalArgumentException("Ο αριθμός των κόμβων δεν μπορεί να είναι αρνητικός");
        }
        this.N = N;
    }

    // Μέθοδος για την εκτύπωση των δεδομένων του κόμβου
    @Override
    public String toString() {
        return "TreeNode [item=" + item + ", left=" + (left != null ? "TreeNode" : "null") 
                + ", right=" + (right != null ? "TreeNode" : "null") + ", N=" + N + "]";
    }
}
