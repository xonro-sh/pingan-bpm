package com.pajf.dm.event.zfx;

import com.actionsoft.bpms.bo.engine.BO;
import com.actionsoft.bpms.bpmn.engine.core.delegate.ProcessExecutionContext;
import com.actionsoft.bpms.bpmn.engine.listener.ValueListener;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.ProcessInstance;
import com.actionsoft.bpms.server.UserContext;
import com.actionsoft.sdk.local.SDK;
import com.pajf.dm.enums.BoEnum;
import com.pajf.dm.enums.ProcessEnum;

import java.util.Date;

/**
 *
 * @author louie
 * @date created in 2019-10-5 18:29
 */
public class ForeignServiceDwCommit extends ValueListener {

	@Override
	public String execute(ProcessExecutionContext context) throws Exception {
		BO foreignDwBo = context.getBO(BoEnum.FOREIGN_DW.getValue());
		UserContext userContext = context.getUserContext();
		String userId = context.getUserContext().getUID();

		// 创建外事服务流程并初始化数据
		ProcessInstance foreignProcessInstance = SDK
				.getProcessAPI()
				.createProcessInstance(ProcessEnum.FOREIGN_PROCESS_ID.getValue(), userId, "外事服务流程");

		BO foreignProcessBo = new BO();
		foreignProcessBo.setAll(foreignDwBo.asMap());
		foreignProcessBo.remove("ID");
		foreignProcessBo.set("BINDID", foreignProcessInstance.getId());
		SDK.getBOAPI().create(BoEnum.FOREIGN_PROCESS_MAIN.getValue(), foreignProcessBo, foreignProcessInstance, userContext);
		SDK.getProcessAPI().start(foreignProcessInstance);

		// 提交流程至任务池
		String taskInstanceId = foreignProcessInstance.getStartTaskInstId();
		SDK.getTaskAPI().commitComment(taskInstanceId, userId, "提交", "", true);
		SDK.getTaskAPI().completeTask(taskInstanceId, userId);

		// 更新DW状态值
		foreignDwBo.set("STATUS", 1);
		foreignDwBo.set("TRANS_USER_ID", userId);
		foreignDwBo.set("TRANS_USER_NAME", userContext.getUserName());
		foreignDwBo.set("TASK_ARRIVE_TIME", new Date());
		foreignDwBo.set("BUSINESS_TYPE", foreignDwBo.get("DEMAND_TYPE").toString() + foreignDwBo.get("TRANS_TYPE").toString());
		foreignDwBo.set("ROLE_AUTHOR", "02");
		foreignDwBo.set("PBINDID", foreignProcessInstance.getId());
		foreignDwBo.set("PTASKID", SDK.getTaskQueryAPI().processInstId(foreignProcessInstance.getId()).list().get(0).getId());
		foreignDwBo.set("WF_STATUS", "排队待认领");
		foreignDwBo.set("CLAIM_STATUS", "未认领");

		SDK.getBOAPI().update(BoEnum.FOREIGN_DW.getValue(), foreignDwBo);
		return "success";
	}
}
