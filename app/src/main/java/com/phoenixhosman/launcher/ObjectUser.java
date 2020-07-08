package com.phoenixhosman.launcher;

/**
 * The type Objectuser.
 */
@SuppressWarnings("ALL")
class ObjectUser {
    public Integer success;
    public String message;
    public final Integer id;
    public final String username;
    private final Integer grade;
    private final String gradename;
    private final Integer department;
    private final String departmentname;

    /**
     * Instantiates a new ObjectUser.
     *
     * @param id         the id
     * @param username   the username
     * @param grade      the grade number
     * @param gradename  the grade name
     * @param department the department number
     * @param departmentname the department name
     */
    ObjectUser(Integer id, String username, Integer grade, String gradename, Integer department, String departmentname) {
        this.id = id;
        this.username = username;
        this.grade = grade;
        this.gradename = gradename;
        this.department = department;
        this.departmentname = departmentname;
    }

    /**
     * Gets the success flag
     *
     * @return the success flag
     */
    Integer getSuccess() {
        return success;
    }

    /**
     * Gets the message
     *
     * @return the message
     */
    String getMessage() {
        return message;
    }

    /**
     * Gets username
     *
     * @return the username
     */
    String getUsername() {
        return username;
    }

    /**
     * Gets grade number
     *
     * @return Integer the grade number
     */
    Integer getGrade() {
        return grade;
    }

    /**
     * Gets the gradename
     *
     * $return String - the gradename
     */
    String getGradename() {
        return gradename;
    }

    /**
     * Gets department.
     *
     * @return the department
     */
    Integer getDepartment() {
        return department;
    }

    /**
     * Gets the department name
     *
     * @return String - the department name
     */
    String getDepartmentname() {
        return departmentname;
    }
}
