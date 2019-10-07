package com.pajf.dm.web;

import com.actionsoft.bpms.bo.engine.BO;
import com.actionsoft.bpms.bpmn.engine.model.run.delegate.TaskInstance;
import com.actionsoft.bpms.commons.mvc.view.ActionWeb;
import com.actionsoft.bpms.commons.mvc.view.ResponseObject;
import com.actionsoft.bpms.server.UserContext;
import com.actionsoft.sdk.local.SDK;
import com.pajf.dm.enums.BoEnum;

import java.util.Arrays;
import java.util.List;

/**
 * foreign service web
 * @author louie
 * @date created in 2019-10-7 17:00
 */
public class ForeignServiceWeb extends ActionWeb {
	public ForeignServiceWeb () {

	}
	public ForeignServiceWeb (UserContext userContext) {
		super(userContext);
	}

	public String takenTask(String process, String rowIds) {
		String userId = getContext().getUID();
		String[] processInsIds = process.split(",");
		String[] rowId = rowIds.split(",");

		// 待提取流程提交，下一节点办理人为当前操作者
		for (int i = 0; i < processInsIds.length; i++) {
			String taskId = SDK.getTaskQueryAPI().processInstId(processInsIds[i]).list().get(0).getId();
			List<TaskInstance> newTaskInst = SDK.getTaskAPI().completeTask(taskId, userId).fetchActiveTasks();
			newTaskInst.forEach(task -> SDK.getTaskAPI().claimTask(task.getId(), userId));

			// 更新DW表数据状态
			BO beUpdateBo = new BO();
			beUpdateBo.set("ID", rowId[i]);
			beUpdateBo.set("CLAIM_STATUS", "已认领");
			beUpdateBo.set("WF_STATUS", "需求确认");
			beUpdateBo.set("PTASKID", newTaskInst.get(0).getId());

			SDK.getBOAPI().update(BoEnum.FOREIGN_DW.getValue(), beUpdateBo);
		}
		return ResponseObject.newOkResponse().toString();
	}
}
