package hospital.hospital_management_system2.service;

import hospital.hospital_management_system2.dao.DepartmentDao;
import hospital.hospital_management_system2.model.Department;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentService {
    private DepartmentDao departmentDao = new DepartmentDao();
    private Map<Long, Department> departmentCache = new HashMap<>();

    public void addDepartment(Department department) {
        departmentDao.addDepartment(department);
        //if (department.getDeptId() != null) {<-------------
            departmentCache.put(department.getDeptId(), department);
    }

    public void deleteDepartment(  Long id){
        departmentDao.deleteDepartment(id);
        departmentCache.remove(id);
        clearCache();
    }

    public List<Department> getAllDepartment(){
        List<Department> departments = departmentDao.getAllDepartment();
        for(Department dept : departments){
            departmentCache.put(dept.getDeptId(),dept);
        }
        return departments;
    }

    public void updateDepartment(Department department){
        departmentDao.updateDepartment(department);
        departmentCache.put(department.getDeptId(),department);
    }


    public void clearCache(){
        departmentCache.clear();
    }
}
