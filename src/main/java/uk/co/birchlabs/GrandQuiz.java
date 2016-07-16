package uk.co.birchlabs;

import java.util.List;

/**
 * Created by jamiebirch on 13/07/2016.
 */
public class GrandQuiz {
    private final List<SingleTierQuiz> tiers;

    public GrandQuiz(List<SingleTierQuiz> tiers) {
        this.tiers = tiers;
    }

    public List<SingleTierQuiz> getTiers() {
        return tiers;
    }
}
