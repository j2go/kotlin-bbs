<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>我的消息</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="keywords" content="fly,layui,前端社区">
    <meta name="description" content="Fly社区是模块化前端UI框架Layui的官网社区，致力于为web开发提供强劲动力">
    <link rel="stylesheet" href="/layui/css/layui.css">
    <link rel="stylesheet" href="/css/global.css">
</head>
<body>
<#include "../common/header.ftl"/>

<div class="layui-container fly-marginTop fly-user-main">
    <ul class="layui-nav layui-nav-tree layui-inline" lay-filter="user">
        <li class="layui-nav-item">
            <a href="/user/home">
                <i class="layui-icon">&#xe609;</i>
                我的主页
            </a>
        </li>
        <li class="layui-nav-item">
            <a href="/user/index">
                <i class="layui-icon">&#xe612;</i>
                用户中心
            </a>
        </li>
        <li class="layui-nav-item">
            <a href="/user/setting">
                <i class="layui-icon">&#xe620;</i>
                基本设置
            </a>
        </li>
        <li class="layui-nav-item layui-this">
            <a href="/user/message">
                <i class="layui-icon">&#xe611;</i>
                我的消息
            </a>
        </li>
    </ul>

    <div class="site-tree-mobile layui-hide">
        <i class="layui-icon">&#xe602;</i>
    </div>
    <div class="site-mobile-shade"></div>

    <div class="site-tree-mobile layui-hide">
        <i class="layui-icon">&#xe602;</i>
    </div>
    <div class="site-mobile-shade"></div>

    <div class="fly-panel fly-panel-user" pad20>
        <div class="layui-tab layui-tab-brief" lay-filter="user" id="LAY_msg" style="margin-top: 15px;">
            <button class="layui-btn layui-btn-danger" id="LAY_delallmsg">清空全部消息</button>
            <div id="LAY_minemsg" style="margin-top: 10px;">
                <ul class="mine-msg">
                    <#if messages?? && (messages?size > 0) >
                        <#list messages as msg>
                        <li data-id="${msg.id}">
                            <blockquote class="layui-elem-quote">
                                <#if msg.source == 2>
                                    系统消息：${msg.content}
                                <#else>
                                    ${msg.content}
                                </#if>
                            </blockquote>
                            <p>
                                <span>${msg.createTime}</span>
                                <a href="javascript:;" class="layui-btn layui-btn-small layui-btn-danger fly-delete">删除</a>
                            </p>
                        </li>
                        </#list>
                    <#else>
                    <div class="fly-none">您暂时没有最新消息</div>
                    </#if>

                </ul>
            </div>
        </div>
    </div>

</div>


<#include "../common/footer.ftl"/>

<script src="/layui/layui.js"></script>
<script>
    layui.cache.page = 'user';
    layui.cache.user = {
        username: '游客'
        , uid: -1
        , avatar: '/images/avatar/00.jpg'
        , experience: 83
        , sex: '男'
    };
    layui.config({
        version: "3.0.0"
        , base: '/mods/'
    }).extend({
        fly: 'index'
    }).use('fly');
</script>

</body>
</html>