package com.example.demo.util;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyboardUtil {

    private static final Logger LOGGER = Logger.getLogger(KeyboardUtil.class.getName());

    public static void pressEnterButton() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        LOGGER.log(Level.INFO, "CALLED: pressEnterButton()");
    }

    public static void pressEscButton() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ESCAPE);
        LOGGER.log(Level.INFO, "CALLED: pressEscButton()");
    }

    public static void pressDeleteButton() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_DELETE);
        LOGGER.log(Level.INFO, "CALLED: pressDeleteButton()");
    }

    public static void pressBackspaceButton() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_BACK_SPACE);
        LOGGER.log(Level.INFO, "CALLED: pressBackspaceButton()");
    }

    public static void pressF11Button() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_F11);
        LOGGER.log(Level.INFO, "CALLED: pressF11Button()");
    }

    public static void pressCtrlButton() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        LOGGER.log(Level.INFO, "CALLED: pressCtrlButton()");
    }

}