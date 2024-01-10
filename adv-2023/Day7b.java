import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Hashtable;


public class Day7b {
    static class Card {
        Dictionary<String, ArrayList<ArrayList<Integer>>> dict;
        Dictionary<String, ArrayList<Integer>> bid;

        public Card() {
            dict = new Hashtable<>();
            bid = new Hashtable<>();
        }

        public void putCard(String key) {
            this.dict.put(key, new ArrayList<>());
            this.bid.put(key, new ArrayList<>());
        }

        public void addTypeHandBid(String type, ArrayList<Integer> hand, int val) {
            this.dict.get(type).add(hand);
            this.bid.get(type).add(val);
        } 

        public ArrayList<ArrayList<Integer>> getTypeHands(String type) {
            return this.dict.get(type);
        } 
        public ArrayList<Integer> getTypeBids(String type) {
            return this.bid.get(type);
        } 
    }
    public static int convertCardLetterToNum(char c) {
        switch(c) {
            case 'T':
                return 10;
            case 'J':
                return 1;
            case 'Q':
                return 12;
            case 'K':
                return 13;
            case 'A':
                return 14;
            default:
                return Integer.parseInt(c + "");
        }
    }

    public static ArrayList<Integer> cardToArray(String card) {
        ArrayList<Integer> temp = new ArrayList<>();
        for(int i=0; i<card.length(); i++) {
            temp.add(convertCardLetterToNum(card.charAt(i)));
        }
        return temp;
    }

    
    public static String cardType(ArrayList<Integer> cardArray) {
        int count = 1;
        int j = 0;
        int x = 0;
        int y = 0;
        for(int i=0; i<cardArray.size(); i++) {
            int val = cardArray.get(i);
            if(val == 1) {
                j++;
            }
            if((i+1) < cardArray.size() && val == cardArray.get(i+1)) {
                count++;
            }
            else {
                if(x != 0 && count > 1) {
                    y = count;
                }
                if(count > 1 && y == 0) {
                    x = count;
                }
                count = 1;
            }
        }

        
        if(x == 5) {
            return "Five of a kind";
        }
        if(x == 4) {
            if(j>=1) {
                return "Five of a kind"; 
            }
            return "Four of a kind";
        }
        if((x == 3 && y == 2) || (x == 2 && y == 3)) {
            if(j>=2) {
                return "Five of a kind"; 
            }
            return "Full house";
        }
        if(x == 3 && y == 0) {
            if(j>=1) {
                return "Four of a kind"; 
            }
            return "Three of a kind";
        }
        if(x == 2 && y == 2) {
            if(j>=2) {
                return "Four of a kind"; 
            }
            if(j==1) {
                return "Full house"; 
            }
            return "Two pair";
        }
        if(x == 2 && y == 0) {
            if(j>=1) {
                return "Three of a kind"; 
            }
            return "One of a kind";
        }
        if(count == 1 && x == 0 && y == 0) {
            if(j==1) {
                return "One of a kind"; 
            }
            return "High card";
        }

        return "bruh card";
    }

    public static int checkBiggerCard(ArrayList<Integer> array1, ArrayList<Integer> array2) {
        for (int i=0; i<array1.size(); i++) {
            if(array1.get(i) != array2.get(i)) {
                return Integer.compare(array1.get(i), array2.get(i));
            }
            
        }
        return 0;
    }

    public static void sorttwoArray(ArrayList<ArrayList<Integer>> cardArray, ArrayList<Integer> array2) {
        for(int i = 0; i<cardArray.size()-1; i++) {
            int pos = i;
            for(int j = i+1; j<cardArray.size(); j++) {
                if(checkBiggerCard(cardArray.get(pos), cardArray.get(j)) > 0) {
                    pos = j;
                }
            }
            if(pos != -1) {
                ArrayList<Integer> temp = cardArray.get(i);
                Integer cur = array2.get(i);
                cardArray.set(i, cardArray.get(pos));
                cardArray.set(pos, temp);
                array2.set(i, array2.get(pos));
                array2.set(pos, cur);
            }
            
        }
    }
    public static void main(String[] args) {
        String filePath = "Bigtest7.txt";

        Card card = new Card();

        String[] type = {"Five of a kind", "Four of a kind", "Full house", "Three of a kind", "Two pair", "One of a kind", "High card"};
        for(int i=0; i<type.length; i++) {
            card.putCard(type[i]);
        }
        
        
        int sum = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line from the file until there are no more lines
            while ((line = reader.readLine()) != null) {
                String[] word_split = {};
                word_split = line.split(" +");
                ArrayList<Integer> cardArray = cardToArray(word_split[0]);
                ArrayList<Integer> tempArray = cardToArray(word_split[0]);
                Collections.sort(cardArray);
                //System.out.println(cardArray + " " + word_split[1]);
                //System.out.println(cardType(cardArray));
                
                String cname = cardType(cardArray);
                card.addTypeHandBid(cname, tempArray, Integer.parseInt(word_split[1]));
            }
        } catch (IOException e) {
            // Handle any exceptions that may occur during file reading
            e.printStackTrace();
        }

        for(int i=0; i<type.length; i++) {
            sorttwoArray(card.getTypeHands(type[i]), card.getTypeBids(type[i]));
        }
        
        int res = 0;
        int x = 1;
        for(int i=type.length-1; i>=0; i--) {
           for(int j=0; j<card.getTypeBids(type[i]).size(); j++) {
                res = res + (x * card.getTypeBids(type[i]).get(j));
                x++;
           }
        }
        System.out.println(res);
    }
}
