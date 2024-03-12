import javax.swing.*;
import java.awt.*;

public class Test2 implements Runnable{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Test2());
    }

    @Override
    public void run() {
        JFrame frame = new JFrame();
        frame.setTitle("test 2");
        frame.setSize(500, 300);
        frame.setLayout(new BorderLayout());

        JPanel testPanel = new JPanel();
        testPanel.setBackground(Color.darkGray);
        JLabel label = new JLabel("label");
        JLabel label1 = new JLabel("label1");
        JLabel label2 = new JLabel("label2", SwingConstants.CENTER);
        //timerLabel = new JLabel("Time Remaining 300 seconds", SwingConstants.CENTER);
        JLabel label3 = new JLabel("label3");

        frame.add(testPanel, BorderLayout.CENTER);
        frame.add(label, BorderLayout.SOUTH);
        frame.add(label1, BorderLayout.EAST);
        frame.add(label2, BorderLayout.NORTH);
        frame.add(label3, BorderLayout.WEST);

        frame.setVisible(true);

    }
}
