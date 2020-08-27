package org.bot.yabot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.LinkedList;
import java.util.Queue;

public class TrackScheduleAdapter extends AudioEventAdapter {
  private final AudioPlayer player;
  private final Queue<AudioTrack> tracks;

  public TrackScheduleAdapter(AudioPlayer player) {
    this.player = player;
    this.tracks = new LinkedList<>();
  }

  @Override
  public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
    if (tracks.isEmpty()) {
      return;
    }

    player.playTrack(tracks.remove());
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

  public void pause() {
    if (player.isPaused()) {
      return;
    }

    player.setPaused(true);
  }

  public void resume() {
    if (!player.isPaused()) {
      return;
    }

    player.setPaused(false);
  }

  public boolean isPlaying() {
    return (player.getPlayingTrack() != null) && !player.isPaused();
  }

  public boolean isPaused() {
    return (player.getPlayingTrack() != null) && player.isPaused();
  }
}
