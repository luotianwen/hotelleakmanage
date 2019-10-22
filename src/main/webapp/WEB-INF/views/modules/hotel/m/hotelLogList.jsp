<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>酒店日志管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            $(":checkbox[name='orderIds']").click(function () {
                $("#checkId").attr('checked', $(":checkbox[name='orderIds']").length == $(":checkbox[name='orderIds']:checked").length);
            });
            $(":checkbox[name='checkId']").click(function () {
                checkAll(this, 'orderIds');
            });
		});
        function checkAll(e, itemName){
            var flag=e.checked;
            $(":checkbox[name="+itemName+"]").attr('checked',flag);
        }
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function checkDeliver() {
            var num = $("input[type='checkbox']:checked").length;
            if (num == 0) {
                top.$.jBox.alert("请选择你要批量删除的数据");
            } else {
                confirmx('确定要批量删除已选中的数据吗？', allDeliver);
            }

        }

        function allDeliver() {
            var ids = [];
            $("input[name='orderIds']:checked").each(function () {
                ids.push($(this).val());
            });
            var delIds = ids.join(",");
            var oldAction = $("#searchForm").attr("action");
            $("#searchForm").attr("action", "${ctx}/hotel/m/hotelLog/allDeliver?ids=" + delIds);
            $("#searchForm").submit();

        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hotel/m/hotelLog/">酒店日志列表</a></li>
		<shiro:hasPermission name="hotel:m:hotelLog:edit"><li><a href="${ctx}/hotel/m/hotelLog/form">酒店日志添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hotelLog" action="${ctx}/hotel/m/hotelLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">

			<li><label>设备名称：</label>
				<form:input path="d.name" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>处理状态：</label>
				<form:select path="state" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('h_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>

			<li><label>处理人名称：</label>
				<form:input path="updateName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('h_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>进入时间：</label>
				<input name="beginStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${hotelLog.beginStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="endStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${hotelLog.endStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>离开时间：</label>
				<input name="beginOutDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${hotelLog.beginOutDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="endOutDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${hotelLog.endOutDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>房间号：</label>
				<form:input path="num" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<a href="#" onclick="checkDeliver()" class="btn btn-primary">批量删除</a></li>

			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input type=checkbox name="checkId" id="checkId"></th>
				<th>酒店</th>
				<th>设备</th>
				<th>图像</th>
				<th>内容</th>
				<th>处理状态</th>
				<th>更新时间</th>
				<th>处理人名称</th>
				<th>类型</th>
				<th>进入时间</th>
				<th>离开时间</th>
				<th>房间号</th>
				<shiro:hasPermission name="hotel:m:hotelLog:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hotelLog">
			<tr>
				<td><input type="checkbox" name="orderIds" value="${hotelLog.id}"/></td>
				<td><a href="${ctx}/hotel/m/hotelLog/form?id=${hotelLog.id}">
						${hotelLog.h.name}
				</a></td>
				<td>
						${hotelLog.d.name}
				</td>
				<td>
					<img   src="${hotelLog.pto}" width="80" >
				</td>
				<td>
						${hotelLog.content}
				</td>
				<td>
						${fns:getDictLabel(hotelLog.state, 'h_state', '')}
				</td>
				<td>
					<fmt:formatDate value="${hotelLog.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>

				<td>
						${hotelLog.updateName}
				</td>
				<td>
						${fns:getDictLabel(hotelLog.type, 'h_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${hotelLog.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${hotelLog.outDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
						${hotelLog.num}
				</td>
				<shiro:hasPermission name="hotel:m:hotelLog:edit"><td>
    				<a href="${ctx}/hotel/m/hotelLog/form?id=${hotelLog.id}">修改</a>
					<a href="${ctx}/hotel/m/hotelLog/delete?id=${hotelLog.id}" onclick="return confirmx('确认要删除该酒店日志吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>