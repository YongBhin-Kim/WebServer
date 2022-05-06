package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.*; 
import java.net.*;

@SpringBootApplication
@RestController
public class Application {
	public static void main(String[] args) throws Exception{
		SpringApplication.run(Application.class, args);

		String clientSentence, serverSentence; 
		ServerSocket welcomeSocket = new ServerSocket(10000);

		while(true){
			Socket connectionSocket = welcomeSocket.accept(); 
			System.out.println("client-server 연결완료\n");
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream())); 
			DataOutputStream  outToClient = 
			new DataOutputStream(connectionSocket.getOutputStream()); 
			clientSentence = inFromClient.readLine();
			serverSentence = clientSentence;
				//===============================================
				// 받은 clientSentence를 복호화
				// 보낼 serverSentence를 암호화
				//===============================================
			System.out.println("clientSentence = " + clientSentence);
			outToClient.writeBytes(serverSentence + '\n'); 
		}
	}
}