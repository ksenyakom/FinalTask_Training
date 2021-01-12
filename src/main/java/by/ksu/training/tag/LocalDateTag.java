package by.ksu.training.tag;

import by.ksu.training.controller.filter.CommandFromUriFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

/**
 * @Author Kseniya Oznobishina
 * @Date 11.01.2021
 */
public class LocalDateTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger(LocalDateTag.class);

    private LocalDate localDate;
    private String language;

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public int doStartTag() throws JspException {
        try {


            Locale locale = null;
            if (language != null) {
                String[] mass = language.split("_");
                locale = new Locale(mass[0], mass[1]);
            }
            if (locale == null) {
                locale = Locale.getDefault();
            }
            String stringDate = localDateToStringLocale(localDate, locale);
            //Та ли это локаль? или из куков доставать?

        //    logger.debug("Local date {} using locale {} translated to: {}", localDate, locale , stringDate);

            pageContext.getOut().write(stringDate);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    /**
     * A method translates LocalDate to String according Locale.
     * @param localDate a date to translate,
     * @param locale a locale, wich representation is needed,
     * @return String representation of localDate.
     */
    public String localDateToStringLocale(LocalDate localDate, Locale locale) {
        DateTimeFormatter pattern = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(locale);
        return localDate.format(pattern);
    }
}
