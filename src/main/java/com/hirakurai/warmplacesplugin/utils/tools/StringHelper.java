package com.hirakurai.warmplacesplugin.utils.tools;

import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {
    private static final Pattern HEX_PATTERN = Pattern.compile("&(#[a-f0-9]{6})", 2);

    @NotNull
    public static String color(@NotNull String input) {
        Matcher m = HEX_PATTERN.matcher(input);
        while (m.find()) {
            input = input.replace(m.group(), ChatColor.of(m.group(1)).toString());
        }
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
