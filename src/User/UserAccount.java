package User;

public abstract class UserAccount {
    private String name;
    private String email;
    private int personalNumber;
    private final String password;
    private String telephone;

    public UserAccount(String name, String email, int personalNumber, String password, String telephone){
        this.name = name;
        this.email = email;
        this.personalNumber = personalNumber;
        this.password = password;
        this.telephone = telephone;

    }
}



