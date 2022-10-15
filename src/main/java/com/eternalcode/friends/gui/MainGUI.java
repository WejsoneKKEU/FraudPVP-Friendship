package com.eternalcode.friends.gui;

import com.eternalcode.friends.config.implementation.GuiConfig;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.List;


public class MainGUI {

    private final MiniMessage miniMessage;
    private final GuiConfig guiConfig;
    private final FriendListGUI friendListGUI;
    private Gui gui;


    public MainGUI(MiniMessage miniMessage, GuiConfig guiConfig, FriendListGUI friendListGUI) {
        this.miniMessage = miniMessage;
        this.guiConfig = guiConfig;
        this.friendListGUI = friendListGUI;
        initializeGui();
    }

    private void initializeGui() {
        GuiConfig.MainGui mainGui = guiConfig.mainGui;
        this.gui = Gui.gui()
                .title(this.miniMessage.deserialize(mainGui.title))
                .rows(mainGui.rows)
                .disableItemTake()
                .create();

        GuiItem guiItem = guiConfig.friendListItem.toGuiItem();
        guiItem.setAction(event -> {
            friendListGUI.openInventory((Player) event.getWhoClicked(), player -> openInventory(player));
            });
        this.gui.setItem(guiConfig.friendListItem.slot, guiItem);

        List.of(
                guiConfig.receivedAndSentInvitesItem,
                guiConfig.sendInvitesItem,
                guiConfig.settingItem
        ).forEach(item -> this.gui.setItem(item.slot, item.toGuiItem()));
    }

    public void openInventory(Player player) {
        gui.open(player);
    }
}