package ejb;

import data.Content;
import data.User;

import javax.ejb.Local;

@Local
public interface UsersEJBInterface {
    boolean register(String username, String password, String email, String name, String credit_card);

    User login(String email, String password);

    User changeEmail(User user, String new_email);

    User changeName(User user, String new_name);

    User changePassword(User user, String new_password);

    User changeCreditCardNumber(User user, String new_credit_card_number);

    User changeUsername(User user, String new_username);

    boolean deleteUser(User user);

    User changeToManager(User user, String privilege);

    User changeToUser(User user, String privilege);

    boolean followContent(User user, Content c);

    boolean unfollowContent(User user, Content c);

    String userFollowsContent(User user, Content c);

}
