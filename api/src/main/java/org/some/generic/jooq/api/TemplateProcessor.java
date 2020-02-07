package org.some.generic.jooq.api;

import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Component
public class TemplateProcessor {

    private static final String LANGUAGE_TAG = "en";

    private final SpringTemplateEngine templateEngine;

    @Autowired
    public TemplateProcessor(@Qualifier("templateEngine") SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String createEmailBody(String templateName, Map<String, Object> params) {
        Locale locale = Locale.forLanguageTag(LANGUAGE_TAG);
        Context context = new Context(locale);
        context.setVariables(params);
        return templateEngine.process(templateName, context);
    }
}
