package guru.qa.tests;
import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Configuration.pageLoadStrategy;
import static com.codeborne.selenide.Selenide.*;

public class TestClass {
    static {
        pageLoadStrategy = "eager";
    }
    @BeforeEach
    void setUp() {

        open("https://www.lamoda.ru/");
    }

    @CsvFileSource(resources = "/search.csv")
    @Tags({
            @Tag("smoke"), // BLOCKER
            @Tag("web")
    })
    @DisplayName("Проверка поиска")
    @ParameterizedTest(name = "В поисковой выдаче присутствует {0} по запросу {1}")
    void successfulSearchTextTest(String url, String searchQuery) {
        $("input[type='text']").click();
        $("input[type='text']")
                .setValue(searchQuery)
                .pressEnter();

        $("._titleWrap_641wy_6").shouldHave(text(url));
    }

    @ValueSource(
            strings = {"женская одежда uniqlo", "женская одежда"}
    )
    @Tags({
            @Tag("smoke"),
            @Tag("web")
    })
    @DisplayName("Проверка поиска")
    @ParameterizedTest(name = "В поиске выдача не пустая для запроса {0}")
    void searchResultsShouldNotBeEmpty(String searchQuery) {
        $("input[type='text']").click();
        $("input[type='text']")
                .setValue(searchQuery)
                .pressEnter();
        $$("._titleWrap_641wy_6").shouldHave(CollectionCondition.sizeGreaterThan(0));
    }
}
