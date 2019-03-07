package com.tony.billing.test;

import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 将swagger.yaml文件转换成markdown
 *
 * @author jiangwenjie 2019-03-07
 */
@RunWith(JUnit4.class)
public class SwaggerConvert {
    @Test
    public void convert()throws Exception{
        String swaggerYamlPath = "../swagger.yaml";
        String outputPath = "../接口文档";


        Path localSwaggerFile = Paths.get(swaggerYamlPath);
        Path outputFile = Paths.get(outputPath);

        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();
        Swagger2MarkupConverter converter = Swagger2MarkupConverter.from(localSwaggerFile)
                .withConfig(config)
                .build();
        converter.toFile(outputFile);
    }
}
