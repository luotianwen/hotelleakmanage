<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>酒店管理管理</title>
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
		<li class="active"><a href="${ctx}/hotel/hotel/hotel/">酒店管理列表</a></li>
		<shiro:hasPermission name="hotel:hotel:hotel:edit"><li><a href="${ctx}/hotel/hotel/hotel/form">酒店管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hotel" action="${ctx}/hotel/hotel/hotel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>账号：</label>
				<form:input path="account" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>负责人：</label>
				<form:input path="leader" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>电话：</label>
				<form:input path="phone" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>开通时间：</label>
				<input name="beginStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${hotel.beginStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${hotel.endStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>开通状态：</label>
				<form:select path="state" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>账号</th>
				<th>负责人</th>
				<th>电话</th>
				<th>合同年限</th>
				<th>缴费金额</th>
				<th>开通时间</th>
				<th>开通状态</th>
				<th>更新时间</th>
				<th>访客离开时间</th>
				<th>提前提醒时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="hotel:hotel:hotel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hotel">
			<tr>
				<td><a href="${ctx}/hotel/hotel/hotel/form?id=${hotel.id}">
					${hotel.name}
				</a></td>
				<td>
					${hotel.account}
				</td>
				<td>
					${hotel.leader}
				</td>
				<td>
					${hotel.phone}
				</td>
				<td>
					${hotel.contract}
				</td>
				<td>
					${hotel.money}
				</td>
				<td>
					<fmt:formatDate value="${hotel.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(hotel.state, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${hotel.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${hotel.outTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hotel.remind}
				</td>
				<td>
					${hotel.remarks}
				</td>
				<shiro:hasPermission name="hotel:hotel:hotel:edit"><td>
    				<a href="${ctx}/hotel/hotel/hotel/form?id=${hotel.id}">修改</a>
					<a href="${ctx}/hotel/hotel/hotel/delete?id=${hotel.id}" onclick="return confirmx('确认要删除该酒店管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>