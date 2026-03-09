package web.runners;

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/web_features")   // folder where .feature files are stored
@ConfigurationParameter(
        key = GLUE_PROPERTY_NAME,
        value = "web.stepdefinitions,web.hooks"    // need web.hooks to initialize WebDriver
)
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty," +
                "html:build/reports/cucumber/webUi_test_report.html," +
                "json:build/reports/cucumber/webUi_test_report.json" +
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
)

@ConfigurationParameter(
        key = FILTER_TAGS_PROPERTY_NAME,
        value = "@web and not @exclude" // filter, can be overridden with ./gradlew test -Dcucumber.filter.tags="@web"
)

public class TestRunnerWeb {
    // empty, no code needed here, auto runner based on annotations
}