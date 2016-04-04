import java.io.*;

/**
 * Created by User on 4/3/2016.
 * Input parameters: mode, input filename, key
 */
public class ColTrans {
    public static boolean checkInput(String[] ins) {
        try {
            if(ins.length == 3){
                if((ins[0].equals("e") || ins[0].equals("d"))){
                    if (new File(ins[1]).exists()) {
                        if(Integer.parseInt(ins[2]) > 0)
                            return true;
                        else
                            System.out.println("ERROR: Invalid key.");
                    } else
                        System.out.println("ERROR: File not found.");
                } else
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

            outTxt = inTxt.toString();
        } catch (Exception e){
            e.printStackTrace();
        }
        return outTxt;
    }

    public static void encrypt(char[] inTxt, int key){
        char[][] outTxt = new char[inTxt.length /key][key];
        int inTxtIndex = 0;

        //Create the table and write the input text row by row
        for(int row = 0; row < outTxt.length; row++){
            for(int col = 0; col < outTxt[row].length; col++){
                outTxt[row][col] = inTxt[inTxtIndex];
                inTxtIndex++;
            }
        }

        //Read the table column by column
        StringBuilder outTxtBuilder = new StringBuilder();
        for (int col = 0; col < outTxt[0].length; col++){
            for(int row = 0; row < outTxt.length; row++)
                outTxtBuilder.append(outTxt[row][col]);
        }
        System.out.println("\nCIPHER TEXT: \n" + outTxtBuilder.toString());
    }
    public static String addSpace(String inTxt, int key){
        if(inTxt.length() % key == 0)
            return inTxt;
        else{
            StringBuilder inTxtBuilder = new StringBuilder(inTxt);
            int i = 0;
            int inTxtSize = inTxt.length();
            while(inTxtSize % key != 0){
                inTxtBuilder.append(" ");
                inTxtSize++;
            }
            return inTxtBuilder.toString();
        }
    }

    public static void decrypt(char[] inTxt, int key) throws IOException {
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
        System.out.println("\nORIGINAL MESSAGE: \n" + outTxtBuilder.toString());

        //Write the original message to file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("coltrans.txt"));
            writer.write(outTxtBuilder.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        char [] inChars;
        if(checkInput(args)){
            inChars = addSpace(readFile(args[1]), Integer.parseInt(args[2])).toCharArray();
            if (inChars != null)
                switch (args[0]) {
                    case "e":
                        encrypt(inChars, Integer.parseInt(args[2]));
                        break;
                    case "d":
                        decrypt(inChars, Integer.parseInt(args[2]));
                }
            else
                System.out.println("ERROR: File reading is unsuccessful");
        }else
            return;
    }
}
