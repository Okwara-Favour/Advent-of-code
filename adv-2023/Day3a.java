import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day3a {
    public static boolean adjacentMatch(int r, ArrayList<Integer> indexStore, ArrayList<String> array) {
        for(int i=r-1; i<=r+1; i++) {
            for(int j=indexStore.get(0)-1; j<=indexStore.get(indexStore.size()-1)+1; j++) {
                if(checkArrayIndex(array, i, j)) {
                    if(array.get(i).charAt(j) != '.' && !Character.isDigit(array.get(i).charAt(j))) {
                        System.out.print(array.get(i).charAt(j));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkArrayIndex(ArrayList<String> array, int r, int c) {
        if(r >= 0 && r < array.size() && c >= 0 && c < array.get(r).length()) {
            return true;
        }
        return false;
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

        for (int i=0; i<array.size(); i++) {
            String val = "";
            ArrayList<Integer> indexStore = new ArrayList<>();
            for(int j=0; j<array.get(i).length(); j++) {
                char c = array.get(i).charAt(j);
                if(Character.isDigit(c)) {
                    val += c;
                    indexStore.add(j);
                    if(j+1>=array.get(i).length() || (j+1<array.get(i).length() && !Character.isDigit(array.get(i).charAt(j+1)))) {
                        if(adjacentMatch(i, indexStore, array)) {
                            System.out.print(val + " ");
                            sum += Integer.parseInt(val);
                            //System.out.print(" " + sum + " ");
                        }
                        indexStore.clear();
                        val = "";
                    }
                }
                //System.out.print(c);
            }
            //System.out.print("loc" + (i+1));
            System.out.println();
        }
        System.out.println(sum);
    }
}
