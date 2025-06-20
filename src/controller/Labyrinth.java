package controller;

import java.awt.Dimension;

import javax.swing.*;

import model.World;
import view.ConsoleView;
import view.GraphicView;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 * This is our main program. It is responsible for creating all of the objects
 * that are part of the MVC pattern and connecting them with each other.
 */
public class Labyrinth {


    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                int width = 28;
                int height = 28;
                World world = new World(width, height);

                Dimension fieldDimensions = new Dimension(25, 25);
                GraphicView gview = new GraphicView(
                        width * fieldDimensions.width,
                        height * fieldDimensions.height,
                        fieldDimensions);
                world.registerView(gview);
                gview.setFocusable(true);
                gview.requestFocusInWindow();

                ConsoleView cview = new ConsoleView();
                world.registerView(cview);

                Controller controller = new Controller(world);
                controller.setTitle("Labyrinth Game");
                controller.setResizable(false);
                controller.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                controller.setFocusable(true);
                controller.requestFocusInWindow();

                //creates a button to start a new game in current difficulty
                JButton regenerateButton = new JButton("New Game!");
                regenerateButton.addActionListener(e -> {
                    controller.resetGame();
                });

                //creates a button to start a new game in difficulty 1
                JButton diff_1 = new JButton("Difficulty 1");
                diff_1.addActionListener(e -> {
                    controller.setDifficulty(1);
                });

                //creates a button to start a new game in difficulty 2
                JButton diff_2 = new JButton("Difficulty 2");
                diff_2.addActionListener(e -> {
                    controller.setDifficulty(2);
                });

                //creates a button to start a new game in difficulty 3
                JButton diff_3 = new JButton("Difficulty 3");
                diff_3.addActionListener(e -> {
                    controller.setDifficulty(3);
                });

                JPanel mainPanel = new JPanel(new BorderLayout());
                mainPanel.add(gview, BorderLayout.CENTER);

                JPanel buttonPanel = new JPanel();
                buttonPanel.add(regenerateButton);
                buttonPanel.add(diff_1);
                buttonPanel.add(diff_2);
                buttonPanel.add(diff_3);
                mainPanel.add(buttonPanel, BorderLayout.SOUTH);

                controller.getContentPane().add(mainPanel);

                controller.pack();
                controller.setMinimumSize(controller.getSize());
                controller.setLocationRelativeTo(null);
                controller.setVisible(true);
            }


        });;
    }
}
