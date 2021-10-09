import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class DialogExample {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Frame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationByPlatform(true);

            JButton button = new JButton("Click me for dialog");
            button.addActionListener(e -> {
                Component customView = new CustomPanel();

                Object[] options = { "Yes, please", "No, thanks", "No eggs, no ham!" };
                int n = JOptionPane.showOptionDialog(frame, customView, "A Silly Question",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        options, options[2]);
                if (n == 0) {
                    JOptionPane.showMessageDialog(frame, "Thanks for selecting yes.");
                }

            });

            frame.setLayout(new FlowLayout());
            frame.add(button);
            frame.pack();
            frame.setVisible(true);
        });

    }

    static class CustomPanel extends JPanel {
        public CustomPanel() {
            super(new BorderLayout());

            add(new JLabel("CustomPanel"));
        }
    }
}