package searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebPage {

    private String siteName;
    private String url;
    private int frequency;
    private List<String> wordsOnPage;
    private Map<String, Integer> frequencyMap = new HashMap<>();

    private static int totalPages = 0;

    public WebPage(String siteName, String url) {

        this.wordsOnPage = new ArrayList<>(); 
        this.siteName = siteName;
        this.url = url;

        totalPages++; // increments every time a Page is created
    }

    public void incrementWordFrequency(String word) {
        frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
        frequency++; // maintain backwards compatibility
    }

    public int getWordFrequency(String word) {
        return frequencyMap.getOrDefault(word, 0);
    }

    public Map<String, Integer> getFrequencyMap() {
        return frequencyMap;
    }

    public List<String> getWordsOnPage() {
        return wordsOnPage;
    }

    public void addWordToWordsOnPage(String newWordOnSite) {
        wordsOnPage.add(newWordOnSite);
    }

    public String getSiteName() {
        return siteName;
    }

    public String getUrl() {
        return url;
    }

    public void incrementFrequency() {
        frequency++;
    }

    public int getFrequency() {
        return frequency;
    }

    public static int getTotalPages() {
        return totalPages;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof WebPage))
            return false;

        WebPage other = (WebPage) obj;
        return url.equals(other.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
