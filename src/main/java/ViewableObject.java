/**
 * Should be inherited by all object types that should be displayed to the user.
 */
@FunctionalInterface
public interface ViewableObject {

  /**
   * Views the object.
   * @param view The view that displays output to the user.
   */
  void view(View view);

}
