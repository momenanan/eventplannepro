package eventplanner;

public abstract class User {
    private String email;
    private String phoneNumber;
    private String userName;
    private String password;
    private boolean isLogged;

    // Default constructor
    public User() {
        isLogged = false; // Initializing boolean field
    }

    // Constructor with email and password parameters
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        isLogged = false; // Initializing boolean field
    }
