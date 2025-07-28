package reforged.mods.blockhelper.addons.utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A utility class for managing a stack of screen rectangles for scissoring.
 */
@SideOnly(Side.CLIENT)
public class ScissorsStack {
    Deque<ScreenRectangle> stack = new ArrayDeque<ScreenRectangle>();

    /**
     * Pushes a screen rectangle onto the scissor stack.
     * <p>
     * @param scissor the screen rectangle to push.
     */
    public void push(ScreenRectangle scissor) {
        if (stack.isEmpty()) {
            stack.push(scissor);
            return;
        }
        scissor.limit(stack.peek());
    }

    public ScreenRectangle pop() {
        stack.pop();
        return stack.peek();
    }
}
