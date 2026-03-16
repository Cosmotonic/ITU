import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReaderWithFilter extends TextReader {
    private List<String> filteredWords;

    public ReaderWithFilter(String text, List<String> filteredWords) {
        super(text);
        this.filteredWords = filteredWords;
    }

    // 5
    public void filter() {
        for (String filtereWord : this.filteredWords) {
            replace(filtereWord, "***");
        }
    }

    // 6 returns a map word, count unless word is in filteredWords.
    public Map<String, Integer> counts() {

        Map<String, Integer> wordCountMap = super.counts();
        for (String word : this.filteredWords) {
            wordCountMap.remove(word);
        }

        return wordCountMap;
    }
}
