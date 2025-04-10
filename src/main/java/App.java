import db.course.CourseDao;
import db.enrollment.EnrollmentDao;
import db.user.UserDao;
import model.Course;
import model.Enrollment;
import model.User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UserDao userDao = new UserDao();
        CourseDao courseDao = new CourseDao();
        EnrollmentDao enrollmentDao = new EnrollmentDao();

        printMenu();
        ActionMenu choice = enterChoice(scanner);

        while (choice != ActionMenu.ACTION_EXIT) {
            switch (choice) {
                case ActionMenu.ACTION_ADD_USER -> {
                    System.out.print("Введите имя пользователя: ");
                    String firstName = scanner.nextLine();

                    System.out.print("Введите фамилию пользователя: ");
                    String lastName = scanner.nextLine();

                    System.out.print("Введите email пользователя (Если не нужен, оставьте поле пустым): ");
                    String email = scanner.nextLine();
                    email = email.isEmpty() ? null : email;

                    User newUser = new User(firstName, lastName, email);
                    userDao.save(newUser);
                }
                case ActionMenu.ACTION_DELETE_USER -> {
                    Long userId = readLong("Введите ID пользователя, которого хотите удалить: ", scanner);

                    if (userId == null)
                        break;

                    boolean status = userDao.deleteById(userId);
                    System.out.printf("Удаление прошло %s!\n", status ? "успешно" : "неудачно");
                }
                case ACTION_UPDATE_USER -> {
                    Long userId = readLong("Введите ID пользователя, которого хотите изменить: ", scanner);

                    if (userId == null)
                        break;

                    User user = userDao.getById(userId);

                    if (user == null) {
                        System.out.println("Пользователь не найден");
                        break;
                    }

                    System.out.print("Введите новое имя пользователя: ");
                    String firstName = scanner.nextLine();

                    System.out.print("Введите новую фамилию пользователя: ");
                    String lastName = scanner.nextLine();

                    user.setFirstName(firstName);
                    user.setLastName(lastName);

                    userDao.update(user);

                    System.out.println("Пользователь обновлён");
                }
                case ActionMenu.ACTION_SHOW_ALL_USERS -> {
                    List<User> users = userDao.getAll();
                    if (users.isEmpty()) {
                        System.out.println("Пользователи не найдены.");
                    } else {
                        users.forEach(System.out::println);
                    }
                }
                case ActionMenu.ACTION_ADD_COURSE -> {
                    Long userId = readLong(
                            "Введите ID пользователя, который будет являться автором курса: ",
                            scanner
                    );

                    if (userId == null)
                        break;

                    User user = userDao.getById(userId);

                    if (user == null) {
                        System.out.println("Указан несуществующий ID");
                        break;
                    }

                    System.out.print("Введите название курса: ");
                    String courseName = scanner.nextLine();

                    Course newCourse = new Course(courseName, user);
                    courseDao.save(newCourse);
                }
                case ACTION_DELETE_COURSE -> {
                    Long courseId = readLong("Введите ID курса, которого хотите удалить: ", scanner);

                    if (courseId == null)
                        break;

                    boolean status = courseDao.deleteById(courseId);
                    System.out.printf("Удаление прошло %s!\n", status ? "успешно" : "неудачно");
                }
                case ACTION_UPDATE_COURSE -> {
                    Long courseId = readLong("Введите ID курса, который хотите изменить: ", scanner);

                    if (courseId == null)
                        break;

                    Course course = courseDao.getById(courseId);

                    if (course == null) {
                        System.out.println("Курс не найден");
                        break;
                    }

                    System.out.print("Введите новое название курса: ");
                    String name = scanner.nextLine();

                    course.setTitle(name);

                    courseDao.update(course);

                    System.out.println("Курс обновлён");
                }
                case ACTION_SHOW_ALL_COURSE -> {
                    List<Course> courses = courseDao.getAll();
                    if (courses.isEmpty()) {
                        System.out.println("Курсы не найдены.");
                    } else {
                        courses.forEach(System.out::println);
                    }
                }
                case ACTION_ENROLL_IN_A_COURSE -> {

                    Long userId = readLong(
                        "Введите ID пользователя, которого надо записать на курс: ",
                        scanner
                    );

                    if (userId == null)
                        break;

                    User user = userDao.getById(userId);

                    if (user == null) {
                        System.out.println("Пользователя не существует");
                        break;
                    }

                    Long courseId = readLong(
                        "Введите ID курса: ",
                        scanner
                    );

                    if (courseId == null)
                        break;

                    Course course = courseDao.getById(courseId);

                    if (course == null) {
                        System.out.println("Курса не существует");
                        break;
                    }

                    Enrollment enrollment = new Enrollment(user, course);
                    enrollmentDao.save(enrollment);
                }
                case ACTION_TAKING_THE_COURSE -> {
                    Long enrollmentId = readLong("Введите ID зачисления: ", scanner);
                    if (enrollmentId == null)
                        break;

                    boolean status = enrollmentDao.deleteById(enrollmentId);
                    System.out.printf("Удаление прошло %s!\n", status ? "успешно" : "неудачно");
                }
                case ACTION_SHOW_ALL_ENROLLS -> {
                    List<Enrollment> enrollments = enrollmentDao.getAll();
                    if (enrollments.isEmpty()) {
                        System.out.println("Зачисления не найдены.");
                    } else {
                        enrollments.forEach(System.out::println);
                    }
                }
            }

            printMenu();
            choice = enterChoice(scanner);
        }
    }

    static private void printMenu() {
        System.out.println("Пункты меню:");
        for (ActionMenu value : ActionMenu.values()) {
            System.out.printf("%d. %s\n", value.getChoice(), value.getLabel());
        }
    }

    static private ActionMenu enterChoice(Scanner scanner) {
        ActionMenu actionMenu = null;
        while (actionMenu == null) {
            System.out.print("Введите номер пункта меню: ");
            try {
                actionMenu = ActionMenu.fromInt(scanner.nextInt());
            } catch (IllegalArgumentException e) {
                System.out.println("Некорректный ввод пункта меню. Попробуйте ещё раз!");
            } catch (InputMismatchException e) {
                System.out.println("Введено некорректное число. Повторите попытку!");
            } catch (Exception e) {
                System.out.println("Неизвестная ошибка. Попробуйте ещё раз!");
            }
            scanner.nextLine();
        }
        return actionMenu;
    }

    private static Long readLong(String message, Scanner scanner) {
        Long value = null;
        while (value == null) {
            System.out.print(message);
            try {
                value = scanner.nextLong();
            } catch (InputMismatchException e) {
                System.out.println("Введено некорректное число. Повторите попытку!");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ошибка ввода. Прерывание.");
                break;
            }
            scanner.nextLine();
        }
        return value;
    }
}
