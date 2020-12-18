import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;

public class PropertiesReader {

  private final Properties properties;

  public PropertiesReader(Properties properties) throws NullPointerException {
    this.properties = Objects.requireNonNull(properties, "Properties is null");
  }

  public Optional<String> getString(String key) {
    return Optional.ofNullable(properties.get(key)).map(Object::toString);
  }

  public Optional<Integer> getInteger(String key) throws NumberFormatException {
    return get(key, Integer::valueOf);
  }

  public Optional<Boolean> getBoolean(String key) {
    return get(key, Boolean::valueOf);
  }

  public <R> Optional<R> get(String key, Function<String, R> mappingFunction) {
    return getString(key).map(mappingFunction);
  }

}
