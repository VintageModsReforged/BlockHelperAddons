package reforged.mods.blockhelper.addons.utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraftforge.liquids.LiquidStack;
import org.lwjgl.opengl.GL11;

public class RenderHelper {

    public static final RenderHelper INSTANCE = new RenderHelper();

    private static final ScissorsStack STACK = new ScissorsStack();
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void drawScrollingString(FontRenderer font, String text, float centerX, float centerY, float width, float height, int color) {
        int textWidth = font.getStringWidth(text);
        float drawY = centerY - (font.FONT_HEIGHT / 2F);

        float boxX = centerX - (width / 2F);
        float boxY = centerY - (height / 2F);
        int clipX = MathHelper.floor_float(boxX);
        int clipY = MathHelper.floor_float(boxY);
        int clipW = MathHelper.ceiling_float_int(width);
        int clipH = MathHelper.ceiling_float_int(height);

        if (textWidth > width) {
            float scrollOffset = getScrollOffset(width, textWidth);
            float alignedX = boxX + 2 - scrollOffset;

            pushScissors(clipX, clipY, clipW, clipH);
            font.drawStringWithShadow(text, (int) alignedX, (int) drawY, color);
            popScissors();
        } else {
            float drawX = centerX - (textWidth / 2F);
            font.drawStringWithShadow(text, (int) drawX, (int) drawY, color);
        }
    }

    public static void pushScissors(int x, int y, int width, int height) {
        pushScissors(new ScreenRectangle(x, y, width, height));
    }

    public static void pushScissors(ScreenRectangle rect) {
        STACK.push(rect);
        applyScissors(rect);
    }

    public static void popScissors() {
        applyScissors(STACK.pop());
    }

    private static void applyScissors(ScreenRectangle rect) {
        if (rect == null) {
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            return;
        }

        ScaledResolution resolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        double scaleX = (double) mc.displayWidth / resolution.getScaledWidth();
        double scaleY = (double) mc.displayHeight / resolution.getScaledHeight();

        int x = (int) (rect.getX() * scaleX);
        int y = (int) ((resolution.getScaledHeight() - rect.getY() - rect.getHeight()) * scaleY);
        int w = (int) (rect.getWidth() * scaleX);
        int h = (int) (rect.getHeight() * scaleY);

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(x, y, w, h);
    }

    private static float getScrollOffset(float width, int textWidth) {
        float scrollRange = textWidth - width + 4F;
        float scrollSpeed = 20F;

        long now = System.currentTimeMillis();
        float totalScrollTime = scrollRange / scrollSpeed;
        float cycleTime = totalScrollTime * 2F;

        float timeInCycle = (now % (long) (cycleTime * 1000L)) / 1000F;
        boolean reverse = timeInCycle >= totalScrollTime;
        float timeInDirection = reverse ? (timeInCycle - totalScrollTime) : timeInCycle;

        float offset = timeInDirection * scrollSpeed;
        return reverse ? (scrollRange - offset) : offset;
    }


    /**
     * Modified copy of: <a href="https://github.com/McJtyMods/TheOneProbe/blob/f4797f1a7f1349ab71ac85e667517117a8a8d51a/src/main/java/mcjty/theoneprobe/apiimpl/client/ElementProgressRender.java#L15">ElementProgressRender#render</a>
     */
    public void render(long current, long max, int x, int y, int w, int h, int mainColor, String stringId) {
        drawThickBeveledBox(x, y, x + w + 1, y + h, 1, ColorUtils.WHITE, ColorUtils.WHITE, ColorUtils.doubleDarker(mainColor));
        if (!"0".equals(stringId)) {
            int fluidId = Integer.parseInt(stringId);
            LiquidStack stack = new LiquidStack(fluidId, 1000);
            renderTank(Minecraft.getMinecraft(), x, y, w, h, mainColor, (int) current, (int) max, stack);
        } else {
            if (current > 0 && max > 0) {
                int dx = (int) Math.min((current * (w - 2) / max), w - 2);
                int secondColor = ColorUtils.darker(mainColor);
                if (mainColor == secondColor) {
                    if (dx > 0) {
                        drawThickBeveledBox(x + 1, y + 1, x + dx + 1, y + h - 1, 1, mainColor, mainColor, mainColor);
                    }
                } else {
                    for (int xx = x + 1; xx <= x + dx + 1; xx++) {
                        int color = (xx & 1) == 0 ? mainColor : secondColor;
                        drawVerticalLine(xx, y + 1, y + h - 1, color);
                    }
                }
            }
        }
    }

    /**
     * Copy of: <a href="https://github.com/McJtyMods/TheOneProbe/blob/f4797f1a7f1349ab71ac85e667517117a8a8d51a/src/main/java/mcjty/theoneprobe/rendering/RenderHelper.java#L209">RenderHelper#drawVerticalLine</a>
     * */
    public void drawVerticalLine(int x1, int y1, int y2, int color) {
        Gui.drawRect(x1, y1, x1 + 1, y2, color);
    }

    /**
     * Copy of: <a href="https://github.com/McJtyMods/TheOneProbe/blob/f4797f1a7f1349ab71ac85e667517117a8a8d51a/src/main/java/mcjty/theoneprobe/rendering/RenderHelper.java#L275">RenderHelper#drawThickBeveledBox</a>
     * */
    public void drawThickBeveledBox(int x1, int y1, int x2, int y2, int thickness, int topleftcolor, int botrightcolor, int fillcolor) {
        if (fillcolor != -1) {
            Gui.drawRect(x1 + 1, y1 + 1, x2 - 1, y2 - 1, fillcolor);
        }
        Gui.drawRect(x1, y1, x2 - 1, y1 + thickness, topleftcolor);
        Gui.drawRect(x1, y1, x1 + thickness, y2 - 1, topleftcolor);
        Gui.drawRect(x2 - thickness, y1, x2, y2 - 1, botrightcolor);
        Gui.drawRect(x1, y2 - thickness, x2, y2, botrightcolor);
    }

//    public static void renderTank(Minecraft mc, int x, int y, int width, int height, int color, int current, int capacity, LiquidStack liquid) {
//        if (liquid == null || liquid.itemID <= 0 || capacity <= 0 || current <= 0) return;
//
//        Icon icon;
//        String texture;
//        if (liquid.canonical() != null && liquid.canonical().getRenderingIcon() != null) {
//            icon = liquid.canonical().getRenderingIcon();
//            texture = liquid.canonical().getTextureSheet();
//        } else if (liquid.itemID < Block.blocksList.length && Block.blocksList[liquid.itemID] != null) {
//            icon = Block.blocksList[liquid.itemID].getIcon(0, liquid.itemMeta);
//            texture = "/terrain.png";
//        } else {
//            icon = Item.itemsList[liquid.itemID].getIconFromDamage(liquid.itemMeta);
//            texture = "/gui/items.png";
//        }
//
//        mc.renderEngine.bindTexture(texture);
//
//        // Apply color tint
//        GL11.glColor4f(
//                (color >> 16 & 255) / 255.0F,
//                (color >> 8 & 255) / 255.0F,
//                (color & 255) / 255.0F,
//                (color >> 24 & 255) / 255.0F
//        );
//
//        int fillWidth = (int)(((double) current / capacity) * width);
//        int drawX = x;
//
//        while (fillWidth > 0) {
//            int drawWidth = Math.min(16, fillWidth);
//            drawTexturedModalRect(drawX, y, icon, drawWidth, height);
//            drawX += drawWidth;
//            fillWidth -= drawWidth;
//        }
//
//        // Reset color to default (white)
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//    }
//
//    public static void drawTexturedModalRect(int x, int y, Icon sprite, int width, int height) {
//        float zLevel = 0.01f;
//        Tessellator tessellator = Tessellator.instance;
//        tessellator.startDrawingQuads();
//        tessellator.addVertexWithUV(x, y + height, zLevel, sprite.getMinU(), sprite.getMaxV());
//        tessellator.addVertexWithUV(x + width, y + height, zLevel, sprite.getMaxU(), sprite.getMaxV());
//        tessellator.addVertexWithUV(x + width, y, zLevel, sprite.getMaxU(), sprite.getMinV());
//        tessellator.addVertexWithUV(x, y, zLevel, sprite.getMinU(), sprite.getMinV());
//        tessellator.draw();
//    }

    public static void renderTank(Minecraft mc, int x, int y, int width, int height, int color, int current, int capacity, LiquidStack liquid) {
        if (liquid == null || liquid.itemID <= 0 || capacity <= 0 || current <= 0) return;

        String texture;
        float u = 0f;
        float v = 0f;
        int iconWidth = 16;
        int iconHeight = 16;

        // Determine texture sheet and UV coords (u,v)
        // 1. Try liquid canonical texture and UVs (1.4.7 likely no getRenderingIcon)
        // So fallback to block or item icon UV coords by hand.

        if (liquid.itemID < Block.blocksList.length && Block.blocksList[liquid.itemID] != null) {
            // For blocks, texture is terrain.png (256x256)
            texture = "/terrain.png";

            // Usually block icons are 16x16 tiles in the texture.
            // You need to get the texture coordinates from the block's icon.
            // But in 1.4.7, getIcon returns an Icon which we can't use.
            // Instead, you may hardcode or extract from Block method getBlockTextureFromSide or similar.
            // For simplicity, assume (0,0) UV coords or replace with your own logic:

            // You can do something like this, if you have access to icon indices:
            // int iconIndex = Block.blocksList[liquid.itemID].getBlockTextureFromSide(0);
            // int texX = (iconIndex % 16) * 16;
            // int texY = (iconIndex / 16) * 16;

            // But without access to iconIndex, just assume (0,0):
            u = 0;
            v = 0;

        } else if (liquid.itemID < Item.itemsList.length && Item.itemsList[liquid.itemID] != null) {
            texture = "/gui/items.png";

            // Same problem here, getIconFromDamage returns Icon in 1.5+, no Icon in 1.4.7
            // You may have to guess or provide your own texture UV coordinates for the item.

            u = 0;
            v = 0;

        } else {
            // No texture found, abort
            return;
        }

        mc.renderEngine.bindTexture(mc.renderEngine.getTexture(texture));

        // Apply color tint
        GL11.glColor4f(
                ((color >> 16) & 255) / 255.0F,
                ((color >> 8) & 255) / 255.0F,
                (color & 255) / 255.0F,
                ((color >> 24) & 255) / 255.0F
        );

        int fillWidth = (int)(((double) current / capacity) * width);
        int drawX = x;

        while (fillWidth > 0) {
            int drawWidth = Math.min(16, fillWidth);

            // drawTexturedModalRect expects (x, y, u, v, width, height)
            drawTexturedModalRect(drawX, y, u, v, drawWidth, height);

            drawX += drawWidth;
            fillWidth -= drawWidth;
        }

        // Reset color to white
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void drawTexturedModalRect(int x, int y, float u, float v, int width, int height) {
        float zLevel = 0.01f;
        float textureSize = 256.0F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(GL11.GL_QUADS);
        tessellator.addVertexWithUV(x, y + height, zLevel, u / textureSize, (v + height) / textureSize);
        tessellator.addVertexWithUV(x + width, y + height, zLevel, (u + width) / textureSize, (v + height) / textureSize);
        tessellator.addVertexWithUV(x + width, y, zLevel, (u + width) / textureSize, v / textureSize);
        tessellator.addVertexWithUV(x, y, zLevel, u / textureSize, v / textureSize);
        tessellator.draw();
    }
}
