package ejb;

import data.Content;
import data.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;


@Stateless
public class UsersEJB implements UsersEJBInterface {
    @PersistenceContext(name = "Assignment2")
    EntityManager em;
    private Logger log = Logger.getLogger("UsersEJB");

    public UsersEJB() {
        super();
    }

    public void logs(Logger logger, String msg, boolean error) {
        if (error)
            logger.severe(msg);
        else
            logger.warning(msg);
    }

    private String MD5crypt(String password) {
        String crypt_password = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            crypt_password = sb.toString();
            return crypt_password;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return crypt_password;
    }

    public boolean register(String username, String password, String email, String name, String credit_card) {
        Query query = em.createQuery("from User u where u.email = :email")
                .setParameter("email", email);
        List<User> res = query.getResultList();
        if (!res.isEmpty()) {
            this.logs(log, "User already registered", true);
            return false;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        formatter.format(date);

        em.merge(new User(username, MD5crypt(password), email, name, credit_card, "Normal", date));
        this.logs(log, "User is register with success", false);
        return true;
    }

    public User login(String email, String password) {
        Query query = em.createQuery("from User u where u.email = :email")
                .setParameter("email", email);
        List<User> res = query.getResultList();
        if (res.isEmpty()) {
            this.logs(log, "Email not assigned to a registered user", true);
            return null;
        }
        User user = res.get(0);
        if (user.getPassword().equals(MD5crypt(password))) {
            this.logs(log, "User " + user.getId() + " successfully logged in", false);
            return user;
        } else {
            this.logs(log, "User " + user.getId() + " inserted the wrong password", true);
            return null;
        }
    }

    public User changeEmail(User user, String new_email) {
        Query query = em.createQuery("from User u where u.email = :new_email")
                .setParameter("new_email", new_email);
        List<User> res = query.getResultList();
        if (!res.isEmpty()) {
            this.logs(log, "Email is already assigned to a registered user", true);
            return null;
        } else {
            User u = em.find(User.class, user.getId());
            u.setEmail(new_email);
            this.logs(log, "User's " + u.getId() + " email changed", false);
            return u;
        }
    }

    public User changeName(User user, String new_name) {
        Query query = em.createQuery("from User u where u.name= :new_name").setParameter("new_name", new_name);
        List<User> res = query.getResultList();
        if (!res.isEmpty()) {
            this.logs(log, "Name is already assigned to a registered user", true);
            return null;
        } else {
            User u = em.find(User.class, user.getId());
            u.setName(new_name);
            this.logs(log, "User's " + u.getId() + " name changed", false);

            return u;
        }
    }

    public User changePassword(User user, String new_password) {
        Query query = em.createQuery("from User u where u.password= :new_password").setParameter("new_password", new_password);
        List<User> res = query.getResultList();
        if (!res.isEmpty()) {
            this.logs(log, "Password is already assigned to a registered user", true);
            return null;
        } else {
            User u = em.find(User.class, user.getId());
            u.setPassword(MD5crypt(new_password));
            this.logs(log, "User's " + u.getId() + " password changed", false);

            return u;
        }
    }

    public User changeCreditCardNumber(User user, String new_credit_card_number) {
        Query query = em.createQuery("from User u where u.creditcard_number= :new_credit_card_number").setParameter("new_credit_card_number", new_credit_card_number);
        List<User> res = query.getResultList();
        if (!res.isEmpty()) {
            this.logs(log, "Credit card Number is already assigned to a registered user", true);
            return null;
        } else {
            User u = em.find(User.class, user.getId());
            u.setCreditcard_number(new_credit_card_number);
            this.logs(log, "User's " + u.getId() + " credit card number changed", false);

            return u;
        }
    }

    public User changeUsername(User user, String new_username) {
        Query query = em.createQuery("from User u where u.username= :new_username").setParameter("new_username", new_username);
        List<User> res = query.getResultList();
        if (!res.isEmpty()) {
            this.logs(log, "Username is already assigned to a registered user", true);
            return null;
        } else {
            User u = em.find(User.class, user.getId());
            u.setUsername(new_username);
            this.logs(log, "User's " + u.getId() + " username changed", false);

            return u;
        }
    }

    public boolean deleteUser(User user) {
        User u = em.find(User.class, user.getId());
        if (u != null) {
            em.remove(u);
            this.logs(log, "User " + u.getId() + " deleted", false);
            return true;
        }
        this.logs(log, "User doesn't exist", true);

        return false;
    }

    public User changeToManager(User user, String privilege) {
        Query query = em.createQuery("from User u where u.privilege= :privilege").setParameter("privilege", privilege);
        List<User> res = query.getResultList();

        User u = em.find(User.class, user.getId());
        u.setPrivilege(privilege);
        this.logs(log, "User's " + u.getId() + " privilege changed", false);

        return u;

    }

    public User changeToUser(User user, String privilege) {
        Query query = em.createQuery("from User u where u.privilege= :privilege").setParameter("privilege", privilege);
        List<User> res = query.getResultList();

        User u = em.find(User.class, user.getId());
        u.setPrivilege(privilege);
        this.logs(log, "User's " + u.getId() + " privilege changed", false);

        return u;

    }

    public boolean followContent(User user, Content c) {
        Content cont = em.find(Content.class, c.getIdContent());
        User u = em.find(User.class, user.getId());

        if (!u.getWatchList().contains(cont) && u != null && cont != null) {
            u.addToWatchList(cont);
            em.merge(u);
            this.logs(log, "User " + u.getId() + " started following the content " + cont.getIdContent(), false);
            return true;
        }
        this.logs(log, "User can't follow content", true);
        return false;
    }

    public boolean unfollowContent(User user, Content c) {
        Content cont = em.find(Content.class, c.getIdContent());
        User u = em.find(User.class, user.getId());

        if (u.getWatchList().contains(cont)) {
            u.removeFromWatchList(cont);
            em.merge(u);
            this.logs(log, "User " + u.getId() + " unfollowed content " + cont.getIdContent(), false);
            return true;
        }
        this.logs(log, "User " + u.getId() + " is not following content  " + cont.getIdContent(), true);
        return false;
    }

    public String userFollowsContent(User user, Content c) {
        Content cont = em.find(Content.class, c.getIdContent());
        User u = em.find(User.class, user.getId());
        if (u.getWatchList().contains(c)) {
            return "true";
        } else {
            return "false";
        }
    }


}



