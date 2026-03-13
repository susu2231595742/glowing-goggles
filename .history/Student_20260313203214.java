/**
 * 学生类
 * 用于存储学生的基本信息：学号、姓名、年龄、成绩
 */
public class Student {
    // 学号
    private String id;
    // 姓名
    private String name;
    // 年龄
    private int age;
    // 成绩
    private double score;

    /**
     * 无参构造方法
     */
    public Student() {
    }

    /**
     * 带参数的构造方法
     * @param id 学号
     * @param name 姓名
     * @param age 年龄
     * @param score 成绩
     */
    public Student(String id, String name, int age, double score) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.score = score;
    }

    // Getter 和 Setter 方法

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    /**
     * 重写 toString 方法，方便输出学生信息
     * @return 学生信息的字符串表示
     */
    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }
}
