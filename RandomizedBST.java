import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Comparator;
public class RandomizedBST implements TaxEvasionInterface {
    // Κατασκευαστής χωρίς παραμέτρους
    public RandomizedBST() {
        root = null;
    }

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
    
    private TreeNode root; // pointer to root of the tree

    
    // Υλοποίηση των μεθόδων του interface
    public void insert(LargeDepositor item) {
        if (searchByAFM(item.getAFM()) != null) {
            System.out.println("Ένας μεγαλοκαταθέτης με ΑΦΜ " + item.getAFM() + " υπάρχει ήδη στο δέντρο.");
            return;
        }
        root = insertAtRoot(item, root);
    }

    private TreeNode insertAtRoot(LargeDepositor item, TreeNode node) {
        if (node == null) {
            return new TreeNode(item, null, null, 1);
        }
        int leftSize = size(node.left);
        if (Math.random() * (leftSize + 1) < 1) { // Πιθανότητα εισαγωγής στη ρίζα
            return insertRoot(item, node);
        }
        if (item.key() < node.item.key()) {
            node.left = insertAtRoot(item, node.left);
        } else {
            node.right = insertAtRoot(item, node.right);
        }
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }
    private TreeNode insertRoot(LargeDepositor item, TreeNode node) {
        if (node == null) {
            return new TreeNode(item, null, null, 1);
        }
        if (item.key() < node.item.key()) {
            node.left = insertRoot(item, node.left);
            node = rotateRight(node);
        } else {
            node.right = insertRoot(item, node.right);
            node = rotateLeft(node);
        }
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }
    private int size(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return node.N;
    }
    private TreeNode rotateRight(TreeNode node) {
        if (node == null || node.left == null) {
            return node;
        }
        TreeNode temp = node.left;
        node.left = temp.right;
        temp.right = node;
        temp.N = node.N;
        node.N = size(node.left) + size(node.right) + 1;
        return temp;
    }
    
    private TreeNode rotateLeft(TreeNode node) {
        if (node == null || node.right == null) {
            return node;
        }
        TreeNode temp = node.right;
        node.right = temp.left;
        temp.left = node;
        temp.N = node.N;
        node.N = size(node.left) + size(node.right) + 1;
        return temp;
    }
    
    public void load(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                int afm = Integer.parseInt(parts[0]);
                String firstName = parts[1];
                String lastName = parts[2];
                double savings = Double.parseDouble(parts[3]);
                double taxedIncome = Double.parseDouble(parts[4]);
    
                LargeDepositor depositor = new LargeDepositor(afm, firstName, lastName, savings, taxedIncome);
                insert(depositor);
            }
            scanner.close();
        }  catch (FileNotFoundException e) {
            System.err.println("Το αρχείο δεν βρέθηκε: " + filename);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Σφάλμα κατά την ανάγνωση του αρχείου: " + e.getMessage());
        }
    }

    public void updateSavings(int AFM, double savings) {
        LargeDepositor depositor = searchByAFM(AFM);
        if (depositor != null) {
            depositor.setSavings(savings);
            System.out.println("Οι αποταμιεύσεις του υπόπτου με ΑΦΜ " + AFM + " ενημερώθηκαν.");
        } else {
            System.out.println("Δεν βρέθηκε ύποπτος με ΑΦΜ " + AFM);
        }
    }
    

    private LargeDepositor searchByAFM(TreeNode node, int AFM) {
        if (node == null) {
            return null;
        }
        if (AFM < node.item.getAFM()) {
            return searchByAFM(node.left, AFM);
        } else if (AFM > node.item.getAFM()) {
            return searchByAFM(node.right, AFM);
        } else {
            return node.item;
        }
    }
    
    public LargeDepositor searchByAFM(int AFM) {
        return searchByAFM(root, AFM);
    }
    

    private void searchByLastName(TreeNode node, String lastName, SingleNode head, SingleNode tail) {
        if (node == null) {
            return;
        }
        if (lastName.equals(node.item.getLastName())) {
            SingleNode newNode = new SingleNode(node.item);
            if (head.next == null) {
                head.next = newNode;
            } else {
                tail.next = newNode;
            }
            tail.next = newNode;
        }
        searchByLastName(node.left, lastName, head, tail);
        searchByLastName(node.right, lastName, head, tail);
    }
    
    public void searchByLastName(String lastName) {
        SingleNode dummyHead = new SingleNode(null);
        SingleNode tail = dummyHead;
        searchByLastName(root, lastName, dummyHead, tail);
    
        SingleNode current = dummyHead.next;
        int count = 0;
        while (current != null && count < 5) {
            System.out.println(current.item);
            current = current.next;
            count++;
        }
        if (count == 0) {
            System.out.println("Δεν βρέθηκε κανένας με επίθετο: " + lastName);
        }
    }
    

    private TreeNode remove(TreeNode node, int AFM) {
        if (node == null) {
            return null;
        }
        if (AFM < node.item.getAFM()) {
            node.left = remove(node.left, AFM);
        } else if (AFM > node.item.getAFM()) {
            node.right = remove(node.right, AFM);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                TreeNode t = node;
                node = min(t.right); // Βρίσκουμε τον ελάχιστο κόμβο του δεξιού υποδέντρου
                node.right = deleteMin(t.right);
                node.left = t.left;
            }
        }
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }
    
    public void remove(int AFM) {
        root = remove(root, AFM);
    }
    
    private TreeNode min(TreeNode node) {
        if (node.left == null) return node;
        return min(node.left);
    }
    
    private TreeNode deleteMin(TreeNode node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }
    
    public double getMeanSavings() {
        double[] sumAndCount = getSumAndCount(root);
        return sumAndCount[1] > 0 ? sumAndCount[0] / sumAndCount[1] : 0.0;
    }
    
    private double[] getSumAndCount(TreeNode node) {
        if (node == null) {
            return new double[]{0.0, 0}; // Αρχικοποίηση του αθροίσματος και του μετρητή σε 0
        }
        double[] left = getSumAndCount(node.left);
        double[] right = getSumAndCount(node.right);
    
        double sum = left[0] + right[0] + node.item.getSavings(); // Άθροισμα των αποταμιεύσεων
        double count = left[1] + right[1] + 1; // Συνολικός αριθμός των καταθετών
    
        return new double[]{sum, count};
    }
    

    public void printTopLargeDepositors(int k) {
        PriorityQueue<LargeDepositor> pq = new PriorityQueue<>(k, new Comparator<LargeDepositor>() {
            @Override
            public int compare(LargeDepositor d1, LargeDepositor d2) {
                double score1 = d1.getTaxedIncome() < 8000 ? Double.MAX_VALUE : d1.getSavings() - d1.getTaxedIncome();
                double score2 = d2.getTaxedIncome() < 8000 ? Double.MAX_VALUE : d2.getSavings() - d2.getTaxedIncome();
                return Double.compare(score2, score1);
            }
        });
    
        addDepositorsToPriorityQueue(root, pq, k);
        for (int i = 0; i < k && !pq.isEmpty(); i++) {
            LargeDepositor depositor = pq.poll();
            System.out.println(depositor);
        }
    }
    
    private void addDepositorsToPriorityQueue(TreeNode node, PriorityQueue<LargeDepositor> pq, int k) {
        if (node == null) {
            return;
        }
        pq.offer(node.item);
        if (pq.size() > k) {
            pq.poll();
        }
        addDepositorsToPriorityQueue(node.left, pq, k);
        addDepositorsToPriorityQueue(node.right, pq, k);
    }
    
    private void printInOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        printInOrder(node.left); // Επισκεφτείτε πρώτα το αριστερό υποδέντρο
        System.out.println(node.item); // Εκτυπώστε τον κόμβο
        printInOrder(node.right); // Στη συνέχεια επισκεφτείτε το δεξί υποδέντρο
    }
    
    public void printByAFM() {
        printInOrder(root);
    }
    public static void main(String[] args) {
        RandomizedBST bst = new RandomizedBST();
        if (args.length > 0) {
            String filename = args[0];
            bst.load(filename);
        } else {
            System.out.println("Παρακαλώ δώστε το όνομα του αρχείου ως όρισμα.");
        }
        Scanner scanner = new Scanner(System.in);
    
        while (true) {
            System.out.println("\nΜενού Διαχείρισης:");
            System.out.println("1. Εισαγωγή Υπόπτου");
            System.out.println("2. Διαγραφή Υπόπτου");
            System.out.println("3. Αναζήτηση με ΑΦΜ");
            System.out.println("4. Αναζήτηση με Επίθετο");
            System.out.println("5. Εμφάνιση Μέσου Ποσού Αποταμιεύσεων");
            System.out.println("6. Εμφάνιση Πρώτων Υπόπτων για Φοροδιαφυγή");
            System.out.println("7. Εμφάνιση Όλων των Υπόπτων Ανά ΑΦΜ");
            System.out.println("8. Έξοδος");
            System.out.print("Επιλέξτε μια ενέργεια: ");
    
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // Εισαγωγή Υπόπτου
                    System.out.print("Εισάγετε ΑΦΜ: ");
                    int afm = scanner.nextInt();
                    System.out.print("Εισάγετε Όνομα: ");
                    String firstName = scanner.next();
                    System.out.print("Εισάγετε Επίθετο: ");
                    String lastName = scanner.next();
                    System.out.print("Εισάγετε Αποταμιεύσεις: ");
                    double savings = scanner.nextDouble();
                    System.out.print("Εισάγετε Δηλωμένο Εισόδημα: ");
                    double taxedIncome = scanner.nextDouble();
                    LargeDepositor depositor = new LargeDepositor(afm, firstName, lastName, savings, taxedIncome);
                    bst.insert(depositor);
                    break;
                case 2:
                    // Διαγραφή Υπόπτου
                    System.out.print("Εισάγετε ΑΦΜ για διαγραφή: ");
                    int afmToDelete = scanner.nextInt();
                    bst.remove(afmToDelete);
                    break;
                case 3:
                    // Αναζήτηση με ΑΦΜ
                    System.out.print("Εισάγετε ΑΦΜ για αναζήτηση: ");
                    int afmToSearch = scanner.nextInt();
                    LargeDepositor foundDepositor = bst.searchByAFM(afmToSearch);
                    if (foundDepositor != null) {
                        System.out.println("Βρέθηκε ο Υπόπτος: " + foundDepositor);
                    } else {
                        System.out.println("Δεν βρέθηκε Υπόπτος με ΑΦΜ: " + afmToSearch);
                    }
                    break;
                case 4:
                    // Αναζήτηση με Επίθετο
                    System.out.print("Εισάγετε Επίθετο για αναζήτηση: ");
                    String lastNameToSearch = scanner.next();
                    bst.searchByLastName(lastNameToSearch);
                    break;
                case 5:
                    // Εμφάνιση Μέσου Ποσού Αποταμιεύσεων
                    System.out.println("Μέσο ποσό αποταμιεύσεων: " + bst.getMeanSavings());
                    break;
                case 6:
                    // Εμφάνιση Πρώτων Υπόπτων για Φοροδιαφυγή
                    System.out.print("Εισάγετε τον αριθμό των υπόπτων προς εμφάνιση: ");
                    int k = scanner.nextInt();
                    bst.printTopLargeDepositors(k);
                    break;
                case 7:
                    // Εμφάνιση Όλων των Υπόπτων Ανά ΑΦΜ
                    bst.printByAFM();
                    break;
                case 8:
                    // Έξοδος
                    System.out.println("Έξοδος από το πρόγραμμα.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Μη έγκυρη επιλογή. Παρακαλώ επιλέξτε ξανά.");
                    break;
            }
        }
    }
    
    
    private class SingleNode {
        LargeDepositor item;
        SingleNode next;

        SingleNode(LargeDepositor item) {
            this.item = item;
            this.next = null;
        }
    }
}
