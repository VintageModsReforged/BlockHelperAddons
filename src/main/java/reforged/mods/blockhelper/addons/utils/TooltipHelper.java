package reforged.mods.blockhelper.addons.utils;

import mcp.mobius.waila.api.ITaggedList;
import mcp.mobius.waila.api.SpecialChars;
import reforged.mods.blockhelper.addons.utils.interfaces.ITooltipHelper;

/**
 * Tooltip Builder
 * param 0 - current
 * param 1 - max
 * param 2 - color
 * param 3 - text
 * param 4 - string only, 0 - false, 1 - true
 * param 5 - centered, 0 - means false, 1 - true
 * param 6 - fluid id: 0 for nothing
 * */

public class TooltipHelper implements ITooltipHelper {

    @Override
    public void text(ITaggedList<String, String> tooltip, String text, boolean centered) {
        String[] textData = new String[7];
        textData[0] = String.valueOf(1);
        textData[1] = String.valueOf(1);
        textData[2] = String.valueOf(1);
        textData[3] = text;
        textData[4] = "1";
        if (centered) {
            textData[5] = "1";
        } else {
            textData[5] = "0";
        }
        textData[6] = "0";
        tooltip.add(SpecialChars.getRenderString("jade.progress", textData));
    }

    @Override
    public void bar(ITaggedList<String, String> tooltip, int current, int max, String text, int color) {
        String[] barData = new String[7];
        barData[0] = String.valueOf(current);
        barData[1] = String.valueOf(max);
        barData[2] = String.valueOf(color);
        barData[3] = text;
        barData[4] = "0";
        barData[5] = "0";
        barData[6] = "0";
        tooltip.add(SpecialChars.getRenderString("jade.progress", barData));
    }

    @Override
    public void fluid(ITaggedList<String, String> tooltip, int current, int max, String text, int fluidId) {
        String[] fluidData = new String[7];
        fluidData[0] = String.valueOf(current);
        fluidData[1] = String.valueOf(max);
        fluidData[2] = String.valueOf(1);
        fluidData[3] = text;
        fluidData[4] = "0";
        fluidData[5] = "0";
        fluidData[6] = String.valueOf(fluidId);
        tooltip.add(SpecialChars.getRenderString("jade.progress", fluidData));
    }
}
