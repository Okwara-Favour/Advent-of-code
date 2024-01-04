import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

public class Day3b {
    public static Dictionary<Integer, ArrayList<Integer>> adjacentMatch(int r, int c, ArrayList<String> array) {
        Dictionary<Integer, ArrayList<Integer>> dict= new Hashtable<>();
        for(int i=r-1; i<=r+1; i++) {
            dict.put(i, new ArrayList<>());

            for(int j=c-1; j<=c+1; j++) {
                if(checkArrayIndex(array, i, j)) {
                    if(Character.isDigit(array.get(i).charAt(j))) {
                        dict.get(i).add(j);
                        //System.out.println(i + " " + j);
                    }
                }
            }
        }

        Enumeration<Integer> k = dict.keys();
        while (k.hasMoreElements()) {
            Integer key = k.nextElement();
            if(dict.get(key).size() == 0) {
                dict.remove(key);
            }
        }
        //System.out.println(dict.get(k) == null);
        k = dict.keys();
        while (k.hasMoreElements()) {
            Integer key = k.nextElement();
            //System.out.println("Key: " + key + ", Value: " + dict.get(key));
        }

        return dict;
    }

    public static boolean checkArrayIndex(ArrayList<String> array, int r, int c) {
        if(r >= 0 && r < array.size() && c >= 0 && c < array.get(r).length()) {
            return true;
        }
        return false;
    }

    public static boolean checkDiff(ArrayList<Integer> array) {
        //System.out.println(array.size());
        if(array != null && array.size() >= 2) {
            return (array.get(1) - array.get(0)) == 2;
        }
        return false;
    }

    public static void storeValues(ArrayList<Dictionary<Integer, ArrayList<Integer>>> resDict, Dictionary<Integer, ArrayList<ArrayList<Integer>>> dict, Dictionary<Integer, ArrayList<String>> valDict, ArrayList<String> array) {
        for (int i=0; i<array.size(); i++) {
            //System.out.print("index " + i  + " col: ");
            String val = "";
            ArrayList<ArrayList<Integer>> indexStorage = new ArrayList<>();
            ArrayList<Integer> indexStore = new ArrayList<>();
            dict.put(i, indexStorage);
            valDict.put(i, new ArrayList<>());
            for(int j=0; j<array.get(i).length(); j++) {
                char c = array.get(i).charAt(j);
                //System.out.print(c);
                if(c == '*') {
                    Dictionary<Integer, ArrayList<Integer>> res = adjacentMatch(i, j, array);
                    if(res.size() >= 2) {
                        resDict.add(res);
                    }
                    Enumeration<Integer> d = res.keys();
                    Integer key = d.nextElement();
                    //System.out.println(j + " " + res.get(key));
                    if(res.size() == 1 && checkDiff(res.get(key))) {
                        resDict.add(res);
                        //System.out.print(j + " " + res + " " + res.size() + ", ");
                    }
                    
                }
                if(Character.isDigit(c)) {
                    val += c;
                    indexStore.add(j);
                    if(j+1>=array.get(i).length() || (j+1<array.get(i).length() && !Character.isDigit(array.get(i).charAt(j+1)))) {
                        //System.out.println(indexStore);
                        dict.get(i).add(indexStore);
                        valDict.get(i).add(val);
                        indexStore = new ArrayList<>();
                        val = "";
                    }
                }
            }
            //System.out.println();
        }
    }
    public static void main(String[] args) {
        String filePath = "Bigtest3.txt";
        ArrayList<String> array = new ArrayList<>();
        int sum = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line from the file until there are no more lines
            while ((line = reader.readLine()) != null) {
                array.add(line);              
            }
        } catch (IOException e) {
            // Handle any exceptions that may occur during file reading
            e.printStackTrace();
        }

        Dictionary<Integer, ArrayList<ArrayList<Integer>>> dict= new Hashtable<>();
        Dictionary<Integer, ArrayList<String>> valDict= new Hashtable<>();
        ArrayList<Dictionary<Integer, ArrayList<Integer>>> resDict = new ArrayList<>();
        storeValues(resDict, dict, valDict, array);
        //System.out.println(resDict);
        //System.out.println(dict);
        //System.out.println(valDict);

        for(int i = 0; i < resDict.size(); i++) {
            int mult = 1;
            Enumeration<Integer> k = resDict.get(i).keys();
            while (k.hasMoreElements()) {
                Integer key = k.nextElement();
                boolean check = false;
                int x = 0;
                for(int m = 0; m < dict.get(key).size(); m++) {
                    for(int n = 0; n < resDict.get(i).get(key).size(); n++) {
                        if(dict.get(key).get(m).contains(resDict.get(i).get(key).get(n))) {
                            System.out.println("Key "+ key + " r: " + resDict.get(i).get(key) + " d: " + dict.get(key).get(m) + " v: " + valDict.get(key).get(m));
                            //System.out.println("Key "+ key + " " + valDict.get(key).get(m));
                            
                            x = Integer.parseInt(valDict.get(key).get(m));
                            //System.out.println(" " + x);
                            check = true;
                            break;
                        }
                    }
                    if(check == true) {
                        mult *= x;
                        check = false;
                    }
                }
                
                //System.out.println("Key "+ key + " " + resDict.get(i).get(key) + " " + dict.get(key));
            
            }
            //System.out.print(" " + mult);
            System.out.println();
            //System.out.println(mult + " " + sum);
            //System.out.println(resDict.get(i));
            sum += mult;
        }
        System.out.println(sum);
    }
}
