package uk.co.birchlabs;

/**
 * Created by jamiebirch on 13/07/2016.
 */
public class QuizRowFactory {
    public enum Type{
        def,
        pron,
        kanji
    }

    public QuizRow getQuizRow(Type type, String bf, String fulldef){
        switch (type){
            case def:
                return new DefQuizRow(bf, fulldef);
            case pron:
                return new PronQuizRow(bf, fulldef);
            case kanji:
                break;
        }
    }
}
