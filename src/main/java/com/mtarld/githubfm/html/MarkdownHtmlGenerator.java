package com.mtarld.githubfm.html;

import com.mtarld.githubfm.flavour.GfmFlavourDescriptor;
import org.intellij.markdown.ast.ASTNode;
import org.intellij.markdown.html.HtmlGenerator;
import org.intellij.markdown.parser.MarkdownParser;

public class MarkdownHtmlGenerator {
    public static String generate(final String text) {
        ASTNode root = new MarkdownParser(new GfmFlavourDescriptor()).buildMarkdownTreeFromString(text);

        //TODO cache

        return (new HtmlGenerator(text, root, new GfmFlavourDescriptor(), true)).generateHtml();
    }
}
