import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;

public class CreateSmallWorkbook {
	static int temp = 7;

	static NumberFormat formatterr = NumberFormat.getCurrencyInstance();
	static double grandGross;
	static double grandSalesTax;
	static double grandCustomerPayment;
	static double grandAdminFee;
	static double grandTotalDueToArtist;

	static Workbook newBook = new XSSFWorkbook();
	static DataFormat df = newBook.createDataFormat();
	static Sheet sheet1 = newBook.createSheet(Controller.latestSale);
	// creating fonts and styles//
	static XSSFFont boldFont = (XSSFFont) newBook.createFont();
	static CellStyle boldStyle = newBook.createCellStyle();
	static CellStyle boldMoneyStyle = newBook.createCellStyle();

	// this is to generate the workbook and title stuff. called once
	public static void createSupports() {
		// setting column widths
		sheet1.setColumnWidth(0, 18 * 256);
		sheet1.setColumnWidth(1, 15 * 256);
		sheet1.setColumnWidth(2, 15 * 256);
		sheet1.setColumnWidth(3, 15 * 256);
		sheet1.setColumnWidth(4, 10 * 256);
		sheet1.setColumnWidth(5, 12 * 256);

		// Fonts//

		boldFont.setBold(true);
		XSSFFont boldItalicFont = (XSSFFont) newBook.createFont();
		boldItalicFont.setBold(true);
		boldItalicFont.setItalic(true);
		XSSFFont titleFont = (XSSFFont) newBook.createFont();
		titleFont.setBold(true);
		titleFont.setItalic(true);
		titleFont.setFontHeight(14);
		XSSFFont headerFont = (XSSFFont) newBook.createFont();
		headerFont.setBold(true);
		headerFont.setItalic(true);
		headerFont.setFontHeight(12);
		XSSFFont redFont = (XSSFFont) newBook.createFont();
		redFont.setColor(IndexedColors.RED.getIndex());

		// Styles//

		// title
		CellStyle titleStyle = newBook.createCellStyle();
		titleStyle.setFont(titleFont);
		titleStyle.setWrapText(false);
		// header
		CellStyle headerStyle = newBook.createCellStyle();
		headerStyle.setFont(headerFont);
		headerStyle.setWrapText(true);
		// boldItalic
		CellStyle boldItalicStyle = newBook.createCellStyle();
		boldItalicStyle.setFont(boldItalicFont);
		boldItalicStyle.setWrapText(false);
		// boldItalicStyle.setBorderBottom(BorderStyle.MEDIUM);

		// Bold Style
		boldStyle.setFont(boldFont);

		// Bold money style
		boldMoneyStyle.setFont(boldFont);
		boldMoneyStyle.setDataFormat(df.getFormat("$#,#0.00"));

		// red for admin fee
		CellStyle redStyle = newBook.createCellStyle();
		redStyle.setFont(redFont);
		redStyle.setDataFormat(df.getFormat("$#,#0.00"));
		// general money style
		CellStyle moneyStyle = newBook.createCellStyle();
		moneyStyle.setDataFormat(df.getFormat("$#,#0.00"));

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

		Cell artistCell1 = row.createCell(0);
		artistCell1.setCellValue("Artist");
		artistCell1.setCellStyle(headerStyle);

		Cell grossSalesCell1 = row.createCell(1);
		grossSalesCell1.setCellValue("Gross Sale");
		grossSalesCell1.setCellStyle(headerStyle);

		Cell salesTaxCell1 = row.createCell(2);
		salesTaxCell1.setCellValue("Sales Tax");
		salesTaxCell1.setCellStyle(headerStyle);

		Cell totalCustomerPaymentCell1 = row.createCell(3);
		totalCustomerPaymentCell1.setCellValue("Total Customer Payment");
		totalCustomerPaymentCell1.setCellStyle(headerStyle);

		Cell adminFeeCell1 = row.createCell(4);
		adminFeeCell1.setCellValue("Admin Fee");
		adminFeeCell1.setCellStyle(headerStyle);

		Cell totalDueToArtistCell1 = row.createCell(5);
		totalDueToArtistCell1.setCellValue("Total Due to Artist");
		totalDueToArtistCell1.setCellStyle(headerStyle);

	}

	public static void exportWorkBook() throws IOException {
		newBook.close();
	}

	public static void generateRow(String artistName, double grandGross, double grandSalesTax, double grandCustomerFee,
			double grandAdminFee, double grandTotalCustomerPay) {
		Row row = sheet1.createRow(temp);

		Cell totalArtistName = row.createCell(0);
		totalArtistName.setCellStyle(boldStyle);
		totalArtistName.setCellValue(artistName + " Total");

		Cell totalGrossTotal = row.createCell(1);
		totalGrossTotal.setCellStyle(boldMoneyStyle);
		totalGrossTotal.setCellValue(grandGross);

		Cell totalSalesTax = row.createCell(2);
		totalSalesTax.setCellStyle(boldMoneyStyle);
		totalSalesTax.setCellValue(grandSalesTax);

		Cell totalCustPayment = row.createCell(3);
		totalCustPayment.setCellStyle(boldMoneyStyle);
		totalCustPayment.setCellValue(grandCustomerFee);

		Cell totalAdminFee = row.createCell(4);
		totalAdminFee.setCellStyle(boldMoneyStyle);
		totalAdminFee.setCellValue(grandAdminFee);

		Cell totalDueToArtist = row.createCell(5);
		totalDueToArtist.setCellStyle(boldMoneyStyle);
		totalDueToArtist.setCellValue(grandTotalCustomerPay);

		temp++;
	}

	public static void generateGrandTotals(String artistName, double grandGross, double grandSalesTax,
			double grandCustomerFee, double grandAdminFee, double grandTotalCustomerPay) {

		Row row = sheet1.createRow(sheet1.getLastRowNum() + 1);

		Cell grandTotalArtistName = row.createCell(0);
		grandTotalArtistName.setCellStyle(boldStyle);
		grandTotalArtistName.setCellValue("Grand Total: ");

		Cell grandTotalGrossTotal = row.createCell(1);
		grandTotalGrossTotal.setCellStyle(boldMoneyStyle);
		grandTotalGrossTotal.setCellValue(grandGross);

		Cell grandTotalSalesTax = row.createCell(2);
		grandTotalSalesTax.setCellStyle(boldMoneyStyle);
		grandTotalSalesTax.setCellValue(grandSalesTax);

		Cell grandTotalCustPayment = row.createCell(3);
		grandTotalCustPayment.setCellStyle(boldMoneyStyle);
		grandTotalCustPayment.setCellValue(grandCustomerPayment);

		Cell grandTotalAdminFee = row.createCell(4);
		grandTotalAdminFee.setCellStyle(boldMoneyStyle);
		grandTotalAdminFee.setCellValue(grandAdminFee);

		Cell grandTotalDueToArtistCell = row.createCell(5);
		grandTotalDueToArtistCell.setCellStyle(boldMoneyStyle);
		grandTotalDueToArtistCell.setCellValue(grandTotalDueToArtist);

	}

	CreateSmallWorkbook() throws IOException {
		createSupports();
	}

	public static void exportBook() throws IOException {
		try {
			FileOutputStream fileOut = new FileOutputStream(
					Controller.latestSale + " thru " + Controller.oldestSale + " paymentsSMALL.xlsx");
			newBook.write(fileOut);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
