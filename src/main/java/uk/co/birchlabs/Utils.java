package uk.co.birchlabs;

/**
 *
 * @author Duane J. May <djmay@mayhoo.com>
 * @since 10:37 AM - 6/3/14
 *
 * @see <a href="http://sourceforge.net/projects/kanjixml/">http://sourceforge.net/projects/kanjixml/</a>
 */
public class Utils{
    public static String convertKana(String input) {
        if (input == null || input.length() == 0) return "";

        StringBuilder out = new StringBuilder();
        char ch = input.charAt(0);

        if (JapaneseCharacter.isHiragana(ch)) { // convert to hiragana to katakana
            for (int i = 0; i < input.length(); i++) {
                out.append(JapaneseCharacter.toKatakana(input.charAt(i)));
            }
        } else if (JapaneseCharacter.isKatakana(ch)) { // convert to katakana to hiragana
            for (int i = 0; i < input.length(); i++) {
                out.append(JapaneseCharacter.toHiragana(input.charAt(i)));
            }
        } else { // do nothing if neither
            return input;
        }

        return out.toString();
    }
}