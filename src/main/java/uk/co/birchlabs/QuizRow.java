package uk.co.birchlabs;

import static uk.co.birchlabs.EntryReadout.MEANINGS_START_KEY;

/**
 * Created by jamiebirch on 13/07/2016.
 */
public interface QuizRow {

    // TODO: convert to factory method so we can test alternately on def, pronun(s), and kanjiform(s)


    String getInfoPortion();

    String getQuizPortion();
}
