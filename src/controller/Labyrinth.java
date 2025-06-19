package controller;

import java.awt.Dimension;

import javax.swing.JFrame;

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
                int width = 20;
                int height = 20;
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
                controller.setTitle("Square Move Practice");
                controller.setResizable(false);
                controller.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                controller.setFocusable(true);
                controller.requestFocusInWindow();


                JButton regenerateButton = new JButton("Neue Runde");
                regenerateButton.addActionListener(e -> {
                    world.regenerate();
                    gview.repaint();
                    controller.resetGame();
                });

                JPanel mainPanel = new JPanel(new BorderLayout());
                mainPanel.add(gview, BorderLayout.CENTER);

                JPanel buttonPanel = new JPanel();
                buttonPanel.add(regenerateButton);
                mainPanel.add(buttonPanel, BorderLayout.SOUTH);

                controller.getContentPane().add(mainPanel);

                controller.pack();
                controller.setMinimumSize(controller.getSize());
                controller.setLocationRelativeTo(null);
                controller.setVisible(true);

                world.createWalls();
                world.createEnemies(controller.getDifficulty());
            }


        });;
    }
}
