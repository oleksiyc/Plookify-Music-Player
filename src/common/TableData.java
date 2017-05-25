package common;

import javafx.beans.property.SimpleStringProperty;

public class TableData {

    public  SimpleStringProperty trackid = new SimpleStringProperty();
    public  SimpleStringProperty trackname = new SimpleStringProperty();
    public  SimpleStringProperty artist = new SimpleStringProperty();
    public  SimpleStringProperty genre = new SimpleStringProperty();
    public  SimpleStringProperty duration = new SimpleStringProperty();
    public  SimpleStringProperty playlistid = new SimpleStringProperty();
    
    

    public String getTrackID() {
        return trackid.get();
    }

    public void setTrackID(String trackidStr) {
        trackid.set(trackidStr);
    }
    
    public String getTrackName() {
        return trackname.get();
    }

    public void setTrackName(String tracknameStr) {
        trackname.set(tracknameStr);
    }

    public String getArtist() {
        return artist.get();
    }

    public void setArtist(String artistStr) {
        artist.set(artistStr);
    }

    public String getGenre() {
        return genre.get();
    }

    public void setGenre(String genreStr) {
        genre.set(genreStr);
    }

    public String getDuration() {
        return duration.get();
    }

    public void setDuration(String durationStr) {
        duration.set(durationStr);
    }
    
     public String getPlaylistID() {
        return playlistid.get();
    }

    public void setPlaylistID(String playlistidStr) {
        playlistid.set(playlistidStr);
    }
    

}
