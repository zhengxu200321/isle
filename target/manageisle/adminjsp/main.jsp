<%--
  Created by IntelliJ IDEA.
  User: zhengxu
  Date: 2020-05-16
  Time: 21:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <base href=" <%=basePath%>">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>JSD 无规则玩家后台</title>
    <link rel="stylesheet" href="dist/css/layui.css" id="layui">
    <link rel="stylesheet" href="dist/css/theme/default.css" id="theme">
    <link rel="stylesheet" href="dist/css/kitadmin.css" id="kitadmin">
    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>

</head>

<body class="layui-layout-body kit-theme-default">
<div class="layui-layout layui-layout-admin">
    <!-- header -->
    <div class="layui-header">
        <div class="layui-logo">
            <div class="layui-logo-toggle" kit-toggle="side" data-toggle="on">
                <i class="layui-icon">&#xe65a;</i>
            </div>
            <div class="layui-logo-brand">
                <a href="#/">JSD-[巨兽岛}</a>
            </div>
        </div>
        <div class="layui-layout-left">
            <!-- <div class="kit-search">
              <form action="/">
                <input type="text" name="keyword" class="kit-search-input" placeholder="关键字..." />
                <button class="kit-search-btn" title="搜索" type="submit">
                  <i class="layui-icon">&#xe615;</i>
                </button>
              </form>
            </div> -->
        </div>
        <div class="layui-layout-right">
            <ul class="kit-nav" lay-filter="header_right">
                <li class="kit-item" kit-target="help">
                    <a href="javascript:;">
                        <i class="layui-icon">&#xe607;</i>
                        <span>帮助</span>
                    </a>
                </li>
                <li class="kit-item" id="">
                    <a href="steam://connect/103.107.190.86:39999">
                        <i class="layui-icon">&#xe60e;</i>
                        <span>进入游戏</span>
                    </a>
                </li>
                <li class="kit-item" id="cc">
                    <a href="javascript:;">
                        <i class="layui-icon">&#xe64c;</i>
                        <span>编辑信息</span>
                    </a>
                </li>
                <li class="kit-item">
                    <a href="javascript:;">
              <span>
                <img id = "touxiang1" src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590088773109&di=eb330d15a26dc660271444320366fe96&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F01%2F35%2F88%2F67573bfa8b3656f.jpg" class="layui-nav-img">
                  <font id = 'user_namess'>没有</font>
              </span>
                    </a>
                    <ul class="kit-nav-child kit-nav-right">
                        <li class="kit-item">
                            <a href="#/user/my">
                                <i class="layui-icon">&#xe612;</i>
                                <span>个人中心</span>
                            </a>
                        </li>
                        <li class="kit-item" kit-target="setting">
                            <a href="javascript:;">
                                <i class="layui-icon">&#xe614;</i>
                                <span>设置</span>
                            </a>
                        </li>
                        <li class="kit-nav-line"></li>
                        <li class="kit-item">
                            <a href="loginout">
                                <i class="layui-icon">&#x1006;</i>
                                <span>注销</span>
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
    <!-- silds -->
    <div class="layui-side" kit-side="true">
        <div class="layui-side-scroll">
            <div id="menu-box">
                <ul class="kit-menu">
                    <li class="kit-menu-item">
                        <a href="#/">
                            <i class="layui-icon"></i>
                            <span>首页</span>
                        </a>
                    </li>
                    <li class="kit-menu-item layui-show">
                        <a href="javascript:;">
                            <i class="layui-icon"></i>
                            <span>个人中心</span>
                        </a>
                        <ul class="kit-menu-child layui-anim layui-anim-upbit">
                            <li class="kit-menu-item">
                                <a href="#/user/edit">
                                    <i class="layui-icon">&#xe612;</i>
                                    <span>信息修改</span>
                                </a>
                            </li>
                        </ul>
                        <ul class="kit-menu-child layui-anim layui-anim-upbit">
                            <li class="kit-menu-item">
                                <a href="#/user/make_up">
                                    <i class="layui-icon"></i>
                                    <span>我的补偿</span>
                                </a>
                            </li>
                        </ul>
                        <ul class="kit-menu-child layui-anim layui-anim-upbit">
                            <li class="kit-menu-item">
                                <a href="#/user/currendDargonInfo">
                                    <i class="layui-icon"></i>
                                    <span>当前龙信息</span>
                                </a>
                            </li>
                        </ul>

                        <ul class="kit-menu-child layui-anim layui-anim-upbit">
                            <li class="kit-menu-item">
                                <a href="#/user/pointlogs">
                                    <i class="layui-icon"></i>
                                    <span>我的积分明细</span>
                                </a>
                            </li>
                        </ul>
                        <ul class="kit-menu-child layui-anim layui-anim-upbit">
                            <li class="kit-menu-item">
                                <a href="#/user/kucun">
                                    <i class="layui-icon"></i>
                                    <span>我的库存</span>
                                </a>
                            </li>
                        </ul>
                        <ul class="kit-menu-child layui-anim layui-anim-upbit">
                            <li class="kit-menu-item">
                                <a href="#/user/cundanglog">
                                    <i class="layui-icon"></i>
                                    <span>我的存档记录</span>
                                </a>
                            </li>
                        </ul>
                        <ul class="kit-menu-child layui-anim layui-anim-upbit">
                            <li class="kit-menu-item">
                                <a href="steam://connect/103.107.190.86:39999">
                                    <i class="layui-icon"></i>
                                    <span>进入游戏</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="kit-menu-item">
                        <a href="javascript:;">
                            <i class="layui-icon"></i>
                            <span>业务大厅</span>
                        </a>
                        <ul class="kit-menu-child layui-anim layui-anim-upbit">
                            <li class="kit-menu-item">
                                <a href="#/user/currendDargonInfo">
                                    <i class="layui-icon"></i>
                                    <span>自助存龙</span>
                                </a>
                            </li>
                            <li class="kit-menu-item">
                                <a href="#/user/kucun">
                                    <i class="layui-icon"></i>
                                    <span>自助取龙</span>
                                </a>
                            </li>
                            <li class="kit-menu-item">
                                <a href="#/user/store">
                                    <i class="layui-icon"></i>
                                    <span>龙的商店</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="kit-menu-item">
                        <a href="loginout">
                            <i class="layui-icon layui-icon-logout"></i>
                            <span>注销</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <!-- main -->
    <div class="layui-body" kit-body="true">
        <router-view></router-view>
    </div>
    <!-- footer -->
    <div class="layui-footer" kit-footer="true">
        2020 © 作者邮箱(同微信)17631319505@163.com
        <div style="width:400px; height:400px;" class="load-container load1">
            <div class="loader">Loading...</div>
        </div>
    </div>
</div>
<script src="dist/polyfill.min.js"></script>
<script src="dist/layui.js"></script>
<script src="adminjsp/kitadmin.js"></script>
<script src="dist/mockjs-config.js"></script>

<script src="https://cdn.bootcss.com/echarts/4.1.0.rc2/echarts.min.js"></script>
<script>layui.use("admin");

window.onload = getuser();
function getuser(){
    $.ajax({
        url: 'users',
        method:'GET',
        timeout : 30000,
        dataType:"json",
        success:function(result){
            $('#touxiang1').attr("src",result.image);
            var layer = layui.layer;
            layer.msg('欢迎回来 '+result.user_name);

        },error:function(dataa){alert("网络开了点小差(主页);可以联系下管理哦...");}
        ,complete:function (XMLHttpRequest,status) {
            if(status=='timeout'){//超时,status还有success,error等值的情况
                ajaxTimeoutTest.abort();
                alert("网络链接超时");
            }
        }
    });
}

</script>
</body>

<script>



    function getuser(){
        $.ajax({
            url: 'users',
            method:'GET'    ,
            timeout : 30000,
            dataType:"json",
            success:function(result){
                $('#point').text(result.point);
                $('#game_name').text(result.user_name);
                $('#user_namess').text(result.user_name);
                $('#steam_id').text(result.steamid);
                $('#touxiang1').attr("src",result.image);
                $('#touxiang2').attr("src",result.image);
                if(result.is_sign==1){
                    $('#sign').attr("class","layui-btn layui-btn-radius layui-btn-disabled");
                    $('#sign').attr("onlick","");
                    $('#sign_but').text("已签到");
                }

                $('#zunguibutton').hide();
                $('#zhizunbutton').hide();

                if(result.is_vip=="1"&&(result.vip_type=="1"||result.vip_type=="2")){
                    $('#zunguibutton').show();
                }
                if(result.is_vip=="1"&&result.vip_type=="3"||result.vip_type=="4"){
                    $('#zhizunbutton').show();
                }

                // $('#sig').attr("src",result.image);
                $('#yesday_save').text(result.yesday_save);
                $('#yesday_out').text(result.yesday_out);
                layui.use('layer', function(){
                    var layer = layui.layer;
                    // layer.msg('欢迎登陆 '+result.user_name);
                });
            },error:function(dataa){alert("网络开了点小差(主页);可以联系下管理哦...");}
            ,complete:function (XMLHttpRequest,status) {
                if(status=='timeout'){//超时,status还有success,error等值的情况
                    ajaxTimeoutTest.abort();
                    alert("网络链接超时");
                }
            }
        });
    }
</script>

</html>