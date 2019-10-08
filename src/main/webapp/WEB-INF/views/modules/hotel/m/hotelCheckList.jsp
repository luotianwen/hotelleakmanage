<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>入住管理管理</title>
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
		<li class="active"><a href="${ctx}/hotel/m/hotelCheck/">入住管理列表</a></li>
		<shiro:hasPermission name="hotel:m:hotelCheck:edit"><li><a href="${ctx}/hotel/m/hotelCheck/form">入住管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hotelCheck" action="${ctx}/hotel/m/hotelCheck/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>酒店id：</label>
				<form:input path="h.id" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>房间号：</label>
				<form:input path="num" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>离开时间：</label>
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${hotelCheck.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>进来时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${hotelCheck.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
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
				<th>房间号</th>
				<th>名称</th>
				<th>照片</th>
				<th>离开时间</th>
				<th>进来时间</th>
				<th>开通状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="hotel:m:hotelCheck:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hotelCheck">
			<tr>
				<td><a href="${ctx}/hotel/m/hotelCheck/form?id=${hotelCheck.id}">
					${hotelCheck.h.id}
				</a></td>
				<td>
					${hotelCheck.num}
				</td>
				<td>
					${hotelCheck.name}
				</td>
				<td>
					${hotelCheck.phos}
				</td>
				<td>
					<fmt:formatDate value="${hotelCheck.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${hotelCheck.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hotelCheck.state}
				</td>
				<td>
					<fmt:formatDate value="${hotelCheck.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hotelCheck.remarks}
				</td>
				<shiro:hasPermission name="hotel:m:hotelCheck:edit"><td>
    				<a href="${ctx}/hotel/m/hotelCheck/form?id=${hotelCheck.id}">修改</a>
					<a href="${ctx}/hotel/m/hotelCheck/delete?id=${hotelCheck.id}" onclick="return confirmx('确认要删除该入住管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>