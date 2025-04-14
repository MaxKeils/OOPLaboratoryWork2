package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Entity
@Table(name = "\"user\"")
@Data
public class User {

    public User(@NonNull String firstName, @NonNull String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User() { }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "author")
    private List<Course> createdCourses;

    @OneToMany(mappedBy = "user")
    private List<Enrollment> enrollments;

    @Override
    public String toString() {
        return "ID: "+ id + " | Имя: " + firstName + " | Фамилия: " + lastName + " | email: " + email;
    }

}
