package uk.co.birchlabs;

import com.google.common.collect.Range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static uk.co.birchlabs.JMDictPronService.PERCENT_TO_DECIMAL;
import static uk.co.birchlabs.VocabListRowCumulativeMapped.NO_DEF_KEY;

/**
 * Created by jamiebirch on 13/07/2016.
 */
public class TieredSingleDefVRCMList {
    private static final Double ALL= 100.0;
    private final List<VocabListRowCumulativeMapped> tierOne;
    private final List<VocabListRowCumulativeMapped> tierTwo;
    private final List<VocabListRowCumulativeMapped> tierThree;
    private final List<VocabListRowCumulativeMapped> tierFour;

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
//                        && !(row.isFundamental() || row.isN5() || row.isN4() || row.isN3() || row.isN2()) || row.isN1()
                )
                .forEach(row -> {

                    Float cumu = row.getCumu() * PERCENT_TO_DECIMAL;
                    Double cumuInt = cumu.doubleValue();
                    if(Range.closedOpen(          0.0,          ALL - (ALL/Math.pow(2, 1))).contains(cumuInt)) tierOne.add(row);
                        //                       0.0            50.0
                    else if(Range.closedOpen(ALL - (ALL/Math.pow(2, 1)), ALL - (ALL/Math.pow(2, 2))).contains(cumuInt)) tierTwo.add(row);
                        //                       50.0            75.0
                    else if(Range.closedOpen(ALL - (ALL/Math.pow(2, 2)), ALL - (ALL/Math.pow(2, 3))).contains(cumuInt)) tierThree.add(row);
                        //                       75.0            87.5
                    else if(Range.closedOpen(ALL - (ALL/Math.pow(2, 3)), ALL - (ALL/Math.pow(2, 4))).contains(cumuInt)) tierFour.add(row);
                    //                       87.5            93.75
                });

        shuffleTiers(tierOne, tierTwo, tierThree, tierFour);

//        Collections.shuffle(tierOne);
//        Collections.shuffle(tierTwo);
//        Collections.shuffle(tierThree);
//        Collections.shuffle(tierFour);

        this.tierOne = tierOne;
        this.tierTwo = tierTwo;
        this.tierThree = tierThree;
        this.tierFour = tierFour;
    }

//    @SuppressWarnings("varargs")
    private void shuffleTiers(List<VocabListRowCumulativeMapped> ... listsToShuffle){
        for (List<VocabListRowCumulativeMapped> list : listsToShuffle) {
            Collections.shuffle(list);
        }

    }

    public List<VocabListRowCumulativeMapped> getTierOne() {
        return tierOne;
    }

    public List<VocabListRowCumulativeMapped> getTierTwo() {
        return tierTwo;
    }

    public List<VocabListRowCumulativeMapped> getTierThree() {
        return tierThree;
    }

    public List<VocabListRowCumulativeMapped> getTierFour() {
        return tierFour;
    }

}
