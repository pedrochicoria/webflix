package ejb;

import com.sun.mail.smtp.SMTPTransport;
import data.Content;
import data.User;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.jws.soap.SOAPBinding;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.Properties;

@Startup
@Singleton
public class EmailsEJB implements EmailsEJBInterface {
    @PersistenceContext(name = "Emails")

    EntityManager em;
    Logger log = Logger.getLogger("Mails");

    public void mails(){
        Query query = em.createQuery("select u from User u");
        List<User> list  = query.getResultList();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        Date today = c.getTime();

        for(User u: list ){
            User user = em.find(User.class, u.getId());
            if(user.getRegister_date().getDay() == today.getDay() && u.getId()%2 == 0){
                sendEmails(user);

            }

        }

    }


    @Schedule(minute = "*", hour = "*/24", info = "15MinScheduler", persistent = true )
    public void sendEmails(User user){

        User u = em.find(User.class, user.getId());
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtps.auth", "true");
        props.put("mail.smtp.socketFactory.port","25");
        props.put("mail.smtps.quitwait", "false");
        props.setProperty("mail.transport.protocol", "smtp");

        props.put("mail.smtp.port", "25");
        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        Session session = Session.getDefaultInstance(props);

       try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("webflixmoney@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(u.getEmail()));
            message.setSubject("Pagamento da mensalidade ");
            message.setText("A sua mensalidade foi cobrada");
            message.setSentDate(new Date());
            SMTPTransport t = null;
            try { t = (SMTPTransport)session.getTransport("smtps");
            } catch (NoSuchProviderException e) { }
            t.connect("smtp.gmail.com","webflixmoney@gmail.com","pedromenezes@dei");

            t.sendMessage(message, message.getAllRecipients());
            t.close();
            log.warning("Sent email from webflixmoney@gmail.com to "+u.getEmail());
        }catch (MessagingException e) {
            log.severe("Error sending email to "+u.getEmail());
        }


    }

}
