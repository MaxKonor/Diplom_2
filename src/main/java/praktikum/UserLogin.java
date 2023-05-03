package praktikum;

public class UserLogin {
    private String email;
    private String password;

    public UserLogin(String email, String password) {

        this.email = email;
        this.password = password;

    }

    public static UserLogin from(User user) {
        return new UserLogin(user.getEmail(), user.getPassword());

    }

    public UserLogin(User user) {

        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
