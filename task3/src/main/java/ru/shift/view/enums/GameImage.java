package ru.shift.view.enums;

import javax.swing.*;

public enum GameImage {
    CLOSED("gameImages/cell.png"),
    MARKED("gameImages/flag.png"),
    EMPTY("gameImages/empty.png"),
    NUM_1("gameImages/1.png"),
    NUM_2("gameImages/2.png"),
    NUM_3("gameImages/3.png"),
    NUM_4("gameImages/4.png"),
    NUM_5("gameImages/5.png"),
    NUM_6("gameImages/6.png"),
    NUM_7("gameImages/7.png"),
    NUM_8("gameImages/8.png"),
    BOMB("gameImages/mine.png"),
    TIMER("gameImages/timer.png"),
    BOMB_ICON("gameImages/mineImage.png"),
    ;

    private final String fileName;
    private ImageIcon imageIcon;

    GameImage(String fileName) {
        this.fileName = fileName;
    }

    public synchronized ImageIcon getImageIcon() {
        if (imageIcon == null) {
            imageIcon = new ImageIcon(ClassLoader.getSystemResource(fileName));
        }

        return imageIcon;
    }
}
