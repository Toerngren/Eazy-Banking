package User;

public class Employee extends UserAccount{
    private final int employeeID;
    private final String pincode;

    public Employee(String name, String email, int personalNumber, String password, String telephone, int employeeID, String pincode){
        super(name, email, personalNumber, password, telephone);
        this.employeeID = employeeID;
        this.pincode = pincode;

    }
}
