import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;

/**
 * Reads values from a text-based {@link Properties} instance.
 */
public class PropertiesReader {

  private final Properties properties;

  /**
   * Creates a new reader.
   * @param properties The {@code Properties} instance to read values from.
   * @throws NullPointerException Thrown if {@code properties == null}.
   */
  public PropertiesReader(Properties properties) throws NullPointerException {
    this.properties = Objects.requireNonNull(properties, "Properties is null");
  }

  /**
   * Gets the value for the specified key, and converting it to a {@link String} using
   * {@link Object#toString()}.
   * @param key The key to search for.
   * @return The value as a string, or an empty {@link Optional} if no value has been specified.
   */
  public Optional<String> getString(String key) {
    return Optional.ofNullable(properties.get(key)).map(Object::toString);
  }

  /**
   * Gets the value for the specified key, and converting it to an {@link Integer}.
   * @param key The key to search for.
   * @return The value as an integer, or an empty {@link Optional} if no value has been specified.
   * @throws NumberFormatException Thrown if the value exists but cannot be parsed to an integer.
   */
  public Optional<Integer> getInteger(String key) throws NumberFormatException {
    return get(key, Integer::valueOf);
  }

  /**
   * Gets the value for the specified key, and converting it to a {@link Boolean}.
   * @param key The key to search for.
   * @return The value as a boolean, or an empty {@link Optional} if no value has been specified.
   */
  public Optional<Boolean> getBoolean(String key) {
    return get(key, Boolean::valueOf);
  }

  /**
   * Gets the value for the specified key, mapping it to an appropriate object type using the
   * {@code mappingFunction}.
   * @param key The key to search for.
   * @param mappingFunction The function that converts the string value to the desired object type.
   * @param <R> The desired object type.
   * @return The value converted to the desired type, or an empty {@link Optional} if no value has
   * been specified.
   * @throws RuntimeException Thrown from the {@code mappingFunction}.
   */
  public <R> Optional<R> get(String key, Function<String, R> mappingFunction)
      throws RuntimeException {
    return getString(key).map(mappingFunction);
  }

}
