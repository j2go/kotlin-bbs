<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>用户主页</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <#include "../common/link.ftl"/>
</head>
<body style="margin-top: 65px;">

<#include "../common/header.ftl"/>

<div class="fly-home fly-panel" style="background-image: url();">
    <img src="${Session.user.avatorUrl}" alt="${Session.user.name}">
    <i class="iconfont icon-renzheng" title="Fly社区认证"></i>
    <h1>
        ${Session.user.name}
        <i class="iconfont icon-nan"></i>
        <!-- <i class="iconfont icon-nv"></i>  -->
        <i class="layui-badge fly-badge-vip">VIP${Session.user.level}</i>

            <#if isAdmin><span style="color:#c00;">（管理员）</span></#if>
            <!--
            <span style="color:#5FB878;">（社区之光）</span>
            <span>（该号已被封）</span>
            -->
    </h1>

    <p style="padding: 10px 0; color: #5FB878;">认证信息：无</p>

    <p class="fly-home-info">
        <i class="iconfont icon-kiss" title="飞吻"></i><span style="color: #FF7200;">${Session.user.experience} 飞吻</span>
        <i class="iconfont icon-shijian"></i><span>${Session.user.createTime!} 加入</span>
        <i class="iconfont icon-chengshi"></i><span>${Session.user.city!}</span>
    </p>

    <p class="fly-home-sign">${Session.user.sign}</p>

<#--
  <div class="fly-sns" data-user="">
    <a href="javascript:;" class="layui-btn layui-btn-primary fly-imActive" data-type="addFriend">加为好友</a>
    <a href="javascript:;" class="layui-btn layui-btn-normal fly-imActive" data-type="chat">发起会话</a>
  </div>
-->

</div>

<div class="layui-container">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md6 fly-home-jie">
            <div class="fly-panel">
                <h3 class="fly-panel-title">${Session.user.name} 最近的提问</h3>
                <ul class="jie-row">
                    <#if topics?? && (topics?size > 0) >
                        <#list topics as topic>
                        <li>
                            <#if topic.nice><span class="fly-jing">精</span></#if>
                            <a href="/topic/${topic.id}" class="jie-title"> ${topic.title}</a>
                            <i>${topic.createTime}</i>
                            <em class="layui-hide-xs">${topic.readNum}阅/${topic.replyNum}答</em>
                        </li>
                        </#list>
                    <#else>
                        <div class="fly-none" style="min-height: 50px; padding:30px 0; height:auto;"><i style="font-size:14px;">没有发表任何求解</i></div>
                    </#if>
                </ul>
            </div>
        </div>

        <div class="layui-col-md6 fly-home-da">
            <div class="fly-panel">
                <h3 class="fly-panel-title">${Session.user.name} 最近的回答</h3>
                <ul class="home-jieda">
                    <#if replies?? && (replies?size > 0) >
                        <#list replies as reply>
                        <li>
                            <p>
                                <span>${reply.time}</span>
                                ${reply.content}
                            </p>
                            <div class="home-dacontent">
                                ${reply.content}
                            </div>
                        </li>
                        </#list>
                    <#else>
                    <div class="fly-none" style="min-height: 50px; padding:30px 0; height:auto;"><span>没有回答任何问题</span></div>
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
    }).use(['fly', 'face'], function() {
        var $ = layui.$, fly = layui.fly;

        $('.home-dacontent').each(function () {
            var othis = $(this), html = othis.html();
            othis.html(fly.content(html));
        });
    });
</script>

</body>
</html>