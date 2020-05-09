package com.mtarld.githubfm;

import com.intellij.lang.Language;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.mtarld.githubfm.html.MarkdownHtmlGenerator;
import org.junit.Assert;
import org.junit.Test;

public class JsonSyntaxHighlightTest extends LightJavaCodeInsightFixtureTestCase {

    public void toto() {
        Assert.assertTrue(true);
    }

    @Test
    public void testHighlight() {

        final Language language = Language.findLanguageByID("JSON");
        final String code = "{\n    \"key\": 12,\n    \"foo\": \"bar\"\n}";

        MarkdownHtmlGenerator htmlGenerator = new MarkdownHtmlGenerator();
        //final String generated = htmlGenerator.generateHighlightedCode(language, code);
        final String generated = "";
        Assert.assertSame("{<br/>    <span class=\"pl-s\">\"key\"</span>: <span class=\"pl-c1\">12</span>,<br/>    <span class=\"pl-s\">\"foo\"</span>: <span class=\"pl-s\">\"bar\"</span><br/>}", generated);

        /*
            {"comment":"pl-c","punctuation.definition.comment":"pl-c",
            "string.comment":"pl-c",
            "constant":"pl-c1",
            "entity.name.constant":"pl-c1",
            "markup.raw":"pl-c1",
            "meta.diff.header":"pl-c1",
            "meta.module-reference":"pl-c1",
            "meta.output":"pl-c1",
            "meta.property-name":"pl-c1",
            "support":"pl-c1",
            "support.constant":"pl-c1",
            "support.variable":"pl-c1",
            "variable.language":"pl-c1",
            "variable.other.constant":"pl-c1",
            "string":"pl-s",
            "variable":"pl-v",
            "keyword.operator.symbole":"pl-kos",
            "keyword.other.mark":"pl-kos",
            "string.unquoted.import.ada":"pl-kos",
            "entity":"pl-e",
            "entity.name":"pl-en","storage.modifier.import":"pl-smi","storage.modifier.package":"pl-smi","storage.type.java":"pl-smi","variable.other":"pl-smi","variable.parameter.function":"pl-smi","source":"pl-s1","entity.name.tag":"pl-ent","markup.quote":"pl-ent","keyword":"pl-k","storage":"pl-k","storage.type":"pl-k","punctuation.definition.string":"pl-pds","source.regexp":"pl-pds","string.regexp.character-class":"pl-pds","punctuation.section.embedded":"pl-pse","string.regexp":"pl-sr","constant.character.escape":"pl-cce","source.ruby.embedded":"pl-sre","string.regexp.arbitrary-repitition":"pl-sra","sublimelinter.mark.warning":"pl-smw","brackethighlighter.unmatched":"pl-bu","invalid.broken":"pl-bu","invalid.deprecated":"pl-bu","invalid.unimplemented":"pl-bu","message.error":"pl-bu","sublimelinter.mark.error":"pl-bu","invalid.illegal":"pl-ii","carriage-return":"pl-c2","markup.list":"pl-ml","meta.separator":"pl-ms","markup.heading":"pl-mh","markup.italic":"pl-mi","markup.bold":"pl-mb","markup.deleted":"pl-md","meta.diff.header.from-file":"pl-md","punctuation.definition.deleted":"pl-md","markup.inserted":"pl-mi1","meta.diff.header.to-file":"pl-mi1","punctuation.definition.inserted":"pl-mi1","markup.changed":"pl-mc","punctuation.definition.changed":"pl-mc","markup.ignored":"pl-mi2","markup.untracked":"pl-mi2","meta.diff.range":"pl-mdr","brackethighlighter.angle":"pl-ba","brackethighlighter.curly":"pl-ba","brackethighlighter.quote":"pl-ba","brackethighlighter.round":"pl-ba","brackethighlighter.square":"pl-ba","brackethighlighter.tag":"pl-ba","sublimelinter.gutter-mark":"pl-sg","constant.other.reference.link":"pl-corl","string.other.link":"pl-corl"}
         */

    }

}