import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextProcessor {
    private Map<String, String> dictionary; // words from one language to a second language

    public TextProcessor(){
        this.dictionary = new HashMap<>(); 
    }

    // NOTE assume the text is always existing. 

    public void updateDictionary(String w1, String w2){

        // check for single words
        String[] w1Array = w1.split("\\s"); 
        String[] w2Array = w2.split("\\s"); 

        if (w1Array.length<2 && w1 != null && w1.length()>=1 && w2Array.length<2 && w2 != null  && w2.length()>=1){
            dictionary.put(w1, w2); 
        }else {
            throw new IllegalArgumentException(String.format("either %s or %s is not valid", w1, w2));
        }
    }

    public String[] translateWords(String[] words){
        // look up each word in dictionary. 
        // words : hello, goodbye, greetigns, christmas 

        String[] translatedWords = String[words.length]; 

        for (int i=0; i<words.length; i++){
            String translatedWord = dictionary.get(words[i]); 
            translatedWords[i] = translatedWord; 
        }
        return translatedWords; 
    }

    public boolean checkText(String text){
        String[] allWordsInText = text.split("\\s"); 
        List<String> words = Arrays.asList(allWordsInText);
        return words.stream().allMatch(word -> dictionary.get(word)); 
    }

    //  return a string of word by word translation. 
    public String translateText (String text){
        String translatedSentence = ""; 

        if (checkText(text)){
            String[] wordsToTranslate = text.split("\\s"); 
            String [] translatedWords = translateWords(wordsToTranslate); 
            for (String word : translatedWords){
                translatedSentence += word + " "; 
            }
        }

        return "***"; 
    }

    public void mapTexts(String t1, String t2){

        if (t1.length.equals(t2.length)){
            String[] t1Split = t1.split("\\s"); 
            String[] t2Split = t2.split("\\s"); 
            for (int i=0; i<t1.length; i++){
                dictionary.merge(t1Split[i], t2Split[i]); 
            }
        }

        System.out.println("The texts do not seem to corrospond");
    }

    public static void main(String[] args) {
        TextProcessor textProcessor = new TextProcessor(); 
        textProcessor.updateDictionary("apple", "wig");
        textProcessor.updateDictionary("orange", "cup");
        System.out.println(textProcessor.translateText("apple orange apple")); 
    }

}
