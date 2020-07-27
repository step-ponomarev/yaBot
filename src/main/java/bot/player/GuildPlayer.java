package bot.player;

import audio.LoadResultHandler;
import audio.TrackScheduler;
import bot.handler.AudioPlayerSendHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import net.dv8tion.jda.api.audio.AudioSendHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GuildPlayer {
    private final AudioPlayerManager playerManager;
    private final Map<String, AudioLoadResultHandler> loadResultHandlerMap;
    private final Map<String, AudioEventAdapter> schedulerMap;
    private final Map<String, AudioSendHandler> sendHandlerMap;
    private final Map<String, AudioPlayer> playerMap;
    private final Set<String> initGuilds;

    public GuildPlayer(final AudioPlayerManager playerManager) {
        //TODO: Сделать одну мэму, вынести все хендлеры в другое место
        this.playerManager = playerManager;
        this.loadResultHandlerMap = new HashMap<>();
        this.sendHandlerMap = new HashMap<>();
        this.schedulerMap = new HashMap<>();
        this.initGuilds = new HashSet<>();
        this.playerMap = new HashMap<>();
    }

    public void init(final String guildId) {
        if (initGuilds.contains(guildId)) {
            return;
        }

        var player = playerManager.createPlayer();
        var trackScheduler = new TrackScheduler(player);
        var loadResultHandler = new LoadResultHandler(trackScheduler);
        var sendHandler = new AudioPlayerSendHandler(player);

        playerMap.put(guildId, player);
        sendHandlerMap.put(guildId, sendHandler);
        schedulerMap.put(guildId, trackScheduler);
        loadResultHandlerMap.put(guildId, loadResultHandler);
        initGuilds.add(guildId);
    }

    public void addTrack(final String path, final String guildId) {
        playerManager.loadItem(path, loadResultHandlerMap.get(guildId));
    }

    public AudioEventAdapter getAudioEventAdapter(final String guildId) {
        return schedulerMap.get(guildId);
    }

    public AudioLoadResultHandler createLoadResultHandler(final String guildId) {
        return loadResultHandlerMap.get(guildId);
    }

    public AudioSendHandler getSendHandler(final String guildId) {
        return sendHandlerMap.get(guildId);
    }
}
