import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day1a {
    public static void main(String[] args) {
        String filePath = "Bigtest.txt";

        int sum = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line from the file until there are no more lines
            while ((line = reader.readLine()) != null) {
                ArrayList<Character> array = new ArrayList<>();
                for(int i = 0; i < line.length(); i++) {
                    if(Character.isDigit(line.charAt(i))) {
                        array.add(line.charAt(i));
                        //x = line.charAt(i);
                        //break;
                    }
                }

                int value = Integer.parseInt(array.get(0) + "" + array.get(array.size()-1));
                sum += value;
                //System.out.println(array);
            }
        } catch (IOException e) {
            // Handle any exceptions that may occur during file reading
            e.printStackTrace();
        }

        System.out.println(sum);
    }
}
