package uk.co.birchlabs;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by jamiebirch on 13/07/2016.
 */
public class QuizRowFactory {
    public enum Type{
        def,
        pron,
        kanji
    }

    public static QuizRow getQuizRow(Type type, String bf, String fulldef){
        switch (type){
            case def:
                return new DefQuizRow(fulldef);
            case pron:
                return new PronQuizRow(fulldef);
            case kanji:
                return new KanjiQuizRow(bf, fulldef);
            default:
                throw new NotImplementedException();
        }
    }
}
