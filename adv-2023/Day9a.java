import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day9a {
    public static ArrayList<Long> convertToIntegers(String[] array) {
        ArrayList<Long> res = new ArrayList<>();
        for(int i=0; i<array.length; i++) {
            //System.out.println(array.get(i));
            res.add(Long.parseLong(array[i]));
        }
        return res;
    }

    public static boolean checkAllZero(ArrayList<Long> history) {
        for (int i = 0; i<history.size(); i++) {
            if(history.get(i) != 0) {
                return false;
            }
        }
        return true;
    }

    public static ArrayList<Long> getDiffArr(ArrayList<Long> history) {
        ArrayList<Long> temp = new ArrayList<>();
        for(int i =0; i< history.size(); i++) {
            if((i+1) < history.size()) {
                long x = history.get(i);
                long y = history.get(i+1);
                temp.add(y - x);
            }
        }
        return temp;
    }
    public static long historyNextValue(ArrayList<Long> history, long nextValue) {
        if(checkAllZero(history) == true) {
            return nextValue;
        }
        nextValue = nextValue + history.get(history.size()-1);
        //System.out.println(history + " " + nextValue);
        history = getDiffArr(history);
        return historyNextValue(history, nextValue);
    }
    public static void main(String[] args) {
        String filePath = "Bigtest9.txt";

        int sum = 0;
        ArrayList<ArrayList<Long>> historyArr = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line from the file until there are no more lines
            while ((line = reader.readLine()) != null) {
                String[] wordSplit = line.trim().split(" +");
                
                historyArr.add(convertToIntegers(wordSplit));
            }
        } catch (IOException e) {
            // Handle any exceptions that may occur during file reading
            e.printStackTrace();
        }

        for(int i = 0; i < historyArr.size(); i++) {
            sum += historyNextValue(historyArr.get(i), 0);
        }
        //long res = ;
        System.out.println(sum);

    }
}
