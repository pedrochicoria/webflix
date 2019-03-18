package ejb;

import data.Content;
import data.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Stateless
public class ContentEJB implements ContentEJBInterface {
    @PersistenceContext(name = "Content")
    EntityManager em;
    Logger log = Logger.getLogger("ContentEJB");


    public ContentEJB() {
        super();
    }

    public void logs(Logger logger, String msg, boolean error) {
        if (error)
            logger.severe(msg);
        else
            logger.warning(msg);
    }

    public void populate() {
        Content[] contents = {
                new Content("Venom", "Marvel", "Action", 2018),
                new Content("Ant-Man 1", "Marvel", "Action", 2016),
                new Content("American Pie 1", "Universal Pictures", "Comedy", 2000),
                new Content("American Pie 2", "Universal Pictures", "Comedy", 2002),
                new Content("Love", "Universal Pictures", "Romance", 2010),
                new Content("Call me by your name", "Universal Pictures", "Romance", 2010),
                new Content("Rei Leão 1", "Disney", "Fantasy", 1996),
                new Content("Rei Leão 2", "Disney", "Fantasy", 2003),
                new Content("DunKirk", "Cristopher Nolan", "History", 2017),
                new Content("Private Ryan", "Universal Pictures", "History", 2017),
                new Content("A Origem", "Cristopher Nolan", "Thriller", 2010),
                new Content("Shutter Island", "Cristopher Nolan", "Thriller", 2012),
        };

        for (Content c : contents)
            em.persist(c);
    }

    public boolean insertContent(String title, String director, String category, int year, String path) {
        Query query = em.createQuery("from Content c where c.title = :title")
                .setParameter("title", title);
        List<Content> res = query.getResultList();
        if (!res.isEmpty()) {
            this.logs(log, "Content already inserted", true);
            return false;
        }
        Content content = new Content(title, director, category, year);
        content.setPath(path);
        em.merge(content);

        this.logs(log, "Content is insert with success", false);
        return true;
    }


    public List<Content> searchByCategory(String category) {
        Query query = em.createQuery("from Content c where c.category = :category")
                .setParameter("category", category);
        List<Content> list = query.getResultList();
        this.logs(log, "content is find ", false);

        return query.getResultList();
    }

    public List<Content> searchByDirector(String director) {
        Query query = em.createQuery("from Content c where c.director = :director")
                .setParameter("director", director);
        List<Content> list = query.getResultList();
        this.logs(log, " contents is find ", false);

        return query.getResultList();
    }

    public List<Content> searchByYears(int year1, int year2) {
        Query query = em.createQuery(("from Content c where c.year between :year1 and :year2")).setParameter("year1", year1).setParameter("year2", year2);
        this.logs(log, "All content is find ", false);

        return query.getResultList();
    }

    public List<Content> searchAllContent() {
        Query query = em.createQuery("select content from Content content");
        this.logs(log, "All content is find ", false);
        return query.getResultList();
    }

    public Content changeTitle(Content content, String new_title) {
        Query query = em.createQuery("from Content c where c.title = :new_title")
                .setParameter("new_title", new_title);
        List<Content> res = query.getResultList();


        Content c = em.find(Content.class, content.getIdContent());
        c.setTitle(new_title);
        this.logs(log, "Content's " + c.getIdContent() + " title changed", false);
        return c;

    }

    public Content changeFilePath(Content content, String path) {
        Query query = em.createQuery("from Content c where c.path = :path")
                .setParameter("path", path);
        List<Content> res = query.getResultList();


        Content c = em.find(Content.class, content.getIdContent());
        if (c == null) {
            this.logs(log, "Content's" + c.getIdContent() + " path not  changed", true);
            return null;
        }
        c.setPath(path);
        this.logs(log, "Content's " + c.getIdContent() + " path changed", false);
        return c;

    }

    public Content consultDetails(Content content) {
        Content c = em.find(Content.class, content.getIdContent());
        if (c != null) {

            this.logs(log, "Content " + c.getIdContent() + " find", false);
            return c;
        }
        this.logs(log, "Content doesn't exist", true);

        return c;
    }

    public Content changeDirector(Content content, String new_director) {
        Query query = em.createQuery("from Content c where c.director = :new_director")
                .setParameter("new_director", new_director);
        List<Content> res = query.getResultList();
        Content c = em.find(Content.class, content.getIdContent());
        if (c == null) {
            this.logs(log, "Content's" + c.getIdContent() + " director not  changed", true);
            return null;
        }
        c.setDirector(new_director);
        this.logs(log, "Content's " + c.getIdContent() + " director changed", false);
        return c;

    }


    public Content changeCategory(Content content, String new_category) {
        Query query = em.createQuery("from Content c where c.category = :new_category")
                .setParameter("new_category", new_category);
        List<Content> res = query.getResultList();


        Content c = em.find(Content.class, content.getIdContent());
        if (c == null) {
            this.logs(log, "Content's" + c.getIdContent() + " category not  changed", true);
            return null;
        }
        c.setCategory(new_category);
        this.logs(log, "Content's " + c.getIdContent() + " category changed", false);
        return c;

    }

    public Content changeYear(Content content, int year) {
        Query query = em.createQuery("from Content c where c.year = :new_year")
                .setParameter("new_year", year);
        List<Content> res = query.getResultList();

        Content c = em.find(Content.class, content.getIdContent());
        if (c == null) {
            this.logs(log, "Content's" + c.getIdContent() + " year not  changed", true);
            return null;
        }
        c.setYear(year);
        this.logs(log, "Content's " + c.getIdContent() + " year changed", false);
        return c;

    }


    public boolean deleteContent(Content content) {
        Content c = em.find(Content.class, content.getIdContent());
        if (c != null) {

            List<User> userList = c.getUsersFollowing();
            for (int i = 0; i < userList.size(); i++) {
                User u = em.find(User.class, userList.get(i).getId());
                if (u != null) {
                    u.removeFromWatchList(c);
                }
            }

            em.remove(c);
            this.logs(log, "Content " + c.getIdContent() + " deleted", false);
            return true;
        }
        this.logs(log, "Content doesn't exist", true);

        return false;
    }

    public List<Content> showWatchList(User user) {
        User u = em.find(User.class, user.getId());
        System.out.println(user.getId());
        List<Content> list = u.getWatchList();

        this.logs(log, "User's WatchList  find ", false);


        return list;
    }

    public List<Content> searchSuggestedContents(String category) {
        Random rand = new Random();

        Query query = em.createQuery("from Content c where c.category = :category")
                .setParameter("category", category);
        List<Content> list = query.getResultList();
        List<Content> listf = new ArrayList<>();
        ArrayList<Integer> numbers = new ArrayList<>();

        for (int j = 0; j < list.size(); j++) {
            numbers.add(j);
        }
        Collections.shuffle(numbers);


        for (int i = 0; i < 1; i++) {
            int integer = numbers.get(i);
            Content c = list.get(integer);
            listf.add(c);
        }


        return listf;
    }

}
