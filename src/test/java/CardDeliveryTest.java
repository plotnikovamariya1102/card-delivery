import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selectors.byText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import  java.time.Duration;
import java.util.Calendar;

public class CardDeliveryTest {
    public String ShouldSetDateMoreThreeDays(int numberDays) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.DATE, 3);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(currentDate.getTime());
    }

    public String ShouldSetDateLessThreeDays(int Days) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.DATE, 2);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(currentDate.getTime());
    }

    @Test
    void shouldSubmitOrder() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Екатеринбург");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(ShouldSetDateMoreThreeDays(3));
        $("[name='name']").setValue("Плотникова Мария");
        $("[name='phone']").setValue("+73512275695");
        $(".checkbox__box").click();
        $(".button__text").click();
        $(withText("Успешно")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }


    @Test
    void shouldEarlyOrderDate() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Екатеринбург");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(ShouldSetDateLessThreeDays(2));
        $(".button__text").click();
        $(withText("Заказ на выбранную дату невозможен")).shouldBe(visible);
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
        $("[placeholder='Дата встречи']").setValue(ShouldSetDateMoreThreeDays(3));
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
        $("[placeholder='Дата встречи']").setValue(ShouldSetDateMoreThreeDays(3));
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
        $("[placeholder='Дата встречи']").setValue(ShouldSetDateMoreThreeDays(3));
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
        $("[placeholder='Дата встречи']").setValue(ShouldSetDateMoreThreeDays(3));
        $("[name='name']").setValue("Плотникова Мария");
        $("[name='phone']").setValue("+73512275695");
        $(".button__text").click();
        $(".checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}