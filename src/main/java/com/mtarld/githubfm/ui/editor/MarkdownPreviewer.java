package com.mtarld.githubfm.ui.editor;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Alarm;
import com.mtarld.githubfm.html.MarkdownHtmlGenerator;
import com.mtarld.githubfm.ui.panel.MarkdownHtmlPanel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class MarkdownPreviewer extends UserDataHolderBase implements FileEditor {
    private final static long PARSING_TIMEOUT = 50L;

    @Nullable
    private final Document document;

    @NotNull
    private final MarkdownHtmlPanel panel;

    @NotNull
    private final Alarm alarm = new Alarm(Alarm.ThreadToUse.POOLED_THREAD, this);

    public MarkdownPreviewer(@NotNull VirtualFile file) {
        this.document = FileDocumentManager.getInstance().getDocument(file);
        this.panel = new MarkdownHtmlPanel();

        Document document = FileDocumentManager.getInstance().getDocument(file);
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
        return panel;
    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return panel;
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

    public void updateHtml() {
        if (null == document || Disposer.isDisposed(this)) {
            return;
        }

        //TODO Sanitize
        panel.setText(MarkdownHtmlGenerator.generate(document.getText()));

        //TODO improve async ?
    }
}
