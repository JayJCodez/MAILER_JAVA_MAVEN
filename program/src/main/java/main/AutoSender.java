package main;

import java.util.Scanner;

/**
 * AutoSender
 */
public class AutoSender {

    public void AutoSender(){

        String message = "Welcome to AUTOGMAILER";
        String welcome = "Welcome Message";

        Scanner scanner = new Scanner(System.in);

        System.out.println(""+message+"\nWhat's your name?");

        String username = scanner.nextLine();

        Scanner scanner2 = new Scanner(System.in);
        String answer = "Hi "+username+ " What's the subject of the email?";


        String subject = scanner2.nextLine();

        System.out.println("What would you like to send?");

        Scanner scanner3 = new Scanner(System.in);

        String content = scanner3.nextLine();


        

        

    }
}