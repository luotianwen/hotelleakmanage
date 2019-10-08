<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>酒店员工管理</title>
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
		<li class="active"><a href="${ctx}/hotel/staff/hotelStaff/">酒店员工列表</a></li>
		<shiro:hasPermission name="hotel:staff:hotelStaff:edit"><li><a href="${ctx}/hotel/staff/hotelStaff/form">酒店员工添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hotelStaff" action="${ctx}/hotel/staff/hotelStaff/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>开通状态：</label>
				<form:input path="state" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li><label>账号：</label>
				<form:input path="account" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>名称</th>
				<th>开通状态</th>
				<th>账号</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="hotel:staff:hotelStaff:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hotelStaff">
			<tr>
				<td><a href="${ctx}/hotel/staff/hotelStaff/form?id=${hotelStaff.id}">
					${hotelStaff.num}
				</a></td>
				<td>
					${hotelStaff.name}
				</td>
				<td>
					${hotelStaff.state}
				</td>
				<td>
					${hotelStaff.account}
				</td>
				<td>
					<fmt:formatDate value="${hotelStaff.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hotelStaff.remarks}
				</td>
				<shiro:hasPermission name="hotel:staff:hotelStaff:edit"><td>
    				<a href="${ctx}/hotel/staff/hotelStaff/form?id=${hotelStaff.id}">修改</a>
					<a href="${ctx}/hotel/staff/hotelStaff/delete?id=${hotelStaff.id}" onclick="return confirmx('确认要删除该酒店员工吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>