import java.awt.Flowlayout;
import javax.swing.JFrame;

public class Caller
{
public static void main(string[] args)
{
X obj= new X;  
}
}
class X extends JFrame
{
public X()
{
JButton m= new JButton("Matrix Calculator");
m.addActionListener(){
public void actionperformed(ActionEvent e)
{
new Matrix();
 }
});
add(m);
setlayout(new FlowLayout());
setVisible(true);
setSize(300,300);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }