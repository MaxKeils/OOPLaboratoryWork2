package model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "course")
@Data
public class Course {

    public Course(String title, User user) {
        this.author = user;
        this.title = title;
    }

    private Course() { }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments;


    @Override
    public String toString() {
        return "ID: "+ id + " | Название: " + title + " | Автор: " + author.getFirstName() + " " + author.getLastName();
    }

}
