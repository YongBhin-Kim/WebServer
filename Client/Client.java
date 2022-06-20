import java.io.*; 
import java.net.*; 
class Client { 
    public static int[] stringToInt(String s) {
        int[] result = new int[128];
        
    
        for (int i = 0; i < s.length(); i++) {
            result[i] = (int) s.charAt(i);
        }
        for (int i = s.length(); i < 128; i++){
            result[i] = 0;
        }
    
        return result;
    }
    public native int[] Enc(int[] tmp);
    public native int[] Dec(int[] tmp);
    public static void main(String argv[]) throws Exception { 

        Socket clientSocket = new Socket("localhost", 10000); // Client 포트 : 10000
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); // 입력값 받기
        int[] PT = stringToInt(inFromUser.readLine());
        Client h = new Client();
        int[] out = h.Enc(PT); // int 배열 JNI 이용하여 C로 넘겨 암호화
        StringBuilder inttoSB = new StringBuilder();
        for (int i = 0; i < 128; i++) {
            inttoSB.append(String.valueOf(out[i])); // int배열을 stringbuilder으로 합체
            if (i < 127)
                inttoSB.append(" ");
        } 
        String clientSentence = inttoSB.toString(); // stringbuilder -> string 변환

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream()); 
        outToServer.writeBytes(clientSentence + "\n"); // send encrypted input string to server

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
        String receivedSentence = inFromServer.readLine(); // received encrypted string sentence from server
        String[] CT = receivedSentence.split(" ");
        int[] CT2 = new int[CT.length]; // string.split() -> int array
        for (int i = 0; i < CT.length; i++) {
            CT2[i] = Integer.parseInt(CT[i]);
        }


        Client j = new Client();
        int[] out2 = j.Dec(CT2); // int 배열 JNI 이용하여 C로 넘겨 복호화 [80] [80] [80] [80]
        StringBuilder inttoStr = new StringBuilder();
        for (int i = 0; i < PT.length; i++) {
            inttoStr.append((char) out2[i]);   // 80 -> a
        }
        String tmp = inttoStr.toString(); // "80 80 80 80 80"
        System.out.println("[복호화]\n" + tmp);
        clientSocket.close(); 
    } 
    static {
        System.loadLibrary("BlockCipher");
    }
} 

