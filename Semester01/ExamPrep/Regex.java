/*
 * JAVA REGEX CHEATSHEET
 * * Note: Backslashes must be doubled inside Java Strings (e.g., "\\d")
 */

// --- CHARACTER CLASSES ---
// "[abc]"     -> Matches a, b, or c.                  Example: "cat".matches("[bc]at") -> true
// "[^abc]"    -> Any char except a, b, or c.          Example: "dat".matches("[^bc]at") -> true
// "[a-z]"     -> Any lowercase letter.                Example: "m".matches("[a-z]") -> true
// "."         -> Any single character.                Example: "a".matches(".") -> true

// --- PREDEFINED CLASSES ---
// "\\d"       -> Any digit [0-9].                     Example: "5".matches("\\d") -> true
// "\\D"       -> Any non-digit.                       Example: "A".matches("\\D") -> true
// "\\s"       -> Any whitespace.                      Example: " ".matches("\\s") -> true
// "\\w"       -> Word char [a-zA-Z_0-9].              Example: "9".matches("\\w") -> true
// "\\W"       -> Non-word char.                       Example: "!".matches("\\W") -> true

// --- QUANTIFIERS ---
// "X?"        -> X, zero or one time.                 Example: "color".matches("colours?") -> true
// "X*"        -> X, zero or more times.               Example: "goooal".matches("go*al") -> true
// "X+"        -> X, one or more times.                Example: "goal".matches("go+al") -> true
// "X{n}"      -> X, exactly n times.                  Example: "123".matches("\\d{3}") -> true
// "X{n,}"     -> X, at least n times.                 Example: "1234".matches("\\d{2,}") -> true
// "X{n,m}"    -> X, between n and m times.            Example: "123".matches("\\d{2,4}") -> true

// --- BOUNDARIES ---
// "^"         -> Start of line.                       Example: "Start".matches("^Start.*") -> true
// "$"         -> End of line.                         Example: "End".matches(".*End$") -> true
// "\\b"       -> Word boundary.                       Example: " cat ".matches(".*\\bcat\\b.*") -> true

// --- LOGIC / GROUPING ---
// "X|Y"       -> X or Y.                              Example: "yes".matches("yes|no") -> true
// "(X)"       -> Grouping.                            Example: "abab".matches("(ab){2}") -> true


// --- QUANTIFIER LOGIC & EXAM TRAPS ---
// "*"         -> Zero or more (Allows field to be empty).              Example: "".matches("a*") -> true
// "+"         -> One or more (Requires at least one character).        Example: "".matches("a+") -> false
// "?"         -> Zero or one (Makes the character optional).           Example: "ac".matches("ab?c") -> true
// "{n}"       -> Exactly n times.                                      Example: "123".matches("\\d{3}") -> true
// "{n,}"      -> At least n times.                                     Example: "12345".matches("\\d{3,}") -> true


/*
 * PRACTICAL JAVA REGEX EXAMPLES
 * Format: Purpose -> Regex String -> Example Match
 */

// 1. Exact Word Match
// Purpose: Find the word "Java" regardless of surrounding text
String regex1 = ".*\\bJava\\b.*";
// Matches: "I love Java programming"

// 2. Simple Email Validation
// Purpose: Basic username@domain.extension
String regex2 = "^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}";
// Matches: "user.name@email.com"

// 3. International Phone Number
// Purpose: Country code (+), then 10-15 digits
String regex3 = "^\\+\\d{1,3}\\d{10,15}$";
// Matches: "+441234567890"

// 4. IPv4 Address
// Purpose: Four sets of 1-3 digits separated by dots
String regex4 = "^(\\d{1,3}\\.){3}\\d{1,3}$";
// Matches: "192.168.1.1"

// 5. Date (YYYY-MM-DD)
// Purpose: Standard ISO date format
String regex5 = "\\d{4}-\\d{2}-\\d{2}";
// Matches: "2026-01-03"

// 6. Strong Password Requirement
// Purpose: At least 1 digit, 1 lowercase, 1 uppercase, 1 special char, min 8
// chars
String regex6 = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
// Matches: "P@ssw0rd2026"

// 7. Extracting HTML Tag Content
// Purpose: Content between <h1> tags
String regex7 = "<h1>(.*?)</h1>";
// Matches: "<h1>Welcome</h1>" (Group 1: Welcome)

// 8. Hex Color Code
// Purpose: # followed by 3 or 6 hex characters
String regex8 = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
// Matches: "#FFFFFF" or "#3c9"

// 9. Numeric Value (Integer or Decimal)
// Purpose: Optional minus sign, digits, and optional decimal part
String regex9 = "-?\\d+(\\.\\d+)?";
// Matches: "-123.45" or "50"

// 10. Social Security Number (SSN)
// Purpose: 3 digits, dash, 2 digits, dash, 4 digits
String regex10 = "\\d{3}-\\d{2}-\\d{4}";
// Matches: "123-45-6789"

// 11. 24-Hour Time Format
// Purpose: HH:MM (00:00 to 23:59)
String regex11 = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
// Matches: "13:45" or "09:00"

// 12. Removing Duplicate Whitespace
// Purpose: Find 2 or more spaces/tabs
String regex12 = "\\s{2,}";
// Matches: " " (Useful for: str.replaceAll("\\s{2,}", " "))

// 13. URL Slug
// Purpose: Only lowercase letters, numbers, and hyphens
String regex13 = "^[a-z0-9-]+$";
// Matches: "how-to-learn-java-regex"

// 14. File Extension Check
// Purpose: Ends in .jpg, .png, or .gif (Case insensitive)
String regex14 = "(?i).*\\.(jpg|png|gif)$";
// Matches: "vacation_photo.PNG"

// 15. Credit Card (Basic)
// Purpose: 4 sets of 4 digits separated by optional space or dash
String regex15 = "^\\d{4}[ -]?\\d{4}[ -]?\\d{4}[ -]?\\d{4}$";
// Matches: "1111-2222-3333-4444"

// General use
public boolean isValidWord(String input) {
    // Example regex: "^[a-zA-Z]+$" checks if the word contains only letters
    return input.matches("^[a-zA-Z]+$");
}

/*
 * 
 * String input = "2024";
 * String regex = "\\d{4}";
 * 
 * if (input.matches(regex)) {
 * System.out.println("Valid Year");
 * }
 * 
 * 
 * 
 */