package com.mtarld.githubfm.ui.editor;

import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.mtarld.githubfm.lang.FooType;
import org.jetbrains.annotations.NotNull;

public class MarkdownEditorWithPreviewProvider implements AsyncFileEditorProvider, DumbAware {
    private final static String EDITOR_TYPE_ID = "com.mtarld.githubfm.editor";

    @NotNull
    @Override
    public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
        return createEditorAsync(project, file).build();
    }

    @NotNull
    @Override
    public Builder createEditorAsync(@NotNull Project project, @NotNull VirtualFile file) {
        return new EditorBuilder(project, file);
    }

    @Override
    public boolean accept(@NotNull Project project, @NotNull VirtualFile file) {
        return FileTypeRegistry.getInstance().isFileOfType(file, FooType.INSTANCE);
    }

    @NotNull
    @Override
    public String getEditorTypeId() {
        return EDITOR_TYPE_ID;
    }

    @NotNull
    @Override
    public FileEditorPolicy getPolicy() {
        return FileEditorPolicy.HIDE_DEFAULT_EDITOR;
    }

    private static class EditorBuilder extends Builder {
        @NotNull
        private final Project project;

        @NotNull
        private final VirtualFile file;

        public EditorBuilder(@NotNull Project project, @NotNull VirtualFile file) {
            this.project = project;
            this.file = file;
        }

        @Override
        public FileEditor build() {
            TextEditor editor = (TextEditor) TextEditorProvider.getInstance().createEditor(project, file);
            FileEditor preview = new MarkdownPreviewer(file, editor);

            return new TextEditorWithPreview(editor, preview);
        }
    }
}
