package com.matarld.githubfm;

import com.google.common.collect.ImmutableMap;
import com.intellij.lang.Language;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonSyntaxHighlightTest extends LightJavaCodeInsightFixtureTestCase {

    public void toto() {
        Assert.assertTrue(true);
    }

    @Test
    public void testHighlight() {

        //JsonSyntaxHighlighterFactory jsonSyntaxHighlighterFactory = new JsonSyntaxHighlighterFactory();

        final PsiFileFactory factory = PsiFileFactory.getInstance(getProject());
        //final PsiFile jsonFile = factory.createFileFromText("test.json", Language.findLanguageByID("JSON"), "{ \"test\": 2,\n \"toto\": \"test\" }");
        final PsiFile jsonFile = factory.createFileFromText("test.json", Language.findLanguageByID("HTML"), "<!-- test --><strong>Salut</strong>");

        System.out.println(jsonFile.getVirtualFile());
        //SyntaxHighlighter h = jsonSyntaxHighlighterFactory.getSyntaxHighlighter(null, jsonFile.getVirtualFile());

        //SyntaxHighlighter h = SyntaxHighlighterFactory.getSyntaxHighlighter(Language.findLanguageByID("JSON"), null, null);
        SyntaxHighlighter h = SyntaxHighlighterFactory.getSyntaxHighlighter(Language.findLanguageByID("HTML"), null, null);

        Lexer lexer = h.getHighlightingLexer();

        Map<TextAttributesKey, String> map = ImmutableMap.of(
                DefaultLanguageHighlighterColors.BLOCK_COMMENT, "pl-c",
                DefaultLanguageHighlighterColors.LINE_COMMENT, "pl-c",
                DefaultLanguageHighlighterColors.NUMBER, "pl-c1",
                DefaultLanguageHighlighterColors.STRING, "pl-s");

        StringBuilder s = new StringBuilder();
        lexer.start(jsonFile.getText());
        do {
            TextAttributesKey[] attributesKeys = h.getTokenHighlights(lexer.getTokenType());
            System.out.println(lexer.getTokenType() + " " + lexer.getTokenText() + " " + Arrays.stream(attributesKeys).map(TextAttributesKey::getFallbackAttributeKey).collect(Collectors.toList()));

            if (attributesKeys.length > 0 && map.containsKey(attributesKeys[attributesKeys.length-1].getFallbackAttributeKey())) {
                s.append(String.format("<span class=\"%s\">%s</span>", map.get(attributesKeys[attributesKeys.length-1].getFallbackAttributeKey()), lexer.getTokenText()));
            } else {
                s.append(lexer.getTokenText());
            }

            lexer.advance();
        } while (lexer.getCurrentPosition().getOffset() < lexer.getBufferEnd());

        System.out.println("\n\n"+s.toString());
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
