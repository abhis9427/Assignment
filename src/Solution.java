import java.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Solution {

    static Random random = new Random();


    public static List<Employee> generateEmployees(int numberOfEmployees) {
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee(1, "CEO", -1));
        Map<Integer, Set<Integer>> managerEmployeeMap = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            int employeeId = 2 + i;
            String name = "Employee" + employeeId;
            employees.add(new Employee(employeeId, name, 1));
            managerEmployeeMap.computeIfAbsent(1, k -> new HashSet<>()).add(employeeId);
        }

        for (int id = 7; id <= numberOfEmployees; id++) {
            String name = "Employee" + id;
            int managerId = assignManager(employees, id, managerEmployeeMap, numberOfEmployees);
            employees.add(new Employee(id, name, managerId));
        }

        return employees;
    }

    private static int assignManager(List<Employee> employees, int employeeId, Map<Integer, Set<Integer>> managerEmployeeMap,int numberOfEmployees) {
        while (true) {
            int potentialManagerId = (employeeId + random.nextInt(numberOfEmployees - 1)) % numberOfEmployees + 1;
            if (isValidManagerAssignment(employees, employeeId, potentialManagerId, managerEmployeeMap)) {
                return potentialManagerId;
            }
        }
    }

    private static boolean isValidManagerAssignment(List<Employee> employees, int employeeId, int potentialManagerId, Map<Integer, Set<Integer>> managerEmployeeMap) {
        return potentialManagerId != employeeId &&
                !hasReportingCycle(employees, employeeId, potentialManagerId) &&
                managerEmployeeMap.getOrDefault(potentialManagerId, new HashSet<>()).size() < 5;
    }

    private static boolean hasReportingCycle(List<Employee> employees, int sourceId, int targetId) {
        return hasReportingCycleUtil(employees, sourceId, targetId, new boolean[employees.size() + 2]);
    }

    private static boolean hasReportingCycleUtil(List<Employee> employees, int currentId, int targetId, boolean[] visited) {
        if (currentId == targetId) {
            return true;
        }
        if (visited[currentId]) {
            return false;
        }
        visited[currentId] = true;
        for (Employee employee : employees) {
            if (employee.id == currentId) {
                if (hasReportingCycleUtil(employees, employee.managerId, targetId, visited)) {
                    return true;
                }
            }
        }
        return false;
    }
}
