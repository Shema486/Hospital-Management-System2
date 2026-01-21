package hospital.hospital_management_system2.model;

public class Department {
    private Long deptId;
    private String deptName;
    private int locationFloor;

    public Department(int locationFloor, String deptName) {
        this.locationFloor = locationFloor;
        this.deptName = deptName;
    }

    public Department(Long deptId, int locationFloor, String deptName) {
        this.deptId = deptId;
        this.locationFloor = locationFloor;
        this.deptName = deptName;
    }

    public Department(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {return deptId;}
    public void setDeptId(Long deptId) {this.deptId = deptId;}
    public int getLocationFloor() {return locationFloor;}
    public void setLocationFloor(int locationFloor) {this.locationFloor = locationFloor;}
    public String getDeptName() {return deptName;}
    public void setDeptName(String deptName) {this.deptName = deptName;}

    @Override
    public String toString() {
        return "Department{" +
                "deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                ", locationFloor=" + locationFloor +
                '}';
    }
}
