import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {
    private String address;

    public Email(String address) {
        this.address = "joen@default.com";

        String mailRegex = "^[\\w.-]{4}@[\\w.-]+\\.[a-z]+";

        if (address.matches(mailRegex)) {
            this.address = address;
        }

        System.out.println(this.address);
    }

    public String getDomain() {
        // return this.address.split("@")[1];

        Pattern pattern = Pattern.compile("@(.+)$");
        Matcher matcher = pattern.matcher(this.address);
 
        if (matcher.find()) {
            return matcher.group(1);
        }

        return "";
    }

    public String toString(){
        // return "+++" + this.address.substring(3, this.address.length());

        String subString = this.address.substring(3); 
        System.out.println("+++"+subString);

        return "+++"+subString;  
    }
}
