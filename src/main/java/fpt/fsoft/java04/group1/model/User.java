package fpt.fsoft.java04.group1.model;

public class User
{
    private int userId;
    private String userName;
    private String password;
    private String email;
    private String role;


    public User(int userId, String userName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
    }


    public User( String userName, String password, String email, String role) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User() {
    }

    public User(int userId, String userName, String password, String email, String role) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

