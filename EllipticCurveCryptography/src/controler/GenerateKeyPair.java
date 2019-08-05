package controler;

import java.io.IOException;
import java.security.spec.ECGenParameterSpec;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@WebServlet("/generatekeypair")
public class GenerateKeyPair extends HttpServlet {
	private static final long serialVersionUID = 2L;
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		KeyPair pair = null;
		String name="";
		name = (String)request.getAttribute("name");
		ECGenParameterSpec spec = new ECGenParameterSpec("brainpoolp256r1");
		try {
			KeyPairGenerator g = KeyPairGenerator.getInstance("ECDH", new org.bouncycastle.jce.provider.BouncyCastleProvider());
			g.initialize(spec, new SecureRandom());
			pair = g.generateKeyPair();
		} catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
			System.out.println(e);
		}
		HttpSession session = request.getSession(false);
		//System.out.println(pair.getPublic());
        //System.out.println(pair.getPrivate());
        session.setAttribute(name+"publickey", pair.getPublic());
        session.setAttribute(name+"privatekey", pair.getPrivate());
	}
}