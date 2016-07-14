package uk.co.birchlabs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamiebirch on 13/07/2016.
 */
public class GrandQuiz {
    private final SingleTierQuiz tierOne;
    private final SingleTierQuiz tierTwo;
    private final SingleTierQuiz tierThree;
    private final SingleTierQuiz tierFour;

    public GrandQuiz(){
        tierOne = new SingleTierQuiz(new ArrayList<>());
        tierTwo = new SingleTierQuiz(new ArrayList<>());
        tierThree = new SingleTierQuiz(new ArrayList<>());
        tierFour = new SingleTierQuiz(new ArrayList<>());
    }

    public GrandQuiz(List<VocabListRowCumulativeMapped> untieredList) {
        TieredSingleDefVRCMList tierHolder = new TieredSingleDefVRCMList(untieredList);

        tierOne = new SingleTierQuiz(tierHolder.getTierOne());
        tierTwo = new SingleTierQuiz(tierHolder.getTierTwo());
        tierThree = new SingleTierQuiz(tierHolder.getTierThree());
        tierFour = new SingleTierQuiz(tierHolder.getTierFour());
    }

    public SingleTierQuiz getTierOne() {
        return tierOne;
    }

    public SingleTierQuiz getTierTwo() {
        return tierTwo;
    }

    public SingleTierQuiz getTierThree() {
        return tierThree;
    }

    public SingleTierQuiz getTierFour() {
        return tierFour;
    }
}
