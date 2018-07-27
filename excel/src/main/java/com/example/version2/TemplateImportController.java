package com.example.version2;

import com.google.zxing.Result;
import com.psms.utils.DateUnity;
import com.psms.utils.Json;
import com.psms.utils.JwtHelper;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
@RestController
public class TemplateImportController {

    /**
     * Excel模板下载   通过URL
     * @param param
     * @param request
     * @param response
     * @throws Exception
     */
	@RequestMapping(value = "/templateImport", method = GET)
	public void templateImport(@RequestBody String param, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Result result = new Result();
		String tokenId = request.getHeader("token");
		String resultStr = JwtHelper.verifyToken(tokenId);
		JSONObject resultJson = JSONObject.fromObject(resultStr);
		if ("9".equals(resultJson.get("code"))) {
			result = new Result(false, 900, resultJson.get("msg").toString(), null, 0);
		}
		JSONObject jsonobject = JSONObject.fromObject(param);
		String templateType = jsonobject.getString("templateType");
		StringBuffer fileName = new StringBuffer("template");
		fileName.append(DateUnity.format(new Date(), DateUnity.YYYYMMDDHHMMSSSSS));
		fileName.append(".xls");
		String sheetName = "模板";
		List<String> list = getTable(templateType);
		String[] headArr =list.toArray(new String[list.size()]);
		String[] propertyNames = null;
		try {
			ExportUtils.exportView(response, "xls", fileName.toString(), sheetName, list, headArr,
					propertyNames);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Json.toJson(result, response);
	}
	public List<String> getTable(String temaplateType){
        List<String> list = new ArrayList<String>();

        if (temaplateType.equals("1")) {
            //students表
            list.add("姓名");
            list.add("性别");
            list.add("身份证号");
            list.add("民族");
            list.add("政治面貌");
            list.add("单位");
            list.add("职务");
            list.add("学历");
            list.add("电话");
            list.add("邮箱");
        } else if (temaplateType.equals("2")) {
            //teacher表
            list.add("姓名");
            list.add("性别");
            list.add("身份证号");
            list.add("电话");
            list.add("学历");
        } else if (temaplateType.equals("3")) {
            //course表
            list.add("课程名称");
            list.add("学时");
            list.add("简介");
        } else if (temaplateType.equals("4")) {
            //classroom表
            list.add("教室编码");
            list.add("教室名称");
            list.add("座位数");
            list.add("地址");
        } else if (temaplateType.equals("5")) {
            //room表
            list.add("房间号");
            list.add("地址");
            list.add("床位数");
        }

        return list;
    }
}
