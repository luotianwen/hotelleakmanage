<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>酒店管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=IWivvt87wH8a5iMMjTGZGlSIApn19e87"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/hotel/hotel/hotel/">酒店管理列表</a></li>
		<li class="active"><a href="${ctx}/hotel/hotel/hotel/form?id=${hotel.id}">酒店管理<shiro:hasPermission name="hotel:hotel:hotel:edit">${not empty hotel.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="hotel:hotel:hotel:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="hotel" action="${ctx}/hotel/hotel/hotel/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
			</div>
		</div>
		<div    class="control-group" >
			<label class="control-label">左键点击选择地点：</label>
			<div  class="controls"  id="allmap" style="width: 600px;height:400px;">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经度：</label>
			<div class="controls">
				<form:input path="lat" htmlEscape="false" maxlength="255" class="input-xlarge  required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">纬度：</label>
			<div class="controls">
				<form:input path="lng" htmlEscape="false" maxlength="255" class="input-xlarge required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">省：</label>
			<div class="controls">
				<sys:treeselect id="province" name="province.id" value="${hotel.province.id}" labelName="province.name" labelValue="${hotel.province.name}"
					title="区域" url="/sys/area/treeData" cssClass="" allowClear="true"  />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市：</label>
			<div class="controls">
				<sys:treeselect id="city" name="city.id" value="${hotel.city.id}" labelName="city.name" labelValue="${hotel.city.name}"
					title="区域" url="/sys/area/treeData" cssClass="" allowClear="true"  />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">区：</label>
			<div class="controls">
				<sys:treeselect id="area" name="area.id" value="${hotel.area.id}" labelName="area.name" labelValue="${hotel.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">详细地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">账号：</label>
			<div class="controls">
				<form:input path="account" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码：</label>
			<div class="controls">
				<form:input path="pass" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">负责人：</label>
			<div class="controls">
				<form:input path="leader" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同年限：</label>
			<div class="controls">
				<form:input path="contract" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">缴费金额：</label>
			<div class="controls">
				<form:input path="money" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开通时间：</label>
			<div class="controls">
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required "
					value="<fmt:formatDate value="${hotel.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开通状态：</label>
			<div class="controls">
				<form:select path="state" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电子合同：</label>
			<div class="controls">
				<form:input path="eContract" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">访客离开时间：</label>
			<div class="controls">
				<input name="outTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${hotel.outTime}" pattern="HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提前提醒时间：</label>
			<div class="controls">
				<form:input path="remind" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<form hidden="user.id"/>

		<div class="form-actions">
			<shiro:hasPermission name="hotel:hotel:hotel:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script>
        var map = new BMap.Map("allmap", {
            enableMapClick : false
        }); // 创建Map实例
        var markersArray = [];

        var longitude=116.404
        var latitude=39.915
        <c:if test="${not empty hotel.lng}"> longitude=${hotel.lng};   latitude=${hotel.lat}; </c:if>
        <c:if test="${  empty hotel.lng}">  $("#lng").val(longitude);$("#lat").val(latitude); </c:if>
        var point = new BMap.Point(longitude, latitude);
        map.centerAndZoom(point, 15); // 中心点
        map.addEventListener("click", showInfo);
        map.addControl(new BMap.MapTypeControl());
        map.enableScrollWheelZoom(true);
        addMarker(point);

        //清除标识
        function clearOverlays() {
            if (markersArray) {
                for (i in markersArray) {
                    map.removeOverlay(markersArray[i])
                }
            }
        }
        //地图上标注
        function addMarker(point) {
            var marker = new BMap.Marker(point);
            markersArray.push(marker);
            clearOverlays();
            map.addOverlay(marker);
        }
        //点击地图时间处理
        function showInfo(e) {
            $("#lng").val(e.point.lng);
            $("#lat").val(e.point.lat);

            addMarker(e.point);
        }

	</script>
</body>
</html>