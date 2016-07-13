package uk.co.birchlabs;

import com.google.common.collect.Range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static uk.co.birchlabs.JMDictPronService.PERCENT_TO_DECIMAL;
import static uk.co.birchlabs.VocabListRowCumulativeMapped.NO_DEF_KEY;

/**
 * Created by jamiebirch on 13/07/2016.
 */
public class TieredSingleDefVRCMList {
    private static final Integer ALL= 100;
    private static final Integer MAX_QUESTIONS= 4;
    private final List<QuizRow> tierOne;
    private final List<QuizRow> tierTwo;
    private final List<QuizRow> tierThree;
    private final List<QuizRow> tierFour;

    public TieredSingleDefVRCMList(List<VocabListRowCumulativeMapped> untieredList) {
        List<VocabListRowCumulativeMapped>
                tierOne = new ArrayList<>(),
                tierTwo = new ArrayList<>(),
                tierThree = new ArrayList<>(),
                tierFour = new ArrayList<>();

        untieredList
                .stream()
                .filter(row -> row.getDefs().size() == 1
                        && !row.getDefs().get(0).startsWith(NO_DEF_KEY)
                        // TODO: examine instead based on chosen pre-filtering level?
                        && !(row.isFundamental() || row.isN5() || row.isN4() || row.isN3() || row.isN2()) || row.isN1())
                .forEach(row -> {
                    Float cumu = row.getCumu() * PERCENT_TO_DECIMAL;
                    Integer cumuInt = cumu.intValue();
                    if(Range.closedOpen(          0,          ALL - (ALL/2^1)).contains(cumuInt)) tierOne.add(row);
                        //                       0.0            50.0
                    else if(Range.closedOpen(ALL - (ALL/2^1), ALL - (ALL/2^2)).contains(cumuInt)) tierTwo.add(row);
                        //                       50.0            75.0
                    else if(Range.closedOpen(ALL - (ALL/2^2), ALL - (ALL/2^3)).contains(cumuInt)) tierThree.add(row);
                        //                       75.0            87.5
                    else if(Range.closedOpen(ALL - (ALL/2^3), ALL - (ALL/2^4)).contains(cumuInt)) tierFour.add(row);
                    //                       87.5            93.75
                });

        this.tierOne = tierToQuizList(tierOne);
        this.tierTwo = tierToQuizList(tierTwo);
        this.tierThree = tierToQuizList(tierThree);
        this.tierFour = tierToQuizList(tierFour);
    }

    /**
     * Note: any tier passed to this will be shuffled.
     * @param tier
     * @return
     */
    private List<QuizRow> tierToQuizList(List<VocabListRowCumulativeMapped> tier){
        Collections.shuffle(tier);
        return tier
                .stream()
                .map(row -> new QuizRow(row.getBf(), row.getDefs().get(0)))
                .limit(MAX_QUESTIONS)
                .collect(Collectors.toList());
    }
}
