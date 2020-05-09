package com.mtarld.githubfm.html;

import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.lang3.StringUtils;
import org.intellij.markdown.MarkdownTokenTypes;
import org.intellij.markdown.ast.ASTNode;
import org.intellij.markdown.html.GeneratingProvider;
import org.intellij.markdown.html.HtmlGenerator;
import org.intellij.plugins.markdown.extensions.MarkdownCodeFencePluginGeneratingProvider;

import java.util.ArrayList;
import java.util.List;

import static org.intellij.markdown.ast.ASTUtilKt.getTextInNode;

/**
 * Consumes Markdown code fences, and generates HTML with syntax highlighting
 *
 * (Taken from the original Markdown plugin)
 */
public class MarkdownCodeFenceGeneratingProvider implements GeneratingProvider {

    private final List<MarkdownCodeFencePluginGeneratingProvider> pluginCacheProviders;

    public MarkdownCodeFenceGeneratingProvider(List<MarkdownCodeFencePluginGeneratingProvider> pluginCacheProviders) {
        this.pluginCacheProviders = pluginCacheProviders;
    }

    private String pluginGeneratedHtml(String language, String codeFenceContent, String codeFenceRawContent) {
        return pluginCacheProviders
                .stream()
                .filter(p -> p.isApplicable(language))
                .findFirst()
                .map(p -> p.generateHtml(codeFenceRawContent))
                .orElse(codeFenceContent);
    }

    public void processNode(HtmlGenerator.HtmlGeneratingVisitor visitor, String text, ASTNode node) {
        int indentBefore = StringUtils.getCommonPrefix(getTextInNode(node, text).toString(), " ".repeat(10)).length();

        visitor.consumeHtml("<pre>");

        int state = 0;

        List<ASTNode> childrenToConsider = node.getChildren();
        if (childrenToConsider.get(childrenToConsider.size() - 1).getType() == MarkdownTokenTypes.CODE_FENCE_END) {
            childrenToConsider = childrenToConsider.subList(0, childrenToConsider.size() - 1);
        }

        boolean lastChildWasContent = false;

        List<String> attributes = new ArrayList<>();
        String language = null;
        StringBuilder codeFenceRawContent = new StringBuilder();
        StringBuilder codeFenceContent = new StringBuilder();

        for (ASTNode child : childrenToConsider) {
            if ((state == 1) && List.of(MarkdownTokenTypes.CODE_FENCE_CONTENT, MarkdownTokenTypes.EOL).contains(child.getType())) {
                codeFenceRawContent.append(HtmlGenerator.Companion.trimIndents(codeFenceRawText(text, child), indentBefore));
                codeFenceContent.append(HtmlGenerator.Companion.trimIndents(codeFenceText(text, child), indentBefore));
                lastChildWasContent = child.getType() == MarkdownTokenTypes.CODE_FENCE_CONTENT;
            }
            if (state == 0 && child.getType() == MarkdownTokenTypes.FENCE_LANG) {
                language = HtmlGenerator.Companion.leafText(text, child, false).toString().trim().split(" ")[0];
                attributes.add("class=\"language-$language\"");
            }
            if (state == 0 && child.getType() == MarkdownTokenTypes.EOL) {
                visitor.consumeTagOpen(node, "code", attributes.toArray(new String[0]), true);
                state = 1;
            }
        }

        if (state == 1) {
            visitor.consumeHtml(language != null
                ? pluginGeneratedHtml(language, codeFenceContent.toString(), codeFenceRawContent.toString())
                : codeFenceContent);
        }

        if (state == 0) {
            visitor.consumeTagOpen(node, "code", attributes.toArray(new String[0]), true);
        }

        if (lastChildWasContent) {
            visitor.consumeHtml("\n");
        }

        visitor.consumeHtml("</code></pre>");
    }

    private CharSequence codeFenceRawText(String text, ASTNode node) {
        return !Intrinsics.areEqual(node.getType(), MarkdownTokenTypes.BLOCK_QUOTE) ? getTextInNode(node, text) : "";
    }

    private CharSequence codeFenceText(String text, ASTNode node) {
        return !Intrinsics.areEqual(node.getType(), MarkdownTokenTypes.BLOCK_QUOTE) ? HtmlGenerator.Companion.leafText(text, node, false) : "";
    }

}
