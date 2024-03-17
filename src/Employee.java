public class Employee implements Contractor {
    private String employeeId;
    private String name;
    private boolean isContractor;

    public Employee(String employeeId, String name, boolean isContractor) {
        this.employeeId = employeeId;
        this.name = name;
        this.isContractor = isContractor;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public boolean isContractor() {
        return isContractor;
    }

    @Override
    public boolean isContractorEmployee() {
        return isContractor;
    }
}
