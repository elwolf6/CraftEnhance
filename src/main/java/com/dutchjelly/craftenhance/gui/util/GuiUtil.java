package com.dutchjelly.craftenhance.gui.util;

import com.dutchjelly.craftenhance.exceptions.ConfigError;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GuiUtil {

    public static Inventory CopyInventory(ItemStack[] invContent, String title, InventoryHolder holder){
        if(invContent == null) return null;
        List<ItemStack> copiedItems = Arrays.stream(invContent).map(x -> x == null ? null : x.clone()).collect(Collectors.toList());
        if(copiedItems.size() != invContent.length)
            throw new IllegalStateException("Failed to copy inventory items.");
        Inventory copy = Bukkit.createInventory(holder, invContent.length, title);
        for(int i = 0; i < copiedItems.size(); i++)
            copy.setItem(i, copiedItems.get(i));
        return copy;
    }



    public static Inventory FillInventory(Inventory inv, List<Integer> fillSpots, List<ItemStack> items){
        if(inv == null)
            throw new ConfigError("Cannot fill null inventory");

        if(items.size() > fillSpots.size())
            throw new ConfigError("Too few slots to fill.");

        for(int i = 0; i < items.size(); i++){
            if(fillSpots.get(i) >= inv.getSize())
                throw new ConfigError("Fill spot is outside inventory.");
            inv.setItem(fillSpots.get(i), items.get(i));
        }
        return inv;
    }

    public static ItemStack ReplaceAllPlaceHolders(ItemStack item, Map<String,String> placeholders){
        if(item == null) return null;
        placeholders.keySet().forEach(x -> ReplacePlaceHolder(item, x, placeholders.get(x)));
        return item;
    }

    public static ItemStack ReplacePlaceHolder(ItemStack item, String placeHolder, String value){
        if(item == null) return null;


        ItemMeta meta = item.getItemMeta();
        if(meta.getDisplayName().contains(placeHolder)){
            meta.setDisplayName(meta.getDisplayName().replace(placeHolder, value));
            item.setItemMeta(meta);
        }


        List<String> lore = meta.getLore();
        if(lore == null)
            return item;

        lore = lore.stream().map(x -> (x == null ? null : x.replace(placeHolder, value))).collect(Collectors.toList());
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}