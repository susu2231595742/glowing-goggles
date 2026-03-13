import java.util.ArrayList;
import java.util.List;

/**
 * 学生管理类
 * 负责管理学生信息的增删改查操作
 */
public class StudentManager {
    // 使用 ArrayList 存储学生信息
    private ArrayList<Student> students;

    /**
     * 构造方法，初始化学生列表
     */
    public StudentManager() {
        this.students = new ArrayList<>();
    }

    /**
     * 添加学生
     * @param student 要添加的学生对象
     * @return 添加成功返回 true，学号已存在返回 false
     */
    public boolean addStudent(Student student) {
        // 检查学号是否已存在
        if (findStudentById(student.getId()) != null) {
            return false;
        }
        students.add(student);
        return true;
    }

    /**
     * 根据学号删除学生
     * @param id 学号
     * @return 删除成功返回 true，学生不存在返回 false
     */
    public boolean deleteStudent(String id) {
        Student student = findStudentById(id);
        if (student != null) {
            students.remove(student);
            return true;
        }
        return false;
    }

    /**
     * 根据学号修改学生信息
     * @param id 学号
     * @param newName 新姓名
     * @param newAge 新年龄
     * @param newScore 新成绩
     * @return 修改成功返回 true，学生不存在返回 false
     */
    public boolean updateStudent(String id, String newName, int newAge, double newScore) {
        Student student = findStudentById(id);
        if (student != null) {
            student.setName(newName);
            student.setAge(newAge);
            student.setScore(newScore);
            return true;
        }
        return false;
    }

    /**
     * 根据学号查询学生
     * @param id 学号
     * @return 找到返回学生对象，未找到返回 null
     */
    public Student findStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    /**
     * 查询所有学生
     * @return 包含所有学生的列表
     */
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    /**
     * 根据姓名模糊查询学生
     * @param name 姓名关键字
     * @return 匹配的学生列表
     */
    public List<Student> findStudentsByName(String name) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getName().contains(name)) {
                result.add(student);
            }
        }
        return result;
    }

    /**
     * 获取学生数量
     * @return 学生总数
     */
    public int getStudentCount() {
        return students.size();
    }
}
