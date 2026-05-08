package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	public FileInputStream fi;
	public FileOutputStream fo;
	public XSSFWorkbook wb;
	public XSSFSheet ws;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	String path;
	
	public ExcelUtility(String path) {
		this.path=path;
	}
	
	public int getRowCount(String sheetName) throws IOException{
		fi=new FileInputStream(path);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(sheetName);
		int rowcount=ws.getLastRowNum();
		wb.close();
		fi.close();
		System.out.println(rowcount);
		return rowcount;		
	}
	
	public int getCellCount(String sheetName,int rownum) throws IOException{
		fi=new FileInputStream(path);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(sheetName);
		row=ws.getRow(rownum);
		int cellcount=row.getLastCellNum();
		wb.close();
		fi.close();
		System.out.println(cellcount);
		return cellcount;		
	}
	
	public String getCellData(String sheetName,int rownum,int colnum) throws IOException{
		fi=new FileInputStream(path);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(sheetName);
		row=ws.getRow(rownum);
		cell=row.getCell(colnum);
		
		DataFormatter formatter=new DataFormatter();
		String data;
		try {
			System.out.println(cell);
			data=formatter.formatCellValue(cell);	
		}catch(Exception e){
			data="";
		}
		wb.close();
		fi.close();
		return data;
	}
	
	public void setCellData(String sheetName,int rownum,int colnum,String data) throws IOException{
	
		File xlfile=new File(path);
		if(!xlfile.exists()) // If file not exists the create new one
		{
			wb=new XSSFWorkbook();
			fo=new FileOutputStream(path);
			wb.write(fo);
		}
		
		fi=new FileInputStream(path);
		wb=new XSSFWorkbook(fi);
		
		if(wb.getSheetIndex(sheetName)==-1)//if sheet not exisits then create new
			wb.createSheet(sheetName);
		ws=wb.getSheet(sheetName);
		
		if(ws.getRow(rownum)==null)//if row not exisits then create new
			ws.createRow(rownum);
		row=ws.getRow(rownum);
		
		cell=row.createCell(colnum);
		cell.setCellValue(data);
		fo=new FileOutputStream(path);
		wb.write(fo);
		wb.close();
		fi.close();
		fo.close();
		
	}
}
