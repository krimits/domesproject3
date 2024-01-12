public class LargeDepositor {
    // Δήλωση ιδιωτικών μεταβλητών για την ασφάλεια των δεδομένων
    private int afm; // Το ΑΦΜ του καταθέτη
    private String firstName; // Το όνομα του καταθέτη
    private String lastName; // Το επώνυμο του καταθέτη
    private double savings; // Το ποσό των καταθέσεων σε άλλες χώρες
    private double taxedIncome; // Το δηλωμένο εισόδημα στην Ελλάδα

    // Κατασκευαστής με έλεγχο για έγκυρες τιμές
    public LargeDepositor(int afm, String firstName, String lastName, double savings, double taxedIncome) {
        if (afm < 0) {
            throw new IllegalArgumentException("Το ΑΦΜ δεν μπορεί να είναι αρνητικό");
        }
        if (savings < 0 || taxedIncome < 0) {
            throw new IllegalArgumentException("Τα ποσά των καταθέσεων και του δηλωμένου εισοδήματος δεν μπορούν να είναι αρνητικά");
        }
        this.afm = afm;
        this.firstName = firstName;
        this.lastName = lastName;
        this.savings = savings;
        this.taxedIncome = taxedIncome;
    }

    // Getters και setters με έλεγχο για έγκυρες τιμές
    public int getAFM() {
        return afm;
    }

    public void setAFM(int afm) {
        if (afm < 0) {
            throw new IllegalArgumentException("Το ΑΦΜ δεν μπορεί να είναι αρνητικό");
        }
        this.afm = afm;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getSavings() {
        return savings;
    }

    public void setSavings(double savings) {
        if (savings < 0) {
            throw new IllegalArgumentException("Το ποσό των καταθέσεων δεν μπορεί να είναι αρνητικό");
        }
        this.savings = savings;
    }

    public double getTaxedIncome() {
        return taxedIncome;
    }

    public void setTaxedIncome(double taxedIncome) {
        if (taxedIncome < 0) {
            throw new IllegalArgumentException("Το δηλωμένο εισόδημα δεν μπορεί να είναι αρνητικό");
        }
        this.taxedIncome = taxedIncome;
    }

    // Μέθοδος για την επιστροφή του κλειδιού (ΑΦΜ)
    public int key() {
        return afm;
    }

    // Μέθοδος που επιστρέφει την αναπαράσταση του αντικειμένου σε μορφή String
    @Override
    public String toString() {
        return "LargeDepositor [AFM=" + afm + ", firstName=" + firstName + ", lastName=" + lastName + ", savings=" + savings
                + ", taxedIncome=" + taxedIncome + "]";
    }
}
