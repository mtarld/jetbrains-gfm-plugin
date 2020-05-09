package com.mtarld.githubfm.html;

import com.intellij.lang.Language;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.mtarld.githubfm.constants.HighlightingConstants;
import org.apache.commons.lang3.StringUtils;
import org.intellij.plugins.markdown.extensions.MarkdownCodeFencePluginGeneratingProvider;
import org.jetbrains.annotations.NotNull;

public class LanguageHtmlGeneratingProvider implements MarkdownCodeFencePluginGeneratingProvider {

    private final Language language;

    public LanguageHtmlGeneratingProvider(Language language) {
        this.language = language;
    }

    @NotNull
    @Override
    public String generateHtml(@NotNull String code) {
        SyntaxHighlighter h = SyntaxHighlighterFactory.getSyntaxHighlighter(language, null, null);

        // Processes code
        Lexer lexer = h.getHighlightingLexer();
        lexer.start(code);

        // Generates HTML
        StringBuilder s = new StringBuilder();
        do {
            TextAttributesKey[] attributesKeys = h.getTokenHighlights(lexer.getTokenType());

            String content = lexer.getTokenText().replaceAll("\n", "<br/>");
            String cssClass = HighlightingConstants.getCssClass(attributesKeys);
            if (StringUtils.isNotEmpty(cssClass)) {
                s.append(String.format("<span class=\"%s\">%s</span>", cssClass, content));
            } else {
                s.append(content);
            }

            lexer.advance();
        } while (lexer.getCurrentPosition().getOffset() < lexer.getBufferEnd());

        return s.toString();
    }

    @Override
    public boolean isApplicable(@NotNull String language) {
        return this.language.isKindOf(language.toUpperCase());
    }
}
