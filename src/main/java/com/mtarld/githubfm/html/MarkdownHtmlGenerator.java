package com.mtarld.githubfm.html;

import com.mtarld.githubfm.flavours.GfmFlavourDescriptor;
import org.intellij.markdown.IElementType;
import org.intellij.markdown.ast.ASTNode;
import org.intellij.markdown.html.GeneratingProvider;
import org.intellij.markdown.html.HtmlGenerator;
import org.intellij.markdown.parser.LinkMap;
import org.intellij.markdown.parser.MarkdownParser;

import java.util.Map;

public class MarkdownHtmlGenerator {
    public static String generate(final String text) {
        ASTNode tree = new MarkdownParser(new GfmFlavourDescriptor()).buildMarkdownTreeFromString(text);

        //TODO What is it exactly ?
        LinkMap links = LinkMap.Builder.buildLinkMap(tree, text);
        Map<IElementType, GeneratingProvider> providers = (new GfmFlavourDescriptor()).createHtmlGeneratingProviders(links, null);

        //TODO cache

        return (new HtmlGenerator(text, tree, providers, true)).generateHtml();
    }
}
