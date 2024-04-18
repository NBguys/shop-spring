package jpabook.jpashop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

class JpashopApplicationTests {

	public static void main(String[] args) {
		List<Student> studentList = Arrays.asList(
				new Student("이산", 10, Student.Sex.MALE, Student.City.Seoul),
				new Student("진영", 8, Student.Sex.FEMALE, Student.City.Pusan),
				new Student("별찬", 9, Student.Sex.MALE, Student.City.Seoul),
				new Student("민주", 11, Student.Sex.FEMALE, Student.City.Pusan)
		);

		Map<Student.Sex, List<Student>> mapBySex = studentList.stream()
				.collect(Collectors.groupingBy(student -> student.getSex()));

		System.out.print("[남학생] ");
		mapBySex.get(Student.Sex.MALE).forEach(s -> System.out.print(s.getName() + " "));

		System.out.print("\n[여학생] ");
		mapBySex.get(Student.Sex.FEMALE).forEach(s -> System.out.print(s.getName() + " "));
		System.out.println();

		Map<Student.City, List<String>> mapByCity = studentList.stream()
				.collect(Collectors.groupingBy(
						Student::getCity,
						Collectors.mapping(Student::getName, Collectors.toList()))
				);

		System.out.print("[서울] ");
		mapByCity.get(Student.City.Seoul).forEach(s -> System.out.print(s + " "));

		System.out.print("\n[부산] ");
		mapByCity.get(Student.City.Pusan).forEach(s -> System.out.print(s + " "));
		System.out.println();

	}

}

class Student {

	public enum Sex {MALE, FEMALE}

	public enum City {Seoul, Pusan}

	private String name;
	private int score;
	private Sex sex;
	private City city;

	public Student(String name, int score, Sex sex) {
		this.name = name;
		this.score = score;
		this.sex = sex;
	}

	public Student(String name, int score, Sex sex, City city) {
		this.name = name;
		this.score = score;
		this.sex = sex;
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	public Sex getSex() {
		return sex;
	}

	public City getCity() {
		return city;
	}

}
