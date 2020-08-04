package audio.sources.yandex.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class YandexId {
  @NonNull
  public String id;
  @NonNull
  public YandexIdType type;

  @Override
  public String toString() {
    return id;
  }
}
