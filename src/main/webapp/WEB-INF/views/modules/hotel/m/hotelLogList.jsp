<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>酒店日志管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
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
			<li><label>酒店id：</label>
				<form:input path="h.id" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>设备id：</label>
				<form:input path="d.id" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>处理状态：</label>
				<form:input path="state" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li><label>处理人：</label>
				<form:input path="updateBy.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>处理人名称：</label>
				<form:input path="updateName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>类型：</label>
				<form:input path="type" htmlEscape="false" maxlength="255" class="input-medium"/>
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
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>酒店id</th>
				<th>设备id</th>
				<th>处理状态</th>
				<th>更新时间</th>
				<th>处理人</th>
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
				<td><a href="${ctx}/hotel/m/hotelLog/form?id=${hotelLog.id}">
					${hotelLog.h.id}
				</a></td>
				<td>
					${hotelLog.d.id}
				</td>
				<td>
					${hotelLog.state}
				</td>
				<td>
					<fmt:formatDate value="${hotelLog.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hotelLog.updateBy.id}
				</td>
				<td>
					${hotelLog.updateName}
				</td>
				<td>
					${hotelLog.type}
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