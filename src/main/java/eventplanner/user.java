package eventplanner;

public abstract class user {
    String email ,PhoneNumber,UserName,password;
    user(){}
    user(String email,String Password){
        this.email=email;
        this.password=Password;
    }

}