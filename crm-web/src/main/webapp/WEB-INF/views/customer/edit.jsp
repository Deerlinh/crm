<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛软件CRM-编辑用户</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <%@include file="../include/css.jsp"%>
    <![endif]-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../include/header.jsp"%>

    <!-- =============================================== -->

    <!-- 左侧菜单栏 -->
    <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
            <!-- 菜单 -->
            <ul class="sidebar-menu">
                <li class="header">系统功能</li>
                <!-- 客户管理 -->
                <li class="treeview active">
                    <a href="#">
                        <i class="fa fa-address-book-o"></i> <span>客户管理</span>
                        <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                    </a>
                    <ul class="treeview-menu">
                        <li class="active"><a href="/customer/my"><i class="fa fa-circle-o"></i> 我的客户</a></li>
                        <li><a href="/customer/public"><i class="fa fa-circle-o"></i> 公海客户</a></li>
                    </ul>
                </li>
                <!-- 工作记录 -->
                <li class="treeview">
                    <a href="#">
                        <i class="fa fa-bars"></i> <span>工作记录</span>
                        <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                    </a>
                    <ul class="treeview-menu">
                        <li><a href="/recode/my"><i class="fa fa-circle-o"></i> 我的记录</a></li>
                        <li><a href="/recode/public"><i class="fa fa-circle-o"></i> 公共记录</a></li>
                    </ul>
                </li>
                <!-- 待办事项 -->
                <li class="treeview">
                    <a href="#">
                        <i class="fa fa-calendar"></i> <span>待办事项</span>
                        <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                    </a>
                    <ul class="treeview-menu">
                        <li><a href="/task"><i class="fa fa-circle-o"></i> 待办列表</a></li>
                        <li><a href=""><i class="fa fa-circle-o"></i> 逾期事项</a></li>
                    </ul>
                </li>
                <!-- 统计报表 -->
                <li class="treeview">
                    <a href="#">
                        <i class="fa fa-pie-chart"></i> <span>统计报表</span>
                        <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                    </a>
                    <ul class="treeview-menu">
                        <li><a href="../../index.html"><i class="fa fa-circle-o"></i> 待办列表</a></li>
                        <li><a href="../../index2.html"><i class="fa fa-circle-o"></i> 逾期事项</a></li>
                    </ul>
                </li>


                <li><a href="../../documentation/index.html"><i class="fa fa-share-alt"></i> <span>公司网盘</span></a></li>
                <li class="header">系统管理</li>
                <!-- 部门员工管理 -->
                <li class="treeview">
                    <a href="#">
                        <i class="fa fa-users"></i> <span>组织架构</span>
                        <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                    </a>
                    <ul class="treeview-menu">
                        <li><a href="../../index.html"><i class="fa fa-circle-o"></i> 部门管理</a></li>
                        <li><a href="../../index2.html"><i class="fa fa-circle-o"></i> 员工管理</a></li>
                    </ul>
                </li>
                <!--<li><a href="#"><i class="fa fa-circle-o text-yellow"></i> <span>Warning</span></a></li>
                <li><a href="#"><i class="fa fa-circle-o text-aqua"></i> <span>Information</span></a></li>-->
            </ul>
        </section>
        <!-- /.sidebar -->
    </aside>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <c:if test="${not empty message}">
            <div class="alert alert-danger">
                    ${message}
            </div>
        </c:if>

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">编辑信息</h3>
                    <div class="box-tools pull-right">
                        <a href="/customer/message/${Client.id}"> <button class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</button></a>
                    </div>
                </div>
                <div class="box-body">
                    <form action="/customer/edit/${Client.id}" method="post"  id="customerFrom">
                        <div class="form-group">
                            <label>姓名</label>
                            <input type="text" name="custName"value="${Client.custName}" class="form-control">
                        </div>
                              <div class="form-group">
                            <label>职位</label>
                            <input type="text" name="jobTitle" value="${Client.jobTitle}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>联系方式</label>
                            <input type="text" name="mobile" value="${Client.mobile}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>地址</label>
                            <input type="text" name="address" VALUE="${Client.address}" class="form-control">
                        </div>
                        <div class="form-group" >
                            <label>所属行业</label>
                            <select name="trade"  class="form-control">
                                <option value=""></option>
                                <option value="互联网">互联网</option>
                                <option value="电力能源">电力能源</option>
                                <option value="其他">其他</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>客户来源</label>
                            <select name="source" class="form-control">
                                <option value=""></option>
                                <option value="DM广告">DM广告</option>
                                <option value="电视媒体">电视媒体</option>
                                <option value="网络媒体">网络媒体</option>
                                <option value="顾客推荐">顾客推荐</option>
                                <option value="主动上门">主动上门</option>
                                <option value="其他">其他</option>
                            </select>
                        </div>
                        <div class="form-group">
                                <label>级别</label>
                                <select   name="level"  class="form-control">
                                    <option value=""></option>
                                    <option value="★">★</option>
                                    <option value="★★">★★</option>
                                    <option value="★★★">★★★</option>
                                    <option value="★★★★">★★★★</option>
                                    <option value="★★★★★">★★★★★</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>备注</label>
                            <input type="text" name="reminder" value="${Client.reminder}" class="form-control">
                        </div>
                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn btn-primary" id="customerBtn">保存</button>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
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

<!-- jQuery 2.2.3 -->
<%@include file="../include/js.jsp"%>
<script src="/static/plugins/validate/jquery.validate.min.js"></script>

<script>
    $(function () {


    $("#customerBtn").click(function () {
        $("#customerFrom").submit();
    });

    $("#customerFrom").validate({
        errorClass:"text-danger",
        errorElement:"span",
        rules: {
            custName: {
                required: true
            },
            mobile: {
                required: true
            }
        },
            messages: {
                custName    : {
                    required: "请输入账号"
                },
                mobile: {
                    required: "请输入手机号码"
                }
            }
        });
    });


</script>
</body>
</html>
