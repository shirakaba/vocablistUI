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
    // also a Test object.
//    private static final Integer MAX_TOKENS= 10;


    /**
     * Just includes list.
     * @param list
     */
    public Test6Model(List<VocabListRowCumulativeMapped> list) {
        this.list = list;
    }

    /**
     * Includes quiz.
     * @param list
     * @param exampleSentences
     */
    public Test6Model(List<VocabListRowCumulativeMapped> list, SetMultimap<ForwardingToken, Sentence> exampleSentences) {
        this.list = list;
//        // need a random example sentence, with definitions for all the tokens inside.
//        // We collect all single-def, niche rows into frequency tiers.
//        List<VocabListRowCumulativeMapped> upTo50 = new ArrayList<>();
//        List<VocabListRowCumulativeMapped> upTo75 = new ArrayList<>();
//        List<VocabListRowCumulativeMapped> upTo88 = new ArrayList<>();
//        List<VocabListRowCumulativeMapped> upTo94 = new ArrayList<>();
//        list
//                .stream()
//                .filter(row -> row.getDefs().size() == 1
//                        && !row.getDefs().get(0).startsWith(NO_DEF_KEY)
//                        // TODO: examine instead based on chosen pre-filtering level?
//                        && !(row.isFundamental() || row.isN5() || row.isN4() || row.isN3() || row.isN2()) || row.isN1())
//                .forEach(row -> {
//                    Float cumu = row.getCumu() * PERCENT_TO_DECIMAL;
//                    Integer cumuInt = cumu.intValue();
//                    if(Range.closedOpen(     0,          ALL - (ALL/2^1)).contains(cumuInt)) upTo50.add(row);
//                    //                       0.0            50.0
//                    else if(Range.closedOpen(ALL - (ALL/2^1), ALL - (ALL/2^2)).contains(cumuInt)) upTo75.add(row);
//                    //                       50.0            75.0
//                    else if(Range.closedOpen(ALL - (ALL/2^2), ALL - (ALL/2^3)).contains(cumuInt)) upTo88.add(row);
//                    //                       75.0            87.5
//                    else if(Range.closedOpen(ALL - (ALL/2^3), ALL - (ALL/2^4)).contains(cumuInt)) upTo94.add(row);
//                    //                       87.5            93.75
//                });


    }



    public List<VocabListRowCumulativeMapped> getList()
    {
        return list;
    }
}
