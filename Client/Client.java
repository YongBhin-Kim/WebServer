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
    public static void main(String argv[]) throws Exception { 

        Socket clientSocket = new Socket("localhost", 10000); // Client 포트 : 10000
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); // 입력값 받기
        String[] PT = stringToHex(inFromUser.readLine()).split(" "); 

        int[] PT2 = new int[PT.length];
        for (int i = 0; i < PT.length; i++) {
            PT2[i] = Integer.parseInt(PT[i]); // string -> int
        } 
        Client h = new Client();
        int[] out = h.Enc(PT2); // int 배열 JNI 이용하여 C로 넘겨 암호화
        StringBuilder inttoSB = new StringBuilder();
        for (int i = 0; i < 128; i++) {
            inttoSB.append(String.valueOf(out[i])); // int배열을 stringbuilder으로 합체
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
        int[] out2 = j.Dec(CT2); // int 배열 JNI 이용하여 C로 넘겨 복호화
        StringBuilder hexStr = new StringBuilder();
        for (int i = 0; i < 128; i++) {
            hexStr.append(String.valueOf(out2[i]) + " "); // int array : [val] [val] [val] -> string : "[val] [val] [val]"
        }
        String tmp = hexStr.toString(); // hex (string)

        StringBuilder realhex = new StringBuilder();
        String[] tmp2 = tmp.split(" "); // hex (string)
        for (int i = 0; i < 128; i++) {
            realhex.append( (char) Integer.parseInt(tmp2[i], 16));
        }
        String decrypted = realhex.toString();
        System.out.println("[복호화]\n" + decrypted);
        clientSocket.close(); 
    } 
    static {
        System.loadLibrary("BlockCipher");
    }
} 

