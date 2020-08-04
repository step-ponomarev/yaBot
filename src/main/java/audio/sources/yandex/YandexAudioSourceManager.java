package audio.sources.yandex;

import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.io.HttpConfigurable;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioReference;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;

import java.beans.Encoder;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.security.CryptoPrimitive;
import java.util.function.Consumer;
import java.util.function.Function;

public class YandexAudioSourceManager implements AudioSourceManager
//        , HttpConfigurable {
{
    @Override
    public String getSourceName() {
        return "yandex";
    }

    @Override
    public AudioItem loadItem(DefaultAudioPlayerManager manager, AudioReference reference) {
        System.out.println(reference.identifier);
        System.out.println(reference.title);
        System.out.println("KEK");
        return null;
    }

    @Override
    public boolean isTrackEncodable(AudioTrack track) {
        return true;
    }

    @Override
    public void encodeTrack(AudioTrack track, DataOutput output) throws IOException {

    }

    @Override
    public AudioTrack decodeTrack(AudioTrackInfo trackInfo, DataInput input) throws IOException {
        System.out.println("KEK");
        return null;
    }

    @Override
    public void shutdown() {

    }

//    @Override
//    public void configureRequests(Function<RequestConfig, RequestConfig> configurator) {
//
//    }
//
//    @Override
//    public void configureBuilder(Consumer<HttpClientBuilder> configurator) {
//
//    }
}
