import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

public class MatrixApp {

	private JFrame mainFrame;
	private JTextField sizeTextBox;
	private JPanel matrixPanel = new JPanel();
	private JTextField[][] matrixFields;
	private JPanel gui;

	public MatrixApp() {
		prepareGUI();
	}

	public static void main(String[] args) {
		MatrixApp swingControlDemo = new MatrixApp();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("MatrixApp");
		mainFrame.setSize(400, 400);
		mainFrame.setLayout(new GridLayout(3, 1));
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        gui = new JPanel(new BorderLayout(10,10));
        gui.setBorder(new TitledBorder("Matrix Calculator"));
		mainFrame.setContentPane(gui);

        JPanel head = new JPanel(new GridLayout(2,1));
        gui.add(head, BorderLayout.NORTH);        
        
        //Head region
        JLabel headerLabel = new JLabel("Welcome to Matrix Calculator", JLabel.CENTER);
        head.add(headerLabel);

        JPanel sizeText = new JPanel(new GridLayout(1,2));
		JLabel sizeLabel = new JLabel("Enter matrix size:");
		sizeText.add(sizeLabel);
		sizeTextBox = new JTextField();
		sizeText.add(sizeTextBox);
		sizeTextBox.getDocument().addDocumentListener(new MatrixSizeChangeListener());
		head.add(sizeText, BorderLayout.SOUTH);

		gui.add(matrixPanel, BorderLayout.CENTER);
		
		JButton determinateButton = new JButton("Calculate Determinant");
		determinateButton.setActionCommand("Calculate");
		determinateButton.addActionListener(new ValidateTextBoxes());
		gui.add(determinateButton, BorderLayout.SOUTH);
		
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	private void generateMatrix(int size) {
		gui.remove(matrixPanel);
		matrixPanel = new JPanel(new GridLayout(size,size));
		matrixFields = new JTextField[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				matrixFields[i][j] = new JTextField();
				matrixPanel.add(matrixFields[i][j]);
			}
		gui.add(matrixPanel, BorderLayout.CENTER);
		gui.revalidate();
		gui.repaint();
		mainFrame.pack();
	}

	private Boolean tryParseInt(String text) {
		try {
			Integer.parseInt(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	private Boolean tryParseDouble(String text){
		try {
			Double.parseDouble(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private class ValidateTextBoxes implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (matrixFields == null)
			{
				JOptionPane.showMessageDialog(null, "Please enter a matrix size.");
				return;
			}
			int size = matrixFields[0].length;
			double [][] matrixValues = new double[size][size];
			for (int i = 0; i < size; i++)
				for (int j = 0; j < size; j++) {
					String text = matrixFields[i][j].getText();
					if (text == null || text.isEmpty() || !tryParseDouble(text)){
						JOptionPane.showMessageDialog(null, "Please enter number for each textbox.");
						return;
					}
					matrixValues[i][j] = Double.parseDouble(text);
				}
			calculateDeterminant(matrixValues);
		}
	}

	private class MatrixSizeChangeListener implements DocumentListener {		
		public void changedUpdate(DocumentEvent e) {
			checkMatrix();
		}

		public void removeUpdate(DocumentEvent e) {
			checkMatrix();
		}

		public void insertUpdate(DocumentEvent e) {
			checkMatrix();
		}

		public void checkMatrix() {
			if (tryParseInt(sizeTextBox.getText())) {
				if (Integer.parseInt(sizeTextBox.getText()) < 2 || Integer.parseInt(sizeTextBox.getText()) > 5) {
					JOptionPane.showMessageDialog(null, "Error: Matrix size must be between 2 and 5",
							"Error Massage", JOptionPane.ERROR_MESSAGE);
				} else {

					generateMatrix(Integer.parseInt(sizeTextBox.getText()));
				}
			}
		}
	}
	
	private void calculateDeterminant(double [][] matrixValues)
	{
		Matrix matrix = new Matrix(matrixFields[0].length);
		for (int i = 0; i < matrixValues[0].length; i++)
			for (int j = 0; j < matrixValues[0].length; j++)
			{
				matrix.matrix[i][j] = matrixValues[i][j];
			}
		JOptionPane.showMessageDialog(null, "Matrix determinant is " + matrix.det());
	}
}
