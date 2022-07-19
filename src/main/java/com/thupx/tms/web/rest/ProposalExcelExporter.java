package com.thupx.tms.web.rest;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.thupx.tms.domain.Proposal;

public class ProposalExcelExporter {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<Proposal> listProposals;

	private void writeHeaderRow() {
		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(14);
		style.setFont(font);

		Cell cell = row.createCell(0);
		cell.setCellValue("ID");
		cell.setCellStyle(style);

		cell = row.createCell(1);
		cell.setCellValue("Khoa");
		cell.setCellStyle(style);

		cell = row.createCell(2);
		cell.setCellValue("Ngày đề nghị");
		cell.setCellStyle(style);

		cell = row.createCell(3);
		cell.setCellValue("Nội dung đề nghị");
		cell.setCellStyle(style);

		cell = row.createCell(4);
		cell.setCellValue("Tổ");
		cell.setCellStyle(style);

		cell = row.createCell(5);
		cell.setCellValue("Người thực hiện");
		cell.setCellStyle(style);

		cell = row.createCell(6);
		cell.setCellValue("Tiến trình hiện tại");
		cell.setCellStyle(style);

		cell = row.createCell(7);
		cell.setCellValue("Ngày cập nhật");
		cell.setCellStyle(style);

		cell = row.createCell(8);
		cell.setCellValue("Ghi chú");
		cell.setCellStyle(style);

		cell = row.createCell(9);
		cell.setCellValue("Tình trạng");
		cell.setCellStyle(style);
	}

	public ProposalExcelExporter(List<Proposal> listProposals) {
		super();
		this.listProposals = listProposals;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Proposals");

	}

	private void writeDataRows() {
		int rowCount = 1;

		for (Proposal proposal : listProposals) {
			Row row = sheet.createRow(rowCount++);

			Cell cell = row.createCell(0);
			cell.setCellValue(proposal.getId());

			cell = row.createCell(1);
			cell.setCellValue(proposal.getHospitalDepartment().getHospitalDepartmentName());

			String dateForXml = proposal.getStartDate().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
			cell = row.createCell(2);
			cell.setCellValue(dateForXml);

			cell = row.createCell(3);
			cell.setCellValue(proposal.getContentProposal());

			cell = row.createCell(4);
			cell.setCellValue(proposal.getUserExtra().getUser().getLastName());

			cell = row.createCell(5);
			cell.setCellValue(proposal.getUserExtra().getUser().getFirstName());

			cell = row.createCell(6);
			cell.setCellValue(proposal.getCurrentProgressName());

			try {
			String dateForXml1 = proposal.getEndDate().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
			cell = row.createCell(7);
			cell.setCellValue(dateForXml1);
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			

			cell = row.createCell(8);
			cell.setCellValue(proposal.getNote());

			cell = row.createCell(9);
			cell.setCellValue(proposal.isStatus());
		}
	}

	public void export(HttpServletResponse response) throws IOException {
		writeHeaderRow();
		writeDataRows();

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}
}
