import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by User on 4/4/2016.
 * Message 4: 10 cloverfield lane
 */
public class FrequencyCount {
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

    public static HashMap<Character, Integer> countFreq(String inTxtStr){
        HashMap<Character, Integer> freqMap = new HashMap<>();
        char[] inTxt = inTxtStr.toCharArray();

        for (int i = 0; i < inTxt.length; i++){
            if(!freqMap.containsKey(inTxt[i]))
                freqMap.put(inTxt[i], 1);
            else{
                freqMap.put(inTxt[i], freqMap.get(inTxt[i])+1);
            }
        }
        return freqMap;
    }

    public static void printMap(HashMap<Character, Integer> map){
        Set<HashMap.Entry<Character, Integer>> entrySet = map.entrySet();
        for(HashMap.Entry<Character, Integer> entry : entrySet)
            System.out.println(entry.getKey() + "\t" + entry.getValue());
    }

    public static void main(String[] args) {
        String inTxt = readFile(args[0]);
//        System.out.println("ORIGINAL MESSAGE: \n" + "------------------" + "\n" + inTxt);
        //Frequently-used letters: e, a, o, i

        //Message 4
//        inTxt = inTxt.replace("\n","~~");
//        inTxt = inTxt.replace("P","e");
//        inTxt = inTxt.replace("(", "a");
//        inTxt = inTxt.replace("L", "h");
//        inTxt = inTxt.replace("0", "t");
//        inTxt = inTxt.replace(";", "r");
//        inTxt = inTxt.replace("J", "w");
//        inTxt = inTxt.replace("H", "o");
//        inTxt = inTxt.replace("E", "f");
//        inTxt = inTxt.replace("2", "n");
//        inTxt = inTxt.replace("6", "i");
//        inTxt = inTxt.replace("Y", "b");
//        inTxt = inTxt.replace("A", "l");
//        inTxt = inTxt.replace("U", "c");
//        inTxt = inTxt.replace("C", "d");
//        inTxt = inTxt.replace(".", "g");
//        inTxt = inTxt.replace("F", "m");
//        inTxt = inTxt.replace(")", "u");
//        inTxt = inTxt.replace(":", "v");
//        inTxt = inTxt.replace(" ", "s");
//        inTxt = inTxt.replace("?", "y");
//        inTxt = inTxt.replace("5", "p");
//        inTxt = inTxt.replace("K", ".");
//        inTxt = inTxt.replace("3", "k");
//        inTxt = inTxt.replace("G", "'");
//        //Google here: 10 cloverfield land
//        inTxt = inTxt.replace("9", "1");
//        inTxt = inTxt.replace("X", "0");
//        inTxt = inTxt.replace("N", "\n");
//        inTxt = inTxt.replace("~~", " ");

        //Message 7
        inTxt = inTxt.replace(" ", "~~");
        inTxt = inTxt.replace("B", "e");
        inTxt = inTxt.replace("5", "h");
        inTxt = inTxt.replace("-", "t");
        inTxt = inTxt.replace("R", "a");
        inTxt = inTxt.replace("\"", "i");
        inTxt = inTxt.replace("L", "s");
        inTxt = inTxt.replace("E", "*");
        inTxt = inTxt.replace("!", "p");
        inTxt = inTxt.replace(")", "r");
        inTxt = inTxt.replace("$", "b");
        inTxt = inTxt.replace("N", "l");
        inTxt = inTxt.replace("1", "c");
        inTxt = inTxt.replace("P", "m");
//        inTxt = inTxt.replace("K", ",");
        inTxt = inTxt.replace("Z", "v");
        inTxt = inTxt.replace("T", "k");
        inTxt = inTxt.replace("I", "o");
        inTxt = inTxt.replace("J", "y");
        inTxt = inTxt.replace(",", "f");
        inTxt = inTxt.replace("K", ",");
        inTxt = inTxt.replace("\n", "n");
        inTxt = inTxt.replace("Y", "\n");
        inTxt = inTxt.replace("X", "d");
//        inTxt = inTxt.replace("'", "u");
//        inTxt = inTxt.replace("9", "w");
//        inTxt = inTxt.replace("W", "g");
//        inTxt = inTxt.replace("M", "q");
//        inTxt = inTxt.replace("H", "j");
//        inTxt = inTxt.replace("0", ".");
//        inTxt = inTxt.replace("(", "x");
//        inTxt = inTxt.replace("*", "'");
//        inTxt = inTxt.replace("~~", " ");

        System.out.println("\nNEW MESSAGE: \n" + "------------------" + "\n" + inTxt);
//        System.out.println("\nLETTER FREQUENCY:\n" + "------------------");
//        printMap(countFreq(inTxt));
    }
}
