import java.util.ArrayList;
import java.util.List;

public class Team {
    private String teamName;
    private List<Employee> employees;

    public Team(String teamName) {
        this.teamName = teamName;
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public String getTeamName() {
        return teamName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
