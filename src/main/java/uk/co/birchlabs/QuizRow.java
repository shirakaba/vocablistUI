package uk.co.birchlabs;

import static uk.co.birchlabs.EntryReadout.MEANINGS_START_KEY;

/**
 * Created by jamiebirch on 13/07/2016.
 */
public interface QuizRow {
    String getInfo();

    String getTarget();

    String getType();
}
