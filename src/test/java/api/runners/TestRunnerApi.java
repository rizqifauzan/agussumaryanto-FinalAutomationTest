package api.runners;

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")     // run the cucumber engine on JUnit Platform
@SelectClasspathResource("features/api_features")   // feature folder is in src/test/resources/features
@ConfigurationParameter(
        key = GLUE_PROPERTY_NAME,
        value = "api.stepdefinitions"   // package step definition
)
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty," +
                "html:build/reports/cucumber/api_test_report.html," +
                "json:build/reports/cucumber/api_test_report.json"
)

@ConfigurationParameter(
        key = FILTER_TAGS_PROPERTY_NAME,
        value = "@api and not @exclude" // filter, can be overridden with ./gradlew test -Dcucumber.filter.tags="@api"
)

public class TestRunnerApi {
    // empty, only as an entry point for Cucumber + Gradle/JUnit Platform
}