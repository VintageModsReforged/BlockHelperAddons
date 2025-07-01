package reforged.mods.blockhelper.addons.utils.interfaces;

import mcp.mobius.waila.api.ITaggedList;

public interface ITooltipHelper {

    void text(ITaggedList<String, String> tooltip, String text, boolean centered);
    void bar(ITaggedList<String, String> tooltip, int current, int max, String text, int color);
    void fluid(ITaggedList<String, String> tooltip, int current, int max, String text, int fluidId);
}
