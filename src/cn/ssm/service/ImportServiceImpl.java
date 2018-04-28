package cn.ssm.service;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.ssm.utils.ReadExcel;



@Service
public class ImportServiceImpl implements ImportService {
	@Autowired(required = true)

//voucherAbstract,debitAccount,creditAccount
	@Override
	public String readExcelFile(MultipartFile file) {
		String result = "";
		// 创建处理EXCEL的类
		ReadExcel readExcel = new ReadExcel();
		// 解析excel，获取上传的事件单
		List<Map<String, Object>> abstractList = readExcel.getExcelInfo(file);
		// 至此已经将excel中的数据转换到list里面了,接下来就可以操作list,可以进行保存到数据库,或者其他操作,
		return result;
		
	}

}