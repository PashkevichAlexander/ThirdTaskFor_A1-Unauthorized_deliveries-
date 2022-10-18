import java.sql.Timestamp;

public class Review {

    private String courseName;
    private String studentName;
    private Timestamp timestamp;
    private double rating;
    private String comment;

    public Review() {
    }

    public Review(String courseName, String studentName, Timestamp timestamp, double rating, String comment) {
        this.courseName = courseName;
        this.studentName = studentName;
        this.timestamp = timestamp;
        this.rating = rating;
        this.comment = comment;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}