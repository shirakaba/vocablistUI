package uk.co.birchlabs;

import java.util.List;

/**
 * Created by jamiebirch on 13/07/2016.
 */
public class GrandQuiz {
    private final List<Tier> tiers;

    public GrandQuiz(List<Tier> tiers) {
        this.tiers = tiers;
    }

    public List<Tier> getTiers() {
        return tiers;
    }
}
