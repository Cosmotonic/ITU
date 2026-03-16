import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProjectBase {
    private List<String> projectTitles;

    public ProjectBase(List<String> projectTitles) {
        this.projectTitles = projectTitles;
    }

    // 2
    public void addTitle(String t) {
        projectTitles.add(t);
    }

    // 3
    public Set<String> search(String query) {

        Set<String> matches = new HashSet<>();
        for (String projectTitle : projectTitles) {
            if (projectTitle.contains(query)) {
                matches.add(projectTitle);
            }
        }

        return matches;
    }

}
