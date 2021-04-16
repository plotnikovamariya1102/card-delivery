import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.impl.DurationFormat;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.withText;


import java.time.LocalDate;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class CardDeliveryTest {
    private LocalDate plusDays(int n) {
        LocalDate date = LocalDate.now();
        date = date.plusDays(n);
        return date;
    }

    @Test
    void shouldSubmitOrder() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Екатеринбург");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.DELETE);
        String newDate = plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(newDate);
        $("[name='name']").setValue("Плотникова Мария");
        $("[name='phone']").setValue("+73512275695");
        $(".checkbox__box").click();
        $(".button__text").click();
        $(withText("Успешно")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldBe(visible).shouldHave(exactText("Встреча успешно забронирована на " + newDate));
    }


    @Test
    void shouldEarlyOrderDate() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Екатеринбург");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.DELETE);
        String newDate = plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $(".button__text").click();
        $(withText("Неверно введена дата")).shouldBe(visible);
    }

    @Test
    void shouldOrderIsNotPossible() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Екатеринбург");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.DELETE);
        String newDate = plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(newDate);
        $(".button__text").click();
        $(withText( "Заказ на выбранную дату невозможен")).shouldBe(visible);
    }


    @Test
    void shouldAnotherCity() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Златоуст");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.DELETE);
        $(".button__text").click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(visible);
    }

    @Test
    void shouldWriteWrongName() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Екатеринбург");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.DELETE);
        String newDate = plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(newDate);
        $("[name='name']").setValue("Plotnikova Mariya");
        $(".button__text").click();
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(visible);
    }

    @Test
    void houldWriteNumberWithoutPlus() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Екатеринбург");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.DELETE);
        String newDate = plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(newDate);
        $("[name='name']").setValue("Плотникова Мария");
        $("[name='phone']").setValue("83512275695");
        $(".button__text").click();
        $(withText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(visible);
    }

    @Test
    void shouldWriteNumberTwelveSymbols() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Екатеринбург");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.DELETE);
        String newDate = plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(newDate);
        $("[name='name']").setValue("Плотникова Мария");
        $("[name='phone']").setValue("+7983512275695");
        $(".button__text").click();
        $(withText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(visible);
    }

    @Test
    void shouldFieldsEmpty() {
        open("http://localhost:9999");
        $(".button__text").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void shouldCheckboxEmpty(){
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Екатеринбург");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.DELETE);
        String newDate = plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(newDate);
        $("[name='name']").setValue("Плотникова Мария");
        $("[name='phone']").setValue("+73512275695");
        $(".button__text").click();
        $(".checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
