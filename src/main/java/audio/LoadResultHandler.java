package audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class LoadResultHandler implements AudioLoadResultHandler {
    private final TrackScheduler trackScheduler;

    public LoadResultHandler(final TrackScheduler trackScheduler) {
        this.trackScheduler = trackScheduler;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        trackScheduler.queue(track);
        trackScheduler.play();
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        for (AudioTrack track : playlist.getTracks()) {
            trackScheduler.queue(track);
        }
        trackScheduler.play();
    }

    @Override
    public void noMatches() {
        System.err.println("NOMATHES");
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        System.err.println("LOAD FAILED" + exception.getMessage());
    }
}
