import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Map<String,Employee> employees = loadEmployeesFromCSV("employee-data.csv");
        List<String[]> organizationData = loadCSV("organisation-data.csv");

        Map<String, Team> teams = new HashMap<>();

        for (String[] row : organizationData) {
            String teamName = row[0];
            String role = row[1];
            String employeeId = row[2];

            Team team = teams.computeIfAbsent(teamName, Team::new);

            Employee employee = employees.get(employeeId);
            if (employee == null) {
                System.out.println("Employee not found for ID: " + employeeId);
                continue;
            }

            switch (role) {
                case "ProductOwner"-> team.addEmployee(new ProductOwner(employeeId,employee.getName(),employee.isContractorEmployee()));
                case "LeadDeveloper"-> team.addEmployee(new LeadDeveloper(employeeId,employee.getName(), employee.isContractorEmployee()));
                case "Developer"-> team.addEmployee(new Developer(employeeId,employee.getName(), employee.isContractorEmployee()));
                case "Tester"-> team.addEmployee(new Tester(employeeId,employee.getName(), employee.isContractorEmployee()));
            }
        }

        System.out.println("Team Member Report:");
        for (Team team : teams.values()) {
            System.out.println("Team: " + team.getTeamName());
            Set<String> productOwners = team.getEmployees().stream()
                    .filter(e -> e instanceof ProductOwner)
                    .map(Employee::getName)
                    .sorted()
                    .collect(Collectors.toCollection(TreeSet::new));

            Set<String> leadDevelopers = team.getEmployees().stream()
                    .filter(e -> e instanceof LeadDeveloper)
                    .map(Employee::getName)
                    .sorted()
                    .collect(Collectors.toCollection(TreeSet::new));

            Set<String> testers = team.getEmployees().stream()
                    .filter(e -> e instanceof Tester)
                    .map(Employee::getName)
                    .sorted()
                    .collect(Collectors.toCollection(TreeSet::new));

            Set<String> developers = team.getEmployees().stream()
                    .filter(e -> e instanceof Developer)
                    .map(Employee::getName)
                    .sorted()
                    .collect(Collectors.toCollection(TreeSet::new));

            System.out.println("Product Owners:");
            productOwners.forEach(name -> System.out.println("- " + name));

            System.out.println("Lead Developers:");
            leadDevelopers.forEach(name -> System.out.println("- " + name));

            System.out.println("Testers:");
            testers.forEach(name -> System.out.println("- " + name));

            System.out.println("Developers:");
            developers.forEach(name -> System.out.println("- " + name));
            System.out.println();
        }
    }

    private static List<String[]> loadCSV(String filename) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }
        } catch (IOException e) {
            System.out.println("Error loading CSV file: " + e.getMessage());
        }
        return data;
    }

    private static Map<String, Employee> loadEmployeesFromCSV(String filename) {
        Map<String, Employee> employees = new HashMap<>();
        List<String[]> csvData = loadCSV(filename);
        for (String[] row : csvData) {
            String employeeId = row[0];
            String name = row[1];
            boolean isContractor = row[2].equalsIgnoreCase("yes");
            Employee employee = new Employee(employeeId, name, isContractor);
            employees.put(employeeId, employee);
        }
        return employees;
    }
}
