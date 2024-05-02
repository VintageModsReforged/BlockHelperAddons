package reforged.mods.blockhelper.addons;

import reforged.mods.blockhelper.addons.i18n.I18n;

public enum TextColor {
    BLACK("0"),
    DARK_BLUE("1"),
    DARK_GREEN("2"),
    DARK_AQUA("3"),
    DARK_RED("4"),
    DARK_PURPLE("5"),
    GOLD("6"),
    GRAY("7"),
    DARK_GRAY("8"),
    BLUE("9"),
    GREEN("a"),
    AQUA("b"),
    RED("c"),
    LIGHT_PURPLE("d"),
    YELLOW("e"),
    WHITE("f");

    final String colorCode;

    TextColor(String colorIndex) {
        this.colorCode = "\247" + colorIndex;
    }

    public String literal(String text) {
        return this.colorCode + text;
    }

    public String format(String text) {
        return this.colorCode + I18n.format(text);
    }

    public String format(String text, Object... args) {
        return this.colorCode + I18n.format(text, args);
    }

    @Override
    public String toString() {
        return this.colorCode;
    }
}
