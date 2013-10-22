/**
A sample Data Access Object (DAO). Imagine how bored I was creating a DAO class
without the aid of a code generator. Very. Bored.

@author Chad Estioco
*/

public class CommerceSystemUser{
    private int userId;
    private String userName;
    private String password;
    private String address;
    private String firstName;
    private String lastName;

    public CommerceSystemUser(){
    }
    
    /**
    Should be able to get userId, but not change it since this binds to a
    specific user. Hence, no corresponding set method.
    */
    public int getUserId(){
        return userId;
    }
    
    /**
    Same rules apply as for userId.
    */
    public String getUserName(){
        return userName;
    }
    
    /**
    Hopefully, _not_ plain text!
    */
    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }
}
