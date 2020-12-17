/**
 * Indicates that the implementing class can be copied. The depth of this copy is <i>not</i>
 * enforced in the contract of this interface, so please check that the depth is sufficient for
 * your purposes before using this class.
 * @param <T> The return type from the {@link #copy()} method.
 */
public interface Copyable<T> {

  /**
   * Copies the current instance, returning a new object. The depth of this copy if <i>not</i>
   * enforced by this interface, so please check that the depth is sufficient for your purposes
   * when using the method.
   * @return A copy of this instance.
   */
  T copy();

}
