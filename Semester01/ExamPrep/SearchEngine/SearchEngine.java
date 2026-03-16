package searchengine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class SearchEngine {

    Map<String, List<WebPage>> invertedIndex;
    String filepath;

    public SearchEngine() throws IOException {
        // Start WoedMap class
        DataProcessor wordMap = new DataProcessor();
        invertedIndex = wordMap.processData(); 
    }

    public List<String> search(String searchTerm) {

        String decoded = java.net.URLDecoder.decode(searchTerm, StandardCharsets.UTF_8);

        SearchHandle searchHandle = new SearchHandle(); 
        Map<Integer, List<WebPage>> searchGroupMap = searchHandle.makeSearchGroups(decoded, invertedIndex);
        List<WebPage> scoredSearchList = searchHandle.scoreSearchGroups(searchGroupMap); 
        List<String> httpLinkList = searchHandle.httpList(scoredSearchList);

        return httpLinkList;

 
    }

}
