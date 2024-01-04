import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Enumeration;


public class Day6a {
    public static ArrayList<Long> convertToIntegers(List<String> array) {
        ArrayList<Long> res = new ArrayList<>();
        for(int i=0; i<array.size(); i++) {
            //System.out.println(array.get(i));
            res.add(Long.parseLong(array.get(i)));
        }
        return res;
    }

    public static ArrayList<Long> generateWinTime(long time, long distance) {
        ArrayList<Long> temp = new ArrayList<>();
        for (long i=0; i<= time; i++) {
            long x = (time - i) * i;
            if(x > distance) {
                temp.add(i);
            } 
        }
        return temp;
    }
    public static void main(String[] args) {
        String filePath = "Bigtest6.txt";

        int sum = 0;
        Dictionary<String, ArrayList<Long>> dict = new Hashtable<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line from the file until there are no more lines
            while ((line = reader.readLine()) != null) {
                String[] word_split = {};
                word_split = line.split(":");
                dict.put(word_split[0], convertToIntegers(Arrays.asList(word_split[1].trim().split(" +"))));
            }
        } catch (IOException e) {
            // Handle any exceptions that may occur during file reading
            e.printStackTrace();
        }

        System.out.println(dict);
        long res = 1;
        ArrayList<String> description = new ArrayList<>();
        Enumeration<String> k = dict.keys();
        while(k.hasMoreElements()) {
            String key = k.nextElement();
            description.add(key);
            
        }
        for(int i=0; i<dict.get(description.get(0)).size(); i++) {
            //System.out.println(generateWinTime(dict.get(description.get(1)).get(i), dict.get(description.get(0)).get(i)));
            res *= generateWinTime(dict.get(description.get(1)).get(i), dict.get(description.get(0)).get(i)).size();
        }
        System.out.println(res);
    }
}
