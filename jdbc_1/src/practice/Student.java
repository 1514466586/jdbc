package practice;

public class Student {
    private int flowID;
    private int type;
    private String IDCard;
    private String examCard;
    private String studentName;
    private String location;
    private int grade;

    @Override
    public String toString() {
        return "Student{" +
                "flowID=" + flowID +
                ", type=" + type +
                ", IDCard=" + IDCard +
                ", examCard='" + examCard + '\'' +
                ", studentName='" + studentName + '\'' +
                ", location='" + location + '\'' +
                ", grad='" + grade + '\'' +
                '}';
    }

    public int getFlowID() {
        return flowID;
    }

    public void setFlowID(int flowID) {
        this.flowID = flowID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public String getExamCard() {
        return examCard;
    }

    public void setExamCard(String examCard) {
        this.examCard = examCard;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getGrad() {
        return grade;
    }

    public void setGrad(int grad) {
        this.grade = grad;
    }

    public Student() {
    }

    public Student(int flowID, int type, String IDCard, String examCard, String studentName, String location, int grade) {
        this.flowID = flowID;
        this.type = type;
        this.IDCard = IDCard;
        this.examCard = examCard;
        this.studentName = studentName;
        this.location = location;
        this.grade = grade;
    }
}
