package telran.spring.students;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static telran.spring.students.TestDbCreation.*;


import telran.spring.students.dto.*;
import telran.spring.students.repo.StudentRepository;
import telran.spring.students.service.StudentsService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentsServiceTests {
	@Autowired
	StudentsService studentsService;
	@Autowired
	TestDbCreation testDbCreation;
	@Autowired
	StudentRepository studentRepo;
	
	@BeforeEach
	void setUp() {
		testDbCreation.createDb();
	}
	@Test
	void studentSubjectMarksTest() {
		List<Mark> marks =  studentsService.getMarksStudentSubject(ID1, SUBJECT1);
	
		assertEquals(2, marks.size());
		
		assertEquals(80, marks.get(0).score());
		assertEquals(90, marks.get(1).score());
		
		
	}
	@Test
	void studentDatesMarksTest() {
		List<Mark> marks = studentsService.getMarksStudentDates(ID1, DATE2, DATE3);
		assertEquals(2, marks.size());
		assertEquals(70, marks.get(0).score());
		assertEquals(90, marks.get(1).score());
		marks = studentsService.getMarksStudentDates(ID4, DATE2, DATE3);
		assertTrue(marks.isEmpty());
	}
	@Test
	void studentsPhonePrefixTest() {
		List<Student> students = studentsService.getStudentsPhonePrefix("050");
		assertEquals(3, students.size());
		Student student2 = students.get(0);
		assertEquals(ID2, student2.id());
		students.forEach(s -> assertTrue(s.phone().startsWith("050")));
	}
	@Test
	void studentsAllMarksGreaterTest() {
		List<IdName> students = studentsService.getSudentsAllScoresGreater(70);
		assertEquals(2, students.size());
		IdName studentDoc = students.get(0);
		
		assertEquals(ID3, studentDoc.getId());
		assertEquals("name3", studentDoc.getName());
		assertEquals(ID5, students.get(1).getId());
		
	}
	@Test
	void studentsFewMarksTest() {
		List<Long> ids = studentsService.removeStudentsWithFewMarks(2);
		assertEquals(2, ids.size());
		assertEquals(ID4, ids.get(0));
		assertEquals(ID6, ids.get(1));
		assertNull(studentRepo.findById(ID4).orElse(null));
		assertNull(studentRepo.findById(ID6).orElse(null));
	}
	@Test
	void getAvgMarkTest() {
		assertEquals(testDbCreation.getAvgMark(), studentsService.getStudentsAvgScore(), 0.1);
	}
	@Test
	void getStudentsAvgMarkeGreaterTest() {
		List<IdName> idNamesGood = studentsService.getGoodStudents();
		List<IdName> idNamesGreater = studentsService.getStudentsAvgMarkGreater(75);
		assertEquals(3, idNamesGood.size());
		assertEquals(ID3, idNamesGood.get(0).getId());
		idNamesGood.forEach(in -> assertTrue(testDbCreation.getAvgMarkStudent(in.getId()) > 75));
		assertEquals("name3", idNamesGood.get(0).getName());
		assertEquals(ID1, idNamesGood.get(1).getId());
		assertEquals("name1", idNamesGood.get(1).getName());
		assertEquals(ID5, idNamesGood.get(2).getId());
		assertEquals("name5", idNamesGood.get(2).getName());
		assertEquals(idNamesGood.size(), idNamesGreater.size());
	}
	@Test
	void findQueryTest() {
		List<IdNameMarks> actualRes = studentsService.findStudents("{phone:{$regex:/^050/}}");
		List<Student> expectedRes = studentsService.getStudentsPhonePrefix("050");
		assertEquals(expectedRes.size(), actualRes.size());
		IdNameMarks actual1 = actualRes.get(0);
		Student expected1 = expectedRes.get(0);
		assertEquals(expected1.id(), actual1.getId());
	}
	
	
	@Test
	void getBestStudTest() {
		List<IdNameMarks> bestStud = studentsService.getBestStudents(2);
		assertEquals(2, bestStud.size());
		IdNameMarks expStud1 = bestStud.get(0);
		IdNameMarks expStud2 = bestStud.get(1);
		assertEquals(ID3, expStud1.getId());
		assertEquals(ID1, expStud2.getId());
		int markStud1 = expStud1.getMarks().stream().mapToInt(Mark::score).sum();
		assertEquals(345, markStud1);
	}
	
	
	@Test
	void getWorstStudTest() {
		List<IdNameMarks> worstStud = studentsService.getWorstStudents(3);
		assertEquals(3, worstStud.size());
		IdNameMarks expStud1 = worstStud.get(0);
		IdNameMarks expStud2 = worstStud.get(1);
		IdNameMarks expStud3 = worstStud.get(2);
		assertEquals(ID6, expStud1.getId());
		assertEquals(ID4, expStud2.getId());
		assertEquals(ID2, expStud3.getId());
	}
	
	@Test
	void getBestStudBySubjTest() {
		List<IdNameMarks> bestStudBySubj2 = studentsService.getBestStudentsSubject(2, SUBJECT2);
		assertEquals(2, bestStudBySubj2.size());
		IdNameMarks expStud1 = bestStudBySubj2.get(0);
		IdNameMarks expStud2 = bestStudBySubj2.get(1);
		assertEquals(ID2, expStud1.getId());
		assertEquals(ID3, expStud2.getId());
		assertEquals(75, expStud2.getMarks().stream().mapToInt(Mark::score).sum());
		List<IdNameMarks> bestStudBySubj3 = studentsService.getBestStudentsSubject(2, SUBJECT3);
		assertEquals(2, bestStudBySubj3.size());
		assertEquals(ID5, bestStudBySubj3.get(0).getId());
		assertEquals(ID3, bestStudBySubj3.get(1).getId());
		//assertEquals(ID3, bestStudBySubj3.get(2).getId());
		
	}
	
	
	
	

}