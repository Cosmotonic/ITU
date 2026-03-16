public class RegUser extends User {
    private int region;

    public RegUser(int level, int region) {
        super(level);
        this.region = region;
    }

    private boolean checkIPValidity(String ip){
        String[] ipSplit = ip.split("\\."); 

        for (String split : ipSplit){
            if (split.equals("111")){
                return false; 
            }
        }
        return true; 
    }

    @Override
    public void setIP(String ip){
        if (checkIPValidity(ip)){ 
            super.setIP(ip); 
        }
    }



}
