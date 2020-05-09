package com.mtarld.githubfm.ui.editor;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.Alarm;
import com.mtarld.githubfm.html.MarkdownHtmlGenerator;
import com.mtarld.githubfm.ui.panel.MarkdownHtmlPanel;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class MarkdownPreviewer extends UserDataHolderBase implements FileEditor {
    private final static long PARSING_TIMEOUT = 50L;

    @Nullable
    private final Document document;
    private final VirtualFile file;

    @NotNull
    private final MarkdownHtmlPanel panel;
    private final JComponent container;
    @NotNull
    private final Alarm alarm = new Alarm(Alarm.ThreadToUse.POOLED_THREAD, this);

    private List<String> css = new LinkedList<>();

    public MarkdownPreviewer(@NotNull VirtualFile file, @NotNull TextEditor editor) {
        this.document = FileDocumentManager.getInstance().getDocument(file);
        this.file = file;

        this.panel = new MarkdownHtmlPanel();
        this.container = new JBScrollPane(this.panel);
        this.loadCss();

        Document document = FileDocumentManager.getInstance().getDocument(file);

        editor.getEditor().getScrollingModel().addVisibleAreaListener((e) -> {
            // FIXME : Compute scroll preserving ratio
            this.panel.scrollRectToVisible(e.getNewRectangle());
        });

        if (null != document) {
            document.addDocumentListener(new DocumentListener() {
                @Override
                public void beforeDocumentChange(@NotNull DocumentEvent event) {
                    alarm.cancelAllRequests();
                }

                @Override
                public void documentChanged(@NotNull DocumentEvent event) {
                    alarm.addRequest(() -> {
                        updateHtml();
                    }, PARSING_TIMEOUT);
                }
            });

            updateHtml();
        }

    }

    @NotNull
    @Override
    public JComponent getComponent() {
        return container;
    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return container;
    }

    @NotNull
    @Override
    public String getName() {
        return "Github Flavoured Markdown Preview";
    }

    @Override
    public void setState(@NotNull FileEditorState state) {
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void addPropertyChangeListener(@NotNull PropertyChangeListener listener) {
    }

    @Override
    public void removePropertyChangeListener(@NotNull PropertyChangeListener listener) {
    }

    @Nullable
    @Override
    public FileEditorLocation getCurrentLocation() {
        return null;
    }

    @Override
    public void dispose() {
    }

    private void updateHtml() {
        if (null == document || Disposer.isDisposed(this)) {
            return;
        }

        //TODO Sanitize
        String html = MarkdownHtmlGenerator.generate(document.getText());

        // FIXME : Generate proper HTML...
        html = html.replaceAll("<body ", "<body class=\"markdown-body\" ");
        html = "<html><head><style>" + String.join("\n", css) + "</style></head>" + html + "</html>";

        panel.setText(html);

        //TODO improve async ?
    }

    private void loadCss() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            css.add(IOUtils.resourceToString("/css/primer.css", StandardCharsets.UTF_8, classLoader));
            css.add(IOUtils.resourceToString("/css/light.css", StandardCharsets.UTF_8, classLoader));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
