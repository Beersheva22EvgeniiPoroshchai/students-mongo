package telran.spring.students.dto;

import java.util.List;

public interface IdNameMarks extends IdName{
  List<Mark>getMarks();
}
