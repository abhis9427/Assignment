import java.util.List;

public class Main {
    public static void main(String[] args) {
        Solution employeeGenerator = new Solution();
        List<Employee> employees = employeeGenerator.generateEmployees(100);
        for (Employee employee : employees) {
            System.out.println("ID: " + employee.id + ", Name: " + employee.name + ", ManagerID: " + employee.managerId);
        }

    }
}