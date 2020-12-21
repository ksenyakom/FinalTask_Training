package by.ksu.training.entity;

import java.time.LocalDate;

public class Assignment extends Entity{
    private Trainer trainer;
    private Visitor visitor;
    private LocalDate beginDate;
    private LocalDate endDate;
    private boolean active;
}
