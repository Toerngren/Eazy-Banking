package businessLogic.User;

import java.util.ArrayList;

public class Employee {
    static final String EOL = System.lineSeparator();

    private final String employeeID;
    private final String pinCode;
    private ArrayList<String> employeeMessageList;

    public Employee(String employeeID, String pinCode){
        this.employeeID = employeeID;
        this.pinCode = pinCode;
        this.employeeMessageList = new ArrayList<>();
    }

    public void addMessage(String message){
        this.employeeMessageList.add(message);
    }
    public void removeMessage(){
        employeeMessageList.remove(0);
    }

    public String getPinCode() {
        return pinCode;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String viewMessage(){
        if(employeeMessageList.isEmpty()){
            return EOL + "There are currently no new messages.";
        } else {
            return "Message: " + employeeMessageList.get(0);
        }

    }

    public int numberOfMessages(){
        return employeeMessageList.size();
    }

}
