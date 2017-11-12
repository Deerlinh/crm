<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛软件CRM-资料</title>
    <!-- Tell the browser to be responsive to screen width -->
    <%@include file="../include/css.jsp"%>
    <style>
        .td_title {
            font-weight: bold;
        }
    </style>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../include/header.jsp"%>
    <!-- =============================================== -->

    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="customer_my"/>
    </jsp:include>
    

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">客户资料</h3>
                    <div class="box-tools">
                        <a href="/customer/my"><button class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</button></a>
                        <a href="/customer/edit/${Client.id}"><button class="btn bg-purple btn-sm"><i class="fa fa-pencil"></i> 编辑</button></a>
                        <button id="tranBtn" class="btn bg-orange btn-sm"><i class="fa fa-exchange"></i> 转交他人</button>
                        <button id="publicBtn" class="btn bg-maroon btn-sm"><i class="fa fa-recycle"></i> 放入公海</button>
                         <button id="deleteBtn" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除</button>
                    </div>
                </div>

                <div class="box-body no-padding">

                    <table class="table">




                        <tr >

                            <td class="td_title"></td>
                            <td>${Client.custName}</td>
                            <td class="td_title">职位</td>
                            <td>${Client.jobTitle}</td>
                            <td class="td_title">联系电话</td>
                            <td>${Client.mobile}</td>
                        </tr>
                        <tr>
                            <td class="td_title">所属行业</td>
                            <td>${Client.source}</td>
                            <td class="td_title">客户来源</td>
                            <td>${Client.trade}</td>
                            <td class="td_title">级别</td>
                            <td>${Client.level}</td>
                        </tr>
                        <tr>
                            <td class="td_title">地址</td>
                            <td colspan="5">${Client.address}</td>
                        </tr>
                        <tr>
                            <td class="td_title">备注</td>
                            <td colspan="5">${Client.reminder}</td>
                        </tr>

                    </table>
                </div>
                <div class="box-footer">
                    <span style="color: #ccc" class="pull-right">
                        创建日期： <span title="<fmt:formatDate value="${Client.createTime}"/>"><fmt:formatDate value="${Client.createTime}" pattern="MM月dd日"></fmt:formatDate></span> &nbsp;&nbsp;&nbsp;&nbsp;
                        最后修改日期：<span title="<fmt:formatDate value="${Client.updateTime}"/>"><fmt:formatDate value="${Client.updateTime}" pattern="MM月dd日"></fmt:formatDate></span></span>
                </div>

            </div>

            <div class="row">
                <div class="col-md-8">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">跟进记录</h3>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">日程安排</h3>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">相关资料</h3>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- 底部 -->
    <footer class="main-footer">
        <div class="pull-right hidden-xs">
            <b>Version</b> 1.0
        </div>
        <strong>Copyright &copy; 2010 -2017 <a href="http://almsaeedstudio.com">凯盛软件</a>.</strong> All rights
        reserved.
    </footer>

</div>

<!-- ./wrapper -->
<%--用户选择对话框（转交他人）--%>
<div class="modal fade" id="chooseUserModel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">请选择转入账号</h4>
            </div>
            <div class="modal-body">
                <select id="userSelect" class="form-control">
                    <c:forEach items="${accountList}" var="account">
                        <c:if test="${account.id != Client.accountId}">
                            <option value="${account.id}">${account.userName} (${account.mobile})</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveTranBtn">确定</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->



<%@include file="../include/js.jsp"%>
<script src="/static/plugins/layer/layer.js" ></script>



<script>
    $(function () {
        var customerId=${Client.id}
        $("#deleteBtn").click(function () {
            layer.confirm("确定要删除吗？",function (index) {
                layer.close(index);
                window.location.href= "/customer/my/"+customerId+"/delete";
            })
        });

        $("#publicBtn").click(function () {
            layer.confirm("你确定要把客户放入公海吗？",function (index) {
                layer.close(index);
                window.location.href="/customer/my/"+customerId+"/public";
            });

    });
        
        $("#tranBtn").click(function () {
            $("#chooseUserModel").modal({
                show:true,
                backdrop:'static'

            });
        });
        $("#saveTranBtn").click(function () {
            var  toAccountId=$("#userSelect").val();
            var toAccountName=$("#userSelect option:selected").text();
            layer.confirm("确定要把客户转交给"+toAccountName+"吗？",function (index) {
                layer.close(index);
                window.location.href ="/customer/my/"+customerId+"/tran/"+toAccountId;
            })
        })

    })
    
    
</script>
</body>
</html>
