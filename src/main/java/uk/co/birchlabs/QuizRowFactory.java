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

    public static QuizRow getQuizRow(Type type, VocabListRowCumulativeMapped row){
        switch (type){
            case def:
                return new DefQuizRow(row);
            case pron:
                return new PronQuizRow(row);
            case kanji:
                return new KanjiQuizRow(row);
            default:
                throw new NotImplementedException();
        }
    }
}
