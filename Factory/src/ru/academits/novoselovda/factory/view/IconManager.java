package ru.academits.novoselovda.factory.view;

import javax.swing.*;
import java.awt.*;

class IconManager {
    private static final String RESOURCES_PATH = ".\\Factory\\src\\ru\\academits\\novoselovda\\factory\\resourсes\\";
    private Image icon;
    private static final String A_ICON_PATH = "body.png";
    private ImageIcon aIcon;
    private static final String B_ICON_PATH = "engine.png";
    private ImageIcon bIcon;
    private static final String C_ICON_PATH = "accessories.png";
    private ImageIcon cIcon;
    private static final String CAR_ICON_PATH = "car.png";
    private ImageIcon carIcon;
    private static final String CONTROLLER_ICON_PATH = "controller.png";
    private ImageIcon controllerIcon;
    private static final String DEALERS_ICON_PATH = "dealers.png";
    private ImageIcon dealersIcon;
    private static final String PRODUCERS_ICON_PATH = "producers.png";
    private ImageIcon producersIcon;
    private static final String TARGET_ICON_PATH = "target.png";
    private ImageIcon targetIcon;

    private int size;

    IconManager(int size) {
        this.size = size;
        icon = new ImageIcon(RESOURCES_PATH + PRODUCERS_ICON_PATH).getImage();
        aIcon = imageToIcon(A_ICON_PATH);
        bIcon = imageToIcon(B_ICON_PATH);
        cIcon = imageToIcon(C_ICON_PATH);
        carIcon = imageToIcon(CAR_ICON_PATH);
        controllerIcon = imageToIcon(CONTROLLER_ICON_PATH);
        dealersIcon = imageToIcon(DEALERS_ICON_PATH);
        producersIcon = imageToIcon(PRODUCERS_ICON_PATH);
        targetIcon = imageToIcon(TARGET_ICON_PATH);
    }

    private ImageIcon imageToIcon(String iconPath) {
        Image image = new ImageIcon(RESOURCES_PATH + iconPath).getImage();
        Image newImage = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
    }


    Image getIcon() {
        return icon;
    }

    ImageIcon getAIcon() {
        return aIcon;
    }

    ImageIcon getBIcon() {
        return bIcon;
    }

    ImageIcon getCIcon() {
        return cIcon;
    }

    ImageIcon getCarIcon() {
        return carIcon;
    }

    ImageIcon getControllerIcon() {
        return controllerIcon;
    }

    ImageIcon getDealersIcon() {
        return dealersIcon;
    }

    ImageIcon getProducersIcon() {
        return producersIcon;
    }

    ImageIcon getTargetIcon() {
        return targetIcon;
    }
}
