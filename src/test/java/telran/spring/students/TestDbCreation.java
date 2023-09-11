package telran.spring.students;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import telran.spring.students.docs.StudentDoc;
import telran.spring.students.dto.*;
import telran.spring.students.repo.StudentRepository;

@Component
@RequiredArgsConstructor
public class TestDbCreation {
	public static final  Long ID1 = 123l;
	public static final  String PHONE1 = "054-1231231";
	public static final  Long ID2 = 124l;
	public static final  String PHONE2 = "050-1234333";
	public static final  Long ID3 = 125l;
	public static final  Long ID4 = 126l;
	public static final  String PHONE3 = "050-3333333";
	public static final  String PHONE4 = "051-4444444";
	public static final  Long ID5 = 127l;
	public static final  String PHONE5 = "055-5555555";
	public static final  Long ID6 = 128l;
	public static final  String PHONE6 = "050-6666666";
	public static final String SUBJECT1 = "Java";
	public static final LocalDate DATE1 = LocalDate.parse("2023-08-10");
	public static final String SUBJECT2 = "JavaScript";
	public static final LocalDate DATE2 = LocalDate.parse("2023-08-15");
	public static final String SUBJECT3 = "HTML/CSS";
	public static final LocalDate DATE3 = LocalDate.parse("2023-08-25");
	public static final String SUBJECT4 = "React";
	public static final LocalDate DATE4 = LocalDate.parse("2023-09-01");
	Student [] students = {
		new Student(ID1, "name1", PHONE1),
		new Student(ID2, "name2", PHONE2),
		new Student(ID3, "name3", PHONE3),
		new Student(ID4, "name4", PHONE4),
		new Student(ID5, "name5", PHONE5),
		new Student(ID6, "name6", PHONE6),
	};
	Mark[][]marks = {
			{//ID1
				new Mark(SUBJECT1, DATE1, 80),
				new Mark(SUBJECT1, DATE1, 90),
				new Mark(SUBJECT2, DATE2, 70),
				new Mark(SUBJECT3, DATE3, 90)
			},
			{ //ID2
				new Mark(SUBJECT1, DATE1, 70),
				new Mark(SUBJECT2, DATE2, 80),
			},
			{ //ID3
				new Mark(SUBJECT1, DATE1, 80),
				new Mark(SUBJECT2, DATE2, 75),
				new Mark(SUBJECT3, DATE3, 90),
				new Mark(SUBJECT4, DATE4, 100)
			},
			{
				//ID4
				new Mark(SUBJECT4, DATE4, 70),
				
			},
			{
				//ID5
				new Mark(SUBJECT1, DATE1, 80),
				new Mark(SUBJECT2, DATE2, 71),
				new Mark(SUBJECT3, DATE3, 90)
			},
			{
				//ID6
			}
	};
	final StudentRepository studentRepo;
	void createDb() {
		studentRepo.deleteAll();
		List<StudentDoc> studentDocsList= IntStream.range(0, students.length).mapToObj(this::indexToStudent).toList();
		studentRepo.saveAll(studentDocsList);
	}
	
	StudentDoc indexToStudent(int index) {
		Student student = students[index];
		StudentDoc res = new StudentDoc(student.id(), student.name(), student.phone(), null);
		res.setMarks(new ArrayList<>(List.of(marks[index])));
		return res;
	}
	
	double getAvgMark() {
		
		return Arrays.stream(marks).flatMap(Arrays::stream).collect(Collectors.averagingInt(Mark::score));
	}
	
	double getAvgMarkStudent(long id) {
		return Arrays.stream(marks[(int)id - 123]).collect(Collectors.averagingInt(Mark::score));
	}
}
