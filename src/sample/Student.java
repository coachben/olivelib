package sample;


import java.time.LocalDate;

public class Student {

    private String lastName;
    private String residence;
    private String firstName;
    private String surname;
    private int phone;
    private int studentId;
    private String dateOfBirth;
    private String admissionDate;
    private String schoolClass;
    private String gender;

    public Student(String lastName, String residence, String firstName, String surname, int phone, int studentId, String dateOfBirth, String admissionDate, String schoolClass, String gender) {
        this.lastName = lastName;
        this.residence = residence;
        this.firstName = firstName;
        this.surname = surname;
        this.phone = phone;
        this.studentId = studentId;
        this.dateOfBirth = dateOfBirth;
        this.admissionDate = admissionDate;
        this.schoolClass = schoolClass;
        this.gender = gender;
    }



    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(String schoolClass) {
        this.schoolClass = schoolClass;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }



}
