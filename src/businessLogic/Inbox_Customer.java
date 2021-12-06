package businessLogic;

import java.util.ArrayList;
import java.util.List;

public class Inbox_Customer {
    private List<MessageForCustomer> readMessageList;
    private List<MessageForCustomer> unReadMessageList;

    public Inbox_Customer(){
       this.readMessageList = new ArrayList<>();
       this.unReadMessageList = new ArrayList<>();
    }

    public String addNewMessage(String newMessage){
        MessageForCustomer message = new MessageForCustomer(newMessage);
        this.unReadMessageList.add(message);
        return "Message has been sent successfully.";
    }

    public String addReadMessage(){
        return "";
    }

    public String printUnreadMessages(){
        return "";
    }

    public String printReadMessages(){
        return "";
    }

    public String printAllMessages(){
        return "";
    }




}
