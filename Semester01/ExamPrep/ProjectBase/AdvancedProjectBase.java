import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdvancedProjectBase extends ProjectBase {

    public AdvancedProjectBase(List<String> projectTitles) {
        super(projectTitles);
    }

    @Override
    public Set<String> search(String query) {
        Set<String> matches = new HashSet<>();

        String[] queryStringsrray = query.split(",");

        for (String queryString : queryStringsrray) {
            matches.addAll(super.search(queryString.trim())); // Trim removes empty space
        }

        return matches;
    }
}
