package com.mtarld.githubfm.constants;

import com.google.common.collect.ImmutableMap;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;

import java.util.Map;

public class HighlightingConstants {

    private static final Map<TextAttributesKey, String> cssMapping = ImmutableMap.of(
            DefaultLanguageHighlighterColors.KEYWORD, "pl-k",
            DefaultLanguageHighlighterColors.BLOCK_COMMENT, "pl-c",
            DefaultLanguageHighlighterColors.LINE_COMMENT, "pl-c",
            DefaultLanguageHighlighterColors.NUMBER, "pl-c1",
            DefaultLanguageHighlighterColors.STRING, "pl-s");

    public static String getCssClass(TextAttributesKey[] attributesKeys) {
        if (attributesKeys.length > 0 && cssMapping.containsKey(attributesKeys[attributesKeys.length-1].getFallbackAttributeKey())) {
            return cssMapping.get(attributesKeys[attributesKeys.length-1].getFallbackAttributeKey());
        }
        return null;
    }

}
