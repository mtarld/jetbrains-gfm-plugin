package com.mtarld.githubfm.flavour;

import com.mtarld.githubfm.html.CodeHighlightingProvider;
import org.intellij.markdown.IElementType;
import org.intellij.markdown.MarkdownElementTypes;
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor;
import org.intellij.markdown.html.GeneratingProvider;
import org.intellij.markdown.parser.LinkMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class GfmFlavourDescriptor extends GFMFlavourDescriptor {
    @NotNull
    @Override
    public Map<IElementType, GeneratingProvider> createHtmlGeneratingProviders(@NotNull LinkMap linkMap, @Nullable URI baseURI) {
        Map<IElementType, GeneratingProvider> map = new HashMap<IElementType, GeneratingProvider>();
        map.put(MarkdownElementTypes.CODE_FENCE, new CodeHighlightingProvider());

        return map;
    }
}
