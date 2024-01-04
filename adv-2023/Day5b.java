import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Stack;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;

public class Day5b { 
    public static ArrayList<ArrayList<Long>> rangeCSP(long destination, long source, long n, ArrayList<Long> seedRange, Stack<ArrayList<Long>> stackSeed) {
        System.out.print(seedRange + " | " + source + ", "+ (source + n));
        /*if(seedRange.get(0) >= source && seedRange.get(1) <= (source + n)) {
            System.out.println(" true");
            ArrayList<Long> temp = new ArrayList<>();
            temp.add(destination + (seedRange.get(0) - source));
            temp.add(destination + (seedRange.get(1) - source));
            ArrayList<ArrayList<Long>> cur = new ArrayList<>();
            cur.add(temp);
            return cur;
        }

        else if(seedRange.get(0) < source && seedRange.get(1) >= (source) && seedRange.get(1) <= (source + n)) {
            System.out.println(" true");
            ArrayList<Long> temp1 = new ArrayList<>();
            ArrayList<Long> temp2 = new ArrayList<>();
            temp1.add(seedRange.get(0));
            temp1.add(source);
            temp2.add(destination);
            temp2.add(destination + (seedRange.get(1) - source));
            ArrayList<ArrayList<Long>> cur = new ArrayList<>();
            cur.add(temp1);
            cur.add(temp2);
            return cur;
        }
        else if(seedRange.get(0) >= source && seedRange.get(0) <= (source + n) && seedRange.get(1) > (source + n)) {
            System.out.println(" true");
            ArrayList<Long> temp1 = new ArrayList<>();
            ArrayList<Long> temp2 = new ArrayList<>();
            temp1.add(destination + (seedRange.get(0) - source));
            temp1.add(destination + n);
            temp2.add(source + n);
            temp2.add(seedRange.get(1));
            ArrayList<ArrayList<Long>> cur = new ArrayList<>();
            cur.add(temp1);
            cur.add(temp2);
            return cur;
        }

        else if(seedRange.get(0) < source  && seedRange.get(1) > (source + n)) {
            System.out.println(" true");
            ArrayList<Long> temp1 = new ArrayList<>();
            ArrayList<Long> temp2 = new ArrayList<>();
            ArrayList<Long> temp3 = new ArrayList<>();
            temp1.add(seedRange.get(0));
            temp1.add(source);
            temp2.add(destination);
            temp2.add(destination + n);
            temp3.add(source + n);
            temp3.add(seedRange.get(1));
            ArrayList<ArrayList<Long>> cur = new ArrayList<>();
            cur.add(temp1);
            cur.add(temp2);
            cur.add(temp3);
            return cur;
        }
        else {
            System.out.println(" false");
            ArrayList<Long> temp = new ArrayList<>();
            temp.add(seedRange.get(0));
            temp.add(seedRange.get(1));
            ArrayList<ArrayList<Long>> cur = new ArrayList<>();
            cur.add(temp);
            return cur;
        }*/
        ArrayList<ArrayList<Long>> cur = new ArrayList<>();
        ArrayList<Long> temp = new ArrayList<>();
        long os = Math.max(seedRange.get(0), source);
        long oe = Math.min(seedRange.get(1), (source + n));
        if(os < oe) {
            System.out.println(" true");
            temp.add(os - source + destination);
            temp.add(oe - source + destination);
            cur.add(temp);
            if(os > seedRange.get(0)) {
                ArrayList<Long> cp = new ArrayList<>();
                cp.add(seedRange.get(0));
                cp.add(os);
                stackSeed.push(cp);
                //cur.add(cp);
            }
            if(seedRange.get(1) > oe) {
                ArrayList<Long> cp = new ArrayList<>();
                cp.add(oe);
                cp.add(seedRange.get(1));
                //cur.add(cp);
                stackSeed.push(cp);
            }
            return cur;
        }
        System.out.println(" false");
        cur.add(seedRange);
        return cur;
    }

    public static boolean checkCSP(long destination, long source, long n, ArrayList<Long> seedRange) {
        //ArrayList<ArrayList<Long>> cur = new ArrayList<>();
        //ArrayList<Long> temp = new ArrayList<>();
        long os = Math.max(seedRange.get(0), source);
        long oe = Math.min(seedRange.get(1), (source + n));
        if(os < oe) {
            return true;
        }
        return false;
    }


    public static ArrayList<Long> convertToIntegers(List<String> array) {
        ArrayList<Long> res = new ArrayList<>();
        for(int i=0; i<array.size(); i++) {
            res.add(Long.parseLong(array.get(i)));
        }
        return res;
    }

    public static boolean seedContains(ArrayList<ArrayList<Long>> rangeSeeds, ArrayList<ArrayList<Long>> storeRange) {
        for(int i = 0; i<rangeSeeds.size(); i++) {
            if(!storeRange.contains(rangeSeeds.get(i))) {
                return false;
            }
        }
        return true;
    }
    public static ArrayList<ArrayList<Long>> mapRangeSeeds(ArrayList<ArrayList<Long>> array, ArrayList<ArrayList<Long>> rangeSeeds) {
        Stack<ArrayList<Long>> stackSeed = new Stack<>();
        stackSeed.addAll(rangeSeeds);

        ArrayList<ArrayList<Long>> storeNewRange = new ArrayList<>();
        //ArrayList<ArrayList<Long>> newSeeds = new ArrayList<>();

        while(!stackSeed.isEmpty()) {
            ArrayList<Long> seedRecord = stackSeed.pop();
            for(int i=0; i<array.size(); i++) {
                ArrayList<Long> cur = array.get(i);
                ArrayList<ArrayList<Long>> temp = rangeCSP(cur.get(0), cur.get(1), cur.get(2), seedRecord, stackSeed);
                boolean check = checkCSP(cur.get(0), cur.get(1), cur.get(2), seedRecord);
                if(check == true) {
                    storeNewRange.addAll(temp);
                    break;
                }
                if(i == (array.size()-1) && check == false) {
                    storeNewRange.add(seedRecord);
                }
            }
        }
        
        //newSeeds.addAll(rangeSeeds);
        //System.out.println(storeNewRange);
        return storeNewRange;
    }

    public static ArrayList<ArrayList<Long>> processRangeSeeds(Dictionary<String, ArrayList<ArrayList<Long>>> dict, ArrayList<String> process, ArrayList<ArrayList<Long>> rangeSeeds, String s, int index) {
        
        while(index < process.size()) {
            //s += process.get(index) + ": ";
            System.out.println(process.get(index));
            rangeSeeds = mapRangeSeeds(dict.get(process.get(index)), rangeSeeds);
            System.out.println(rangeSeeds);
            //s += seed + " ";
            index++;
        }
        return rangeSeeds;
    }

    

    public static void main(String[] args) {
        String filePath = "Smalltest5.txt";

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

        System.out.println("Original: " + seeds);
        
        ArrayList<ArrayList<Long>> result = new ArrayList<>();
        int start = 0;
        int end = 1;
        if(seeds.size() != 0 && (seeds.size()%2)==0) {
            while(start < seeds.size()) {
                //if(start > 4) {
                  //  break;
                //}
                System.out.println(seeds.get(start) + " " + (seeds.get(start) + seeds.get(end)));
                ArrayList<Long> testSeed = new ArrayList<>();
                testSeed.add(seeds.get(start));
                testSeed.add(seeds.get(start) + seeds.get(end));

                //testSeed.add((long)49);
                //testSeed.add((long)110);

                String ad = "";
                
                ArrayList<ArrayList<Long>> rangeSeed = new ArrayList<>();
                rangeSeed.add(testSeed);
                //System.out.println(processRangeSeeds(dict, process, rangeSeed, ad, 0));
                result.addAll(processRangeSeeds(dict, process, rangeSeed, ad, 0));
                start += 2;
                end += 2;
            }
        }
        
        ArrayList<Long> res = new ArrayList<>();
        for(int i=0; i<result.size(); i++) {
            res.add(result.get(i).get(0));
        }
        System.out.println(result);
        Collections.sort(res);
        System.out.println(res);
        
    }    
}
