package by.ksu.training.tag;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Locale;

import static org.testng.Assert.*;

public class LocalDateTagTest {
    @DataProvider(name = "data")
    public Object[][] getDate() {
        return new Object[][]{
                {LocalDate.of(2021, 1, 5), new Locale("ru", "RU"), "05.01.2021"},
                {LocalDate.of(2021, 1, 5), new Locale("en", "US"), "1/5/21"},
                {LocalDate.of(2021, 1, 5), new Locale("fr", "FR"), "05/01/2021"},
        };
    }

    @Test(dataProvider = "data")
    public void testLocalDateToStringLocale(LocalDate localDate, Locale locale, String expected) {
        LocalDateTag localDateTag = new LocalDateTag();
        String actual = localDateTag.localDateToStringLocale(localDate, locale);
        assertEquals(actual, expected, String.format("Local date {} using locale {} translated to: {}", localDate, locale , actual));
    }
}