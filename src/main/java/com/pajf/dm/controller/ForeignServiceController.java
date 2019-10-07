package com.pajf.dm.controller;

import com.actionsoft.bpms.api.common.ApiResponse;
import com.actionsoft.bpms.server.RequestParams;
import com.actionsoft.bpms.server.UserContext;
import com.actionsoft.bpms.server.bind.annotation.Controller;
import com.actionsoft.bpms.server.bind.annotation.Mapping;
import com.alibaba.fastjson.JSON;
import com.pajf.dm.web.ForeignServiceWeb;

/**
 * foreign service controller
 * @author louie
 * @date created in 2019-10-7 16:45
 */
@Controller
public class ForeignServiceController {

	@Mapping(value = "com.awspaas.user.apps.pajf.dm.taken_task")
	public String takenProcessTask(UserContext userContext, String process, String rowIds) {
		return new ForeignServiceWeb(userContext).takenTask(process, rowIds);
	}

}
