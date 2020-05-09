package com.mtarld.githubfm.flavour;

import com.intellij.lang.Language;
import com.mtarld.githubfm.html.LanguageHtmlGeneratingProvider;
import com.mtarld.githubfm.html.MarkdownCodeFenceGeneratingProvider;
import org.intellij.markdown.IElementType;
import org.intellij.markdown.MarkdownElementTypes;
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor;
import org.intellij.markdown.html.GeneratingProvider;
import org.intellij.markdown.parser.LinkMap;
import org.intellij.plugins.markdown.extensions.MarkdownCodeFencePluginGeneratingProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GfmFlavourDescriptor extends GFMFlavourDescriptor {
    @NotNull
    @Override
    public Map<IElementType, GeneratingProvider> createHtmlGeneratingProviders(@NotNull LinkMap linkMap, @Nullable URI baseURI) {

        // Adds syntax highlighting for every known languages
        List<MarkdownCodeFencePluginGeneratingProvider> providers = Language.getRegisteredLanguages().stream()
                .map(LanguageHtmlGeneratingProvider::new)
                .collect(Collectors.toList());

        Map<IElementType, GeneratingProvider> map = super.createHtmlGeneratingProviders(linkMap, baseURI);
        map.put(MarkdownElementTypes.CODE_FENCE, new MarkdownCodeFenceGeneratingProvider(providers));

        return map;
    }
}
