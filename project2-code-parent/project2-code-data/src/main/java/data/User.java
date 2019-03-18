package data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String creditcard_number;
    private String privilege;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    private List<Content> watchList;
    private Date register_date;

    public User(String username, String password, String email, String name, String creditcard_number, String privilege, Date register_date) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.creditcard_number = creditcard_number;
        this.privilege = privilege;
        this.register_date = register_date;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreditcard_number() {
        return creditcard_number;
    }

    public void setCreditcard_number(String creditcard_number) {
        this.creditcard_number = creditcard_number;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public List<Content> getWatchList() {
        return watchList;
    }

    public void setWatchList(List<Content> watchList) {
        this.watchList = watchList;
    }

    public void addToWatchList(Content c) {
        watchList.add(c);
        c.getUsersFollowing().add(this);
    }

    public void removeFromWatchList(Content c) {
        c.getUsersFollowing().remove(this);
        watchList.remove(c);
    }

    public Date getRegister_date() {
        return register_date;
    }

    public void setRegister_date(Date register_date) {
        this.register_date = register_date;
    }
}