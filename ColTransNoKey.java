import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by User on 4/4/2016.
 * Message 2: key is 8
 * Input parameter: input filename
 */
public class ColTransNoKey {
    public static boolean checkInput(String[] ins) {
        try {
            //Check the number of parameters
            if(ins.length == 1){

                //Check if the input files is found
                if (new File(ins[0]).exists())
                    return true;
                else
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

            //Final output
            outTxt = inTxt.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return outTxt;
    }

    //Find all the potential keys based on the cipher text length
    public static ArrayList<Integer> findKeys(String inTxt){
        ArrayList<Integer> keys = new ArrayList<>();
        int txtLength = inTxt.length();
        for(int i = 1; i <= txtLength; i++){
            if(txtLength % i == 0)
                keys.add(i);
        }
        return keys;
    }

    public static void decrypt(String inTxt){
        ArrayList<Integer>keys = findKeys(inTxt);
        char[] inTxtArray = inTxt.toCharArray();

        //Traverse the ArrayList to find the right key for the cipher text
        for(int i = 0; i < keys.size(); i++){
            char[][] outTxt = new char[inTxtArray.length /keys.get(i)][keys.get(i)];
            int inTxtIndex = 0;

            //Create the table and write the input text column by column
            for(int col = 0; col < outTxt[0].length; col++){
                for(int row = 0; row < outTxt.length; row++){
                    outTxt[row][col] = inTxtArray[inTxtIndex];
                    inTxtIndex++;
                }
            }

            //Read the table row by row
            StringBuilder outTxtBuilder = new StringBuilder();
            for (int row = 0; row < outTxt.length; row++){
                for(int col = 0; col < outTxt[0].length; col++)
                    outTxtBuilder.append(outTxt[row][col]);
            }
            System.out.println("\nORIGINAL MESSAGE WITH KEY " + keys.get(i) + "\n" + outTxtBuilder.toString());
        }
    }

    public static void main(String[] args) {
        String inTxt;
        if(checkInput(args)){
            inTxt = readFile(args[0]);
            if (inTxt != null)
                decrypt(inTxt);
            else
                System.out.println("ERROR: File reading is unsuccessful");
        }else
            return;
    }

}
