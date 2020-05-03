package com.mtarld.githubfm.ui.panel;

import com.intellij.util.ui.HtmlPanel;
import org.jetbrains.annotations.NotNull;

public class MarkdownHtmlPanel extends HtmlPanel {
    @NotNull
    @Override
    protected String getBody() {
        return getText();
    }
}
