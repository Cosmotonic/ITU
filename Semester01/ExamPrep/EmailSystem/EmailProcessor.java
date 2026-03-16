import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class EmailProcessor {
    private Email[] emails;

    public EmailProcessor(String[] e) {
        this.emails = new Email[e.length];

        for (int i = 0; i < e.length; i++) {
            Email em = new Email(e[i]);
            this.emails[i] = em;
        }
    }

    // 2.5
    public void filteredEmails() {
        Set<String> filteredDomains = new HashSet<>();
        Scanner sc = new Scanner(System.in);
        String input = "";

        while (true) {
            input = sc.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            filteredDomains.add(input);
        }

        for (int i = 0; i < emails.length; i++) {
            // If the set contains this email's domain, replace it
            if (filteredDomains.contains(emails[i].getDomain())) {
                emails[i] = new Email("Joen@default.com");
            }
        }
    }

    // 2.6
    public Map<String, Integer> countDomains() {
        HashMap<String, Integer> domainCountMap = new HashMap<>();
        for (Email mail : emails) {
            // merge checks for existing, if not add 1, if yes then ad 1 to existing value
            domainCountMap.merge(mail.getDomain(), 1, Integer::sum);
        }
        return domainCountMap;
    }

    // 2.7
    public void print() {
        // find biggest key
        HashMap<String, Integer> domainCountMap = countDomains();
        Integer mostFrequent = Collections.max(domainCountMap.values());

        // make a list of all emails with the domain
        List<String> mostPopulairDomains = new ArrayList<>();

        for (String domain : domainCountMap.keySet()) {
            if (domainCountMap.get(domain) == mostFrequent) {
                mostPopulairDomains.add(domain);
            }
        }

        // du skal finde det mest populære domain. Så lav en liste af domains.
        for (Email mail : emails) {
            if (mostPopulairDomains.contains(mail.getDomain()) && !mail.getDomain().equals("default.com")) {
                System.out.println(mail.toString());
            }
        }
    }

    public static void main(String[] args) {
        Email email = new Email("kap1@itu.dk");
        email.print();
    }
}
