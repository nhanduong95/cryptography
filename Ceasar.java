import java.io.*;

/**
 * Created by User on 3/24/2016.
 * Massage 3: key is 23
 * Input parameters: mode, input filename, key
 */
public class Ceasar {
    public static boolean checkInput(String[] ins) {
        try {
            //Check the number of parameters
            if(ins.length == 3){

                //Check if the mode entered is correct
                if((ins[0].equals("e") || ins[0].equals("d"))){

                    //Check if file is found
                    if (new File(ins[1]).exists()) {

                        //Check if the entered key is an integer and larger than 0
                        if(Integer.parseInt(ins[2]) > 0)
                            return true;
                        else
                            System.out.println("ERROR: Invalid key.");
                    }
                }
                else
                    System.out.println("ERROR: Wrong mode input.");
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

            //Remove > and < in the input Text if it has them
            if((inTxt.charAt(0) == '>' && inTxt.charAt(inTxt.length()-1) == '<')
                    || (inTxt.charAt(0) == '<' && inTxt.charAt(inTxt.length()-1) == '>')){
                inTxt.deleteCharAt(0);
                inTxt.deleteCharAt(inTxt.length()-1);
            }

            //Final output
            outTxt = inTxt.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return outTxt;
    }

    public static void encryptOrDecrypt(char[] alphabet, char[] inTxt, int key, String type){
        char[] outTxt = new char[inTxt.length];

        //Traverse the input text for encryption or decryption
        for(int i = 0; i < inTxt.length; i++){
            for(int j = 0; j < alphabet.length; j++){
                if(alphabet[j] == inTxt[i]){
                    if(type.equals("e"))
                        //Encryption algorithm
                        outTxt[i] = (alphabet[Math.floorMod((j + key), 50)]);
                    else
                        //Decryption algorithm
                        outTxt[i] = (alphabet[Math.floorMod((j - key), 50)]);
                }
            }
        }

        //Print the final output
        if(type.equals("e"))
            System.out.println("\nCIPHER TEXT: \n" + new String(outTxt));
        else {
            System.out.println("\nORIGINAL MESSAGE: \n" + new String(outTxt));

            //Write the original message to file
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("ceasar.txt"));
                writer.write(new String(outTxt));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        char [] alphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W',
                'X','Y','Z',' ','.',',',':',';','(',')','-','!','?','$','\'','"','\n','0','1','2','3','4','5','6','7','8','9' };
        char [] inChars;
        if(checkInput(args)){
            inChars = readFile(args[1]).toCharArray();
            if (inChars != null)
                encryptOrDecrypt(alphabet, inChars, Integer.parseInt(args[2]), args[0]);
            else
                System.out.println("ERROR: File reading is unsuccessful");
        }else
            return;
    }
}

