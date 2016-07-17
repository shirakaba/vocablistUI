package uk.co.birchlabs;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by jamiebirch on 13/07/2016.
 */
public class QuizRowFactory {
    public enum Mode {
        def,
        pron,
        kanji
    }

    public static Question getQuizRow(Mode mode, VocabListRowCumulativeMapped row){
        switch (mode){
            case def:
                return new DefQuestion(row);
            case pron:
                return new PronQuestion(row);
            case kanji:
                return new KanjiQuestion(row);
            default:
                throw new NotImplementedException();
        }
    }
}
