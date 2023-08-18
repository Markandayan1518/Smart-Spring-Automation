package com.example.demo.annotation;

import com.example.demo.data.BaseTestData;
import com.opencsv.bean.CsvToBeanBuilder;
import org.junit.jupiter.api.extension.*;

import java.io.FileReader;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class CustomParameterizedTestExtension implements TestTemplateInvocationContextProvider, ParameterResolver {

    private List<?> testData;

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return context.getTestMethod().isPresent() &&
                context.getTestMethod().get().isAnnotationPresent(CustomParameterizedTest.class) &&
                context.getTestMethod().get().isAnnotationPresent(CustomFileSource.class);
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        CustomFileSource customFileSource = context.getRequiredTestMethod().getAnnotation(CustomFileSource.class);
        String filePath = customFileSource.file();
        Class<? extends BaseTestData> clazz = customFileSource.clazz();

        try (FileReader fileReader = new FileReader(Paths.get(filePath).toFile())) {
            testData = new CsvToBeanBuilder<>(fileReader)
                    .withType(clazz)
                    .withSeparator(customFileSource.delimiter().charAt(0))
                    .withSkipLines(customFileSource.skip())
                    .withIgnoreLeadingWhiteSpace(customFileSource.trim())
                    .build()
                    .parse();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read CSV file", e);
        }

        return testData.stream().map(CustomTestInvocationContext::new);
    }


    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getIndex() == 0;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        CustomTestInvocationContext invocationContext = (CustomTestInvocationContext) extensionContext.getStore(ExtensionContext.Namespace.create(CustomParameterizedTestExtension.class, extensionContext))
                .get("invocationContext");

        if (invocationContext == null) {
            throw new ParameterResolutionException("No CustomTestInvocationContext found in ExtensionContext");
        }

        return invocationContext.getParameter();
    }

    private static class CustomTestInvocationContext implements TestTemplateInvocationContext, ParameterResolver {
        private final Object parameter;

        CustomTestInvocationContext(Object parameter) {
            this.parameter = parameter;
        }

        Object getParameter() {
            return parameter;
        }

        @Override
        public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
            return parameterContext.getParameter().getType().isAssignableFrom(parameter.getClass());
        }

        @Override
        public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
            return parameter;
        }

        @Override
        public String getDisplayName(int invocationIndex) {
            return "Invocation " + invocationIndex;
        }
    }
}
