package com.gdgcatania.info.studyjamattendance.object;

/**
 * Created by Andrea on 05/02/2015.
 */
public class Lesson {

    private int userId;
    private int lesson1;
    private int lesson2;
    private int lesson3;
    private int lesson4;
    private int lesson5;
    private int lesson6;
    private int lesson7;
    private int lesson8;

    public Lesson(int userId, int lesson1, int lesson2, int lesson3, int lesson4, int lesson5, int lesson6, int lesson7, int lesson8) {
        this.userId = userId;
        this.lesson1 = lesson1;
        this.lesson2 = lesson2;
        this.lesson3 = lesson3;
        this.lesson4 = lesson4;
        this.lesson5 = lesson5;
        this.lesson6 = lesson6;
        this.lesson7 = lesson7;
        this.lesson8 = lesson8;
    }

    public Lesson(int lesson1, int lesson2, int lesson3, int lesson4, int lesson5, int lesson6, int lesson7, int lesson8) {
        this.lesson1 = lesson1;
        this.lesson2 = lesson2;
        this.lesson3 = lesson3;
        this.lesson4 = lesson4;
        this.lesson5 = lesson5;
        this.lesson6 = lesson6;
        this.lesson7 = lesson7;
        this.lesson8 = lesson8;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLesson1() {
        return lesson1;
    }

    public void setLesson1(int lesson1) {
        this.lesson1 = lesson1;
    }

    public int getLesson2() {
        return lesson2;
    }

    public void setLesson2(int lesson2) {
        this.lesson2 = lesson2;
    }

    public int getLesson3() {
        return lesson3;
    }

    public void setLesson3(int lesson3) {
        this.lesson3 = lesson3;
    }

    public int getLesson4() {
        return lesson4;
    }

    public void setLesson4(int lesson4) {
        this.lesson4 = lesson4;
    }

    public int getLesson5() {
        return lesson5;
    }

    public void setLesson5(int lesson5) {
        this.lesson5 = lesson5;
    }

    public int getLesson6() {
        return lesson6;
    }

    public void setLesson6(int lesson6) {
        this.lesson6 = lesson6;
    }

    public int getLesson7() {
        return lesson7;
    }

    public void setLesson7(int lesson7) {
        this.lesson7 = lesson7;
    }

    public int getLesson8() {
        return lesson8;
    }

    public void setLesson8(int lesson8) {
        this.lesson8 = lesson8;
    }
}
