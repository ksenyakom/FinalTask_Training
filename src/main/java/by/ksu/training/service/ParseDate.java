package by.ksu.training.service;

import java.sql.Date;
import java.time.LocalDate;

public class ParseDate {
    public Date localToSql(LocalDate localDate) {
        return localDate == null ? null : Date.valueOf(localDate);
    }

    public LocalDate sqlToLocal(Date date){
        return date == null? null : date.toLocalDate();
    }
}
