
// 1 

import java.util.HashMap;
import java.util.Map;

public class TextReader {
    private String text;

    public TextReader(String text) {
        this.text = text;

    }

    // 2 replace occurances of s with t in this.text
    public void replace(String s, String t) {
        this.text = this.text.replace(s, t); 
    }

    // 3
    public Map<String, Integer> counts() {

        Map<String, Integer> wordCountMap = new HashMap<>();
        String[] words = this.text.split(" ");

        for (String word : words) {
            wordCountMap.merge(word, 1, Integer::sum);
        }
        return wordCountMap;
    }
}
