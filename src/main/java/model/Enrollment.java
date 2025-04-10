package model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "enrollment")
public class Enrollment {

    private Enrollment() {
    }

    public Enrollment(User user, Course course) {
        this.course = course;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}
