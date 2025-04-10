import lombok.Getter;

@Getter
public enum ActionMenu {
    ACTION_ADD_USER(1, "Добавить пользователя"),
    ACTION_DELETE_USER(2, "Удалить пользователя"),
    ACTION_UPDATE_USER(3, "Изменить пользователя"),
    ACTION_SHOW_ALL_USERS(4, "Просмотреть всех пользователей"),
    ACTION_ADD_COURSE(5, "Добавить курс "),
    ACTION_DELETE_COURSE(6, "Удалить курс"),
    ACTION_UPDATE_COURSE(7, "Изменить пользователя"),
    ACTION_SHOW_ALL_COURSE(8, "Просмотреть все курсы"),
    ACTION_ENROLL_IN_A_COURSE(9, "Записать пользователя на курс"),
    ACTION_TAKING_THE_COURSE(10, "Отсчислить пользователя с курса"),
    ACTION_SHOW_ALL_ENROLLS(11, "Посмотреть все записи на курсы"),
    ACTION_EXIT(12, "Выход");

    private final int choice;
    private final String label;

    ActionMenu(int choice, String label) {
        this.choice = choice;
        this.label = label;
    };

    public static ActionMenu fromInt(int value) {
        for (ActionMenu action : values()) {
            if (action.choice == value)
                return action;
        }
        throw new IllegalArgumentException("Неверное значение выбора");
    }

}
