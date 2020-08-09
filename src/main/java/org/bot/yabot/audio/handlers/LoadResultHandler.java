package org.bot.yabot.audio.handlers;

import org.bot.yabot.audio.TrackScheduleAdapter;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class LoadResultHandler implements AudioLoadResultHandler {
    private final TrackScheduleAdapter trackScheduleAdapter;

    public LoadResultHandler(final TrackScheduleAdapter trackScheduleAdapter) {
        this.trackScheduleAdapter = trackScheduleAdapter;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        trackScheduleAdapter.queue(track);
        trackScheduleAdapter.play();
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        for (AudioTrack track : playlist.getTracks()) {
            trackScheduleAdapter.queue(track);
        }
        trackScheduleAdapter.play();
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
