package reforged.mods.blockhelper.addons.utils;

import mcp.mobius.waila.api.ITaggedList;
import mods.vintage.core.platform.lang.components.IChatComponent;

public interface ITooltipHelper {

    void text(ITaggedList<String, String> tooltip, String text, boolean centered);
    void bar(ITaggedList<String, String> tooltip, int current, int max, String text, int color);
    void fluid(ITaggedList<String, String> tooltip, int current, int max, String text, int fluidId);
}
