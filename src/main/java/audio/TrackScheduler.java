package audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.LinkedList;
import java.util.Queue;

public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final Queue<AudioTrack> tracks;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.tracks = new LinkedList<AudioTrack>();
    }

    //TODO: Разобраться шо тут таки нада делать

    @Override
    public void onPlayerPause(AudioPlayer player) {
        super.onPlayerPause(player);
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        super.onPlayerResume(player);
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        super.onTrackStart(player, track);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (tracks.isEmpty()) {
            return;
        }

        player.playTrack(tracks.remove());

        System.err.println("IT FINISHED");
    }

    public void queue(AudioTrack track) {
        tracks.add(track);
    }

    public void play() {
        if (player.getPlayingTrack() != null) {
            return;
        }

        this.player.playTrack(tracks.remove());
    }

    public void skip() {
        player.stopTrack();

        if (tracks.isEmpty()) {
            return;
        }

        player.playTrack(tracks.remove());
    }
}
