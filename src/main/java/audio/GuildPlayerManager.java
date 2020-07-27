package audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.audio.AudioSendHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GuildPlayerManager {
    private final AudioPlayerManager playerManager;
    private final Map<String, GuildAudioService> audioServiceMap;
    private final Set<String> initGuilds;

    public GuildPlayerManager(final AudioPlayerManager playerManager) {
        //TODO: Сделать одну мэму, вынести все хендлеры в другое место
        this.initGuilds = new HashSet<>();
        this.playerManager = playerManager;
        this.audioServiceMap = new HashMap<>();
    }

    public void init(final String guildId) {
        if (initGuilds.contains(guildId)) {
            return;
        }

        var player = playerManager.createPlayer();
        var trackScheduler = new TrackScheduler(player);
        var loadResultHandler = new LoadResultHandler(trackScheduler);
        var sendHandler = new AudioPlayerSendHandler(player);

        player.addListener(trackScheduler);

        var audioService = GuildAudioService.builder()
                .player(player)
                .audioPlayerSendHandler(sendHandler)
                .trackScheduler(trackScheduler)
                .loadResultHandler(loadResultHandler)
                .build();

        audioServiceMap.put(guildId, audioService);
        initGuilds.add(guildId);
    }

    public void addTrack(final String path, final String guildId) {
        final var loadResultHandler = audioServiceMap.get(guildId).getLoadResultHandler();

        playerManager.loadItem(path, loadResultHandler);
    }

    public void skipTrack(final String guildId) {
        final var trackScheduler = audioServiceMap.get(guildId).getTrackScheduler();

        trackScheduler.skip();
    }

    public AudioSendHandler getSendHandler(final String guildId) {
        final var sendHandler = audioServiceMap.get(guildId).getAudioPlayerSendHandler();

        return sendHandler;
    }
}
