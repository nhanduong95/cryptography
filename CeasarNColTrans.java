import java.io.*;

/**
 * Created by User on 4/4/2016.
 * Message 6: key is 5
 * Input parameters: input filename, key range for testing (the smallest key, the biggest key)
 */
public class CeasarNColTrans {
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

    public static String addSpace(String inTxt, int key){
        //If the length of the input is divisible by the key, return the text
        if(inTxt.length() % key == 0)
            return inTxt;

        //Otherwise, add more spaces to the text until its length becomes divisible
        else{
            StringBuilder inTxtBuilder = new StringBuilder(inTxt);
            int inTxtSize = inTxt.length();
            while(inTxtSize % key != 0){
                inTxtBuilder.append(" ");
                inTxtSize++;
            }
            return inTxtBuilder.toString();
        }
    }

    //Columnar Transposition decryption
    public static void colstrans(char[] inTxt, int key){
        char[][] outTxt = new char[inTxt.length /key][key];
        int inTxtIndex = 0;

        //Create the table and write the input text column by column
        for(int col = 0; col < outTxt[0].length; col++){
            for(int row = 0; row < outTxt.length; row++){
                outTxt[row][col] = inTxt[inTxtIndex];
                inTxtIndex++;
            }
        }

        //Read the table row by row
        StringBuilder outTxtBuilder = new StringBuilder();
        for (int row = 0; row < outTxt.length; row++){
            for(int col = 0; col < outTxt[0].length; col++)
                outTxtBuilder.append(outTxt[row][col]);
        }
        System.out.println("\nORIGINAL MESSAGE WITH KEY " + key + "\n" + outTxtBuilder.toString());
    }

    public static void decrypt(char[] alphabet, char[] inTxt, int beginKey, int endKey){
        char[] outTxt = new char[inTxt.length];

        //Traverse the key range to find the right key for the cipher text
        for(int key = beginKey; key < endKey + 1; key++){

            //Ceasar decryption
            for(int i = 0; i < inTxt.length; i++){
                for(int j = 0; j < alphabet.length; j++){
                    if(alphabet[j] == inTxt[i]){
                        outTxt[i] = (alphabet[Math.floorMod((j - key) , 50)]);
                    }
                }
            }
            //Apply Columnar Transposition decryption for the output from Ceasar decryption
            colstrans((addSpace(new String(outTxt), key)).toCharArray(), key);
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
