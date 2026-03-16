public class User {

    private String userIP;
    private int level;

    public User(int level) {
        this.level = level;
    }

    private boolean checkIPValidity(String ip) {
        if (!ip.startsWith("99")) {
            return false;
        }

        int dotCount = 0;

        for (int i = 0; i < ip.length(); i++) {
            if (ip.charAt(i) == '.') {
                dotCount++;

                if (i == ip.length() - 1) { // if the dot we found is last return false
                    return false;
                }

                if (ip.charAt(i + 1) == '.') { // if two dots are continous
                    return false;
                }
            }
        }

        return dotCount == 3;

        // String regex = "^99\\d*(\\.\\d+){3}$";
        // return ip.matches(regex);
    }

    public void setIP(String ip){
        if (checkIPValidity(ip)){
            this.userIP = ip; 
        } 
    }
}
