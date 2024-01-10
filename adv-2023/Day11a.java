import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;

public class Day11a {
    public static boolean isHashInString(String word) {
        for(int i=0; i<word.length(); i++) {
            if(word.charAt(i) == '#' || Character.isDigit(word.charAt(i))) {
                return true;
            }
        }
        return false;
    }
    public static void storeNewFormat(ArrayList<ArrayList<String>> arrayOfArray, String word, int x) {
        ArrayList<String> array = new ArrayList<>();
        for(int i=0; i<word.length(); i++) {
            if(word.charAt(i) == '#') {
                array.add(x+"");
                x++;
            }
            else {
                array.add(word.charAt(i) + "");
            }
        }
        arrayOfArray.add(array);
    }

    public static void storeNoNumInCol(ArrayList<ArrayList<String>> pattern, ArrayList<Integer> temp) {
        boolean check = false;
        
        int x = 0;
        for(int j=0; j<pattern.get(0).size(); j++) {
            for(int i=0; i<pattern.size(); i++) {
                if(isHashInString(pattern.get(i).get(j))) {
                    check = true;
                    break;
                }
                
            }
            if(check == false) {
                temp.add(j + x);
                x++;
            }
            check = false;
        }
    }

    public static int getPath(ArrayList<Integer> start, ArrayList<Integer> end) {

        int x = end.get(0) - start.get(0);
        int y=0;
        if(end.get(1) >= start.get(1)) {
            y = end.get(1) - start.get(1);
        }
        else {
            y = start.get(1) - end.get(1);
        }
        return x + y;
    }
    public static void main(String[] args) {
        String filePath = "Smalltest11.txt";

        ArrayList<ArrayList<String>> pattern = new ArrayList<>();
        int x = 1;
        int sum = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line from the file until there are no more lines
            while ((line = reader.readLine()) != null) {
                storeNewFormat(pattern, line, x);
                //System.out.println(line);
                if(isHashInString(line)) {
                    x++;
                }
                else {
                    storeNewFormat(pattern, line, x);
                }
            }
        } catch (IOException e) {
            // Handle any exceptions that may occur during file reading
            e.printStackTrace();
        }

        ArrayList<Integer> temp = new ArrayList<>();
        storeNoNumInCol(pattern, temp);
        
        for(int i=0; i<pattern.size(); i++) {
            for(int j=0; j<temp.size(); j++) {
                pattern.get(i).add(temp.get(j), ".");
            }
        }
        
        int val = 1;
        Dictionary<Integer, ArrayList<Integer>> dict = new Hashtable<>();
        for(int i=0; i<pattern.size(); i++) {
            for(int j=0; j<pattern.get(i).size(); j++) {
                if(isHashInString(pattern.get(i).get(j))) {
                    ArrayList<Integer> cur = new ArrayList<>();
                    cur.add(i);
                    cur.add(j);
                    dict.put(val, cur);
                    val++;
                }
            }
        }

        for(int i=1; i<=dict.size(); i++) {
            for(int j=i+1; j<=dict.size(); j++) {
                int res = getPath(dict.get(i), dict.get(j));
                sum += res;
                //System.out.print(i + "," + j + ","+ res + " | ");
            }
            //System.out.println();
        }
        System.out.println(sum);
    }
}
