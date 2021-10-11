
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ImportFile {
	static XSSFWorkbook workbook;

	public static void importWorkbook() {

		// defining variables

		String userDir = System.getProperty("user.home");

		JFileChooser chooser = new JFileChooser(userDir + "/Desktop");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Microsoft Excel Workbook", "xlsx");

		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(chooser);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			GUI.appendConsoleText("You chose to open this file: " + chooser.getSelectedFile().getName() + "\n");
		}
		chooser.setSelectedFile(chooser.getSelectedFile());
		try {
			ConvertFile(chooser);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void ConvertFile(JFileChooser chooser) throws IOException {
		FileInputStream inputStream = new FileInputStream(chooser.getSelectedFile());
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		// GUI.appendConsoleText("XLXS File imported into Java.");
		updateWorkbook(workbook);// setting the input stream to the workbook variable
		Controller.breakWorkbook(workbook);
		Controller.loadMap();
		Controller.artistArray.removeAll(Controller.artistArray);
		System.out.println(Controller.artistArray.toString() + " ImportFile 43");
		inputStream.close();
		workbook = null;
		//workbook.close();
		
	}

	// accessor and mutator method for the imported workbook
	public static void updateWorkbook(XSSFWorkbook book) {
		workbook = book;
	}

	public static XSSFWorkbook getWorkbook() {
		return workbook;
	}
}
