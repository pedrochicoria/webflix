package ejb;

import data.Content;
import data.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ContentEJBInterface {
    boolean insertContent(String title, String director, String category, int year, String path);

    List<Content> searchSuggestedContents(String category);

    List<Content> searchAllContent();

    Content changeTitle(Content content, String new_title);

    Content changeDirector(Content content, String new_director);

    Content changeCategory(Content content, String new_category);

    Content changeYear(Content content, int year);

    boolean deleteContent(Content content);

    List<Content> searchByDirector(String director);

    List<Content> searchByYears(int year1, int year2);

    Content consultDetails(Content content);

    List<Content> showWatchList(User user);

    List<Content> searchByCategory(String category);

    void populate();

    Content changeFilePath(Content content, String path);
}
