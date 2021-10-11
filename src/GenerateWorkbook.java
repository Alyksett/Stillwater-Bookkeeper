import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;

public class GenerateWorkbook {
    /**
     * Static variables for initialization and formatting
     */
	static NumberFormat formatterr = NumberFormat.getCurrencyInstance();   
	// workbook and creating sheet
	private static Workbook newBook = new XSSFWorkbook();
	private static DataFormat df = newBook.createDataFormat();
	private static Sheet sheet = newBook.createSheet();
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
     * Constructor.
	 * Generates heading and fills cells, then exports.
	 * 
	 * @param mainDatabase
	 * @param dateKey
     */
    public GenerateWorkbook(Map<String, PayPeriodStorage> mainDatabase, String dateKey){
		
		newBook.setSheetName(0, dateKey);
		generateFontsAndStyles();
        generateTitleAndHeader();
        fillCells(mainDatabase, dateKey);
        exportXLXS(dateKey);
		//clearSheet(dateKey);
		dateKey = null;
    }
	
	/**
	 * This is for keeping track of the row number. Starts at 7 because of the titles.
	*/
	static int rowNumber = 7;
	static Row row;
	static String name = null;

	/**
	 * 
	 * Fills the XLXS file with the information in the database.
	 * 
	 * Starting from the inside;
	 * once the innermost loop is finished looping through the arrayList and adding the information to the sheet,
	 * then it will move up an iteration of the outer loop. This one will create the total row. 
	 * 
	 * final block at the end to write the grand totals.
	 * 
	 * @param mainDatabase
	 * @param dateKey
	 */
    private void fillCells(Map<String, PayPeriodStorage> unsortedDatabase, String dateKey){
		TreeMap<String, ArtistSales> mainDatabase = new TreeMap<>();
		mainDatabase.putAll(unsortedDatabase.get(dateKey).getPeriod());

		//this loops through each individual artist's sales over a period
        for(ArtistSales sales: mainDatabase.values()){
			//this loops through the arrayList of a single artists sales in a period
			Collections.sort(sales.getAllSales(), new SortArtists());
			for(Artist sale : sales.getAllSales()){
				name = sale.getArtistName();
				fillRow(sale.getSaleDate(), sale.getCategory(),
				sale.getArtistName(), sale.getQuantity(),
				 sale.getGrossSale(), sale.getSalesTax(),
				  sale.getTotalCustomerPayment(), sale.getAdminFee(),
				   sale.getTotalDueToArtist(), sale.getDescription(),
					-1, rowNumber, moneyStyle);
				rowNumber++;
			}	
			//row for each artist
			fillRow("", "", name+" Total",
					-1, sales.getGrossSaleTotal(),
					 sales.getSalesTaxTotal(), sales.getTotalCustomerPaymentTotal(),
					  sales.getAdminFeeTotal(), sales.getTotalDueToArtistTotal(),
					   "", sales.getCheckNumber(), rowNumber, boldStyle);
			rowNumber++;
			
		}
		mainDatabase.clear();
		//Filling the grand total row
		fillRow("", "", "Grand Total",
				-1, unsortedDatabase.get(dateKey).getGrandSaleTotal(),
		 		unsortedDatabase.get(dateKey).getGrandTaxTotal(), unsortedDatabase.get(dateKey).getGrandCustomerPaymentTotal(),
				 unsortedDatabase.get(dateKey).getGrandAdminFeeTotal(), unsortedDatabase.get(dateKey).getGrandTotalDueToArtistTotal(),
		   		"", -1, rowNumber, boldStyle);
			rowNumber = 7;
    }

	private void fillRow(String date, String category,
						  String name, double quantity,
						   double gross, double salesTax,
						    double totalCustPay, double adminFee,
							 double totalArtist, String description,
							  int checkNo, int rowNumber, CellStyle style){
		

		row = sheet.createRow( rowNumber);
		
		Cell dateCell = row.createCell(0);
		dateCell.setCellValue(date);
		dateCell.setCellStyle(style);
		Cell categoryCell = row.createCell(1);
		categoryCell.setCellValue(category);
		categoryCell.setCellStyle(style);
		Cell artistCell = row.createCell(2);
		artistCell.setCellValue(name);
		artistCell.setCellStyle(style);
		Cell quantityCell = row.createCell(3);
		quantityCell.setCellValue(quantity);
		quantityCell.setCellStyle(null);
		Cell grossSalesCell = row.createCell(4);
		grossSalesCell.setCellValue(gross);
		grossSalesCell.setCellStyle(style);
		Cell salesTaxCell = row.createCell(5);
		salesTaxCell.setCellValue(salesTax);
		salesTaxCell.setCellStyle(style);
		Cell totalCustomerPaymentCell = row.createCell(6);
		totalCustomerPaymentCell.setCellValue(totalCustPay);
		totalCustomerPaymentCell.setCellStyle(style);
		Cell adminFeeCell = row.createCell(7);
		adminFeeCell.setCellValue(adminFee);
		adminFeeCell.setCellStyle(redStyle);
		Cell totalDueToArtistCell = row.createCell(8);
		totalDueToArtistCell.setCellValue(totalArtist);
		totalDueToArtistCell.setCellStyle(style);
		Cell descriptionCell = row.createCell(9);
		descriptionCell.setCellValue(description);
		descriptionCell.setCellStyle(style);
		Cell checkNumberCell = row.createCell(10);
		checkNumberCell.setCellValue(checkNo);

		//if quantity is zero, then fill the quantity row with nothing.
		if(quantity == -1){
			quantityCell.setCellValue("");	
		}
		if(checkNo == -1){
			checkNumberCell.setCellValue("");
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
		boldStyle.setDataFormat(df.getFormat("$#,#0.00"));

		// Bold money style
		boldMoneyStyle.setFont(boldFont);
		boldMoneyStyle.setDataFormat(df.getFormat("$#,#0.00"));

		// Bold money style
		moneyStyle.setDataFormat(df.getFormat("$#,#0.00"));

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
		sheet.setColumnWidth(0, 12 * 256);
		sheet.setColumnWidth(1, 15 * 256);
		sheet.setColumnWidth(2, 25 * 256);
		sheet.setColumnWidth(3, 5 * 256);
		sheet.setColumnWidth(4, 10 * 256);
		sheet.setColumnWidth(5, 12 * 256);
		sheet.setColumnWidth(6, 16 * 256);
		sheet.setColumnWidth(7, 10 * 256);
		sheet.setColumnWidth(8, 14 * 256);
		sheet.setColumnWidth(9, 30 * 256);
		sheet.setColumnWidth(10, 15 * 256);

		// Making Title
		Row row = sheet.createRow(0);
		Cell businessTitleCell = row.createCell(0);
		businessTitleCell.setCellValue("Stillwater Art Guild Gallery");
		businessTitleCell.setCellStyle(titleStyle);

		row = sheet.createRow(1);
		Cell paymentTitleCell = row.createCell(0);
		paymentTitleCell.setCellValue("Artist Commission Payment");
		paymentTitleCell.setCellStyle(boldItalicStyle);

		row = sheet.createRow(2);
		Cell datesTitleCell = row.createCell(0);
		datesTitleCell.setCellValue("Sales from " + Controller.dateRange);
		datesTitleCell.setCellStyle(boldItalicStyle);

		// Column headers
		row = sheet.createRow(6);

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
	public static void exportXLXS(String dateKey) {
		// exporting workbook
		try {
			FileOutputStream fileOut = new FileOutputStream(dateKey + " payments.xlsx");
			newBook.write(fileOut);
			
			//must re open at the start	
			//newBook.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
