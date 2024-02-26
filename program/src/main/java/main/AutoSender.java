package main;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Scanner;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

/**
 * AutoSender
 */
public class AutoSender {

    public void AutoSending() throws AddressException, GeneralSecurityException, IOException, MessagingException, Exception{

        String message = "Welcome to AUTOGMAILER";
        String welcome = "Welcome Message";

       

        System.out.println(""+message+"\nWhat's your name?");
        
        Scanner scanner = new Scanner(System.in);

        String username = scanner.nextLine();






        System.out.println("What email would you like to send from? Please note: For now only GMAIL is supported currently. Thanks!!");

        Scanner scanner1 = new Scanner(System.in);
        
        String senderEmail = scanner1.nextLine();



        System.out.println("Who would you like to send this email to?");

        Scanner scannerr = new Scanner(System.in);

        String recieverMail = scannerr.nextLine();



        
        String answer = "Hi "+username+ " What's the subject of the email?";

        System.out.println(answer);

        Scanner scanner2 = new Scanner(System.in);

        String subject = scanner2.nextLine();



        System.out.println("What would you like to send?");

        Scanner scanner3 = new Scanner(System.in);

        String content = scanner3.nextLine();


        new gmail_setup().sendEmail(subject, content, senderEmail, recieverMail);


    }

    public static void main(String[] args) throws AddressException, GeneralSecurityException, IOException, MessagingException, Exception {
        new AutoSender().AutoSending();
    }
}