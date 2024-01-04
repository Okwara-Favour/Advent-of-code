import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day2a {
    public static boolean checkValidGame(String[] rounds) {
        for (int i=0; i<rounds.length; i++) {
            String[] ballsColor = rounds[i].split(",");
            for (int j = 0; j < ballsColor.length; j++) {
                String[] data = ballsColor[j].split(" ");
                int ball_amount = Integer.parseInt(data[1]);
                if  (data[2].equals("red") && ball_amount > 12) {
                    return false;
                }
                if  (data[2].equals("green") && ball_amount > 13) {
                    return false;
                }
                if  (data[2].equals("blue") && ball_amount > 14) {
                    return false;
                }
            }
        }
        return true;
    }
    public static void main(String[] args) {
        String filePath = "Bigtest2.txt";

        int sum = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line from the file until there are no more lines
            while ((line = reader.readLine()) != null) {
                //String test = line;
                String[] word_split = line.split(":");
                try {
                    String[] rounds = word_split[1].split(";");
                    System.out.println(checkValidGame(rounds));
                    if(checkValidGame(rounds)) {
                        String[] getGameID = word_split[0].split(" ");
                        sum += Integer.parseInt(getGameID[1]);
                    }
                }
                catch(Exception e) {
                    System.out.println(e);
                }
                //sum += value;
                //System.out.println(array);
            }
        } catch (IOException e) {
            // Handle any exceptions that may occur during file reading
            e.printStackTrace();
        }

        System.out.println(sum);
    }
}
