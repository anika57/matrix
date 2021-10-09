import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MatrixCalculator {
MatrixCalculator obj=new MatrixCalculator();
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
try {xt
DisplayMatrix(AddMatrix(ReadInMatrix(taA),
ReadInMatrix(taB)), taC);
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
MatrixCalculator app = new MatrixCalculator();
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
}//gui end


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
