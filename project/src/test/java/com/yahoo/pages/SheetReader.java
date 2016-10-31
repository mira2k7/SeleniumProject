package com.yahoo.pages;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public  class SheetReader {
    private static HSSFSheet workSheet;
    private static HSSFWorkbook workBook;

    private HashMap<String, Integer> columnIndexes = new HashMap();

    public SheetReader(String fileName, String sheetName) throws Exception {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(fileName);
            workBook = new HSSFWorkbook(fileInputStream);
            workSheet = workBook.getSheet(sheetName);
            // String RowListCount= getRowIndexForTestName(testName);
            setColumnIndexes();
        } catch (java.io.IOException e) {//e.printStackTrace();
            System.out.print("error" + e);
        }
    }

    public <T> List<T> getData(Class<T> classToFill, String testName) throws IllegalAccessException, InstantiationException {
        //System.out.println("TestName:="+testName);
        List<T> resultObjects = new ArrayList<T>();
        List<Integer> rowIndices = getRowIndexForTestName(testName);
        for (Integer rowIndex : rowIndices) {
            Field[] allFields = classToFill.getDeclaredFields();
            Object obj = classToFill.newInstance();
            for (Field field : allFields) {
                int colIndex = columnIndexes.get(field.getName());
                field.set(obj, workSheet.getRow(rowIndex).getCell(colIndex).getStringCellValue());
            }

            resultObjects.add((T) obj);
            System.out.println("HHH:"+workSheet.getRow(rowIndex).getCell(2).getStringCellValue());

        }
        return resultObjects;
    }

    private List<Integer> getRowIndexForTestName(String testName) {
        Iterator rows = workSheet.rowIterator();
        int rowIndex = 0;
        List rowList = new ArrayList();
        while (rows.hasNext()) {
            HSSFRow row = (HSSFRow) rows.next();
            String testCaseName = row.cellIterator().next().getStringCellValue();
            if (testCaseName.equalsIgnoreCase(testName)) {
                rowList.add(rowIndex);
            }
            rowIndex += 1;
        }
        return rowList;
    }


    private void setColumnIndexes() {
        HSSFRow colHeader = workSheet.getRow(0);
            Iterator<Cell> cellIterator = colHeader.cellIterator();
        int cellIndex = 0;
        while (cellIterator.hasNext()) {
            columnIndexes.put(cellIterator.next().getStringCellValue(), cellIndex++);
        }
    }


   public static void main(String[] args) throws Exception {
        SheetReader sReader = new SheetReader("C:\\selenium\\selenium-learning\\src\\test\\resources\\data1.xls", "homePage");
        sReader.getData(SearchDataObject.class, "testVerifySearchKeywordsAndUrl");

    }
}
   

