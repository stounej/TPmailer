package mailSelection;


import javax.mail.internet.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.*;


class MailSender {


    public static void send(String from, String pwd, String to, String sub, String msg) {
        //Propriétés
        Properties p = new Properties();

       // ResourceBundle config = ResourceBundle.getBundle("mailSelection.properties.config");
//        String mailServer = config.getString("mailServer");
//        String mailPort = config.getString("port");
//        String auth = config.getString("auth");
//        String security = config.getString("security");


        String mailServer = "smtp.gmail.com";
        String mailPort = "465";
        String auth = "true";
        String security = "ssl";


        p.put("mail.smtp.host", mailServer);
        p.put("mail.smtp.socketFactory.port", mailPort);
        if (!security.equals("none")) {
            p.put("mail.smtp.socketFactory.class", "javax.net." + security + ".SSLSocketFactory");
        }
        p.put("mail.smtp.auth", auth);
        p.put("mail.smtp.port", mailPort);
        //Session
        Session s = Session.getDefaultInstance(p,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, pwd);
                    }
                });
        //composer le message
        try {
            MimeMessage m = new MimeMessage(s);
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            m.setSubject(sub);
            m.setContent(msg,"text/html; charset=utf-8");


            //envoyer le message
            Transport.send(m);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

