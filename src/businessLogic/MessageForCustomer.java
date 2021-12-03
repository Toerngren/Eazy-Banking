package businessLogic;

public class MessageForCustomer {
    //private String sender;
    private String message;

    public MessageForCustomer(String message){
        this.message = message;
    }

    // No need this attribute and method for customers because its obvious that its from bank
    // But for employee it will be needed
    /* public String getSender(){
           return this.sender;
    }*/

    public String getMessage(){
        return this.message;
    }
}
