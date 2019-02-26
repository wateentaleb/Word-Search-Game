//import java.io.IOException;
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import static java.lang.System.out;
//
//public class SearchTest {
//    private WordSearchOperations wordSearch;
//    private String fileName = "/Users/wateentaleb/IdeaProjects/assignment2/0005.txt";
//    private String wordTextFile = "/Users/wateentaleb/IdeaProjects/assignment2/words.txt";
//    private ArrayList<Word> findWords;
//    Word word;
//    private String test = "FELINE";
//    private String hello = "";
//
//    // constructor
//    public SearchTest(String fileName, String wordTextFile) {
//        try {
//            wordSearch = new WordSearchOperations(fileName, wordTextFile);
//            this.hello = wordSearch.dict.get(test).getKey();
//        } catch (IOException e) {
//            System.out.println("An error occurred when opening word search text file " + fileName + " or " + wordTextFile);
//            System.exit(0);
//        } catch (DictionaryException e) {
//            System.out.println(e.getMessage());
//            System.exit(0);
//        }
//
//    }
//
////    public void findWords(String line){
////        Word word;
////        String[] wordStrings = line.split("\\s+");
////        for (int i = 0; i<wordStrings.length; i++){
////           System.out.println(checkWords);
////        }
////    }
////
////    public ArrayList()
//
//    public static void main(String[] args) {
//        String wow = "";
////        String string = "FELI";
////        String[] Answers = {"CANINE",
////                "BONE",
////               "LINE",
////                "NINE",
////                "NONE",
////                "FANCY",
////                "ENTER"};
////        String fin[] = new String[5];
//        String string = "  LNOMADIC";
//        String[] Answers = {
//                "NOMAD",
//                "NOMADIC"
//        };
//        String fin[] = new String[5];
//        String split[] = new String[10];
//
//        split = string.split("\\s+");
//
//        // now its all in one string
//        for (int z = 0; z < split.length; z++){
//            wow += split[z];
//        }
//
//        for (int i = 0; i <wow.length()-4; i++)
//            for (int j = 4; i <= wow.length(); j++) {
//                // got a portion of the string,
//                // now i need to scna the Answers array to find if theres any match
//                String compare = wow.substring(i, j);
//                for (int x = 0; x < Answers.length; x++) {
//                    if (compare.equals(Answers[i])) {
//                        // in the code wed add it,
//                        // but im gonna output to see whats happening
//                        System.out.println("string matched with answers key " + compare);
//                    }
//                }
//            }
//
//        System.out.println("The split size is "+ split.length);
//
//
//
////        for (int i = 0; i<string.length()-4; i++){
////            for (int j = 4; i<=string.length(); j++){
////                for (int k = 0; i<split.length && i<Answers.length; i++){
////                    String compare = split[i].substring(i,j);
////                    while(compare.equals(Answers[i])){
////                        fin[i] = compare;
////                    }
////                }
////            }
////        }
////        for (int i = 0; i <=split.length-4; i++) {
////            for (int j = 4 + i; j <=split.length; j++) {
////                String compare = split[i].substring(i, j);
////                for (int k = 0; k<Answers.length; k++){
////                    if(string.substring(i).equals(Answers[i])){
////                        fin[i] = string.substring(i);
////                    }
////                }
////                System.out.println(compare);
// //           }
//   //     }
//        for (int i = 0; i<fin.length; i++){
//            System.out.println(fin[i]);
//        }
//
//    }
////        String string = "FELI  NE";
////        String[] Answers = {"CANINE",
////                "BONE",
////                "LINE",
////                "NINE",
////                "NONE",
////                "FANCY",
////                "ENTER"};
////        Word word;
//
//
////        String[] wordStrings = string.split("\\s+");
////
////        for (int i =0; i<wordStrings.length; i++){
////            System.out.println(wordStrings[i]);
////        }
//
//
////        for (int i = 0; i < wordStrings.length; i++) {
////            for (int k = 0; i < 4; i++) {
////                for (int j = 0; j <= wordStrings.length; j++) {
////                    String compare = string.substring(i, j);
////                    System.out.println("The substring cut up from check words are" + compare);
////                    if (compare.equals(Answers[j])) {
////                        System.out.println("compare string is equal to" + Answers[j]);
////                    }
////                    System.out.println("compare: " + compare + "doesnt equal answers " + Answers[j]);
////                }
////            }
////        }
//
//
//    //}
//}
//
//
