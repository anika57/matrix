import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public  class trial{
public static void main(String[] a){
JFrame f=new JFrame("3 in 1 Calculator");
f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
f.add(new JTabbedPaneDemo());
f.setSize(900,900);
f.setVisible(true);
}
}

class JTabbedPaneDemo extends JPanel{
 JTabbedPaneDemo(){
   makeGUI();
  }
  void makeGUI(){
  JTabbedPane jtp= new JTabbedPane();
  jtp.setBounds(200,200,200,200); 
  jtp.addTab("Matrix",new MatrixPanel());
  jtp.addTab("Scientific",new ScientificPanel());
  add(jtp);

 }
}

class MatrixPanel extends JPanel{
public static class Matrix {
private boolean DEBUG = true;
private boolean INFO = true;
private static int max = 100;
private static int decimals = 3;
private JLabel statusBar;
private JTextArea taA, taB, taC;
private int iDF = 0;
private int n = 4;
private static NumberFormat nf;
public Component createComponents() {

//MATRICES

taA = new JTextArea();
taB = new JTextArea();
taC = new JTextArea();
JPanel paneMs = new JPanel();
paneMs.setLayout(new BoxLayout(paneMs, BoxLayout.X_AXIS));
paneMs.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
paneMs.add(MatrixPane("Matrix A", taA));
paneMs.add(Box.createRigidArea(new Dimension(10, 0)));
paneMs.add(MatrixPane("Matrix B", taB));
paneMs.add(Box.createRigidArea(new Dimension(10, 0)));
paneMs.add(MatrixPane("Matrix C", taC));

//operations buttons

JPanel paneBtn = new JPanel();
paneBtn.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
paneBtn.setLayout(new GridLayout(3, 3));
JButton btnApB = new JButton("A + B = C");
JButton btnAmB = new JButton("A * B = C");
JButton btnBmA = new JButton("B * A = C");
JButton btnAdjA = new JButton("adjoint(A) = C");
JButton btnInvA = new JButton("inverse(A) = C");
JButton btnInvB = new JButton("inverse(B) = C");
JButton btnTrnsA = new JButton("transpose(A) = C");
JButton btnDetA = new JButton("determ(A) = C");
JButton btnDetB = new JButton("determ(B) = C");
paneBtn.add(btnApB);
paneBtn.add(btnAmB);
paneBtn.add(btnBmA);
paneBtn.add(btnAdjA);
paneBtn.add(btnInvA);
paneBtn.add(btnInvB);
paneBtn.add(btnTrnsA);
paneBtn.add(btnDetA);
paneBtn.add(btnDetB);
btnApB.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent evt) {
try {
DisplayMatrix(AddMatrix(ReadInMatrix(taA),ReadInMatrix(taB)), taC);
} catch (Exception e) {
System.err.println("Error: " + e);
}
}
});
btnAmB.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent evt) {
try {
DisplayMatrix(MultiplyMatrix(
ReadInMatrixNotSquare(taA),
ReadInMatrixNotSquare(taB)), taC);
} catch (Exception e) {
System.err.println("Error: " + e);
}
}
});
btnBmA.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent evt) {
try {
DisplayMatrix(MultiplyMatrix(ReadInMatrixNotSquare(taB),
ReadInMatrixNotSquare(taA)), taC);
} catch (Exception e) {
System.err.println("Error: " + e);
}
}
});
btnInvA.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent evt) {
try {
DisplayMatrix(Inverse(ReadInMatrix(taA)), taC);
} catch (Exception e) {
System.err.println("Error: " + e);
}
}
});
btnInvB.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent evt) {
try {
DisplayMatrix(Inverse(ReadInMatrix(taB)), taC);
} catch (Exception e) {
System.err.println("Error: " + e);
}
}
});
btnAdjA.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent evt) {
try {
DisplayMatrix(Adjoint(ReadInMatrix(taA)), taC);
} catch (Exception e) {
System.err.println("Error: " + e);
}
}
});
btnTrnsA.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent evt) {
try {
DisplayMatrix(Transpose(ReadInMatrixNotSquare(taA)), taC);
} catch (Exception e) {
System.err.println("Error: " + e);
}
}
});
btnDetA.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent evt) {
try {
taC.setText("Determinant A: "+ nf.format(Determinant(ReadInMatrix(taA))));
} catch (Exception e) {
System.err.println("Error: " + e);
}
}
});
btnDetB.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent evt) {
try {
taC.setText("Determinant B: "+ nf.format(Determinant(ReadInMatrix(taB))));
} catch (Exception e) {
System.err.println("Error: " + e);
}
}
});
JPanel pane = new JPanel();
pane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
pane.add(paneMs);
pane.add(paneBtn);
JPanel fpane = new JPanel();
fpane.setLayout(new BorderLayout());
fpane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
fpane.add("Center", pane);
statusBar = new JLabel("Ready");
fpane.add("South", statusBar);
return fpane;
}
private JPanel MatrixPane(String str, JTextArea ta) {
JScrollPane scrollPane = new JScrollPane(ta);
int size = 200;
scrollPane.setPreferredSize(new Dimension(size, size));
JLabel label = new JLabel(str);
label.setLabelFor(scrollPane);
JPanel pane = new JPanel();
pane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
pane.add(label);
pane.add(scrollPane);
return pane;
}

public static void main(String[] args) {
JFrame frame = new JFrame("Matrix Calculator");
frame.setSize(new Dimension(725, 200));
Matrix app = new Matrix();
Component contents = app.createComponents();
frame.getContentPane().add(contents, BorderLayout.CENTER);
frame.addWindowListener(new WindowAdapter() {
public void windowClosing(WindowEvent e) {
System.exit(0);
}
});
frame.pack();
frame.setVisible(true);
nf = NumberFormat.getInstance();
nf.setMinimumFractionDigits(1);
nf.setMaximumFractionDigits(decimals);
}
//gui end


public float[][] ReadInMatrix(JTextArea ta) throws Exception {
if (DEBUG) {
System.out.println("Reading In Matrix");
}
String rawtext = ta.getText();
String val = "";
int i = 0;
int j = 0;
int[] rsize = new int[max];
StringTokenizer ts = new StringTokenizer(rawtext, "\n");
while (ts.hasMoreTokens()) {
StringTokenizer ts2 = new StringTokenizer(ts.nextToken());
while (ts2.hasMoreTokens()) {
ts2.nextToken();
j++;
}
rsize[i] = j;
i++;
j = 0;
}
statusBar.setText("Matrix Size: " + i);
if ((DEBUG) || (INFO)) { 
System.out.println("Matrix Size: " + i);
}
for (int c = 0; c < i; c++) {
if (DEBUG) {
System.out.println("i=" + i + "  j=" + rsize[c] + "   Column: "+ c);
}
if (rsize[c] != i) {
statusBar.setText("Invalid Matrix Entered. Size Mismatch.");
throw new Exception("Invalid Matrix Entered. Size Mismatch.");
}
}
n = i;
float matrix[][] = new float[n][n];
i = j = 0;
val = "";		
StringTokenizer st = new StringTokenizer(rawtext, "\n");
while (st.hasMoreTokens()) {
StringTokenizer st2 = new StringTokenizer(st.nextToken());
while (st2.hasMoreTokens()) {
val = st2.nextToken();
try {
matrix[i][j] = Float.valueOf(val).floatValue();
} catch (Exception exception) {
statusBar.setText("Invalid Number Format");
}
j++;
}
i++;
j = 0;
}
if (DEBUG) {
System.out.println("Matrix Read::");
for (i = 0; i < n; i++) {
for (j = 0; j < n; j++) {
System.out.print("m[" + i + "][" + j + "] = "+ matrix[i][j] + "   ");
}
System.out.println();
}
}
return matrix;}

public float[][] ReadInMatrixNotSquare(JTextArea ta)
throws Exception {
if (DEBUG) {
System.out.println("Reading In Matrix");
}
String rawtext = ta.getText();
StringTokenizer ts = new StringTokenizer(rawtext, "\n");
if (DEBUG)
System.out.println("Rows: " + ts.countTokens());
float matrix[][] = new float[ts.countTokens()][];
StringTokenizer st2;
int row = 0;
int col = 0;
int last = -5;
int curr = -5;
while (ts.hasMoreTokens()) {
st2 = new StringTokenizer(ts.nextToken(), " ");
last = curr;
curr = st2.countTokens();
if(last != -5 && curr!= last)
throw new Exception("Rows not of equal length");
if (DEBUG)
System.out.println("Cols: " + st2.countTokens());
matrix[row] = new float[st2.countTokens()];
while (st2.hasMoreElements()) {
matrix[row][col++] = Float.parseFloat(st2.nextToken());
}
row++;
col = 0;
}
System.out.println();
return matrix;
}

public void DisplayMatrix(float[][] matrix, JTextArea ta) {
if (DEBUG) {
System.out.println("Displaying Matrix");
}
String rstr = "";
String dv = "";
for (int i = 0; i < matrix.length; i++) {
for (int j = 0; j < matrix[i].length; j++) {
dv = nf.format(matrix[i][j]);
rstr = rstr.concat(dv + "  ");
}
rstr = rstr.concat("\n");
}
ta.setText(rstr);
}

public float[][] AddMatrix(float[][] a, float[][] b) throws Exception {
int tms = a.length;
int tmsB = b.length;
if (tms != tmsB) {
statusBar.setText("Matrix Size Mismatch");
}
float matrix[][] = new float[tms][tms];
for (int i = 0; i < tms; i++)
for (int j = 0; j < tms; j++) {
matrix[i][j] = a[i][j] + b[i][j];
}
return matrix;
}

public float[][] MultiplyMatrix(float[][] a, float[][] b) throws Exception {
if(a[0].length != b.length)
throw new Exception("Matrices incompatible for multiplication");
float matrix[][] = new float[a.length][b[0].length];
for (int i = 0; i < a.length; i++)
for (int j = 0; j < b[i].length; j++)
matrix[i][j] = 0;
for(int i = 0; i < matrix.length; i++){
for(int j = 0; j < matrix[i].length; j++){
matrix[i][j] = calculateRowColumnProduct(a,i,b,j);
}
}
return matrix;
}
public float calculateRowColumnProduct(float[][] A, int row, float[][] B, int col){
float product = 0;
for(int i = 0; i < A[row].length; i++)
product +=A[row][i]*B[i][col];
return product;
}

	
public float[][] Transpose(float[][] a) {
if (INFO) {
System.out.println("Performing Transpose...");
}
float m[][] = new float[a[0].length][a.length];
for (int i = 0; i < a.length; i++)
for (int j = 0; j < a[i].length; j++)
m[j][i] = a[i][j];
return m;
}
public float[][] Inverse(float[][] a) throws Exception {
// Formula used to Calculate Inverse:
// inv(A) = 1/det(A) * adj(A)
if (INFO) {
System.out.println("Performing Inverse...");
}
int tms = a.length;
float m[][] = new float[tms][tms];
float mm[][] = Adjoint(a);
float det = Determinant(a);
float dd = 0;
if (det == 0) {
statusBar.setText("Determinant Equals 0, Not Invertible.");
if (INFO) {
System.out.println("Determinant Equals 0, Not Invertible.");
}
} else {
dd = 1 / det;
}
for (int i = 0; i < tms; i++)
for (int j = 0; j < tms; j++) {
m[i][j] = dd * mm[i][j];
}
return m;
}

public float[][] Adjoint(float[][] a) throws Exception {
if (INFO) {
System.out.println("Performing Adjoint...");
}
int tms = a.length;
float m[][] = new float[tms][tms];
int ii, jj, ia, ja;
float det;
for (int i = 0; i < tms; i++)
for (int j = 0; j < tms; j++) {
ia = ja = 0;
float ap[][] = new float[tms - 1][tms - 1];
for (ii = 0; ii < tms; ii++) {
for (jj = 0; jj < tms; jj++) {
if ((ii != i) && (jj != j)) {
ap[ia][ja] = a[ii][jj];
ja++;
}
}
if ((ii != i) && (jj != j)) {
ia++;
}
ja = 0;
}
det = Determinant(ap);
m[i][j] = (float) Math.pow(-1, i + j) * det;
}
m = Transpose(m);
return m;
}

public float[][] UpperTriangle(float[][] m) {
if (INFO) {
System.out.println("Converting to Upper Triangle...");
}
float f1 = 0;
float temp = 0;
int tms = m.length; 
int v = 1;
iDF = 1;
for (int col = 0; col < tms - 1; col++) {
for (int row = col + 1; row < tms; row++) {
v = 1;
outahere: while (m[col][col] == 0) 
{ 
if (col + v >= tms) 
{
iDF = 0;
break outahere;
} else {
for (int c = 0; c < tms; c++) {
temp = m[col][c];
m[col][c] = m[col + v][c]; 
m[col + v][c] = temp;
}
v++; // count row switchs
iDF = iDF * -1; 
}
}
if (m[col][col] != 0) {
if (DEBUG) {
System.out.println("tms = " + tms + "   col = " + col+ "   row = " + row);
}
try {
f1 = (-1) * m[row][col] / m[col][col];
for (int i = col; i < tms; i++) {
m[row][i] = f1 * m[col][i] + m[row][i];
}
} catch (Exception e) {
System.out.println("Still Here!!!");
}
}
}
}
return m;
}

public float Determinant(float[][] matrix) {
if (INFO) {
System.out.println("Getting Determinant...");
}
int tms = matrix.length;
float det = 1;
matrix = UpperTriangle(matrix);
for (int i = 0; i < tms; i++) {
det = det * matrix[i][i];
} 
det = det * iDF; 
if (INFO) {
System.out.println("Determinant: " + det);
}
return det;
}
}
}

class ScientificPanel extends JPanel{
public static class ScientificCalculator extends JFrame implements ActionListener{
	JTextField tfield;
	double temp, temp1, result, a;
	static double m1, m2;
	int k = 1, x = 0, y = 0, z = 0;
	char ch;
	JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, zero, clr, pow2, pow3, exp,
			fac, plus, min, div, log, rec, mul, eq, addSub, dot, mr, mc, mp,
			mm, sqrt, sin, cos, tan;
	Container cont;
	JPanel textPanel, buttonpanel;

	ScientificCalculator() {
		cont = getContentPane();
		cont.setLayout(new BorderLayout());
		JPanel textpanel = new JPanel();
		tfield = new JTextField(25);
		tfield.setHorizontalAlignment(SwingConstants.RIGHT);
		tfield.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent keyevent) {
				char c = keyevent.getKeyChar();
				if (c >= '0' && c <= '9') {
				} else {
					keyevent.consume();
				}
			}
		});
		textpanel.add(tfield);
		buttonpanel = new JPanel();
		buttonpanel.setLayout(new GridLayout(8, 4, 2, 2));
		boolean t = true;
		mr = new JButton("MR");
		buttonpanel.add(mr);
		mr.addActionListener(this);
		mc = new JButton("MC");
		buttonpanel.add(mc);
		mc.addActionListener(this);

		mp = new JButton("M+");
		buttonpanel.add(mp);
		mp.addActionListener(this);

		mm = new JButton("M-");
		buttonpanel.add(mm);
		mm.addActionListener(this);

		b1 = new JButton("1");
		buttonpanel.add(b1);
		b1.addActionListener(this);
		b2 = new JButton("2");
		buttonpanel.add(b2);
		b2.addActionListener(this);
		b3 = new JButton("3");
		buttonpanel.add(b3);
		b3.addActionListener(this);

		b4 = new JButton("4");
		buttonpanel.add(b4);
		b4.addActionListener(this);
		b5 = new JButton("5");
		buttonpanel.add(b5);
		b5.addActionListener(this);
		b6 = new JButton("6");
		buttonpanel.add(b6);
		b6.addActionListener(this);

		b7 = new JButton("7");
		buttonpanel.add(b7);
		b7.addActionListener(this);
		b8 = new JButton("8");
		buttonpanel.add(b8);
		b8.addActionListener(this);
		b9 = new JButton("9");
		buttonpanel.add(b9);
		b9.addActionListener(this);

		zero = new JButton("0");
		buttonpanel.add(zero);
		zero.addActionListener(this);

		plus = new JButton("+");
		buttonpanel.add(plus);
		plus.addActionListener(this);

		min = new JButton("-");
		buttonpanel.add(min);
		min.addActionListener(this);

		mul = new JButton("*");
		buttonpanel.add(mul);
		mul.addActionListener(this);

		div = new JButton("/");
		div.addActionListener(this);
		buttonpanel.add(div);

		addSub = new JButton("+/-");
		buttonpanel.add(addSub);
		addSub.addActionListener(this);

		dot = new JButton(".");
		buttonpanel.add(dot);
		dot.addActionListener(this);

		eq = new JButton("=");
		buttonpanel.add(eq);
		eq.addActionListener(this);

		rec = new JButton("1/x");
		buttonpanel.add(rec);
		rec.addActionListener(this);
		sqrt = new JButton("Sqrt");
		buttonpanel.add(sqrt);
		sqrt.addActionListener(this);
		log = new JButton("log");
		buttonpanel.add(log);
		log.addActionListener(this);

		sin = new JButton("SIN");
		buttonpanel.add(sin);
		sin.addActionListener(this);
		cos = new JButton("COS");
		buttonpanel.add(cos);
		cos.addActionListener(this);
		tan = new JButton("TAN");
		buttonpanel.add(tan);
		tan.addActionListener(this);
		pow2 = new JButton("x^2");
		buttonpanel.add(pow2);
		pow2.addActionListener(this);
		pow3 = new JButton("x^3");
		buttonpanel.add(pow3);
		pow3.addActionListener(this);
		exp = new JButton("Exp");
		exp.addActionListener(this);
		buttonpanel.add(exp);
		fac = new JButton("n!");
		fac.addActionListener(this);
		buttonpanel.add(fac);

		clr = new JButton("AC");
		buttonpanel.add(clr);
		clr.addActionListener(this);
		cont.add("Center", buttonpanel);
		cont.add("North", textpanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if (s.equals("1")) {
			if (z == 0) {
				tfield.setText(tfield.getText() + "1");
			} else {
				tfield.setText("");
				tfield.setText(tfield.getText() + "1");
				z = 0;
			}
		}
		if (s.equals("2")) {
			if (z == 0) {
				tfield.setText(tfield.getText() + "2");
			} else {
				tfield.setText("");
				tfield.setText(tfield.getText() + "2");
				z = 0;
			}
		}
		if (s.equals("3")) {
			if (z == 0) {
				tfield.setText(tfield.getText() + "3");
			} else {
				tfield.setText("");
				tfield.setText(tfield.getText() + "3");
				z = 0;
			}
		}
		if (s.equals("4")) {
			if (z == 0) {
				tfield.setText(tfield.getText() + "4");
			} else {
				tfield.setText("");
				tfield.setText(tfield.getText() + "4");
				z = 0;
			}
		}
		if (s.equals("5")) {
			if (z == 0) {
				tfield.setText(tfield.getText() + "5");
			} else {
				tfield.setText("");
				tfield.setText(tfield.getText() + "5");
				z = 0;
			}
		}
		if (s.equals("6")) {
			if (z == 0) {
				tfield.setText(tfield.getText() + "6");
			} else {
				tfield.setText("");
				tfield.setText(tfield.getText() + "6");
				z = 0;
			}
		}
		if (s.equals("7")) {
			if (z == 0) {
				tfield.setText(tfield.getText() + "7");
			} else {
				tfield.setText("");
				tfield.setText(tfield.getText() + "7");
				z = 0;
			}
		}
		if (s.equals("8")) {
			if (z == 0) {
				tfield.setText(tfield.getText() + "8");
			} else {
				tfield.setText("");
				tfield.setText(tfield.getText() + "8");
				z = 0;
			}
		}
		if (s.equals("9")) {
			if (z == 0) {
				tfield.setText(tfield.getText() + "9");
			} else {
				tfield.setText("");
				tfield.setText(tfield.getText() + "9");
				z = 0;
			}
		}
		if (s.equals("0")) {
			if (z == 0) {
				tfield.setText(tfield.getText() + "0");
			} else {
				tfield.setText("");
				tfield.setText(tfield.getText() + "0");
				z = 0;
			}
		}
		if (s.equals("AC")) {
			tfield.setText("");
			x = 0;
			y = 0;
			z = 0;
		}
		if (s.equals("log")) {
			if (tfield.getText().equals("")) {
				tfield.setText("");
			} else {
				a = Math.log(Double.parseDouble(tfield.getText()));
				tfield.setText("");
				tfield.setText(tfield.getText() + a);
			}
		}
		if (s.equals("1/x")) {
			if (tfield.getText().equals("")) {
				tfield.setText("");
			} else {
				a = 1 / Double.parseDouble(tfield.getText());
				tfield.setText("");
				tfield.setText(tfield.getText() + a);
			}
		}
		if (s.equals("Exp")) {
			if (tfield.getText().equals("")) {
				tfield.setText("");
			} else {
				a = Math.exp(Double.parseDouble(tfield.getText()));
				tfield.setText("");
				tfield.setText(tfield.getText() + a);
			}
		}
		if (s.equals("x^2")) {
			if (tfield.getText().equals("")) {
				tfield.setText("");
			} else {
				a = Math.pow(Double.parseDouble(tfield.getText()), 2);
				tfield.setText("");
				tfield.setText(tfield.getText() + a);
			}
		}
		if (s.equals("x^3")) {
			if (tfield.getText().equals("")) {
				tfield.setText("");
			} else {
				a = Math.pow(Double.parseDouble(tfield.getText()), 3);
				tfield.setText("");
				tfield.setText(tfield.getText() + a);
			}
		}
		if (s.equals("+/-")) {
			if (x == 0) {
				tfield.setText("-" + tfield.getText());
				x = 1;
			} else {
				tfield.setText(tfield.getText());
			}
		}
		if (s.equals(".")) {
			if (y == 0) {
				tfield.setText(tfield.getText() + ".");
				y = 1;
			} else {
				tfield.setText(tfield.getText());
			}
		}
		if (s.equals("+")) {
			if (tfield.getText().equals("")) {
				tfield.setText("");
				temp = 0;
				ch = '+';
			} else {
				temp = Double.parseDouble(tfield.getText());
				tfield.setText("");
				ch = '+';
				y = 0;
				x = 0;
			}
			tfield.requestFocus();
		}
		if (s.equals("-")) {
			if (tfield.getText().equals("")) {
				tfield.setText("");
				temp = 0;
				ch = '-';
			} else {
				x = 0;
				y = 0;
				temp = Double.parseDouble(tfield.getText());
				tfield.setText("");
				ch = '-';
			}
			tfield.requestFocus();
		}
		if (s.equals("/")) {
			if (tfield.getText().equals("")) {
				tfield.setText("");
				temp = 1;
				ch = '/';
			} else {
				x = 0;
				y = 0;
				temp = Double.parseDouble(tfield.getText());
				ch = '/';
				tfield.setText("");
			}
			tfield.requestFocus();
		}
		if (s.equals("*")) {
			if (tfield.getText().equals("")) {
				tfield.setText("");
				temp = 1;
				ch = '*';
			} else {
				x = 0;
				y = 0;
				temp = Double.parseDouble(tfield.getText());
				ch = '*';
				tfield.setText("");
			}
			tfield.requestFocus();
		}
		if (s.equals("MC")) {
			m1 = 0;
			tfield.setText("");
		}
		if (s.equals("MR")) {
			tfield.setText("");
			tfield.setText(tfield.getText() + m1);
		}
		if (s.equals("M+")) {
			if (k == 1) {
				m1 = Double.parseDouble(tfield.getText());
				k++;
			} else {
				m1 += Double.parseDouble(tfield.getText());
				tfield.setText("" + m1);
			}
		}
		if (s.equals("M-")) {
			if (k == 1) {
				m1 = Double.parseDouble(tfield.getText());
				k++;
			} else {
				m1 -= Double.parseDouble(tfield.getText());
				tfield.setText("" + m1);
			}
		}
		if (s.equals("Sqrt")) {
			if (tfield.getText().equals("")) {
				tfield.setText("");
			} else {
				a = Math.sqrt(Double.parseDouble(tfield.getText()));
				tfield.setText("");
				tfield.setText(tfield.getText() + a);
			}
		}
		if (s.equals("SIN")) {
			if (tfield.getText().equals("")) {
				tfield.setText("");
			} else {
				a = Math.sin(Double.parseDouble(tfield.getText()));
				tfield.setText("");
				tfield.setText(tfield.getText() + a);
			}
		}
		if (s.equals("COS")) {
			if (tfield.getText().equals("")) {
				tfield.setText("");
			} else {
				a = Math.cos(Double.parseDouble(tfield.getText()));
				tfield.setText("");
				tfield.setText(tfield.getText() + a);
			}
		}
		if (s.equals("TAN")) {
			if (tfield.getText().equals("")) {
				tfield.setText("");
			} else {
				a = Math.tan(Double.parseDouble(tfield.getText()));
				tfield.setText("");
				tfield.setText(tfield.getText() + a);
			}
		}
		if (s.equals("=")) {
			if (tfield.getText().equals("")) {
				tfield.setText("");
			} else {
				temp1 = Double.parseDouble(tfield.getText());
				switch (ch) {
				case '+':
					result = temp + temp1;
					break;
				case '-':
					result = temp - temp1;
					break;
				case '/':
					result = temp / temp1;
					break;
				case '*':
					result = temp * temp1;
					break;
				}
				tfield.setText("");
				tfield.setText(tfield.getText() + result);
				z = 1;
			}
		}
		if (s.equals("n!")) {
			if (tfield.getText().equals("")) {
				tfield.setText("");
			} else {
				a = fact(Double.parseDouble(tfield.getText()));
				tfield.setText("");
				tfield.setText(tfield.getText() + a);
			}
		}
		tfield.requestFocus();
	}

	double fact(double x) {
		int er = 0;
		if (x < 0) {
			er = 20;
			return 0;
		}
		double i, s = 1;
		for (i = 2; i <= x; i += 1.0)
			s *= i;
		return s;
	}

	public static void main(String args[]) {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
		}
		ScientificCalculator f = new ScientificCalculator();
		f.setTitle("ScientificCalculator");
		f.pack();
		f.setVisible(true);
	}
}
}
