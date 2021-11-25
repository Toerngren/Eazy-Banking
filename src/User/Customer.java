package User;

public class Customer extends UserAccount {
    private final String pincode;

    public Customer(String name, String email, int personalNumber, String password, String telephone, String pincode) {
        super(name, email, personalNumber, password, telephone);
        this.pincode = pincode;
    }
}









