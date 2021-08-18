package de.blocki.advancedrank.main.utils.config;

public enum Permissions {

    RANK_SET("rank.use.set"),
    RANK_ADD("rank.use.add"),
    RANK_REMOVE("rank.use.remove"),
    RANK_INFO("rank.use.info"),
    RANK_HELP("rank.use.help"),
    RANK_SELFINFO("rank.use.selfinfo"),
    RANK_OP("rank.*"),
    STAR("*");

    private final String text;

    Permissions(String text){
        this.text = text;
    }

    public String toString() { return text; }

}
