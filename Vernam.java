import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by User on 3/31/2016.
 * Message 5: key is message 2, offset is 0
 * Input parameters: mode, input filename (can be ignored in "generate" mode), key filename, offset/key length
 */
public class Vernam {
    public static boolean checkInput(String[] ins) throws IOException {
        try {
            //If "encrypt" or "decrypt" mode is chosen
            if((ins[0].equals("e") || ins[0].equals("d"))){

                //Check the number of parameters
                if(ins.length == 4){

                    //Check if the input files are found
                    if(new File(ins[1]).exists()
                            && new File(ins[2]).exists()) {

                        //Check if the key is an integer and larger than 0
                        if(Integer.parseInt(ins[3]) >= 0)
                            return true;
                        else
                            System.out.println("ERROR: Invalid key offset.");
                    }else
                        System.out.println("ERROR: File not found.");
                } else
                    System.out.println("ERROR: 4 arguments are needed.");
            }

            //If "generate" mode is chosen
            else if(ins[0].equals("g")){

                //Check the number of parameters
                if(ins.length == 3){

                    //Check if the key is an integer and larger than 0
                    if(Integer.parseInt(ins[2]) >= 0) {

                        //Create the key file based on the key filename
                        File file = new File(ins[1]);
                        file.createNewFile();
                        return true;

                    }else
                        System.out.println("ERROR: Invalid key.");
                }else
                    System.out.println("ERROR: 3 arguments needed.");
            }else
                System.out.println("ERROR: Wrong mode input.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String readFile(String fileName){
        String outTxt = null;
        String line = null;
        StringBuilder inTxt = new StringBuilder();
        try{
            //Read from file
            BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
            while((line = reader.readLine()) != null) {
                inTxt.append(line + "\n");
            }
            reader.close();
            inTxt.trimToSize();

            //Strip out the new line character appending at the end of the text.
            inTxt.deleteCharAt(inTxt.length()-1);

            //Remove > and < in the text if they exists
            if((inTxt.charAt(0) == '>' && inTxt.charAt(inTxt.length()-1) == '<')
                    || (inTxt.charAt(0) == '<' && inTxt.charAt(inTxt.length()-1) == '>')){
                inTxt.deleteCharAt(0);
                inTxt.deleteCharAt(inTxt.length()-1);
            }

            //The final output
            outTxt = inTxt.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return outTxt;
    }

    public static void generate(String fileName, int keySize){
        try{
            //Generate a random alphabet
            ArrayList<Character> alphaLst = new ArrayList<>();
            char [] alphaChars = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W',
                    'X','Y','Z',' ','.',',',':',';','(',')','-','!','?','$','\'','"','\n','0','1','2','3','4','5','6','7','8','9'};
            for(int i = 0; i < alphaChars.length; i++){
                alphaLst.add(alphaChars[i]);
            }
            Collections.shuffle(alphaLst);

            //Write key
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for(int i = 0; i < keySize; i++){
                writer.write(alphaLst.get(i));
            }
            writer.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static int checkKey(String inText, String key, int offset){
        if(offset > key.length()-1){
            System.out.println("ERROR: Offset is out of bounds.");
            return -1;
        }else if(offset < key.length()-1){
            if(inText.length() > key.substring(offset).length()){
                System.out.println("ERROR: Key length is too short.");
                return -1;
            }
            else
                return 1;
        }else{
            if(inText.length() == 1)
                return 2;
            else{
                System.out.println("ERROR: Offset is out of bounds.");
                return -1;
            }
        }
    }

    public static void encryptOrDecrypt(ArrayList<Character> alphabet, char[] inTxt, char[] key, String type){
        char[] outTxt = new char[inTxt.length];

        //Encryption or Decryption
        for(int i = 0; i < inTxt.length; i++){
            if(type.equals("e"))
                outTxt[i] = alphabet.get((alphabet.indexOf(inTxt[i]) + alphabet.indexOf(key[i])) % 50);
            else
                outTxt[i] = alphabet.get(Math.floorMod(alphabet.indexOf(inTxt[i]) - alphabet.indexOf(key[i]),50));
        }

        //Print the output
        if(type.equals("e"))
            System.out.println("\nCIPHER TEXT: \n" + new String(outTxt));
        else
            System.out.println("\nORIGINAL MESSAGE: \n" + new String(outTxt));
    }

    public static void main(String[] args) throws IOException {
        Character [] alphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W',
                'X','Y','Z',' ','.',',',':',';','(',')','-','!','?','$','\'','"','\n','0','1','2','3','4','5','6','7','8','9'};
        ArrayList<Character>alphabetLst = new ArrayList<>(Arrays.asList(alphabet));

        if(checkInput(args)){
            // "encrypt" or "decrypt" mode is chosen
            if(args[0].equals("e") || args[0].equals("d")){
                //Read files
                String inTxt = readFile(args[1]);
                String key = readFile(args[2]);

                //Check the validation of the key
                int keyFlag = checkKey(inTxt, key, Integer.parseInt(args[3]));

                //Encryption or decryption process
                if(inTxt != null && key != null){
                    if(keyFlag == 1)
                        encryptOrDecrypt(alphabetLst,
                                inTxt.toCharArray(),
                                (key.substring(Integer.parseInt(args[3])).toCharArray()),
                                args[0]);
                    else if(keyFlag == 2)
                        encryptOrDecrypt(alphabetLst,inTxt.toCharArray(),key.toCharArray(),args[0]);
                    else
                        return;
                }else
                    System.out.println("ERROR: File reading is unsuccessful");
            }else
                // "generate" mode is chosen
                generate(args[1], Integer.parseInt(args[2]));
        }
    }
}
