package com.hirakurai.warmplacesplugin.tasks;

import com.hirakurai.warmplacesplugin.models.WarmBlockData;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class WarmBlockCheckAroundTask implements Runnable {
    private List<WarmBlockData> warmBlockDataList;
    private Map<Material, Double> preferredDistances;
    private Map<String, Integer> warmArmorInfo;
    private Map<Player, ItemStack[]> playersPreviousArmorContents = new HashMap<>();
    private Map<Player, Integer> playerMaxFreezeTicksMap = new HashMap<>();
    ;

    public WarmBlockCheckAroundTask(List<WarmBlockData> warmBlockDataList,
                                    Map<Material, Double> preferredDistances,
                                    Map<String, Integer> warmArmorInfo) {
        this.warmBlockDataList = warmBlockDataList;
        this.preferredDistances = preferredDistances;
        this.warmArmorInfo = warmArmorInfo;
    }

    @Override
    public void run() {
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        for (Player player : onlinePlayers) {
            if (!playersPreviousArmorContents.containsKey(player)) {
                playersPreviousArmorContents.put(player, player.getEquipment().getArmorContents());
            }
            if (!playerMaxFreezeTicksMap.containsKey(player)) {
                playerMaxFreezeTicksMap.put(player, player.getMaxFreezeTicks());

                ItemStack[] armorContents = player.getEquipment().getArmorContents();
                for (ItemStack armorContent : armorContents) {
                    checkPieceOfArmor(player, armorContent);
                }
            }
        }
        if (warmBlockDataList.isEmpty()) {
            for (Player player : onlinePlayers) {
                if (checkPlayerArmorForUpdates(player, player.getEquipment().getArmorContents())) {
                    coldPlayer(player, playerMaxFreezeTicksMap.get(player));
                }
                if (player.getFreezeTicks() != playerMaxFreezeTicksMap.get(player)) {
                    coldPlayer(player, playerMaxFreezeTicksMap.get(player));
                }
            }
            return;
        }
        for (Player player : onlinePlayers) {
            Location playerLocation = player.getLocation();
            double minDistance = Double.MAX_VALUE;
            Location locationOfNearestWarmBlock = null;
            double preferredDistance = Double.MAX_VALUE;
            for (WarmBlockData warmBlockData : warmBlockDataList) {
                Location blockLocation = warmBlockData.getLocation();
                if (blockLocation.getBlock().getWorld().equals(playerLocation.getWorld())) {
                    if (playerLocation.distance(blockLocation) < minDistance) {
                        minDistance = playerLocation.distance(blockLocation);
                        locationOfNearestWarmBlock = blockLocation;
                        preferredDistance = preferredDistances.get(warmBlockData.getBlockMaterial());
                        if (minDistance < preferredDistance) {
                            break;
                        }
                    }
                }
            }
            if (locationOfNearestWarmBlock == null) {
                if (checkPlayerArmorForUpdates(player, player.getEquipment().getArmorContents())) {
                    coldPlayer(player, playerMaxFreezeTicksMap.get(player));
                }
                if (player.getFreezeTicks() != playerMaxFreezeTicksMap.get(player)) {
                    coldPlayer(player, playerMaxFreezeTicksMap.get(player));
                }
            }
            if (minDistance < preferredDistance) {
                if (player.getFreezeTicks() != 0) {
                    warmPlayer(player, player.getFreezeTicks());
                }
            } else {
                if (checkPlayerArmorForUpdates(player, player.getEquipment().getArmorContents())) {
                    coldPlayer(player, playerMaxFreezeTicksMap.get(player));
                }
                if (player.getFreezeTicks() != playerMaxFreezeTicksMap.get(player)) {
                    coldPlayer(player, playerMaxFreezeTicksMap.get(player));
                }
            }
        }
    }

    private boolean checkPlayerArmorForUpdates(Player player, ItemStack[] playerCurrentArmor) {
        ItemStack[] playerPreviousArmor = playersPreviousArmorContents.get(player);
        boolean wasUpdated = false;
        if (!Arrays.equals(playerPreviousArmor, playerCurrentArmor)) {
            for (int i = 0; i < playerPreviousArmor.length; i++) {

                if (playerPreviousArmor[i] != null && playerCurrentArmor[i] != null) {
                    if (!playerPreviousArmor[i].equals(playerCurrentArmor[i])) {
                        if (playerPreviousArmor[i].hasItemMeta()) {
                            ItemMeta itemMeta = playerPreviousArmor[i].getItemMeta();
                            if (itemMeta.hasLore()) {
                                List<String> lore = itemMeta.getLore();
                                if (ChatColor.stripColor(lore.get(lore.size()-1)).equals("Тёплая вещь")) {
                                    increasePlayerMaxFreezeTicks(player, lore);
                                }
                            }
                        }
                        checkPieceOfArmor(player, playerCurrentArmor[i]);
                    }
                }

                if (playerPreviousArmor[i] == null && playerCurrentArmor[i] != null) {
                    checkPieceOfArmor(player, playerCurrentArmor[i]);
                }

                if (playerPreviousArmor[i] != null && playerCurrentArmor[i] == null) {
                    if (playerPreviousArmor[i].hasItemMeta()) {
                        ItemMeta itemMeta = playerPreviousArmor[i].getItemMeta();
                        if (itemMeta.hasLore()) {
                            List<String> lore = itemMeta.getLore();
                            if (ChatColor.stripColor(lore.get(lore.size()-1)).equals("Тёплая вещь")) {
                                increasePlayerMaxFreezeTicks(player, lore);
                            }
                        }
                    }
                }
            }
            wasUpdated = true;
            playersPreviousArmorContents.replace(player, playerCurrentArmor);
        }
        return wasUpdated;
    }

    private void checkPieceOfArmor(Player player, ItemStack pieceOfArmor) {
        if (pieceOfArmor != null) {
            if (pieceOfArmor.hasItemMeta()) {
                ItemMeta itemMeta = pieceOfArmor.getItemMeta();
                if (itemMeta.hasLore()) {
                    List<String> lore = itemMeta.getLore();
                    if (ChatColor.stripColor(lore.get(lore.size()-1)).equals("Тёплая вещь")) {
                        reducePlayerMaxFreezeTicks(player, lore);
                    }
                }
            }
        }
    }

    private void reducePlayerMaxFreezeTicks(Player player, List<String> lore){
        Integer playerMaxFreezeTicks = playerMaxFreezeTicksMap.get(player);
        if(playerMaxFreezeTicks - warmArmorInfo.get(ChatColor.stripColor(lore.get(0))) <= 0){
            playerMaxFreezeTicks = 0;
        } else{
            playerMaxFreezeTicks -= warmArmorInfo.get(ChatColor.stripColor(lore.get(0)));
        }
        playerMaxFreezeTicksMap.replace(player, playerMaxFreezeTicks);
    }

    private void increasePlayerMaxFreezeTicks(Player player, List<String> lore){
        Integer playerMaxFreezeTicks = playerMaxFreezeTicksMap.get(player);
        if(playerMaxFreezeTicks + warmArmorInfo.get(ChatColor.stripColor(lore.get(0))) >= 140){
            playerMaxFreezeTicks = 140;
        } else{
            playerMaxFreezeTicks += warmArmorInfo.get(ChatColor.stripColor(lore.get(0)));
        }
        playerMaxFreezeTicksMap.replace(player, playerMaxFreezeTicks);
    }

    private void warmPlayer(Player player, Integer countOfTicksToReduce) {
        player.setFreezeTicks(player.getFreezeTicks() - countOfTicksToReduce);
    }

    private void coldPlayer(Player player, Integer countOfTicksToSet) {
        player.setFreezeTicks(countOfTicksToSet);
        player.lockFreezeTicks(true);
    }
}
