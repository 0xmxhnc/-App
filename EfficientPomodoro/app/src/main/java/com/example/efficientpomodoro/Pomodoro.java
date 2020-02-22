package com.example.efficientpomodoro;

//创建Pomodoro类用于设置具体资源
public class Pomodoro {

    //番茄钟名称
    private String name;
    //背景Id
    private int imageId;

    public Pomodoro(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
