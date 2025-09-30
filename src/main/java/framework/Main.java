package framework;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

//        MyMaths.simulateDmg();
        Loader.load();
        JFrame window = new JFrame();
        window.setBounds(0, 0, 0, 0);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setUndecorated(true);

        Engine engine = new Engine(window);

        window.add(engine);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        engine.start();
    }
}
