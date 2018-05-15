package cn.ssm.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

public class CellType {
	
	/**
	 * 对Excel的各个单元格的格式进行判断并转换
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		String cellValue = "";
		DecimalFormat df = new DecimalFormat("#.##");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(cell==null){
			return cellValue;
		}
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			cellValue = cell.getRichStringCellValue().getString().trim();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			if(HSSFDateUtil.isCellDateFormatted(cell)){ //判断是否是日期格式
				cellValue = sdf.format(cell.getDateCellValue()).toLowerCase();
			}else{
				cellValue = df.format(cell.getNumericCellValue()).toString();
			}
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			cellValue = cell.getCellFormula();
			break;
		default:
			cellValue = "";
		}
		return cellValue;
	}

}
