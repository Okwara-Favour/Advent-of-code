import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Collections;

public class Day5a {

    public static long checkSeedProcess(long destination, long source, long n, long seed) {
        if(seed >= source && seed <= (source+n)) {
            return destination + (seed - source);
        }
        return seed;
    }

    public static ArrayList<Long> convertToIntegers(List<String> array) {
        ArrayList<Long> res = new ArrayList<>();
        for(int i=0; i<array.size(); i++) {
            //System.out.println(array.get(i));
            res.add(Long.parseLong(array.get(i)));
        }
        return res;
    }

    public static long mapSeeds(ArrayList<ArrayList<Long>> array, long seed) {
        
        for(int i=0; i<array.size(); i++) {
            for(int j = 0; j < array.get(i).size(); j++) {
                ArrayList<Long> cur = array.get(i);
                long temp = seed;
                seed = checkSeedProcess(cur.get(0), cur.get(1), cur.get(2), seed);
                if(seed != temp) {
                    return seed;
                }
            }
        }
        return seed;
    }

    public static long processSeeds(Dictionary<String, ArrayList<ArrayList<Long>>> dict, ArrayList<String> process, long seed, String s, int index) {
        if(index >= process.size()) {
            System.out.println(s);
            return seed;
        }
        else {
            s += process.get(index) + ": ";
            seed = mapSeeds(dict.get(process.get(index)), seed);
            s += seed + " ";

            return processSeeds(dict, process, seed, s, index+1);
        }
    }
    public static void main(String[] args) {
        String filePath = "Bigtest5.txt";

        int sum = 0;
        ArrayList<Long> seeds = new ArrayList<>();
        ArrayList<String> process = new ArrayList<>();
        Dictionary<String, ArrayList<ArrayList<Long>>> dict = new Hashtable<>();
        String s = "";
        //int index = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line from the file until there are no more lines
            while ((line = reader.readLine()) != null) {
                String[] word_split = {};
                //System.out.println(line);
                if(line.contains(":")) {
                    word_split = line.split(":");
                }
                //System.out.println(line);
                if(word_split.length > 0 && word_split[0].equals("seeds")) {
                    seeds = convertToIntegers(Arrays.asList(word_split[1].trim().split(" +")));
                    //System.out.println(seeds);
                }
                //System.out.println(line);
                if(word_split.length == 1) {
                    s = word_split[0];
                    process.add(s);
                    dict.put(s, new ArrayList<>());
                }
                else if(line.isEmpty()) {
                    s = "";
                    //index = 0;
                }
                if(s != "" && word_split.length == 0) {
                    
                    ArrayList<Long> array = convertToIntegers(Arrays.asList(line.trim().split(" +")));
                    dict.get(s).add(array);
                }
                //System.out.println(word_split.length + " " + s);
            }
        } catch (IOException e) {
            // Handle any exceptions that may occur during file reading
            e.printStackTrace();
        }

        System.out.println(seeds);

        String val = "";
        ArrayList<Long> result = new ArrayList<>();
        for(int i=0; i<seeds.size(); i++) {
            result.add(processSeeds(dict, process, seeds.get(i), val, 0));
        }
        
        System.out.println("locations: " + result);
        System.out.println(Collections.min(result));
    }    
}
