package com.self.designmode.discipline.demeter;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 没有使用迪米特法则的代码示例
 * @author PJ_ZHANG
 * @create 2020-07-17 17:12
 **/
public class NotDemeterCode {

    public static void main(String[] args) {
        TeacherManager teacherManager = new TeacherManager();
        teacherManager.showDetails(new StudentManager());
    }

    @Data
    static class Student {
        private String name;
        public Student(String name) {this.name = name;}
    }

    @Data
    static class Teacher {
        private String name;
        public Teacher(String name) {this.name = name;}
    }

    static class StudentManager {
        public List<Student> allStudent() {
            List<Student> lstData = new ArrayList<>(10);
            for (int i = 0; i < 3; i++) {
                lstData.add(new Student("张三" + i));
            }
            return lstData;
        }
    }
    static class TeacherManager {
        public List<Teacher> allTeacher() {
            List<Teacher> lstData = new ArrayList<>(10);
            for (int i = 0; i < 3; i++) {
                lstData.add(new Teacher("李四" + i));
            }
            return lstData;
        }

        public void showDetails(StudentManager studentManager) {
            List<Student> lstStudent = studentManager.allStudent();
            System.out.println(JSON.toJSON(lstStudent));
            System.out.println(JSON.toJSON(this.allTeacher()));
        }
    }
}
