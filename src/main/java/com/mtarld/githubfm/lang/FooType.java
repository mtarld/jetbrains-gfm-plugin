package com.mtarld.githubfm.lang;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class FooType extends LanguageFileType {
    public static final FooType INSTANCE = new FooType();

    private FooType() {
        super(FooLanguage.INSTANCE);
    }


    @NotNull
    @Override
    public String getName() {
        return "Foo";
    }

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @NotNull
    @Override
    public String getDescription() {
        return "Foo";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "foo";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return null;
    }
}
