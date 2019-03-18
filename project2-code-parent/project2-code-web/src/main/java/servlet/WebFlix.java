package servlet;


import data.Content;
import data.User;
import ejb.ContentEJBInterface;
import ejb.EmailsEJBInterface;
import ejb.UsersEJBInterface;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Random;

@WebServlet("/WebFlix")

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class WebFlix extends HttpServlet {
    @EJB
    private UsersEJBInterface userEJB;
    @EJB
    private ContentEJBInterface contentEJB;

    @EJB
    private EmailsEJBInterface emailsEJB;
    String UPLOAD_DIRECTORY = "/Users/pedrochicoria/Desktop/UC/MEI/IS/wildfly-13.0.0.Final/Upload/uploadjsp";

    public WebFlix() {
        super();
    }


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        String action = request.getParameter("action");

        emailsEJB.mails();

        if (request.getSession().getAttribute("user") == null) {

            //User isn't logged
            if (action == null) {
                request.getSession().removeAttribute("user");
                request.getRequestDispatcher("Welcome.jsp").forward(request, response);

            } else if (action.toLowerCase().equals("login")) {
                request.getRequestDispatcher("Login.jsp").forward(request, response);
            } else if (action.toLowerCase().equals("register")) {
                request.getRequestDispatcher("RegisterUser.jsp").forward(request, response);
            } else if (action.toLowerCase().equals("signup")) {

                boolean check_register = userEJB.register(request.getParameter("username"), request.getParameter("password"), request.getParameter("email"), request.getParameter("name"), request.getParameter("card_number"));
                if (check_register) {
                    request.getSession().setAttribute("result", "Utilizador registado com sucesso!");
                    request.getRequestDispatcher("Login.jsp").forward(request, response);
                } else {
                    request.getSession().setAttribute("result", "Registo falhou. Email já em uso.");
                    request.getRequestDispatcher("Login.jsp").forward(request, response);
                }
            } else if (action.toLowerCase().equals("signin")) {
                User userLogged = userEJB.login(request.getParameter("email"), request.getParameter("password"));
                if (userLogged == null) {
                    request.getSession().setAttribute("result", "Email ou password incorreto(s).");
                    request.getRequestDispatcher("Login.jsp").forward(request, response);
                } else {
                    request.getSession().setAttribute("user", userLogged);

                    request.getSession().setAttribute("allActionContent", contentEJB.searchSuggestedContents("Action"));
                    request.getSession().setAttribute("allComedyContent", contentEJB.searchSuggestedContents("Comedy"));
                    request.getSession().setAttribute("allFantasyContent", contentEJB.searchSuggestedContents("Fantasy"));
                    request.getSession().setAttribute("allRomanceContent", contentEJB.searchSuggestedContents("Romance"));
                    request.getSession().setAttribute("allHistoryContent", contentEJB.searchSuggestedContents("History"));
                    request.getSession().setAttribute("allThrillerContent", contentEJB.searchSuggestedContents("Thriller"));
                    //emailsEJB.mails();
                    if (userLogged.getPrivilege().equals("Manager")) {

                        request.getRequestDispatcher("ManagerHomepage.jsp").forward(request, response);


                    } else {
                        request.getRequestDispatcher("Homepage.jsp").forward(request, response);
                    }
                }
            }


        }
        /*if (action.equals("populate")) {
            contentEJB.populate();
            out.println("POPULATE SUCCESSFUL");

        } */else {
            User userLogged = (User) request.getSession().getAttribute("user");

            request.getSession().setAttribute("allActionContent", contentEJB.searchSuggestedContents("Action"));
            request.getSession().setAttribute("allComedyContent", contentEJB.searchSuggestedContents("Comedy"));
            request.getSession().setAttribute("allFantasyContent", contentEJB.searchSuggestedContents("Fantasy"));
            request.getSession().setAttribute("allRomanceContent", contentEJB.searchSuggestedContents("Romance"));
            request.getSession().setAttribute("allHistoryContent", contentEJB.searchSuggestedContents("History"));
            request.getSession().setAttribute("allThrillerContent", contentEJB.searchSuggestedContents("Thriller"));
            request.getSession().setAttribute("allContent", contentEJB.searchAllContent());


            if (action == null) {
                request.getSession().setAttribute("allActionContent", contentEJB.searchSuggestedContents("Action"));
                request.getSession().setAttribute("allComedyContent", contentEJB.searchSuggestedContents("Comedy"));
                request.getSession().setAttribute("allFantasyContent", contentEJB.searchSuggestedContents("Fantasy"));
                request.getSession().setAttribute("allRomanceContent", contentEJB.searchSuggestedContents("Romance"));
                request.getSession().setAttribute("allHistoryContent", contentEJB.searchSuggestedContents("History"));
                request.getSession().setAttribute("allThrillerContent", contentEJB.searchSuggestedContents("Thriller"));
                if (userLogged.getPrivilege().equals("Manager")) {

                    request.getRequestDispatcher("ManagerHomepage.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("Homepage.jsp").forward(request, response);
                }
            } else if (action.equals("editemail")) {
                request.getRequestDispatcher("EditEmail.jsp").forward(request, response);
            } else if (action.equals("editname")) {
                request.getRequestDispatcher("EditName.jsp").forward(request, response);
            } else if (action.equals("editusername")) {
                request.getRequestDispatcher("EditUsername.jsp").forward(request, response);
            } else if (action.equals("editpassword")) {
                request.getRequestDispatcher("EditPassword.jsp").forward(request, response);
            } else if (action.equals("editcardnumber")) {
                request.getRequestDispatcher("EditCardNumber.jsp").forward(request, response);
            } else if (action.equals("homepage")) {
                request.getSession().setAttribute("allActionContent", contentEJB.searchSuggestedContents("Action"));
                request.getSession().setAttribute("allComedyContent", contentEJB.searchSuggestedContents("Comedy"));
                request.getSession().setAttribute("allFantasyContent", contentEJB.searchSuggestedContents("Fantasy"));
                request.getSession().setAttribute("allRomanceContent", contentEJB.searchSuggestedContents("Romance"));
                request.getSession().setAttribute("allHistoryContent", contentEJB.searchSuggestedContents("History"));
                request.getSession().setAttribute("allThrillerContent", contentEJB.searchSuggestedContents("Thriller"));
                if (userLogged.getPrivilege().equals("Manager"))
                    request.getRequestDispatcher("ManagerHomepage.jsp").forward(request, response);
                else
                    request.getRequestDispatcher("Homepage.jsp").forward(request, response);
            } else if (action.equals("changeemail")) {
                User changeUser = userEJB.changeEmail(userLogged, request.getParameter("email"));
                if (changeUser == null) {
                    request.getSession().setAttribute("result", "Error in change email.");
                } else {
                    request.getSession().setAttribute("user", changeUser);
                    request.getSession().setAttribute("result", "Email is changed with success!");
                }
                request.getSession().setAttribute("allActionContent", contentEJB.searchSuggestedContents("Action"));
                request.getSession().setAttribute("allComedyContent", contentEJB.searchSuggestedContents("Comedy"));
                request.getSession().setAttribute("allFantasyContent", contentEJB.searchSuggestedContents("Fantasy"));
                request.getSession().setAttribute("allRomanceContent", contentEJB.searchSuggestedContents("Romance"));
                request.getSession().setAttribute("allHistoryContent", contentEJB.searchSuggestedContents("History"));
                request.getSession().setAttribute("allThrillerContent", contentEJB.searchSuggestedContents("Thriller"));
                if (userLogged.getPrivilege().equals("Manager")) {

                    request.getRequestDispatcher("ManagerHomepage.jsp").forward(request, response);

                } else {
                    request.getRequestDispatcher("Homepage.jsp").forward(request, response);
                }
            } else if (action.equals("changeusername")) {
                User changeUser = userEJB.changeUsername(userLogged, request.getParameter("username"));
                if (changeUser == null) {
                    request.getSession().setAttribute("result", "Error in change username.");
                } else {
                    request.getSession().setAttribute("user", changeUser);
                    request.getSession().setAttribute("result", "Username is changed with success!");
                }
                request.getSession().setAttribute("allActionContent", contentEJB.searchSuggestedContents("Action"));
                request.getSession().setAttribute("allComedyContent", contentEJB.searchSuggestedContents("Comedy"));
                request.getSession().setAttribute("allFantasyContent", contentEJB.searchSuggestedContents("Fantasy"));
                request.getSession().setAttribute("allRomanceContent", contentEJB.searchSuggestedContents("Romance"));
                request.getSession().setAttribute("allHistoryContent", contentEJB.searchSuggestedContents("History"));
                request.getSession().setAttribute("allThrillerContent", contentEJB.searchSuggestedContents("Thriller"));
                if (userLogged.getPrivilege().equals("Manager")) {
                    request.getRequestDispatcher("ManagerHomepage.jsp").forward(request, response);

                } else {
                    request.getRequestDispatcher("Homepage.jsp").forward(request, response);
                }
            } else if (action.equals("changename")) {
                User changeUser = userEJB.changeName(userLogged, request.getParameter("name"));
                if (changeUser == null) {
                    request.getSession().setAttribute("result", "Error in change name.");

                } else {
                    request.getSession().setAttribute("user", changeUser);
                    request.getSession().setAttribute("result", "Name is changed with success!");
                }
                request.getSession().setAttribute("allActionContent", contentEJB.searchSuggestedContents("Action"));
                request.getSession().setAttribute("allComedyContent", contentEJB.searchSuggestedContents("Comedy"));
                request.getSession().setAttribute("allFantasyContent", contentEJB.searchSuggestedContents("Fantasy"));
                request.getSession().setAttribute("allRomanceContent", contentEJB.searchSuggestedContents("Romance"));
                request.getSession().setAttribute("allHistoryContent", contentEJB.searchSuggestedContents("History"));
                request.getSession().setAttribute("allThrillerContent", contentEJB.searchSuggestedContents("Thriller"));
                if (userLogged.getPrivilege().equals("Manager")) {
                    request.getRequestDispatcher("ManagerHomepage.jsp").forward(request, response);

                } else {
                    request.getRequestDispatcher("Homepage.jsp").forward(request, response);
                }


            } else if (action.equals("changepassword")) {
                User changeUser = userEJB.changePassword(userLogged, request.getParameter("password"));
                if (changeUser == null) {
                    request.getSession().setAttribute("result", "Error in change password.");

                } else {
                    request.getSession().setAttribute("user", changeUser);
                    request.getSession().setAttribute("result", "Password is changed with success!");
                }
                request.getSession().setAttribute("allActionContent", contentEJB.searchSuggestedContents("Action"));
                request.getSession().setAttribute("allComedyContent", contentEJB.searchSuggestedContents("Comedy"));
                request.getSession().setAttribute("allFantasyContent", contentEJB.searchSuggestedContents("Fantasy"));
                request.getSession().setAttribute("allRomanceContent", contentEJB.searchSuggestedContents("Romance"));
                request.getSession().setAttribute("allHistoryContent", contentEJB.searchSuggestedContents("History"));
                request.getSession().setAttribute("allThrillerContent", contentEJB.searchSuggestedContents("Thriller"));
                if (userLogged.getPrivilege().equals("Manager")) {
                    request.getRequestDispatcher("ManagerHomepage.jsp").forward(request, response);

                } else {
                    request.getRequestDispatcher("Homepage.jsp").forward(request, response);
                }
            } else if (action.equals("changecard")) {
                User changeUser = userEJB.changeCreditCardNumber(userLogged, request.getParameter("credit_card_number"));
                if (changeUser == null) {
                    request.getSession().setAttribute("result", "Error in change card number.");

                } else {
                    request.getSession().setAttribute("user", changeUser);
                    request.getSession().setAttribute("result", "Card Number is changed with success!");
                }
                request.getSession().setAttribute("allActionContent", contentEJB.searchSuggestedContents("Action"));
                request.getSession().setAttribute("allComedyContent", contentEJB.searchSuggestedContents("Comedy"));
                request.getSession().setAttribute("allFantasyContent", contentEJB.searchSuggestedContents("Fantasy"));
                request.getSession().setAttribute("allRomanceContent", contentEJB.searchSuggestedContents("Romance"));
                request.getSession().setAttribute("allHistoryContent", contentEJB.searchSuggestedContents("History"));
                request.getSession().setAttribute("allThrillerContent", contentEJB.searchSuggestedContents("Thriller"));
                if (userLogged.getPrivilege().equals("Manager")) {
                    request.getRequestDispatcher("ManagerHomepage.jsp").forward(request, response);

                } else {
                    request.getRequestDispatcher("Homepage.jsp").forward(request, response);
                }

            } else if (action.equals("logout")) {
                request.getSession().removeAttribute("user");
                HttpSession session = request.getSession(false);
                if (session != null) {
                    request.getSession().invalidate();
                }
                request.getSession().setAttribute("result", "Logout well successfull.");
                request.getRequestDispatcher("Welcome.jsp").forward(request, response);
            } else if (action.equals("deleteUser")) {
                boolean result = userEJB.deleteUser(userLogged);
                if (result) {
                    request.getSession().removeAttribute("user");
                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        request.getSession().invalidate();
                    }
                    request.getSession().setAttribute("result", "User erased with success!");
                } else {
                    request.getSession().setAttribute("result", "Error in delete user.");
                }
                request.getRequestDispatcher("Welcome.jsp").forward(request, response);
            } else if (action.equals("managermode")) {

                User changeUser = userEJB.changeToManager(userLogged, "Manager");
                request.getSession().setAttribute("allActionContent", contentEJB.searchSuggestedContents("Action"));
                request.getSession().setAttribute("allComedyContent", contentEJB.searchSuggestedContents("Comedy"));
                request.getSession().setAttribute("allFantasyContent", contentEJB.searchSuggestedContents("Fantasy"));
                request.getSession().setAttribute("allRomanceContent", contentEJB.searchSuggestedContents("Romance"));
                request.getSession().setAttribute("allHistoryContent", contentEJB.searchSuggestedContents("History"));
                request.getSession().setAttribute("allThrillerContent", contentEJB.searchSuggestedContents("Thriller"));
                if (changeUser == null) {
                    request.getSession().setAttribute("result", "Error in change privilege.");

                } else {
                    request.getSession().setAttribute("user", changeUser);
                    request.getSession().setAttribute("allActionContent", contentEJB.searchSuggestedContents("Action"));
                    request.getSession().setAttribute("allComedyContent", contentEJB.searchSuggestedContents("Comedy"));
                    request.getSession().setAttribute("allFantasyContent", contentEJB.searchSuggestedContents("Fantasy"));
                    request.getSession().setAttribute("allRomanceContent", contentEJB.searchSuggestedContents("Romance"));
                    request.getSession().setAttribute("allHistoryContent", contentEJB.searchSuggestedContents("History"));
                    request.getSession().setAttribute("allThrillerContent", contentEJB.searchSuggestedContents("Thriller"));
                    request.getSession().setAttribute("result", "Privilege changed with success!");
                    request.getRequestDispatcher("ManagerHomepage.jsp").forward(request, response);

                }
            } else if (action.equals("usermode")) {
                User changedUser = userEJB.changeToUser(userLogged, "Normal");
                if (changedUser == null) {
                    request.getSession().setAttribute("result", "Error in change privilege.");

                } else {
                    request.getSession().setAttribute("user", changedUser);
                    request.getSession().setAttribute("allActionContent", contentEJB.searchSuggestedContents("Action"));
                    request.getSession().setAttribute("allComedyContent", contentEJB.searchSuggestedContents("Comedy"));
                    request.getSession().setAttribute("allFantasyContent", contentEJB.searchSuggestedContents("Fantasy"));
                    request.getSession().setAttribute("allRomanceContent", contentEJB.searchSuggestedContents("Romance"));
                    request.getSession().setAttribute("allHistoryContent", contentEJB.searchSuggestedContents("History"));
                    request.getSession().setAttribute("allThrillerContent", contentEJB.searchSuggestedContents("Thriller"));
                    request.getSession().setAttribute("result", "Privilege changed with success!");
                    request.getRequestDispatcher("Homepage.jsp").forward(request, response);

                }
            } else if (action.equals("insertcontent")) {
                request.getRequestDispatcher("InsertContent.jsp").forward(request, response);
            } else if (action.equals("createcontent")) {
                String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                Part part = request.getPart("multimedia");
                String directory = UPLOAD_DIRECTORY + File.separator + part.getSubmittedFileName();
                try {
                    part.write(directory);
                } catch (FileAlreadyExistsException exist) {
                    Random r = new Random();
                    int low = 1;
                    int high = 1000;
                    int result = r.nextInt(high - low) + low;
                    String[] auxfilename = part.getSubmittedFileName().split("\\.");
                    directory = UPLOAD_DIRECTORY + File.separator + auxfilename[0] + Integer.toString(result) + "." + auxfilename[1];
                    part.write(directory);
                }
                boolean check_insert = contentEJB.insertContent(request.getParameter("title"), request.getParameter("director"), request.getParameter("category"), Integer.parseInt(request.getParameter("year")), directory);

                if (check_insert) {
                    request.getSession().setAttribute("result", "Content criado com sucesso!");
                } else {
                    request.getSession().setAttribute("result", "Erro ao criar content!");
                }
                request.getSession().setAttribute("allActionContent", contentEJB.searchSuggestedContents("Action"));
                request.getSession().setAttribute("allComedyContent", contentEJB.searchSuggestedContents("Comedy"));
                request.getSession().setAttribute("allFantasyContent", contentEJB.searchSuggestedContents("Fantasy"));
                request.getSession().setAttribute("allRomanceContent", contentEJB.searchSuggestedContents("Romance"));
                request.getSession().setAttribute("allHistoryContent", contentEJB.searchSuggestedContents("History"));
                request.getSession().setAttribute("allThrillerContent", contentEJB.searchSuggestedContents("Thriller"));
                request.getRequestDispatcher("ManagerHomepage.jsp").forward(request, response);

            } else if (action.equals("editcontenttitle")) {
                request.getRequestDispatcher("EditTitle.jsp").forward(request, response);
            } else if (action.equals("changetitle")) {
                List<Content> contentList = contentEJB.searchAllContent();
                Content content1 = null;
                for (Content content : contentList) {


                    if (content.getTitle().equals(request.getParameter("contentname"))) {
                        content1 = contentEJB.changeTitle(content, request.getParameter("title"));
                        break;
                    }
                }
                if (content1 != null) {
                    request.getSession().setAttribute("allActionContent", contentEJB.searchSuggestedContents("Action"));
                    request.getSession().setAttribute("allComedyContent", contentEJB.searchSuggestedContents("Comedy"));
                    request.getSession().setAttribute("allFantasyContent", contentEJB.searchSuggestedContents("Fantasy"));
                    request.getSession().setAttribute("allRomanceContent", contentEJB.searchSuggestedContents("Romance"));
                    request.getSession().setAttribute("allThrillerContent", contentEJB.searchSuggestedContents("Thriller"));
                    request.getRequestDispatcher("ManagerHomepage.jsp").forward(request, response);
                }
            } else if (action.equals("changedirector")) {
                List<Content> contentList = contentEJB.searchAllContent();
                Content content1 = null;
                for (Content content : contentList) {
                    if (content.getTitle().equals(request.getParameter("contentname"))) {
                        content1 = contentEJB.changeDirector(content, request.getParameter("director"));
                        break;
                    }
                }
                if (content1 != null) {
                    request.getSession().setAttribute("allActionContent", contentEJB.searchSuggestedContents("Action"));
                    request.getSession().setAttribute("allComedyContent", contentEJB.searchSuggestedContents("Comedy"));
                    request.getSession().setAttribute("allFantasyContent", contentEJB.searchSuggestedContents("Fantasy"));
                    request.getSession().setAttribute("allRomanceContent", contentEJB.searchSuggestedContents("Romance"));
                    request.getSession().setAttribute("allThrillerContent", contentEJB.searchSuggestedContents("Thriller"));
                    request.getRequestDispatcher("ManagerHomepage.jsp").forward(request, response);

                }


            } else if (action.equals("changecategory")) {
                List<Content> contentList = contentEJB.searchAllContent();
                Content content1 = null;
                for (Content content : contentList) {
                    if (content.getTitle().equals(request.getParameter("contentname"))) {
                        content1 = contentEJB.changeCategory(content, request.getParameter("category"));
                        break;
                    }
                }
                if (content1 != null) {
                    request.getSession().setAttribute("allActionContent", contentEJB.searchSuggestedContents("Action"));
                    request.getSession().setAttribute("allComedyContent", contentEJB.searchSuggestedContents("Comedy"));
                    request.getSession().setAttribute("allFantasyContent", contentEJB.searchSuggestedContents("Fantasy"));
                    request.getSession().setAttribute("allRomanceContent", contentEJB.searchSuggestedContents("Romance"));
                    request.getSession().setAttribute("allThrillerContent", contentEJB.searchSuggestedContents("Thriller"));
                    request.getRequestDispatcher("ManagerHomepage.jsp").forward(request, response);
                }

            } else if (action.equals("editcontentdirector")) {
                request.getRequestDispatcher("EditDirector.jsp").forward(request, response);
            } else if (action.equals("editcontentcategory")) {
                request.getRequestDispatcher("EditCategory.jsp").forward(request, response);
            } else if (action.equals("editcontentyear")) {
                request.getRequestDispatcher("EditYear.jsp").forward(request, response);
            } else if (action.equals("changeyear")) {
                List<Content> contentList = contentEJB.searchAllContent();
                Content content1 = null;
                for (Content content : contentList) {
                    if (content.getTitle().equals(request.getParameter("contentname"))) {
                        content1 = contentEJB.changeYear(content, Integer.parseInt(request.getParameter("year")));
                        break;
                    }
                }
                if (content1 != null) {
                    request.getSession().setAttribute("allActionContent", contentEJB.searchSuggestedContents("Action"));
                    request.getSession().setAttribute("allComedyContent", contentEJB.searchSuggestedContents("Comedy"));
                    request.getSession().setAttribute("allFantasyContent", contentEJB.searchSuggestedContents("Fantasy"));
                    request.getSession().setAttribute("allRomanceContent", contentEJB.searchSuggestedContents("Romance"));
                    request.getSession().setAttribute("allThrillerContent", contentEJB.searchSuggestedContents("Thriller"));
                    request.getRequestDispatcher("ManagerHomepage.jsp").forward(request, response);
                }


            } else if (action.equals("deletecontent")) {
                request.getRequestDispatcher("DeleteContent.jsp").forward(request, response);
            } else if (action.equals("contentdeleted")) {
                List<Content> list = contentEJB.searchAllContent();
                boolean check = false;
                for (Content c : list) {
                    if (c.getTitle().equals(request.getParameter("contentname"))) {
                        check = contentEJB.deleteContent(c);
                        break;
                    }
                }

                if (check) {
                    request.getSession().setAttribute("allActionContent", contentEJB.searchSuggestedContents("Action"));
                    request.getSession().setAttribute("allComedyContent", contentEJB.searchSuggestedContents("Comedy"));
                    request.getSession().setAttribute("allFantasyContent", contentEJB.searchSuggestedContents("Fantasy"));
                    request.getSession().setAttribute("allRomanceContent", contentEJB.searchSuggestedContents("Romance"));
                    request.getSession().setAttribute("allThrillerContent", contentEJB.searchSuggestedContents("Thriller"));
                    request.getRequestDispatcher("ManagerHomepage.jsp").forward(request, response);
                }

            } else if (action.equals("searchallcontent")) {
                List<Content> allContent = contentEJB.searchAllContent();
                if (!allContent.isEmpty()) {
                    request.getSession().setAttribute("allContent", allContent);
                    request.getRequestDispatcher("SearchAllContent.jsp").forward(request, response);
                }
            } else if (action.equals("searchbydirector")) {
                request.getRequestDispatcher("SearchByDirectorFilter.jsp").forward(request, response);
            } else if (action.equals("searchbycategory")) {
                request.getRequestDispatcher("SearchByCategoryFilters.jsp").forward(request, response);
            } else if (action.equals("searchbyyears")) {
                request.getRequestDispatcher("SearchByYearsFilter.jsp").forward(request, response);
            } else if (action.equals("searchcontentbydirector")) {
                List<Content> list = contentEJB.searchByDirector(request.getParameter("director"));
                if (!list.isEmpty()) {
                    request.getSession().setAttribute("searchByDirector", list);
                    request.getRequestDispatcher("SearchByDirector.jsp").forward(request, response);
                }
            } else if (action.equals("searchcontentbycategory")) {
                List<Content> list = contentEJB.searchByCategory(request.getParameter("category"));
                if (!list.isEmpty()) {
                    request.getSession().setAttribute("searchByCategory", list);
                    request.getRequestDispatcher("SearchByCategory.jsp").forward(request, response);
                }
            } else if (action.equals("searchcontentbyyears")) {
                List<Content> list = contentEJB.searchByYears(Integer.parseInt(request.getParameter("year1")), Integer.parseInt(request.getParameter("year2")));
                if (!list.isEmpty()) {
                    request.getSession().setAttribute("searchByYears", list);
                    request.getRequestDispatcher("SearchByYears.jsp").forward(request, response);
                }
            } else if (action.equals("addwatchlist")) {
                request.getRequestDispatcher("AddWatchList.jsp").forward(request, response);
            } else if (action.equals("addtowatchlist")) {
                String title = request.getParameter("addcontent");
                boolean add = false;
                List<Content> allcontent = contentEJB.searchAllContent();

                for (Content c : allcontent) {

                    if (c.getTitle().equals(title)) {
                        add = userEJB.followContent(userLogged, c);
                        break;
                    }
                }

                if (add) {
                    request.getRequestDispatcher("AddWatchList.jsp").forward(request, response);

                }
            } else if (action.equals("removewatchlist")) {
                request.getSession().setAttribute("watchlist",contentEJB.showWatchList(userLogged));

                request.getRequestDispatcher("DeleteFromWatchList.jsp").forward(request, response);

            } else if (action.equals("deletefromwatchlist")) {


                String title = request.getParameter("deletefromlist");
                boolean add = false;
                List<Content> allcontent = contentEJB.searchAllContent();

                for (Content c : allcontent) {
                    if (c.getTitle().equals(title)) {
                        add = userEJB.unfollowContent(userLogged, c);
                        break;
                    }
                }

                if(add){
                    request.getSession().setAttribute("watchlist",contentEJB.showWatchList(userLogged));
                    request.getRequestDispatcher("DeleteFromWatchList.jsp").forward(request, response);

                }



            } else if (action.equals("viewwatchlist")) {
                List<Content> watchlist = contentEJB.showWatchList(userLogged);

                request.getSession().setAttribute("watchlist", watchlist);
                request.getRequestDispatcher("ViewWatchList.jsp").forward(request, response);


            } else if (action.equals("editfile")) {
                request.getRequestDispatcher("EditFilePath.jsp").forward(request, response);
            } else if (action.equals("changefile")) {
                String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();

                Part part = request.getPart("multimedia");
                String directory = UPLOAD_DIRECTORY + File.separator + part.getSubmittedFileName();
                try {
                    part.write(directory);
                } catch (FileAlreadyExistsException exist) {
                    Random r = new Random();
                    int low = 1;
                    int high = 1000;
                    int result = r.nextInt(high - low) + low;
                    String[] auxfilename = part.getSubmittedFileName().split("\\.");
                    directory = UPLOAD_DIRECTORY + File.separator + auxfilename[0] + Integer.toString(result) + "." + auxfilename[1];
                    part.write(directory);
                }
                List<Content> contentList = contentEJB.searchAllContent();
                Content content1 = null;
                for (Content content : contentList) {
                    if (content.getTitle().equals(request.getParameter("contentname"))) {
                        content1 = contentEJB.changeFilePath(content, directory);
                        break;
                    }
                }
                if (content1 != null) {
                    request.getSession().setAttribute("allActionContent", contentEJB.searchSuggestedContents("Action"));
                    request.getSession().setAttribute("allComedyContent", contentEJB.searchSuggestedContents("Comedy"));
                    request.getSession().setAttribute("allFantasyContent", contentEJB.searchSuggestedContents("Fantasy"));
                    request.getSession().setAttribute("allRomanceContent", contentEJB.searchSuggestedContents("Romance"));
                    request.getSession().setAttribute("allThrillerContent", contentEJB.searchSuggestedContents("Thriller"));
                    request.getRequestDispatcher("ManagerHomepage.jsp").forward(request, response);
                }

            }
        }
    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


}
