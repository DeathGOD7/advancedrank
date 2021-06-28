package de.blocki.advancedrank.main.utils.config;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Permissions {

    RANK_SET("rank.use.set"),
    RANK_ADD("rank.use.add"),
    RANK_REMOVE("rank.use.remove"),
    RANK_INFO("rank.use.info"),
    RANK_HELP("rank.use.help"),
    RANK("rank.use.selfinfo");

    private final String text;

    public String toString() { return text; }

}
