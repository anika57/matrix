import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import javax.swing.JFrame;

public class Caller
{
public static void main(String[] args)
{
X obj=new X();
}
}
class X extends JFrame
{
public X()
{
JButton b=new JButton("matrix");
b.addActionListener(new ActionListener()
{
public void ActionPerformed(ActionEvent e){
new MatrixCalculator();
}
});

}
}