import java.util.ArrayList;
import java.util.Scanner;

public class StudentManagementSystem {
    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("========== 学生信息管理系统 ==========");
        
        while (true) {
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
                    queryByName();
                    break;
                case "0":
                    System.out.println("感谢使用，再见！");
                    scanner.close();
                    return;
                default:
                    System.out.println("输入无效，请重新选择！");
            }
            System.out.println();
        }
    }

    private static void showMenu() {
        System.out.println("\n---------- 功能菜单 ----------");
        System.out.println("1. 添加学生");
        System.out.println("2. 删除学生（按学号）");
        System.out.println("3. 修改学生信息（按学号）");
        System.out.println("4. 查询所有学生");
        System.out.println("5. 根据姓名模糊查询");
        System.out.println("0. 退出系统");
        System.out.print("请选择操作：");
    }

    private static void addStudent() {
        System.out.println("\n--- 添加学生 ---");
        
        String id = inputId();
        if (id == null) return;
        
        if (findStudentById(id) != null) {
            System.out.println("该学号已存在，添加失败！");
            return;
        }
        
        String name = inputName();
        if (name == null) return;
        
        Integer age = inputAge();
        if (age == null) return;
        
        Double score = inputScore();
        if (score == null) return;
        
        Student student = new Student(id, name, age, score);
        students.add(student);
        System.out.println("学生添加成功！");
    }

    private static void deleteStudent() {
        System.out.println("\n--- 删除学生 ---");
        
        System.out.print("请输入要删除的学生学号：");
        String id = scanner.nextLine().trim();
        
        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("未找到该学号的学生！");
            return;
        }
        
        System.out.println("找到学生：" + student);
        System.out.print("确认删除？(Y/N)：");
        String confirm = scanner.nextLine().trim().toUpperCase();
        
        if (confirm.equals("Y")) {
            students.remove(student);
            System.out.println("删除成功！");
        } else {
            System.out.println("已取消删除。");
        }
    }

    private static void updateStudent() {
        System.out.println("\n--- 修改学生信息 ---");
        
        System.out.print("请输入要修改的学生学号：");
        String id = scanner.nextLine().trim();
        
        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("未找到该学号的学生！");
            return;
        }
        
        System.out.println("当前信息：" + student);
        System.out.println("请输入新的信息（直接回车保持原值）：");
        
        String newName = inputNameForUpdate();
        if (newName != null && !newName.isEmpty()) {
            student.setName(newName);
        }
        
        Integer newAge = inputAgeForUpdate();
        if (newAge != null) {
            student.setAge(newAge);
        }
        
        Double newScore = inputScoreForUpdate();
        if (newScore != null) {
            student.setScore(newScore);
        }
        
        System.out.println("修改成功！" + student);
    }

    private static void queryAllStudents() {
        System.out.println("\n--- 所有学生信息 ---");
        
        if (students.isEmpty()) {
            System.out.println("暂无学生信息！");
            return;
        }
        
        System.out.println("共 " + students.size() + " 名学生：");
        System.out.println("----------------------------------------");
        for (Student s : students) {
            System.out.println(s);
        }
        System.out.println("----------------------------------------");
    }

    private static void queryByName() {
        System.out.println("\n--- 姓名模糊查询 ---");
        
        System.out.print("请输入查询关键字：");
        String keyword = scanner.nextLine().trim();
        
        if (keyword.isEmpty()) {
            System.out.println("查询关键字不能为空！");
            return;
        }
        
        ArrayList<Student> results = new ArrayList<>();
        for (Student s : students) {
            if (s.getName().contains(keyword)) {
                results.add(s);
            }
        }
        
        if (results.isEmpty()) {
            System.out.println("未找到匹配的学生！");
        } else {
            System.out.println("找到 " + results.size() + " 名匹配的学生：");
            System.out.println("----------------------------------------");
            for (Student s : results) {
                System.out.println(s);
            }
            System.out.println("----------------------------------------");
        }
    }

    private static Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    private static String inputId() {
        System.out.print("请输入学号：");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("学号不能为空！");
            return null;
        }
        return id;
    }

    private static String inputName() {
        System.out.print("请输入姓名：");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("姓名不能为空！");
            return null;
        }
        return name;
    }

    private static String inputNameForUpdate() {
        System.out.print("请输入新姓名：");
        return scanner.nextLine().trim();
    }

    private static Integer inputAge() {
        System.out.print("请输入年龄：");
        String ageStr = scanner.nextLine().trim();
        
        try {
            int age = Integer.parseInt(ageStr);
            if (age < 0 || age > 150) {
                System.out.println("年龄必须在0-150之间！");
                return null;
            }
            return age;
        } catch (NumberFormatException e) {
            System.out.println("年龄必须是有效的整数！");
            return null;
        }
    }

    private static Integer inputAgeForUpdate() {
        System.out.print("请输入新年龄：");
        String ageStr = scanner.nextLine().trim();
        
        if (ageStr.isEmpty()) {
            return null;
        }
        
        try {
            int age = Integer.parseInt(ageStr);
            if (age < 0 || age > 150) {
                System.out.println("年龄必须在0-150之间！");
                return null;
            }
            return age;
        } catch (NumberFormatException e) {
            System.out.println("年龄必须是有效的整数！");
            return null;
        }
    }

    private static Double inputScore() {
        System.out.print("请输入成绩：");
        String scoreStr = scanner.nextLine().trim();
        
        try {
            double score = Double.parseDouble(scoreStr);
            if (score < 0 || score > 100) {
                System.out.println("成绩必须在0-100之间！");
                return null;
            }
            return score;
        } catch (NumberFormatException e) {
            System.out.println("成绩必须是有效的数字！");
            return null;
        }
    }

    private static Double inputScoreForUpdate() {
        System.out.print("请输入新成绩：");
        String scoreStr = scanner.nextLine().trim();
        
        if (scoreStr.isEmpty()) {
            return null;
        }
        
        try {
            double score = Double.parseDouble(scoreStr);
            if (score < 0 || score > 100) {
                System.out.println("成绩必须在0-100之间！");
                return null;
            }
            return score;
        } catch (NumberFormatException e) {
            System.out.println("成绩必须是有效的数字！");
            return null;
        }
    }
}