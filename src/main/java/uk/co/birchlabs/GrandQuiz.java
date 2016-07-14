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

    public GrandQuiz(SingleTierQuiz tierOne, SingleTierQuiz tierTwo, SingleTierQuiz tierThree, SingleTierQuiz tierFour) {
        this.tierOne = tierOne;
        this.tierTwo = tierTwo;
        this.tierThree = tierThree;
        this.tierFour = tierFour;
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
