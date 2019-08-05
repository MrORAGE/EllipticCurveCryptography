package controler;

import java.io.IOException;
//import java.io.PrintWriter;
import java.io.PrintWriter;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.SecretKey;
//import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/controlerservlet")
public class ControlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		
		SecretKey aliceSeceretKey, bobSeceretKey;
		String plainText="";
		String fullyQualifiedPathOfFile=""; 
		
		
		
		/**
		 * Take file path from user;
		 */
        fullyQualifiedPathOfFile = request.getParameter("fileToUpload");
        
        
        
        /**
         * Call GetPlainText.text() method
         * to get the plain text;
         */
        plainText = GetPlainText.text(fullyQualifiedPathOfFile);
        
        
        
        /**
         * Move to GenerateSession servlet
         * to create the session object for 
         * a request;
         */
		request.getRequestDispatcher("generatesession").include(request, response);
		//System.out.println("Session object created Successfully");
		
		
		
		/**
		 * Move to GenerateKeyPair servlet 
		 * to create public key & private key
		 * for "Alice" ;
		 */
		request.setAttribute("name", "Alice");
		request.getRequestDispatcher("generatekeypair").include(request, response);
		
		
		
		/**
		 * Move to GenerateKeyPair servlet
		 * to create public key & private key
		 * for "Bob" ;
		 */
		request.setAttribute("name", "Bob");
		request.getRequestDispatcher("generatekeypair").include(request, response);

		
		
		/**
		 * print plainText
		 */
		out.println(plainText);
		
		
		
		/**
		 * Alice will create her seceret key
		 * by giving her private key and Bob public key
		 * to the GenerateSeceretKey.getSeceretKey() method ;
		 * 
		 * save Alice seceret key to the session object
		 * in the "aliceSeceretKey" name;
		 */
		aliceSeceretKey = GenerateSeceretKey.getSeceretKey((PrivateKey)request.getSession().getAttribute("Aliceprivatekey"), (PublicKey)request.getSession().getAttribute("Bobpublickey"));
		//System.out.println(aliceSeceretKey);
		//System.out.println("alice seceret key : "+aliceSeceretKey.getEncoded().toString());
		request.getSession().setAttribute("aliceSeceretKey", aliceSeceretKey.getEncoded().toString());
		
		
		
		/**
		 * Bob will create his seceret key
		 * by giving his private key and Alice public key
		 * to the GenerateSeceretKey.getSeceretKey() method ;
		 * 
		 * save Bob seceret key to the session object
		 * in the "bobSeceretKey" name;
		 */
		bobSeceretKey = GenerateSeceretKey.getSeceretKey((PrivateKey)request.getSession().getAttribute("Bobprivatekey"), (PublicKey)request.getSession().getAttribute("Alicepublickey"));
		//System.out.println(bobSeceretKey);
		//System.out.println("bob seceret key : "+bobSeceretKey.getEncoded().toString());
		request.getSession().setAttribute("bobSeceretKey", bobSeceretKey.getEncoded().toString());


		
		/** 
		 * Now "Encryption will be done by 
		 * calling the EncryptClass.encryption() method by giving 
		 * parameter like aliceSeceretKey & plainText ;
		 */
		byte[] b = EncryptClass.encryption(aliceSeceretKey, plainText);
		String cipherText = new String(b);
		Base64.Encoder encoder = Base64.getEncoder();
		String cipherText2 = encoder.encodeToString(cipherText.getBytes());
		out.println(cipherText2);
		
		
		
		/** 
		 * Now "Decryption" will be done by 
		 * calling the DecryptClass.decryption() method by giving 
		 * parameter like bobSeceretKey & ciphertext in the form of 
		 * byte[] b ;
		 */
		String newPlainText = DecryptClass.decryption(bobSeceretKey, b);
		out.println(newPlainText);
		
		
	
	}
}
