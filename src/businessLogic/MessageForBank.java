package businessLogic;

public class MessageForBank {
    private String sender;
    private String message;

    public MessageForBank(String message){
        this.message = message;
    }

    public String getSender(){
           return this.sender;
    }

    public String getMessage(){
        return this.message;
    }
}
