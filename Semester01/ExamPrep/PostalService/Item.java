

public class Item{

    private String category; 
    private String address; 
    private int weight; 

    public Item(String category, String address, int weight){
        this.category = category; 
        this.address = address; 
        this.weight = weight; 
    }

    public String getCategory() {
        return category;
    }

    public String getAddress() {
        return address;
    }

    public int getWeight() {
        return weight;
    }

    

}