// 1
public class Song {
    private String title; 
    private String artist; 
    private int likes; 
    private int year; // production year

    public Song(String title, String artist, int year){
        this.title = title; 
        this.artist = artist; 
        this.year = year; 
        this.likes = 0; 
    }

    // 2 
    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getLikes() {
        return likes;
    }

    public int getYear() {
        return year;
    }

    // 3
    public void like(){
        this.likes+=1; 
    }

    // 4 
    public boolean compare(Song anotherSong){
        return (this.likes>anotherSong.getLikes());
    }

    // 5 Print
    public void play(){
        System.out.printf("The song Title: %s, Artist: %s, Year: %d is playing!", this.title, this.artist, this.year);
    }
    
}
