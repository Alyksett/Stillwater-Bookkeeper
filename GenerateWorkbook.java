import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;

public static class GenerateWorkbook {
    /**
     * Static variables for initialization and formatting
     */
	static NumberFormat formatterr = NumberFormat.getCurrencyInstance();   
	// declaring workbook and creating sheet
	private static Workbook newBook = new XSSFWorkbook();
	private static DataFormat df = newBook.createDataFormat();
	private static Sheet sheet1 = newBook.createSheet(Controller.latestSale);
	// declaring fonts and styles
	// fonts
	private static XSSFFont boldFont = (XSSFFont) newBook.createFont();
	private static XSSFFont boldItalicFont = (XSSFFont) newBook.createFont();
	private static XSSFFont titleFont = (XSSFFont) newBook.createFont();
	private static XSSFFont headerFont = (XSSFFont) newBook.createFont();
	private static XSSFFont redFont = (XSSFFont) newBook.createFont();
	// styles
	private static CellStyle titleStyle = newBook.createCellStyle();
	private static CellStyle headerStyle = newBook.createCellStyle();
	private static CellStyle boldItalicStyle = newBook.createCellStyle();
	private static CellStyle boldStyle = newBook.createCellStyle();
	private static CellStyle boldMoneyStyle = newBook.createCellStyle();
	private static CellStyle redStyle = newBook.createCellStyle();
	private static CellStyle moneyStyle = newBook.createCellStyle();

    /**
     * Constructor
     */
    public GenerateWorkbook(Map<String, PayPeriodStorage> mainDatabase, String dateKey){
		generateFontsAndStyles();
        generateTitleAndHeader();

        fillCells(mainDatabase, dateKey);
        
        
        exportXLSX();
    }
}

    private void fillCells(Map<String, PayPeriodStorage> mainDatabase, String dateKey){
        for(String temp : mainDatabase.get(dateKey)){
            for(Artist artist: mainDatabase.get(dateKey)){
				
			}
        }
    }






	private static void generateFontsAndStyles() {
		// Fonts//
		boldFont.setBold(true);
		boldItalicFont.setBold(true);
		boldItalicFont.setItalic(true);
		titleFont.setBold(true);
		titleFont.setItalic(true);
		titleFont.setFontHeight(14);
		headerFont.setBold(true);
		headerFont.setItalic(true);
		headerFont.setFontHeight(12);
		redFont.setColor(IndexedColors.RED.getIndex());
		// Styles//

		// title
		titleStyle.setFont(titleFont);
		titleStyle.setWrapText(false);

		// header
		headerStyle.setFont(headerFont);
		headerStyle.setWrapText(true);

		// boldItalic
		boldItalicStyle.setFont(boldItalicFont);
		boldItalicStyle.setWrapText(false);

		// Bold Style
		boldStyle.setFont(boldFont);

		// Bold money style
		boldMoneyStyle.setFont(boldFont);
		boldMoneyStyle.setDataFormat(df.getFormat("$#,#0.00"));

		// red for admin fee
		redStyle.setFont(redFont);
		redStyle.setDataFormat(df.getFormat("$#,#0.00"));
		// general money style
		moneyStyle.setDataFormat(df.getFormat("$#,#0.00"));
	}
	/**
     * Creating title and the header
     */
	private static void generateTitleAndHeader() {
		// setting the column widths
		sheet1.setColumnWidth(0, 12 * 256);
		sheet1.setColumnWidth(1, 15 * 256);
		sheet1.setColumnWidth(2, 25 * 256);
		sheet1.setColumnWidth(3, 5 * 256);
		sheet1.setColumnWidth(4, 10 * 256);
		sheet1.setColumnWidth(5, 12 * 256);
		sheet1.setColumnWidth(6, 16 * 256);
		sheet1.setColumnWidth(7, 10 * 256);
		sheet1.setColumnWidth(8, 14 * 256);
		sheet1.setColumnWidth(9, 30 * 256);
		sheet1.setColumnWidth(10, 15 * 256);

		// Making Title
		Row row = sheet1.createRow(0);
		Cell businessTitleCell = row.createCell(0);
		businessTitleCell.setCellValue("Stillwater Art Guild Gallery");
		businessTitleCell.setCellStyle(titleStyle);

		row = sheet1.createRow(1);
		Cell paymentTitleCell = row.createCell(0);
		paymentTitleCell.setCellValue("Artist Commission Payment");
		paymentTitleCell.setCellStyle(boldItalicStyle);

		row = sheet1.createRow(2);
		Cell datesTitleCell = row.createCell(0);
		datesTitleCell.setCellValue("Sales from " + Controller.dateRange);
		datesTitleCell.setCellStyle(boldItalicStyle);

		// Column headers
		row = sheet1.createRow(6);

		Cell dateCell1 = row.createCell(0);
		dateCell1.setCellValue("Date of Sale");
		dateCell1.setCellStyle(headerStyle);

		Cell categoryCell1 = row.createCell(1);
		categoryCell1.setCellValue("Category");
		categoryCell1.setCellStyle(headerStyle);

		Cell artistCell1 = row.createCell(2);
		artistCell1.setCellValue("Artist");
		artistCell1.setCellStyle(headerStyle);

		Cell quantityCell1 = row.createCell(3);
		quantityCell1.setCellValue("QTY");
		quantityCell1.setCellStyle(headerStyle);

		Cell grossSalesCell1 = row.createCell(4);
		grossSalesCell1.setCellValue("Gross Sale");
		grossSalesCell1.setCellStyle(headerStyle);

		Cell salesTaxCell1 = row.createCell(5);
		salesTaxCell1.setCellValue("Sales Tax");
		salesTaxCell1.setCellStyle(headerStyle);

		Cell totalCustomerPaymentCell1 = row.createCell(6);
		totalCustomerPaymentCell1.setCellValue("Total Customer Payment");
		totalCustomerPaymentCell1.setCellStyle(headerStyle);

		Cell adminFeeCell1 = row.createCell(7);
		adminFeeCell1.setCellValue("Admin Fee");
		adminFeeCell1.setCellStyle(headerStyle);

		Cell totalDueToArtistCell1 = row.createCell(8);
		totalDueToArtistCell1.setCellValue("Total Due to Artist");
		totalDueToArtistCell1.setCellStyle(headerStyle);

		Cell descriptionCell1 = row.createCell(9);
		descriptionCell1.setCellValue("Description");
		descriptionCell1.setCellStyle(headerStyle);

		Cell checkNumberCell1 = row.createCell(10);
		checkNumberCell1.setCellValue("Check Number");
		checkNumberCell1.setCellStyle(headerStyle);
	}

	// export data to XSLX file
	public static void exportXLXS() {
		// exporting workbook
		try {
			FileOutputStream fileOut = new FileOutputStream(
					Controller.oldestSale + " thru " + Controller.latestSale + " payments.xlsx");
			newBook.write(fileOut);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
