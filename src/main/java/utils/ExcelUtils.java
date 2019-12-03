package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ExcelUtils
{
    public static ArrayList<String> getData(String fileName, String headerName, String sheetName, String testCaseName) throws IOException
    {
        ArrayList<java.lang.String> a = new ArrayList<java.lang.String>();
        FileInputStream fis = new FileInputStream(fileName);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        int sheetCount = workbook.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++)
        {
            if (workbook.getSheetName(i).equalsIgnoreCase(sheetName))
            {
                XSSFSheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rows = sheet.iterator();
                Row firstRow = rows.next();
                Iterator<Cell> cell = firstRow.cellIterator();
                int k = 0;
                int column = 0;
                while (cell.hasNext())
                {
                    Cell value = cell.next();
                    if (value.getStringCellValue().equalsIgnoreCase(headerName))
                    {
                        column = k;
                    }
                    k++;
                }
                //System.out.println(column);
                while (rows.hasNext())
                {
                    Row r = rows.next();
                    if (r.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName))
                    {
                        Iterator<Cell> tv = r.cellIterator();
                        while (tv.hasNext())
                        {
                            Cell checkData = tv.next();
                            if(checkData.getCellTypeEnum()== CellType.STRING)
                            {
                                a.add(checkData.getStringCellValue());
                            }
                            else
                            {
                                a.add(NumberToTextConverter.toText(checkData.getNumericCellValue()));
                            }
                        }
                    }
                }
            }

        }
        return a;
    }
}
