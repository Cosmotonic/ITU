package searchengine;

// import java.io.BufferedReader;
// import java.io.InputStreamReader;
// import java.net.URL;
// import java.net.URLConnection;
import java.util.*;

public class SearchHandle {

    private Map<Integer, String[]> searchGroupWordMap;
    private TreeMap<Integer, WebPage> groupScoreMap;
    private double okapi_k; 
    private double okapi_b;

    public SearchHandle() {
        searchGroupWordMap = new HashMap<>();
        groupScoreMap = new TreeMap<>();
        this.okapi_k = 1.2;
        this.okapi_b = 0.75; 
    }

    public Map<Integer, List<WebPage>> makeSearchGroups(String searchTerm, Map<String, List<WebPage>> invertedIndex) {

        String[] searchGroups = searchTerm.trim().split("(?i)\\s+OR\\s+");
        Map<Integer, List<WebPage>> searchGroupPageMap = new HashMap<>();
        Integer groupNumber = 1;

        for (String wordsString : searchGroups) {
            String[] singleWordsInGroup = wordsString.split("\\s+"); 
            Set<WebPage> intersectingPages = null;

            for (String singleWord : singleWordsInGroup) {
                List<WebPage> pagesForWord = invertedIndex.get(singleWord);
                if (pagesForWord != null) {
                    if (intersectingPages == null) {
                        intersectingPages = new HashSet<>(pagesForWord);
                    } else
                        intersectingPages.retainAll(pagesForWord);
                } else {
                    intersectingPages = null;
                }
            }

            if (intersectingPages != null) {
                searchGroupPageMap.put(groupNumber, new ArrayList<>(intersectingPages)); // adding the result to a map
                searchGroupWordMap.put(groupNumber, singleWordsInGroup);
                groupNumber++;
            }
        }

        return searchGroupPageMap;
    }

    public List<String> httpList(List<WebPage> pages) {
        List<String> httpLinkList = new ArrayList<String>();

        for (WebPage site : pages) {
            httpLinkList.add(String.format("{\"url\": \"%s\", \"title\": \"%s\"}",
                    site.getUrl(), site.getSiteName()));
        }

        return httpLinkList;
    }

    // BUG: Edge case here: If two groups gets the same exact score only one gets chosen. 
    public List<WebPage> scoreSearchGroups(Map<Integer, List<WebPage>> searchGroupPageMap) {

        for (int searchGroup : searchGroupPageMap.keySet()) {
            List<WebPage> pagesInGroup = searchGroupPageMap.get(searchGroup);
            String[] WordsInGroup = searchGroupWordMap.get(searchGroup);

            for (WebPage singlePageInGroup : pagesInGroup) {
                int pageScore = 0;
                for (String singleWordInGroup : WordsInGroup) {
                    int wordFrequencyOnPage = singlePageInGroup.getWordFrequency(singleWordInGroup);
                    pageScore = pageScore + wordFrequencyOnPage;
                }
                groupScoreMap.put(pageScore, singlePageInGroup);
            }
        }

        List<WebPage> scoredOrderedPageList = okapiBM25(groupScoreMap); 
        // List<Page> scoredOrderedPageList = converteTreeMapToList(groupScoreMap);

        return scoredOrderedPageList;
    }

    public TreeMap<Integer, WebPage> getGroupScoreMap() {
        return groupScoreMap;
    }

    private List<WebPage> okapiBM25(TreeMap<Integer, WebPage> groupScoreMap) { 
        // The wiki explaining the system. https://en.wikipedia.org/wiki/Okapi_BM25
        int totalWords = 0;

        Map<String, Integer> pageCountMap = new HashMap<>();

        for (int pageScoreKey : groupScoreMap.keySet()) {   
            pageCountMap.put(groupScoreMap.get(pageScoreKey).getUrl(), groupScoreMap.get(pageScoreKey).getWordsOnPage().size()); 
        }

        int avgWordCountPerPage = totalWords / groupScoreMap.size();

        int urlsWithThisWord = groupScoreMap.size();
        Map<Double, WebPage> orderedPageTreeMap = new TreeMap<>(); // should take inputs from all grouped words.

        int totoalUrlCountInDataSet = WebPage.getTotalPages(); 

        double IDF = Math.log((totoalUrlCountInDataSet - urlsWithThisWord + 0.5) / (urlsWithThisWord + 0.5));

        for (int pageScoreKey : groupScoreMap.keySet()) {

            int wordOccurenceOnPage =  pageScoreKey;

            // Generate BM25 - Document Length Normalization, punish long sites
            int docLength = pageCountMap.get(groupScoreMap.get(pageScoreKey).getUrl()); 
            double BM25_score = IDF * (wordOccurenceOnPage * (okapi_k + 1)) / (wordOccurenceOnPage + okapi_k * (1 - okapi_b + okapi_b * docLength / avgWordCountPerPage));
            // System.out.println("BM Score: " + BM25_score + ". Page URL: " + page.getUrl());
            orderedPageTreeMap.put(BM25_score, groupScoreMap.get(pageScoreKey));
        }

        List<WebPage> pageListOrder = new ArrayList<>(); 
        for (WebPage page : orderedPageTreeMap.values()){
            pageListOrder.add(page); 
        }

        //System.out.println("ordered size = " + orderedPageTreeMap.size());
        Collections.reverse(pageListOrder); 
        return pageListOrder; 
    }
}