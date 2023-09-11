package telran.spring.students.dto;

import java.time.LocalDate;

public record Mark(String subject, LocalDate date, int score) {
  
}
