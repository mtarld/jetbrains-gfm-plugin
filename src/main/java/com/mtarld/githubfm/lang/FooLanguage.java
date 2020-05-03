package com.mtarld.githubfm.lang;

import com.intellij.lang.Language;

public class FooLanguage extends Language {
    public static final FooLanguage INSTANCE = new FooLanguage();

    protected FooLanguage() {
        super("Foo", "text/x-markdown");
    }
}
