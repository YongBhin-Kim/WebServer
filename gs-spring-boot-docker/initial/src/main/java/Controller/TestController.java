package Controller;

import java.io.*; 
import java.net.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping; 


@Controller
public class TestController {
    
    @RequestMapping("/")
    public String home() throws Exception {
		
		String clientSentence; 
		String capitalizedSentence; 
  
		ServerSocket welcomeSocket = new ServerSocket(8080);
	
		while(true) { 
	
			Socket connectionSocket = welcomeSocket.accept(); 
			System.out.print("client 접속완료\n");
			BufferedReader inFromClient = 
				new BufferedReader(new
				InputStreamReader(connectionSocket.getInputStream())); 
  
  			DataOutputStream  outToClient = 
			new DataOutputStream(connectionSocket.getOutputStream()); 
  
			 clientSentence = inFromClient.readLine(); 
  
			 capitalizedSentence = clientSentence.toUpperCase() + '\n'; 
  
			 outToClient.writeBytes(capitalizedSentence); 
		  } 
	}
}
