import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by User on 4/4/2016.
 * Message 1: key is 12
 * Input parameters: input filename, key range for testing (the smallest key, the biggest key)
 */
public class CeasarNoKey {
    public static boolean checkInput(String[] ins) {
        try {
            //Check the number of parameters
            if(ins.length == 3){

                //Check if the input files is found
                if (new File(ins[0]).exists()) {

                    //Check if the 2 bounds of the key range are integers & the left bound is smaller than the right one
                    if(Integer.parseInt(ins[1]) > 0 && Integer.parseInt(ins[2]) > Integer.parseInt(ins[1]))
                        return true;
                    else
                        System.out.println("ERROR: Invalid key range.");
                }else
                    System.out.println("ERROR: File not found.");
            }else
                System.out.println("ERROR: The number of arguments is invalid.");
        } catch (Exception e) {
            System.out.println(e);
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

            outTxt = inTxt.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return outTxt;
    }

    public static void decrypt(char[] alphabet, char[] inTxt, int beginKey, int endKey){
        char[] outTxt = new char[inTxt.length];

        //Traverse the key range and repeat the Ceasar decryption algorithm for each key
        for(int key = beginKey; key < endKey + 1; key++){
            for(int i = 0; i < inTxt.length; i++){
                for(int j = 0; j < alphabet.length; j++){
                    if(alphabet[j] == inTxt[i]){
                        outTxt[i] = (alphabet[Math.floorMod((j - key) , 50)]);
                    }
                }
            }
            System.out.println("\nMESSAGE WITH KEY " + key + ": \n" + new String(outTxt));
        }
    }

    public static void main(String[] args) {
        char [] alphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W',
                'X','Y','Z',' ','.',',',':',';','(',')','-','!','?','$','\'','"','\n','0','1','2','3','4','5','6','7','8','9' };
        char [] inChars;

        if(checkInput(args)){
            inChars = readFile(args[0]).toCharArray();
            if (inChars != null)
                decrypt(alphabet, inChars, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            else
                System.out.println("ERROR: File reading is unsuccessful");
        }else
            return;
    }

}
