package reforged.mods.blockhelper.addons.utils;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import reforged.mods.blockhelper.addons.utils.interfaces.IInfoProvider;

import java.util.ArrayList;
import java.util.List;

public class ToolIntegrationRecipe implements IRecipe {

    private boolean matchesAnyFilter(ItemStack stack) {
        for (IInfoProvider.IFilter filter : ToolIntegrationHelper.TOOL_FILTERS) {
            if (filter.matches(stack)) return true;
        }
        return false;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        ItemStack helmet = null;
        boolean foundTool = false;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null) {
                if (ToolIntegrationHelper.isHelmet(stack)) {
                    if (helmet != null) return false;
                    helmet = stack;
                } else if (matchesAnyFilter(stack)) {
                    foundTool = true;
                } else {
                    return false;
                }
            }
        }
        return helmet != null && foundTool;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack helmet = null;
        List<ToolIntegrationHelper.Tools> toolsToAdd = new ArrayList<ToolIntegrationHelper.Tools>();

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null) {
                if (ToolIntegrationHelper.isHelmet(stack)) {
                    helmet = stack;
                } else if (InfoProvider.READER.matches(stack)) {
                    toolsToAdd.add(ToolIntegrationHelper.Tools.READER);
                } else if (InfoProvider.ANALYZER.matches(stack)) {
                    toolsToAdd.add(ToolIntegrationHelper.Tools.CROPNALYZER);
                } else if (InfoProvider.TREETAP.matches(stack)) {
                    toolsToAdd.add(ToolIntegrationHelper.Tools.TREETAP);
                }
            }
        }

        if (helmet != null && !toolsToAdd.isEmpty()) {
            ItemStack newHelmet = helmet.copy();
            for (ToolIntegrationHelper.Tools tool : toolsToAdd) {
                ToolIntegrationHelper.addTool(newHelmet, tool);
            }
            return newHelmet;
        }
        return null;
    }

    @Override
    public int getRecipeSize() {
        return 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
}
