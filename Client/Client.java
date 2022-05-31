import java.io.*; 
import java.net.*; 
class Client { 

    public native String input(String str);
    public static void main(String argv[]) throws Exception { 

        // Socket clientSocketForSpring = new Socket("localhost", 8080); // Docker Image 다운 시에는 필요 없음.

         // C에서 stdin으로 메시지를 입력받아 암호화한 후 가져와서 Server로 보낸다.
        Client h = new Client();
        String clientSentence = h.input("Input : "); String receivedSentence;
        System.out.println(clientSentence);

        Socket clientSocket = new Socket("localhost", 10000);

        // loop:
        // while(true) {
        // BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); 
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream()); 
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
        // System.out.println("Input : ");
        // clientSentence = inFromUser.readLine();

        outToServer.writeBytes(clientSentence + '\n'); 
        receivedSentence = inFromServer.readLine();
        //===============================================
        // 보낼 clientSentence를 암호화
        // 받은 receivedSentence를 복호화
        //===============================================
        System.out.println("From Server: " + receivedSentence);
        // }
        clientSocket.close(); 
        // clientSocketForSpring.close();
    } 
    static {
        System.loadLibrary("BlockCipher");
    }
} 

