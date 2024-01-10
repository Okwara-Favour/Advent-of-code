import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;

public class Day11b {
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

        for(int j=0; j<pattern.get(0).size(); j++) {
            for(int i=0; i<pattern.size(); i++) {
                if(isHashInString(pattern.get(i).get(j))) {
                    check = true;
                    break;
                }
                
            }
            if(check == false) {
                temp.add(j);
            }
            check = false;
        }
    }

    public static long getPath(ArrayList<Integer> start, ArrayList<Integer> end, ArrayList<Integer> row, ArrayList<Integer> column, long expansion) {
        long exp = expansion;
        if(expansion <=1) {
            exp = 0;
        }
        else {
            exp = expansion - 1;
        }
        long x = 0;
        long y = 0;
        for(int i=0; i<row.size(); i++) {
            if(row.get(i) < end.get(0) && row.get(i) > start.get(0)) {
                x+=exp;
            }
        }
        x += end.get(0) - start.get(0);
        
        if(end.get(1) >= start.get(1)) {
            for(int i=0; i<column.size(); i++) {
                if(column.get(i) < end.get(1) && column.get(i) > start.get(1)) {
                    y+=exp;
                }
            }
            y += end.get(1) - start.get(1);
        }
        else {
            for(int i=0; i<column.size(); i++) {
                if(column.get(i) > end.get(1) && column.get(i) < start.get(1)) {
                    y+=exp;
                }
            }
            y += start.get(1) - end.get(1);
        }
        return x + y;
    }
    public static void main(String[] args) {
        String filePath = "Bigtest11.txt";

        ArrayList<ArrayList<String>> pattern = new ArrayList<>();
        ArrayList<Integer> row = new ArrayList<>();
        int x = 1;
        int index = 0;
        long sum = 0;
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
                    row.add(index);
                }
                index++;
            }
        } catch (IOException e) {
            // Handle any exceptions that may occur during file reading
            e.printStackTrace();
        }

        ArrayList<Integer> column = new ArrayList<>();
        storeNoNumInCol(pattern, column);
        
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
                long res = getPath(dict.get(i), dict.get(j), row, column, 1000000);
                sum += res;
                //System.out.print(i + "," + j + ","+ res + " | ");
            }
            //System.out.println();
            
        }
        System.out.println(sum);
    }
}
