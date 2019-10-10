<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>访客管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var p=false;
		<c:if test="${!empty hotelVisitor.id}">
		   p=true;
		</c:if>
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
				    if(p){
                        loading('正在提交，请稍等...');
                        form.submit();
					}
					else{
                        top.$.jBox.tip('请拍照','warning');
					}

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
		<li><a href="${ctx}/hotel/m/hotelVisitor/">访客管理列表</a></li>
		<li class="active"><a href="${ctx}/hotel/m/hotelVisitor/form?id=${hotelVisitor.id}">访客管理<shiro:hasPermission name="hotel:m:hotelVisitor:edit">${not empty hotelVisitor.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="hotel:m:hotelVisitor:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="hotelVisitor" action="${ctx}/hotel/m/hotelVisitor/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="h.id"/>
		<sys:message content="${message}"/>		

		<div class="control-group">
			<label class="control-label">房间号：</label>
			<div class="controls">
				<form:input path="num" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">照片：</label>
			<div class="controls">
				<form:hidden path="phos"/>
				<video id="video" width="480" height="320" controls>
				</video><button id="capture" type="button">拍照</button>
				<div style="display: none"><canvas id="canvas" width="480" height="320" ></canvas></div>


			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<img id="imgs" src="${hotelVisitor.phos}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">离开时间：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate  required"
					value="<fmt:formatDate value="${hotelVisitor.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进来时间：</label>
			<div class="controls">
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${hotelVisitor.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否离开：</label>
			<div class="controls">
				<form:select path="state" class="input-xlarge required ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="hotel:m:hotelVisitor:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script>
        //访问用户媒体设备的兼容方法
        function getUserMedia(constraints, success, error) {
            if (navigator.mediaDevices.getUserMedia) {
                //最新的标准API
                navigator.mediaDevices.getUserMedia(constraints).then(success).catch(error);
            } else if (navigator.webkitGetUserMedia) {
                //webkit核心浏览器
                navigator.webkitGetUserMedia(constraints,success, error)
            } else if (navigator.mozGetUserMedia) {
                //firfox浏览器
                navigator.mozGetUserMedia(constraints, success, error);
            } else if (navigator.getUserMedia) {
                //旧版API
                navigator.getUserMedia(constraints, success, error);
            }
        }

        let video = document.getElementById('video');
        let canvas = document.getElementById('canvas');
        let context = canvas.getContext('2d');

        function success(stream) {
            //兼容webkit核心浏览器
            let CompatibleURL = window.URL || window.webkitURL;
            //将视频流设置为video元素的源
            console.log(stream);

            //video.src = CompatibleURL.createObjectURL(stream);
            video.srcObject = stream;
            video.play();
        }

        function error(error) {
            console.log(`访问用户媒体设备失败${error.name}, ${error.message}`);
        }

        if (navigator.mediaDevices.getUserMedia || navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia) {
            //调用用户媒体设备, 访问摄像头
            getUserMedia({video : {width: 480, height: 320}}, success, error);
        } else {
            alert('不支持访问用户媒体');
        }

        document.getElementById('capture').addEventListener('click', function () {

            context.drawImage(video, 0, 0, 480, 320);
            var url = canvas.toDataURL('image/png');
            var base64Data = url.substr(22);
            $("#phos").val(url);
            p=true;
            document.getElementById('imgs').src = url;
        })
	</script>
</body>
</html>