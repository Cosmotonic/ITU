public class OnlineCourse extends Course {
    public void increase(){
        super.increase();
        super.increase();
    }

    public static void main(String[] args) {
        Course c; 
        c = new OnlineCourse(); 
        for (int i = 1; i<10; i++){c.increase();}
        System.out.println(c.getCapacity());
    }
}
