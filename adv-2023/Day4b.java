import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class Day4b {
    public static ArrayList<Integer> dataIncrement(ArrayList<Integer> array1, ArrayList<Integer> array2) {
        for(int i=0; i<array1.size(); i++) {
            if(array2.contains(array1.get(i))) {
                array1.set(i, array1.get(i) + 1);
            }
        }
        return array1;
    }
    public static int getNumFromString(String word) {
        String res = "";
        for(int i = 0; i < word.length(); i++) {
            if(Character.isDigit(word.charAt(i))) {
                res += word.charAt(i);
            }
        }

        return Integer.parseInt(res);
    }
    public static int countMatchNum(String[] array1, String[] array2) {
        int count = 0;
        for(int i=0; i<array1.length; i++) {
            if(i == 0) {
                continue;
            }
            for(int j=0; j<array2.length; j++) {
                if(j == 0) {
                    continue;
                }
                if(array2[j].equals(array1[i])) {
                    count += 1;
                }
            }
        }
        return count;
    }

    public static ArrayList<Integer> createCopyCards(int winCard, int wins) {
        ArrayList<Integer> array = new ArrayList<>();
        for(int i=winCard + 1; i<=wins+winCard; i++) {
            array.add(i);
        }
        return array;
    }

    public static Dictionary<Integer, ArrayList<Integer>> makeDictFromArr(Dictionary<Integer, ArrayList<Integer>> dict, ArrayList<Integer> array) {
        Dictionary<Integer, ArrayList<Integer>> temp = new Hashtable<>();
            
        for(int i=0; i<array.size(); i++) {
            temp.put(array.get(i), dict.get(array.get(i)));
        }
        return temp;
    }

    public static ArrayList<ArrayList<Integer>> traverseCopies(ArrayList<Integer> dataArray, ArrayList<ArrayList<Integer>> book ,Dictionary<Integer, ArrayList<Integer>> dict, ArrayList<Integer> array,int index) {
        
        while(index < array.size()) {
            ArrayList<Integer> copyCards = new ArrayList<>();
            if(dict.get(array.get(index)) == null) {
                copyCards = createCopyCards(array.get(index), 0);
            }
            else {
                copyCards = createCopyCards(array.get(index), dict.get(array.get(index)).size());
            }
            int x = array.get(index)-1;
            dataArray.set(x, dataArray.get(array.get(index)-1)+1);

            book.add(copyCards);
            index++;

        }
        return book;
    }

    public static ArrayList<Integer> copyBook(ArrayList<ArrayList<Integer>> book) {
        ArrayList<Integer> CopiedArray = new ArrayList<>();
        for(int i=0; i<book.size(); i++) {
            CopiedArray.addAll(book.get(i));
        }
        return CopiedArray;
    }
    public static void processScratchCards(ArrayList<Integer> dataArray, ArrayList<ArrayList<Integer>> book, Dictionary<Integer, ArrayList<Integer>> card_dict, ArrayList<Integer> card_num, int count) {
        if(card_num.size() == 0) {
            return;
        }
        else {
            book = new ArrayList<>();
            //boolean check = false;
            book = traverseCopies(dataArray, book, card_dict, card_num, 0);

            ArrayList<Integer> CopiedArray = new ArrayList<>();
            CopiedArray = copyBook(book);
            
            System.out.println(CopiedArray.size());

            processScratchCards(dataArray, book, card_dict, CopiedArray, ++count);
        }
    }
    
    public static void main(String[] args) {
        String filePath = "Bigtest4.txt";

        int sum = 0;
        Dictionary<Integer, ArrayList<Integer>> card_dict = new Hashtable<>();
        ArrayList<Integer> card_num = new ArrayList<>();
        //Dictionary<Integer, Integer> win_dict = new Hashtable<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line from the file until there are no more lines
            while ((line = reader.readLine()) != null) {
                //ArrayList<Character> array = new ArrayList<>();
                String[] word_split = line.split(":");
                try {
                    String[] rounds = word_split[1].split("\\|");
                    String[] win_num = rounds[0].split(" +");
                    String[] can_num = rounds[1].split(" +");

                    int val = countMatchNum(win_num, can_num);

                    int winCard = getNumFromString(word_split[0]);
                    ArrayList<Integer> copyCards = createCopyCards(winCard, val);

                    //System.out.println("Win card: " + winCard + " wins: " + val + " Copies: " + copyCards);
                    card_dict.put(winCard, copyCards);
                    card_num.add(winCard);
                    //win_dict.put(winCard, val);

                }
                catch(Exception e) {
                    System.out.println(e);
                }

            }
        } catch (IOException e) {
            // Handle any exceptions that may occur during file reading
            e.printStackTrace();
        }
        
        
        ArrayList<Integer> dataArray = new ArrayList<>();
        for(int i=0; i<card_num.size(); i++) {
            dataArray.add(0);
        }


        ArrayList<ArrayList<Integer>> book = new ArrayList<>();
        
        processScratchCards(dataArray, book, card_dict, card_num, 0);

        for(int i=0; i<dataArray.size(); i++) {
            sum += dataArray.get(i);
        }
        System.out.println(dataArray);
        System.out.println(sum);
    }
}
