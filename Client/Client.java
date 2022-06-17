import java.io.*; 
import java.net.*; 
class Client { 
    public static String stringToHex(String s) {
        String result = "";
    
        for (int i = 0; i < s.length(); i++) {
          result += String.format("%02X ", (int) s.charAt(i));
        }
    
        return result;
    }
    public native int[] Enc(int[] tmp);
    public native int[] Dec(int[] tmp);
    public native int addInteger(int num1, int num2);
    public native String input(String str);
    // public native String Dec(String str);
    public static void main(String argv[]) throws Exception { 

        // Socket clientSocketForSpring = new Socket("localhost", 8080); // Docker Image 다운 시에는 필요 없음.
        Socket clientSocket = new Socket("localhost", 10000); // Client 포트 : 10000
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        String[] PT = stringToHex(inFromUser.readLine()).split(" "); 

        int[] PT2 = new int[PT.length];
        for (int i = 0; i < PT.length; i++) {
            PT2[i] = Integer.parseInt(PT[i]);
        } // int 배열 JNI를 이용하여 C로 넘기기

        Client h = new Client();
        int[] out = h.Enc(PT2); // out = int[128]
        StringBuilder inttoSB = new StringBuilder();
        for (int i = 0; i < 128; i++) {
            inttoSB.append(String.valueOf(out[i])); // out : String 배열
            inttoSB.append(" ");
        } 
        String clientSentence = inttoSB.toString(); // clientSentenct : String 배열
        /////// 여기부터 /////
        //  C에서 stdin으로 메시지를 입력받아 암호화한 후 가져와서 Server로 보낸다.        

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream()); 
        outToServer.writeBytes(clientSentence + "\n");

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
        String receivedSentence = inFromServer.readLine();
        String[] CT = receivedSentence.split(" ");
        int[] CT2 = new int[CT.length];
        for (int i = 0; i < CT.length; i++) {
            CT2[i] = Integer.parseInt(CT[i]);
        } // PT2 배열 JNI로 넘기기!!


        Client j = new Client();
        // int[] t = new int[PT.length];
        int[] out2 = j.Dec(CT2); // int (사실 hex)
        StringBuilder hexStr = new StringBuilder();
        for (int i = 0; i < 128; i++) {
            hexStr.append(String.valueOf(out2[i]) + " ");
        }
        String tmp = hexStr.toString(); // hex (string)

        StringBuilder realhex = new StringBuilder();
        String[] tmp2 = tmp.split(" "); // hex (string)
        for (int i = 0; i < 128; i++) {
            realhex.append( (char) Integer.parseInt(tmp2[i], 16));
        }
        String decrypted = realhex.toString();
        System.out.println("[복호화]\n" + decrypted);


        // for (int i = 0; i < 128; i++) {

        // }
        // String decryptedSentence = inttoASCII.toString();
        // System.out.println(decryptedSentence);
        // System.out.println("[복호문]");
        // System.out.println(decryptedSentence);
        // out = String
        // for (int i = 0; i < 128; i++) {
        //     inttoSB2.append(String.valueOf(out2[i]));
        //     inttoSB2.append(" ");
        // }
        // String serverSentence = inttoSB2.toString();

        // System.out.println(serverSentence);



        // System.out.println("From Server: " + receivedSentence);
        clientSocket.close(); 

        // //===============================================
        // // 보낼 clientSentence를 암호화
        // // 받은 receivedSentence를 복호화
        // //===============================================
        // // }
        // // clientSocketForSpring.close();

    } 
    static {
        System.loadLibrary("BlockCipher");
    }
} 

