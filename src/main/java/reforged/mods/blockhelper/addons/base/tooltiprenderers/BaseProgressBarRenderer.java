package reforged.mods.blockhelper.addons.base.tooltiprenderers;

import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import mcp.mobius.waila.api.impl.TipList;
import mcp.mobius.waila.mod_BlockHelper;
import mcp.mobius.waila.overlay.RayTracing;
import mcp.mobius.waila.overlay.Tooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Dimension;
import org.lwjgl.util.Rectangle;
import reforged.mods.blockhelper.addons.utils.ColorUtils;
import reforged.mods.blockhelper.addons.utils.GuiHelper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BaseProgressBarRenderer implements ITooltipRenderer {

    private final Map<String[], Tooltip> subTooltips = new HashMap<String[], Tooltip>();
    private static Field FIELD_POS;

    static {
        try {
            FIELD_POS = Tooltip.class.getDeclaredField("pos");
            FIELD_POS.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dimension getSize(String[] strings, ICommonAccessor accessor) {
        if (FIELD_POS == null) {
            return new Dimension();
        }
        // An instance of our tooltip group for width consistency
        Tooltip tooltip;
        if (this.subTooltips.containsKey(strings)) {
            // is present
            tooltip = this.subTooltips.get(strings);
        } else {
            TipList<String, String> list = new TipList<String, String>();
            list.addAll(Arrays.asList(strings));
            // create a new one
            tooltip = new Tooltip(list, RayTracing.instance().getTargetStack());
            this.subTooltips.put(strings, mod_BlockHelper.TICK_HANDLER.tooltip);
        }

        int height = "1".equals(strings[4]) ? 10 : 11;
        try {
            // get sizes for our tooltip
            Rectangle rect = (Rectangle) FIELD_POS.get(tooltip);
            return new Dimension(rect.getWidth(), height);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return new Dimension();
        }
    }

    /**
     * Tooltip Builder
     * param 0 - current
     * param 1 - max
     * param 2 - color
     * param 3 - text
     * param 4 - string only, 0 - false, 1 - true
     * param 5 - centered, 0 - means false, 1 - true
     * param 6 - fluid id
     * */

    @Override
    public void draw(String[] strings, ICommonAccessor iWailaCommonAccessor, int x, int y) {

        Tooltip tooltip = subTooltips.get(strings);
        if (tooltip != null) {
            Rectangle rectangle = new Rectangle();
            rectangle.setHeight(11);
            try {
                rectangle.setWidth(((Rectangle) FIELD_POS.get(tooltip)).getWidth() - 37);
            } catch (IllegalAccessException exception) {
                exception.printStackTrace();
                return;
            }

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            int current = Integer.parseInt(strings[0]);
            int max = Integer.parseInt(strings[1]);
            int color = Integer.parseInt(strings[2]);
            String text = strings[3];
            boolean isStringOnly = "1".equals(strings[4]);
            String fluidStringId = strings[6];
            FontRenderer font = Minecraft.getMinecraft().fontRenderer;
            if (!isStringOnly) {
                GuiHelper.INSTANCE.render(current, max, x, y, rectangle.getWidth(), rectangle.getHeight() + 1, color, fluidStringId);
                x += rectangle.getWidth() / 2 - font.getStringWidth(text) / 2 + 1;
            }
            boolean centered = "1".equals(strings[5]);
            if (centered) {
                x += rectangle.getWidth() / 2 - font.getStringWidth(text) / 2 + 1;
            }
            font.drawStringWithShadow(text, x, y + 2, ColorUtils.WHITE);
        }
    }
}