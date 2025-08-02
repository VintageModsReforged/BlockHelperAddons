package reforged.mods.blockhelper.addons.utils.interfaces;

import mcp.mobius.waila.api.ITaggedList;
import reforged.mods.blockhelper.addons.utils.TooltipData;

public interface ITooltipHelper {

    void text(ITaggedList<String, String> tooltip, String text, boolean centered);
    void bar(ITaggedList<String, String> tooltip, int current, int max, String text, int color);
    void fluid(ITaggedList<String, String> tooltip, int current, int max, String text, int fluidId);
    void applyTooltipData(ITaggedList<String, String> tooltip, TooltipData data);
}
