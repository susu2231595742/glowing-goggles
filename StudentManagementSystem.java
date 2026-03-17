import java.util.ArrayList;
import java.util.Scanner;

public class StudentManagementSystem {
    private ArrayList<Student> studentList;
    private Scanner scanner;

    public StudentManagementSystem() {
        studentList = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            showMenu();
            int choice = getIntInput("请选择操作: ");
            
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    deleteStudent();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    showAllStudents();
                    break;
                case 5:
                    searchStudentByName();
                    break;
                case 0:
                    System.out.println("感谢使用学生信息管理系统，再见！");
                    scanner.close();
                    return;
                default:
                    System.out.println("无效选择，请重新输入！");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n========== 学生信息管理系统 ==========");
        System.out.println("1. 添加学生");
        System.out.println("2. 删除学生（按学号）");
        System.out.println("3. 修改学生信息（按学号）");
        System.out.println("4. 查询所有学生");
        System.out.println("5. 根据姓名模糊查询学生");
        System.out.println("0. 退出系统");
        System.out.println("=====================================");
    }

    private void addStudent() {
        System.out.println("\n----- 添加学生 -----");
        
        String id = getStringInput("请输入学号: ");
        if (isIdExists(id)) {
            System.out.println("学号已存在，添加失败！");
            return;
        }

        String name = getNameInput("请输入姓名: ");
        int age = getAgeInput("请输入年龄: ");
        double score = getScoreInput("请输入成绩: ");

        Student student = new Student(id, name, age, score);
        studentList.add(student);
        System.out.println("学生添加成功！");
    }

    private void deleteStudent() {
        System.out.println("\n----- 删除学生 -----");
        String id = getStringInput("请输入要删除的学生学号: ");
        
        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("未找到该学号的学生！");
            return;
        }

        studentList.remove(student);
        System.out.println("学生删除成功！");
    }

    private void updateStudent() {
        System.out.println("\n----- 修改学生信息 -----");
        String id = getStringInput("请输入要修改的学生学号: ");
        
        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("未找到该学号的学生！");
            return;
        }

        System.out.println("找到学生: " + student);
        String name = getNameInput("请输入新姓名（直接回车保留原姓名）: ");
        if (!name.isEmpty()) {
            student.setName(name);
        }

        String ageStr = getStringInput("请输入新年龄（直接回车保留原年龄）: ");
        if (!ageStr.isEmpty()) {
            try {
                int age = Integer.parseInt(ageStr);
                if (age > 0) {
                    student.setAge(age);
                } else {
                    System.out.println("年龄必须大于0，保持原年龄不变。");
                }
            } catch (NumberFormatException e) {
                System.out.println("年龄格式无效，保持原年龄不变。");
            }
        }

        String scoreStr = getStringInput("请输入新成绩（直接回车保留原成绩）: ");
        if (!scoreStr.isEmpty()) {
            try {
                double score = Double.parseDouble(scoreStr);
                if (score >= 0 && score <= 100) {
                    student.setScore(score);
                } else {
                    System.out.println("成绩必须在0-100之间，保持原成绩不变。");
                }
            } catch (NumberFormatException e) {
                System.out.println("成绩格式无效，保持原成绩不变。");
            }
        }

        System.out.println("学生信息修改成功！");
    }

    private void showAllStudents() {
        System.out.println("\n----- 所有学生信息 -----");
        if (studentList.isEmpty()) {
            System.out.println("暂无学生信息！");
            return;
        }

        for (Student student : studentList) {
            System.out.println(student);
        }
    }

    private void searchStudentByName() {
        System.out.println("\n----- 模糊查询学生 -----");
        String keyword = getStringInput("请输入要查询的姓名关键字: ");
        
        if (keyword.isEmpty()) {
            System.out.println("查询关键字不能为空！");
            return;
        }
        
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : studentList) {
            if (student.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(student);
            }
        }

        if (result.isEmpty()) {
            System.out.println("未找到匹配的学生！");
        } else {
            System.out.println("查询结果:");
            for (Student student : result) {
                System.out.println(student);
            }
        }
    }

    private boolean isIdExists(String id) {
        for (Student student : studentList) {
            if (student.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private Student findStudentById(String id) {
        for (Student student : studentList) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("输入无效，请输入数字！");
            }
        }
    }

    private String getNameInput(String prompt) {
        while (true) {
            String name = getStringInput(prompt);
            if (!name.isEmpty()) {
                return name;
            }
            System.out.println("姓名不能为空，请重新输入！");
        }
    }

    private int getAgeInput(String prompt) {
        while (true) {
            int age = getIntInput(prompt);
            if (age > 0) {
                return age;
            }
            System.out.println("年龄必须大于0，请重新输入！");
        }
    }

    private double getScoreInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double score = Double.parseDouble(scanner.nextLine().trim());
                if (score >= 0 && score <= 100) {
                    return score;
                }
                System.out.println("成绩必须在0-100之间，请重新输入！");
            } catch (NumberFormatException e) {
                System.out.println("输入无效，请输入数字！");
            }
        }
    }

    public static void main(String[] args) {
        new StudentManagementSystem().start();
    }
}
