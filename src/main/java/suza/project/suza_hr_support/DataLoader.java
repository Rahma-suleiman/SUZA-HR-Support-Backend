package suza.project.suza_hr_support;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import suza.project.suza_hr_support.entity.Department;
import suza.project.suza_hr_support.repository.DeptRepository;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final DeptRepository deptRepository;

    public DataLoader(DeptRepository deptRepository) {
        this.deptRepository = deptRepository;
    }

    @Override
    public void run(String... args) {
        if (deptRepository.count() == 0) {
            List<Department> departments = List.of(
                createDept("HR", "Human Resources Department"),
                createDept("IT", "Information Technology Department"),
                createDept("Accountancy", "Accountancy & Finance Department"),
                createDept("Administration", "Administration Department"),
                createDept("Operations", "Operations Department")
            );
            deptRepository.saveAll(departments);
            System.out.println("Seeded " + departments.size() + " departments.");
        }
    }

    private Department createDept(String name, String description) {
        Department d = new Department();
        d.setName(name);
        d.setDescription(description);
        return d;
    }
}
