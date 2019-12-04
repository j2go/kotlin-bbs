<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>你好大学 首页</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="keywords" content="fly,layui,前端社区">
    <link rel="stylesheet" href="/layui/css/layui.css">
    <link rel="stylesheet" href="/css/global.css">
</head>
<body>

<#include "../common/header.ftl"/>
<#include "../common/column.ftl"/>

<div class="layui-container">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md8">
            <div class="fly-panel" style="margin-bottom: 0;">

            <#include "../common/filter.ftl"/>

                <ul class="fly-list">
                <#list topics as topic >
                    <li>
                        <a href="/user/home/${topic.user.id}" class="fly-avatar">
                            <img src="${topic.user.avatorUrl}"
                                 alt="${topic.user.name}">
                        </a>
                        <h2>
                            <a class="layui-badge">${topic.typeName}</a>
                            <a href="/topic/${topic.id}">${topic.title}</a>
                        </h2>
                        <div class="fly-list-info">
                            <a href="/user/home/${topic.user.id}" link>
                                <cite>${topic.user.name}</cite>
                                <!--
                                <i class="iconfont icon-renzheng" title="认证信息：XXX"></i>
                                <i class="layui-badge fly-badge-vip">VIP3</i>
                                -->
                            </a>
                            <span>${topic.createTime}</span>

                            <span class="fly-list-kiss layui-hide-xs" title="悬赏飞吻"><i
                                    class="iconfont icon-kiss"></i> ${topic.experience}</span>
                            <!--<span class="layui-badge fly-badge-accept layui-hide-xs">已结</span>-->
                            <span class="fly-list-nums">
                    <i class="iconfont icon-pinglun1" title="回答"></i> ${topic.replyNum}
                  </span>
                        </div>
                        <div class="fly-list-badge">
                            <#if topic.top>
                            <span class="layui-badge layui-bg-black">置顶</span>
                            </#if>
                            <#if topic.nice>
                            <span class="layui-badge layui-bg-red">精帖</span>
                            </#if>
                        </div>
                    </li>
                </#list>
                </ul>

                <!-- <div class="fly-none">没有相关数据</div> -->

                <div style="text-align: center">
                    <div class="laypage-main">
                         <#if page gt 1>
                         <a href="/topic?type=${type}&sort=${sort}&page=${page - 2}" class="laypage-next">上一页</a>
                         </#if>
                        <span class="laypage-curr">${page}</span>
                    <#--<a href="/jie/page/2/">2</a>-->
                    <#--<a href="/jie/page/3/">3</a>-->
                    <#--<a href="/jie/page/4/">4</a>-->
                    <#--<a href="/jie/page/5/">5</a>-->
                        <#if totalPageNum gt 1 && hasNext>
                            <a href="/topic?type=${type}&sort=${sort}&page=${page}" class="laypage-next">下一页</a>
                            <a href="/topic?type=${type}&sort=${sort}&page=${totalPageNum - 1}" class="laypage-last"
                               title="尾页">尾页</a>
                        </#if>
                    </div>
                </div>

            </div>
        </div>
        <div class="layui-col-md4">
            <dl class="fly-panel fly-list-one">
                <dt class="fly-panel-title">本周热议</dt>
                <dd>
                    <a href="">（这里的功能还没实现）</a>
                    <span><i class="iconfont icon-pinglun1"></i> 666</span>
                </dd>

                <!-- 无数据时 -->
                <!--
                <div class="fly-none">没有相关数据</div>
                -->
            </dl>

            <div class="fly-panel">
                <div class="fly-panel-title">
                    开发合作
                </div>
                <div class="fly-panel-main">
                    <a href="" target="_blank" class="fly-zanzhu" style="background-color: #393D49;">华东理工大学</a>
                    <a href="" target="_blank" class="fly-zanzhu" style="background-color: #393D49;">华东师范大学</a>
                </div>
            </div>

            <div class="fly-panel fly-link">
                <h3 class="fly-panel-title">友情链接</h3>
                <dl class="fly-panel-main">
                    <dd><a href="http://www.layui.com/" target="_blank">layui</a>

                        <a href="mailto:tiangao1102@163.com?subject=%E7%94%B3%E8%AF%B7Fly%E7%A4%BE%E5%8C%BA%E5%8F%8B%E9%93%BE"
                           class="fly-link">申请友链</a>
                    <dd>
                </dl>
            </div>

        </div>
    </div>
</div>

<#include "../common/footer.ftl"/>

<script src="/layui/layui.js"></script>
<script>
    layui.cache.page = 'jie';
    layui.cache.user = {
        username: '${Session.user.name}'
        , uid: ${Session.user.id}
        , avatar: '${Session.user.avatorUrl}'
        , experience: 83
        , sex: '${Session.user.sex}'
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