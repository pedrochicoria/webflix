package data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Content implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idContent;


    private String title;
    private String director;
    private String category;
    @ManyToMany(mappedBy = "watchList", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    private List<User> usersFollowing;
    private String path;


    private int year;

    public Content(String title, String director, String category, int year) {
        super();
        this.title = title;
        this.director = director;
        this.category = category;
        this.year = year;

    }

    public Content() {
    }

    public int getIdContent() {
        return idContent;
    }

    public void setIdContent(int idContent) {
        this.idContent = idContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<User> getUsersFollowing() {
        return usersFollowing;
    }

    public void setUsersFollowing(List<User> usersFollowing) {
        this.usersFollowing = usersFollowing;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
