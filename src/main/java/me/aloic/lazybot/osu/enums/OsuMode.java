package me.aloic.lazybot.osu.enums;

public enum OsuMode
{
    Osu(0, "osu"),
    Taiko(1, "taiko"),
    Catch(2, "fruits"),
    Mania(3, "mania"),
    Default(-1, "");

    private final int value;
    private final String describe;

    OsuMode(int value, String describe) {
        this.value = value;
        this.describe = describe;
    }

    public int getValue() {
        return value;
    }

    public String getDescribe() {
        return describe;
    }

    public static OsuMode getMode(int value) {
        return switch (value)
        {
            case 0 -> Osu;
            case 1 -> Taiko;
            case 2 -> Catch;
            case 3 -> Mania;
            default -> Default;
        };
    }

    public static OsuMode getMode(String name) {
        if (name == null) throw new RuntimeException("Null mode provided");
        return switch (name.toLowerCase().trim())
        {
            case "osu", "o", "0", "std", "standard" -> Osu;
            case "taiko", "t", "1", "tk" -> Taiko;
            case "catch", "c", "ctb", "fruits", "fruit", "f", "2" -> Catch;
            case "mania", "m", "3", "mn" -> Mania;
            default -> throw new RuntimeException("Invalid mode provided: " + name);
        };
    }
    @Override
    public String toString() {
        return describe;
    }

}
