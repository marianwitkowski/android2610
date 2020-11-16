package pl.alx.sqliteexample;

public class Employee {
    int id;
    String name, departament, joiningdate;
    double salary;

    public Employee(int id, String name, String departament, String joiningdate, double salary) {
        this.id = id;
        this.name = name;
        this.departament = departament;
        this.joiningdate = joiningdate;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartament() {
        return departament;
    }

    public String getJoiningdate() {
        return joiningdate;
    }

    public double getSalary() {
        return salary;
    }
}
