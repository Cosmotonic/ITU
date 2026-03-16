import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// 6 
public class MusicManager {
    private Set<Song> allSongs;

    public musicManager() {
        this.allSongs = new HashSet<>();
    }

    // 7
    public void addSong(Song s) {
        if (allSongs.contains(s)) {
            System.out.println("The song is already in the set");
            return;
        }
        allSongs.add(s);
    }

    // 8 returns a list of songs by one artist.
    public List<Song> makePlayList(String a) {
        List<Song> playList = new ArrayList<>();
        playList = allSongs.stream().filter(name -> name.getArtist().equals(a)).collect(Collectors.toList());
        return playList;
    }

    // 9 returns true if there are songs produced in the given year
    public boolean remove(int y) {

        Iterator<E> songsItr = allSongs.iterator();
        while (songsItr.hasNext()) {
            Song song = songsItr.next();
            if (song.getYear() == y) {
                return true;
            }
        }
    }

    // 11 merge instead of containsKey
    public Map<String, Integer> artistSongs() {
        Map<String, Integer> artistSongCountMap = new HashMap<>();

        for (Song song : allSongs) {
            artistSongCountMap.merge(song.getArtist(), 1, Integer::sum);
        }
        return artistSongCountMap;
    }

    // 12
    public void playWithAds(List<Song> l) {
        for (int i = 0; i < l.size(); i++) {
            l.get(i).play();
            if ((i + 1) % 6 == 0) {
                System.out.println("playing advertisements");
            }
        }
    }

    // 13 Smallest index compare 
    public String findMostPopular(List<Song> l) {

        int highestLikes = -1;
        String mostLikedSong = "";
        int lowestPrioIndex = 0;

        for (Song song : l) {
            if (song.getLikes() > highestLikes) {
                highestLikes = song.getLikes();
                mostLikedSong = song.getTitle();
            }
        }

        return mostLikedSong;

    }

    // 14 playTopHits order list sort comparaing 
    public void playTopHits(List<Song> l) {
        List<Song> playList = l.stream().sorted(Comparator.comparingInt(Song::getLikes).reversed())
                .collect(Collectors.toList());

        int i = 0;
        Iterator<Song> playListIterator = playList.iterator();

        while (playListIterator.hasNext() && i < 20) {
            Song song = playListIterator.next();
            song.play();
            song.like();
            l.remove(song);
            playListIterator.remove(); // not nessecary but for notes. 
            i++; 
        }
    }

    // 10

    public static void main(String[] args) {
        Song song1 = new Song("Driving home for christmas", "Kasper", 1998);
        Song song2 = new Song("Last Christmas", "Wham", 1995);

        MusicManager musicManager = new MusicManager();
        musicManager.addSong(song1);
        musicManager.addSong(song2);

    }
}