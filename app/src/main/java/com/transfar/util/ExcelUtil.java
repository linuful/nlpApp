package com.transfar.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;



public class ExcelUtil {
    private static Logger log  = LogManager.getLogger(ExcelUtil.class);  
    private final static String xls = "xls";  
    private final static String xlsx = "xlsx";  
     
    /**
     * 
     * @param excelFileName
     * @return normal excel data <columns<rows> >
     * @throws IOException
     */
    public static List<String[]> readExcel(String excelFileName) throws IOException{    
        Workbook workbook = getWorkBook(excelFileName);
        List<String[]> list = new ArrayList<String[]>();  
        if(workbook != null){  
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){  
                Sheet sheet = workbook.getSheetAt(sheetNum);  
                if(sheet == null){  
                    continue;  
                }  
                int firstRowNum  = sheet.getFirstRowNum();
                int lastRowNum = sheet.getLastRowNum();
                //System.out.println("firstRowNum:"+firstRowNum+",lastRowNum:"+lastRowNum);
                for(int rowNum = firstRowNum;rowNum <= lastRowNum;rowNum++){  
                    Row row = sheet.getRow(rowNum);  
                    if(row == null){  
                        continue;
                    }
                    int firstCellNum = row.getFirstCellNum();
                    int lastCellNum = row.getLastCellNum();
                    String[] cells = new String[row.getPhysicalNumberOfCells()];// values in a row
                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){  
                        Cell cell = row.getCell(cellNum);  
                        cells[cellNum] = getCellValue(cell);  
                    }  
                    list.add(cells);
                }  
            }  
            workbook.close();  
        }  
        return list;  
    }
    
    
    public static PoiMergedResult isMergedRegion(Sheet sheet,int row ,int column) {
	   int sheetMergeCount = sheet.getNumMergedRegions();   
	   for (int i = 0; i < sheetMergeCount; i++) {   
	         CellRangeAddress range = sheet.getMergedRegion(i);   
	         int firstColumn = range.getFirstColumn(); 
	         int lastColumn = range.getLastColumn();   
	         int firstRow = range.getFirstRow();   
	         int lastRow = range.getLastRow();   
	         if(row >= firstRow && row <= lastRow){ 
	             if(column >= firstColumn && column <= lastColumn){ 
	            return new PoiMergedResult(true,firstRow,lastRow,firstColumn,lastColumn);   
	             } 
	         }
	   } 
	   return new PoiMergedResult(false,0,0,0,0);  
    }

    public static Workbook createWorkBook(String fileName) throws IOException{

    	Workbook workbook = null;
        if(fileName.endsWith(xls)){    //2003  
            workbook = new HSSFWorkbook();  
        }else if(fileName.endsWith(xlsx)){  //2007  
            workbook = new XSSFWorkbook();  
        }
        
		return workbook;
    }
   
    
    public static Workbook getWorkBook(String fileName) {  
    	
    	File file = new File(fileName);    	
        Workbook workbook = null;  
        try {  
            InputStream is = new FileInputStream(file);
            if(fileName.endsWith(xls)){    //2003  
                workbook = new HSSFWorkbook(is);  
            }else if(fileName.endsWith(xlsx)){  //2007  
                workbook = new XSSFWorkbook(is);  
            }
            else{
            	log.error(fileName + " is not excel document!");
            }
        } catch (IOException e) {  
            log.info(e.getMessage());  
        }  
        return workbook;  
    }
    
    public static String getCellValue(Cell cell){  
        String cellValue = "";  
        if(cell == null){  
            return cellValue;  
        }  

        switch (cell.getCellTypeEnum()){  
            case NUMERIC:
            	if(DateUtil.isCellDateFormatted(cell)){//Date
            		SimpleDateFormat sdf = null;
            		if(cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")){//Time
            			sdf = new SimpleDateFormat("HH:mm");
            		}
            		else{//Date
            			sdf = new SimpleDateFormat("yyyy-MM-dd");
            		}
            		Date date = cell.getDateCellValue();
            		cellValue = sdf.format(date);
            	}
            	else if(cell.getCellStyle().getDataFormat()>=BuiltinFormats.FIRST_USER_DEFINED_FORMAT_INDEX ){//user define
            		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            		Double v = cell.getNumericCellValue();
            		Date date = DateUtil.getJavaDate(v);
            		cellValue = sdf.format(date);
            	}
            	else{
            		/*
            		 * still 1.1 -> 1.1000000000000001 ..
            		 * */
            		cell.setCellType(CellType.STRING);
            		cellValue = String.valueOf(cell.getStringCellValue());
            	}
                break;  
            case STRING:
                cellValue = String.valueOf(cell.getStringCellValue());  
                break;  
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());  
                break;  
            case FORMULA:
                //cellValue = String.valueOf(cell.getCellFormula()); //get formula

            	try {//get value
            		cellValue = String.valueOf(cell.getNumericCellValue());
            	}catch(IllegalStateException i){
            		cellValue = cell.getStringCellValue();
            	}
                break;  
            case BLANK:
            	//System.out.println("BLANK");
                cellValue = "";  
                break;  
            case ERROR:
                cellValue = "illegal characters";  
                break;  
            default:  
                cellValue = "unknow";  
                break;  
        }  
        return cellValue;  
    }  
}
