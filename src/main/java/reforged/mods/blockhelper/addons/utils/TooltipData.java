package reforged.mods.blockhelper.addons.utils;

public class TooltipData {

    public final int current;
    public final int max;
    public final int color;
    public final String text;
    public final boolean isStringOnly;
    public final boolean centered;
    public final int fluidId;

    public TooltipData(String[] strings) {
        if (strings == null || strings.length < 7) {
            throw new IllegalArgumentException("Invalid tooltip data array (null or length < 7)");
        }

        this.current = Integer.parseInt(strings[0]);
        this.max = Integer.parseInt(strings[1]);
        this.color = Integer.parseInt(strings[2]);
        this.text = strings[3];
        this.isStringOnly = "1".equals(strings[4]);
        this.centered = "1".equals(strings[5]);
        this.fluidId = Integer.parseInt(strings[6]);
    }

    public TooltipData(int current, int max, int color, String text, boolean isStringOnly, boolean centered, int fluidId) {
        this.current = current;
        this.max = max;
        this.color = color;
        this.text = text;
        this.isStringOnly = isStringOnly;
        this.centered = centered;
        this.fluidId = fluidId;
    }

    public static TooltipData parse(String[] strings) {
        return new TooltipData(strings);
    }

    public static TooltipData textOnly(String text, boolean centered) {
        return new TooltipData(1, 1, 1, text, true, centered, 0);
    }

    public static TooltipData bar(int current, int max, int color, String text) {
        return new TooltipData(current, max, color, text, false, false, 0);
    }

    public static TooltipData fluid(int current, int max, String text, int fluidId) {
        return new TooltipData(current, max, 1, text, false, false, fluidId);
    }

    public String[] toArray() {
        return new String[] {
                String.valueOf(current),
                String.valueOf(max),
                String.valueOf(color),
                text,
                isStringOnly ? "1" : "0",
                centered ? "1" : "0",
                String.valueOf(fluidId)
        };
    }
}
