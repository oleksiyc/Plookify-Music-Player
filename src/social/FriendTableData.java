/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package social;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author fahad
 */
public class FriendTableData {
    public  SimpleStringProperty ID = new SimpleStringProperty();
    public  SimpleStringProperty Username = new SimpleStringProperty();
    
    public SimpleStringProperty Message = new SimpleStringProperty();
    public SimpleStringProperty senderID = new SimpleStringProperty();
    
    
    public String getID() {
        return ID.get();
    }

    public void setID(String id) {
        ID.set(id);
    }
    public String getsenderID() {
        return senderID.get();
    }

    public void setsenderID(String senderid) {
        senderID.set(senderid);
    }

    public String getUsername() {
        return Username.get();
    }

    public void setUsername(String usernameStr) {
        Username.set(usernameStr);
    }
    
    
    
    public String getMessage(){
        return Message.get();
    }
    public void setMessage(String messageStr){
        Message.set(messageStr);
    }
    

    
    
    
    
    
}
