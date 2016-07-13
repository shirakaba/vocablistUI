package uk.co.birchlabs;

import catRecurserPkg.ForwardingToken;
import catRecurserPkg.Sentence;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.SetMultimap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static uk.co.birchlabs.JMDictPronService.*;
import static uk.co.birchlabs.VocabListRowCumulativeMapped.NO_DEF_KEY;

/**
 * Created by jamiebirch on 22/06/2016.
 */
public class Test6Model {
    List<VocabListRowCumulativeMapped> list;
    Quiz quiz;
    // also a Quiz object.
//    private static final Integer MAX_TOKENS= 10;


    /**
     * Just includes list.
     * @param list
     */
    public Test6Model(List<VocabListRowCumulativeMapped> list, boolean makeQuiz) {
        this.list = list;
        if(makeQuiz){

        }
        else this.quiz = new Quiz();
    }

    /**
     * Includes quiz.
     * @param list
     */
    public Test6Model(List<VocabListRowCumulativeMapped> list) {
        this.list = list;
        this.quiz = new Quiz();
    }



    public List<VocabListRowCumulativeMapped> getList()
    {
        return list;
    }
}
