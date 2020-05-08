package com.mtarld.githubfm.html;

import org.intellij.markdown.ast.ASTNode;
import org.intellij.markdown.html.GeneratingProvider;
import org.intellij.markdown.html.HtmlGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CodeHighlightingProvider implements GeneratingProvider {
    @Override
    public void processNode(@NotNull HtmlGenerator.HtmlGeneratingVisitor visitor, @NotNull String text, @NotNull ASTNode node) {
        List<ASTNode> children = node.getChildren();

        //TODO do the lexing here
        // @see MarkdownCodeFenceGeneratingProvider
    }
}
