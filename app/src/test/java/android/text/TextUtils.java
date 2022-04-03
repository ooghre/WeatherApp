package android.text;

/*
 * Utility class to support TextUtils in unit tests
 * */
public class TextUtils {
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }
}
