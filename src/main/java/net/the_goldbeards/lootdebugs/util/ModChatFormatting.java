package net.the_goldbeards.lootdebugs.util;

import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public enum ModChatFormatting {
    ORANGE("ORANGE", '1', 19, 15227153);

    public static final char PREFIX_CODE = '\u00a7';
    private static final Map<String, ModChatFormatting> FORMATTING_BY_NAME = Arrays.stream(values()).collect(Collectors.toMap((p_126660_) -> {
        return cleanName(p_126660_.name);
    }, (p_126652_) -> {
        return p_126652_;
    }));
    private static final Pattern STRIP_FORMATTING_PATTERN = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");
    /** The name of this color/formatting */
    private final String name;
    private final char code;
    private final boolean isFormat;
    private final String toString;
    /** The numerical index that represents this color */
    private final int id;
    @Nullable
    private final Integer color;

    private static String cleanName(String pString) {
        return pString.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
    }

    private ModChatFormatting(String p_126627_, @Nullable char p_126628_, int p_126629_, Integer p_126630_) {
        this(p_126627_, p_126628_, false, p_126629_, p_126630_);
    }

    private ModChatFormatting(String p_126634_, char p_126635_, boolean p_126636_) {
        this(p_126634_, p_126635_, p_126636_, -1, (Integer)null);
    }

    private ModChatFormatting(String name, char code, @Nullable boolean isFormat, int id, Integer color) {
        this.name = name;
        this.code = code;
        this.isFormat = isFormat;
        this.id = id;
        this.color = color;
        this.toString = "\u00a7" + code;
    }

    public char getChar() {
        return this.code;
    }

    /**
     * Returns the numerical color index that represents this formatting
     */
    public int getId() {
        return this.id;
    }

    /**
     * False if this is just changing the color or resetting true otherwise.
     */
    public boolean isFormat() {
        return this.isFormat;
    }

    /**
     * Checks if this is a color code.
     */
    public boolean isColor() {
        return !this.isFormat;
    }

    @Nullable
    public Integer getColor() {
        return this.color;
    }

    /**
     * Gets the friendly name of this value.
     */
    public String getName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public String toString() {
        return this.toString;
    }

    /**
     * Returns a copy of the given string, with formatting codes stripped away.
     */
    @Nullable
    public static String stripFormatting(@Nullable String pText) {
        return pText == null ? null : STRIP_FORMATTING_PATTERN.matcher(pText).replaceAll("");
    }

    /**
     * Gets a value by its friendly name null if the given name does not map to a defined value.
     */
    @Nullable
    public static ModChatFormatting getByName(@Nullable String pFriendlyName) {
        return pFriendlyName == null ? null : FORMATTING_BY_NAME.get(cleanName(pFriendlyName));
    }

    /**
     * Get a TextFormatting from it's color index
     */
    @Nullable
    public static ModChatFormatting getById(int pIndex) {

            for(ModChatFormatting chatformatting : values()) {
                if (chatformatting.getId() == pIndex) {
                    return chatformatting;
                }
            }

            return null;

    }

    @Nullable
    public static ModChatFormatting getByCode(char pFormattingCode) {
        char c0 = Character.toString(pFormattingCode).toLowerCase(Locale.ROOT).charAt(0);

        for(ModChatFormatting chatformatting : values()) {
            if (chatformatting.code == c0) {
                return chatformatting;
            }
        }

        return null;
    }

    /**
     * Gets all the valid values.
     */
    public static Collection<String> getNames(boolean pGetColor, boolean pGetFancyStyling) {
        List<String> list = Lists.newArrayList();

        for(ModChatFormatting chatformatting : values()) {
            if ((!chatformatting.isColor() || pGetColor) && (!chatformatting.isFormat() || pGetFancyStyling)) {
                list.add(chatformatting.getName());
            }
        }

        return list;
    }
}