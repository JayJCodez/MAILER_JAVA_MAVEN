package main;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.*;
import java.io.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.apache.commons.codec.binary.Base64;
import com.google.api.client.util.Sets;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Draft;
import com.google.api.services.gmail.model.Message;

public class Main {
  
  private static String subjectmain;

  private static String contentmain;

  private static final String credentials = "program/src/main/resources/client_secret_143.json";
  Gmail service;
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

  
public Main () throws Exception {


  final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
  
 service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, JSON_FACTORY))
   .setApplicationName("Test Mailer")
   .build();


}

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, JsonFactory JSON_FACTORY) throws IOException {
    // Load client secrets.
    InputStream in = new FileInputStream(credentials); 

    if (in == null) {
      throw new FileNotFoundException("Resource not found: " + credentials);
    }


  
    GoogleClientSecrets clientSecrets =
        GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, Set.of(GmailScopes.GMAIL_SEND))
        .setDataStoreFactory(new FileDataStoreFactory(Paths.get("tokens").toFile()))
        .setAccessType("offline")
        .build();
        
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    // Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    //returns an authorized Credential object.
    return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
  }



    public Message sendEmail(String subject, String message, String sender, String reciever) throws GeneralSecurityException, IOException, AddressException, MessagingException{


      // String welcome = "Welcome to AUTOGMAILER\n";
      // // String welcome = "Welcome Message";


      // System.out.println(""+welcome+"What's your name?");


      // Scanner scanner = new Scanner(System.in);

      // String username = scanner.nextLine();

      
      // Scanner scanner2 = new Scanner(System.in);

      
      // String answer = "Hi "+username+ " What's the subject of the email?";
     
      // System.out.println(answer);

      // subjectmain = scanner2.nextLine();

      

      // System.out.println("What would you like to send?");

      // Scanner scanner3 = new Scanner(System.in);

      // contentmain = scanner3.nextLine();
    

    

      String email_p = "jesseokuji123@gmail.com";
      String content = "Trial of GMAIL API";
      // String reciever = "jesseokuji123@gmail.com";


      // Create the email content
    String messageSubject = "Test message";
    String bodyText = "lorem ipsum.";

    // Encode as MIME message
    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);
    MimeMessage email = new MimeMessage(session);
    email.setFrom(new InternetAddress(sender));
    email.addRecipient(javax.mail.Message.RecipientType.TO,
        new InternetAddress(reciever));
    email.setSubject(subject);
    email.setText(message);

    // Encode and wrap the MIME message into a gmail message
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    email.writeTo(buffer);
    byte[] rawMessageBytes = buffer.toByteArray();
    String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
    Message msg = new Message();
    msg.setRaw(encodedEmail);

    try {
      // Create send message
      msg = service.users().messages().send("me", msg).execute();
      System.out.println("Message id: " + msg.getId());
      System.out.println(msg.toPrettyString());
      return msg;
    } catch (GoogleJsonResponseException e) {
      // TODO(developer) - handle error appropriately
      GoogleJsonError error = e.getDetails();
      if (error.getCode() == 403) {
        System.err.println("Unable to send message: " + e.getDetails());
      } else {
        throw e;
      }
    }
    return msg;
    

    }

  
}
