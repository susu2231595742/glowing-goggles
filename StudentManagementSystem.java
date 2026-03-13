import java.util.List;
import java.util.Scanner;

/**
 * 学生信息管理系统主程序
 * 提供控制台交互界面，实现学生信息的增删改查功能
 */
public class StudentManagementSystem {
    // 学生管理器
    private StudentManager manager;
    // 输入扫描器
    private Scanner scanner;

    /**
     * 构造方法，初始化系统
     */
    public StudentManagementSystem() {
        this.manager = new StudentManager();
        this.scanner = new Scanner(System.in);
    }

    /**
     * 显示主菜单
     */
    public void showMenu() {
        System.out.println("\n========== 学生信息管理系统 ==========");
        System.out.println("1. 添加学生");
        System.out.println("2. 删除学生");
        System.out.println("3. 修改学生信息");
        System.out.println("4. 查询所有学生");
        System.out.println("5. 根据姓名模糊查询");
        System.out.println("0. 退出系统");
        System.out.println("=====================================");
        System.out.print("请选择操作：");
    }

    /**
     * 运行系统
     */
    public void run() {
        boolean running = true;
        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    addStudent();
                    break;
                case "2":
                    deleteStudent();
                    break;
                case "3":
                    updateStudent();
                    break;
                case "4":
                    queryAllStudents();
                    break;
                case "5":
                    queryStudentsByName();
                    break;
                case "0":
                    running = false;
                    System.out.println("感谢使用，再见！");
                    break;
                default:
                    System.out.println("无效的选择，请重新输入！");
            }
        }
        scanner.close();
    }

    /**
     * 添加学生
     */
    private void addStudent() {
        System.out.println("\n----- 添加学生 -----");

        // 输入学号
        String id;
        while (true) {
            System.out.print("请输入学号：");
            id = scanner.nextLine().trim();
            if (id.isEmpty()) {
                System.out.println("学号不能为空，请重新输入！");
            } else if (manager.findStudentById(id) != null) {
                System.out.println("该学号已存在，请重新输入！");
            } else {
                break;
            }
        }

        // 输入姓名
        String name;
        while (true) {
            System.out.print("请输入姓名：");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("姓名不能为空，请重新输入！");
            } else {
                break;
            }
        }

        // 输入年龄
        int age = inputValidAge();

        // 输入成绩
        double score = inputValidScore();

        // 创建学生对象并添加
        Student student = new Student(id, name, age, score);
        if (manager.addStudent(student)) {
            System.out.println("学生添加成功！");
        } else {
            System.out.println("添加失败，学号已存在！");
        }
    }

    /**
     * 删除学生
     */
    private void deleteStudent() {
        System.out.println("\n----- 删除学生 -----");
        System.out.print("请输入要删除的学生学号：");
        String id = scanner.nextLine().trim();

        if (id.isEmpty()) {
            System.out.println("学号不能为空！");
            return;
        }

        Student student = manager.findStudentById(id);
        if (student == null) {
            System.out.println("未找到该学号的学生！");
            return;
        }

        System.out.print("确认删除学生 " + student.getName() + " 吗？(y/n)：");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (confirm.equals("y") || confirm.equals("yes")) {
            if (manager.deleteStudent(id)) {
                System.out.println("删除成功！");
            } else {
                System.out.println("删除失败！");
            }
        } else {
            System.out.println("已取消删除操作。");
        }
    }

    /**
     * 修改学生信息
     */
    private void updateStudent() {
        System.out.println("\n----- 修改学生信息 -----");
        System.out.print("请输入要修改的学生学号：");
        String id = scanner.nextLine().trim();

        if (id.isEmpty()) {
            System.out.println("学号不能为空！");
            return;
        }

        Student student = manager.findStudentById(id);
        if (student == null) {
            System.out.println("未找到该学号的学生！");
            return;
        }

        System.out.println("当前学生信息：" + student);
        System.out.println("请输入新的信息（直接回车保持不变）：");

        // 修改姓名
        String newName;
        while (true) {
            System.out.print("请输入新姓名[" + student.getName() + "]：");
            newName = scanner.nextLine().trim();
            if (newName.isEmpty()) {
                newName = student.getName();
                break;
            } else {
                break;
            }
        }

        // 修改年龄
        int newAge = inputValidAgeWithDefault(student.getAge());

        // 修改成绩
        double newScore = inputValidScoreWithDefault(student.getScore());

        if (manager.updateStudent(id, newName, newAge, newScore)) {
            System.out.println("修改成功！");
            System.out.println("更新后信息：" + manager.findStudentById(id));
        } else {
            System.out.println("修改失败！");
        }
    }

    /**
     * 查询所有学生
     */
    private void queryAllStudents() {
        System.out.println("\n----- 所有学生信息 -----");
        List<Student> students = manager.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("暂无学生信息！");
        } else {
            System.out.println("共有 " + students.size() + " 名学生：");
            System.out.println("----------------------------------------");
            System.out.printf("%-10s %-10s %-6s %-8s%n", "学号", "姓名", "年龄", "成绩");
            System.out.println("----------------------------------------");
            for (Student student : students) {
                System.out.printf("%-10s %-10s %-6d %-8.2f%n",
                        student.getId(),
                        student.getName(),
                        student.getAge(),
                        student.getScore());
            }
            System.out.println("----------------------------------------");
        }
    }

    /**
     * 根据姓名模糊查询
     */
    private void queryStudentsByName() {
        System.out.println("\n----- 根据姓名模糊查询 -----");
        System.out.print("请输入姓名关键字：");
        String keyword = scanner.nextLine().trim();

        if (keyword.isEmpty()) {
            System.out.println("关键字不能为空！");
            return;
        }

        List<Student> students = manager.findStudentsByName(keyword);
        if (students.isEmpty()) {
            System.out.println("未找到匹配的学生！");
        } else {
            System.out.println("找到 " + students.size() + " 名匹配的学生：");
            System.out.println("----------------------------------------");
            System.out.printf("%-10s %-10s %-6s %-8s%n", "学号", "姓名", "年龄", "成绩");
            System.out.println("----------------------------------------");
            for (Student student : students) {
                System.out.printf("%-10s %-10s %-6d %-8.2f%n",
                        student.getId(),
                        student.getName(),
                        student.getAge(),
                        student.getScore());
            }
            System.out.println("----------------------------------------");
        }
    }

    /**
     * 输入有效的年龄
     * @return 有效的年龄值
     */
    private int inputValidAge() {
        while (true) {
            System.out.print("请输入年龄：");
            String input = scanner.nextLine().trim();
            try {
                int age = Integer.parseInt(input);
                if (age <= 0 || age > 150) {
                    System.out.println("年龄必须在 1-150 之间，请重新输入！");
                } else {
                    return age;
                }
            } catch (NumberFormatException e) {
                System.out.println("请输入有效的数字！");
            }
        }
    }

    /**
     * 输入有效的年龄（带默认值）
     * @param defaultAge 默认年龄值
     * @return 有效的年龄值
     */
    private int inputValidAgeWithDefault(int defaultAge) {
        while (true) {
            System.out.print("请输入新年龄[" + defaultAge + "]：");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return defaultAge;
            }
            try {
                int age = Integer.parseInt(input);
                if (age <= 0 || age > 150) {
                    System.out.println("年龄必须在 1-150 之间，请重新输入！");
                } else {
                    return age;
                }
            } catch (NumberFormatException e) {
                System.out.println("请输入有效的数字！");
            }
        }
    }

    /**
     * 输入有效的成绩
     * @return 有效的成绩值
     */
    private double inputValidScore() {
        while (true) {
            System.out.print("请输入成绩（0-100）：");
            String input = scanner.nextLine().trim();
            try {
                double score = Double.parseDouble(input);
                if (score < 0 || score > 100) {
                    System.out.println("成绩必须在 0-100 之间，请重新输入！");
                } else {
                    return score;
                }
            } catch (NumberFormatException e) {
                System.out.println("请输入有效的数字！");
            }
        }
    }

    /**
     * 输入有效的成绩（带默认值）
     * @param defaultScore 默认成绩值
     * @return 有效的成绩值
     */
    private double inputValidScoreWithDefault(double defaultScore) {
        while (true) {
            System.out.print("请输入新成绩[" + defaultScore + "]：");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return defaultScore;
            }
            try {
                double score = Double.parseDouble(input);
                if (score < 0 || score > 100) {
                    System.out.println("成绩必须在 0-100 之间，请重新输入！");
                } else {
                    return score;
                }
            } catch (NumberFormatException e) {
                System.out.println("请输入有效的数字！");
            }
        }
    }

    /**
     * 主方法，程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        StudentManagementSystem system = new StudentManagementSystem();
        system.run();
    }
}
