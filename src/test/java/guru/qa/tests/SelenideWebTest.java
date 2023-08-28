package guru.qa.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.CollectionCondition.texts;

public class SelenideWebTest {
    static {
        Configuration.pageLoadStrategy = "eager";
    }
    @BeforeEach
    void setUp() {
        open("https://www.selenium.dev/");
    }

    static Stream<Arguments> LocaleTest() {
        return Stream.of(
                Arguments.of(Locale.Other, List.of("About")),
                Arguments.of(Locale.Português, List.of("About"))
        );
    }
    @Tags({
            @Tag("smoke"),
            @Tag("web")
    })
    @MethodSource("LocaleTest")
    @ParameterizedTest
    void LocaleTest(Locale locale, List<String> expectedButtons) {
        $$("div a").findBy(text("English")).click();
        $$("div a").find(text(locale.name())).click();
        $$("#navbarDropdown").should(texts(expectedButtons));
    }
}
