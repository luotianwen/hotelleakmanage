<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>设备管理管理</title>
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
		<li class="active"><a href="${ctx}/hotel/device/hotelDevice/">设备管理列表</a></li>
		<shiro:hasPermission name="hotel:device:hotelDevice:edit"><li><a href="${ctx}/hotel/device/hotelDevice/form">设备管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hotelDevice" action="${ctx}/hotel/device/hotelDevice/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>开通状态：</label>
				<form:input path="state" htmlEscape="false" maxlength="2" class="input-medium"/>
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
				<th>序号</th>
				<th>安装位置</th>
				<th>ip地址</th>
				<th>开通状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="hotel:device:hotelDevice:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hotelDevice">
			<tr>
				<td><a href="${ctx}/hotel/device/hotelDevice/form?id=${hotelDevice.id}">
					${hotelDevice.name}
				</a></td>
				<td>
					${hotelDevice.num}
				</td>
				<td>
					${hotelDevice.address}
				</td>
				<td>
					${hotelDevice.ip}
				</td>
				<td>
					${hotelDevice.state}
				</td>
				<td>
					<fmt:formatDate value="${hotelDevice.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hotelDevice.remarks}
				</td>
				<shiro:hasPermission name="hotel:device:hotelDevice:edit"><td>
    				<a href="${ctx}/hotel/device/hotelDevice/form?id=${hotelDevice.id}">修改</a>
					<a href="${ctx}/hotel/device/hotelDevice/delete?id=${hotelDevice.id}" onclick="return confirmx('确认要删除该设备管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>