package com.dutchjelly.craftenhance.gui.guis;

import com.dutchjelly.craftenhance.CraftEnhance;
import com.dutchjelly.craftenhance.crafthandling.recipes.FurnaceRecipe;
import com.dutchjelly.craftenhance.crafthandling.recipes.WBRecipe;
import com.dutchjelly.craftenhance.gui.GuiManager;
import com.dutchjelly.craftenhance.gui.guis.editors.FurnaceRecipeEditor;
import com.dutchjelly.craftenhance.gui.guis.editors.WBRecipeEditor;
import com.dutchjelly.craftenhance.gui.templates.GuiTemplate;
import com.dutchjelly.craftenhance.gui.util.ButtonType;
import com.dutchjelly.craftenhance.gui.util.GuiUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EditorTypeSelector extends GUIElement {

    private Inventory inventory;

    public EditorTypeSelector(GuiManager manager, GuiTemplate template, GUIElement previousGui, Player player, String key, String permission){
        super(manager, template, previousGui, player);
        this.addBtnListener(ButtonType.ChooseFurnaceType, (btn, type) -> {
            WBRecipe newRecipe = new WBRecipe(permission, null, new ItemStack[9]);
            newRecipe.setKey(key);

            WBRecipeEditor gui = new WBRecipeEditor(
                    CraftEnhance.self().getGuiManager(),
                    CraftEnhance.self().getGuiTemplatesFile().getTemplate(WBRecipeEditor.class),
                    null, getPlayer(), newRecipe
            );
            getManager().openGUI(getPlayer(), gui);
        });
        this.addBtnListener(ButtonType.ChooseWorkbenchType, (btn, type) -> {
            FurnaceRecipe newRecipe = new FurnaceRecipe(permission, null, new ItemStack[9]);
            newRecipe.setKey(key);

            FurnaceRecipeEditor gui = new FurnaceRecipeEditor(
                    CraftEnhance.self().getGuiManager(),
                    CraftEnhance.self().getGuiTemplatesFile().getTemplate(WBRecipeEditor.class),
                    null, getPlayer(), newRecipe
            );
            getManager().openGUI(getPlayer(), gui);
        });
        inventory = GuiUtil.CopyInventory(getTemplate().getTemplate(), getTemplate().getInvTitle(), this);
    }

    @Override
    public void handleEventRest(InventoryClickEvent e) {}

    @Override
    public boolean isCancelResponsible() {
        return false;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
