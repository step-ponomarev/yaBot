import bot.factory.BotFactory;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import config.ApplicationProperties;
import consts.Consts;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;

// https://storage.mds.yandex.net/download-info/${storageDir}/2?format=json - стора
// Secret XGRlBW9FXlekgbPrRHuSiA
// https://${info.host}/get-mp3/${hashedUrl}/${info.ts}${info.path}
// https://music.yandex.ru/handlers/track.jsx?track=${trackID} - трек информация

public final class YaBotApplication {
    static final ApplicationProperties properties = new ApplicationProperties();

    public static void main(String[] args) {
        try {


            var str = DigestUtils.md5Hex("XGRlBW9FXlekgbPrRHuSiA");
            System.out.println(str);
//            //TODO: Правильно ли так читать проперти? Мб подключить спринг?? Не слишком ли тяжело будет?
            properties.init(getResourceAsStream(Consts.PROPERTIES_FILE_NAME));

            var bot = BotFactory.getInstance(properties.getProperty(Consts.BOT_TOKEN));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static InputStream getResourceAsStream(final String filename) {
        return YaBotApplication.class.getResourceAsStream(filename);
    }

}
