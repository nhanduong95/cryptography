import org.omg.CORBA.Environment;
import org.omg.CORBA.SystemException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by User on 3/30/2016.
 * Input parameters: mode, input filename (can be ignored in "generate" mode), key filename
 */
public class Subst {
    public static boolean checkInput(String[] ins) {
        try {
            //If "encrypt" or "decrypt" mode is chosen
            if(ins[0].equals("e") || ins[0].equals("d")){

                //Check the number of parameters
                if(ins.length == 3){

                    //Check if the input file is found
                    if((new File(ins[1]).exists()) && new File(ins[2]).exists())
                        return true;
                    else
                        System.out.println("ERROR: File not found.");
                }else
                    System.out.println("ERROR: The number of arguments is invalid.");

            //If "generate" mode is chosen
            }else if(ins[0].equals("g")){

                //Check the number of parameters
                if(ins.length == 2){

                    //Create the key file based on the key filename
                    File file = new File(ins[1]);
                    file.createNewFile();
                    return true;

                }else
                    System.out.println("ERROR: The number of arguments is invalid.");
            } else
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

            //Remove > and < in the input Text if it has them
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

    //Map the key character with the alphabet character.
    //Row 0 contains the alphabet characters
    //Row 1 contains the key characters
    public static char[][] createCharMap(char[] alphabet, char[] key){
        char[][] charMap = new char[2][alphabet.length];

        for (int i = 0; i < alphabet.length; i++) {
            charMap[0][i] = alphabet[i];
            charMap[1][i] = key[i];
        }
        return charMap;
    }

    public static void encrypt(char[] alpha, char[] inTxt, char[] key){
        char[] outTxt = new char[inTxt.length];
        char[][] charMap = createCharMap(alpha, key);

        //Encryption process
        for(int i = 0; i < inTxt.length; i++){
            for (int j = 0; j < alpha.length; j++) {
                if (alpha[j] == inTxt[i])
                    outTxt[i] = charMap[1][j];
            }
        }
        System.out.println("\nCIPHER TEXT:" + "\n" + new String(outTxt));
    }

    public static void decrypt(char[] alpha, char[] inTxt, char[] key){
        char[] outTxt = new char[inTxt.length];
        char[][] charMap = createCharMap(alpha, key);

        //Decryption process
        for(int i = 0; i < inTxt.length; i++){
            for (int j = 0; j < key.length; j++) {
                if (key[j] == inTxt[i])
                    outTxt[i] = charMap[0][j];
            }
        }
        System.out.println("\nORIGINAL MESSAGE:" + "\n" + new String(outTxt));
    }
    public static void generate(String fileName){
        try{
            //Generate key
            ArrayList<Character> alphaLst = new ArrayList<>();
            char [] alphaChars = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W',
                    'X','Y','Z',' ','.',',',':',';','(',')','-','!','?','$','\'','"','\n','0','1','2','3','4','5','6','7','8','9'};
            for(int i = 0; i < alphaChars.length; i++){
                alphaLst.add(alphaChars[i]);
            }
            Collections.shuffle(alphaLst);

            //Write key
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for(int i = 0; i < alphaLst.size(); i++){
                writer.write(alphaLst.get(i));
            }
            writer.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        char [] alpha = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W',
                'X','Y','Z',' ','.',',',':',';','(',')','-','!','?','$','\'','"','\n','0','1','2','3','4','5','6','7','8','9'};
        char [] inChars;
        char [] keyChars;

        inChars = readFile(args[1]).toCharArray();
        keyChars = readFile(args[2]).toCharArray();

        if(checkInput(args)){
            switch(args[0]){
                case "e":
                    if(inChars != null && keyChars != null)
                        encrypt(alpha, inChars, keyChars);
                    else
                        System.out.println("ERROR: File reading is unsuccessful");
                    break;

                case "d":
                    if(inChars != null && keyChars != null)
                        decrypt(alpha, inChars, keyChars);
                    else
                        System.out.println("ERROR: File reading is unsuccessful");
                    break;

                case "g":
                    generate(args[1]);
            }
        }
    }
}