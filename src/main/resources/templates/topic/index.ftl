<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>基于 layui 的极简社区页面模版</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="keywords" content="fly,layui,前端社区">
    <meta name="description" content="Fly社区是模块化前端UI框架Layui的官网社区，致力于为web开发提供强劲动力">
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
                        <a href="/user/home/${topic.userId}" class="fly-avatar">
                            <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"
                                 alt="贤心">
                        </a>
                        <h2>
                            <a class="layui-badge">${topic.typeName}</a>
                            <a href="/topic/${topic.id}">${topic.title}</a>
                        </h2>
                        <div class="fly-list-info">
                            <a href="/user/home/${topic.userId}" link>
                                <cite>${topic.userName}</cite>
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
                        <span class="laypage-curr">${page}</span>
                    <#--<a href="/jie/page/2/">2</a>-->
                    <#--<a href="/jie/page/3/">3</a>-->
                    <#--<a href="/jie/page/4/">4</a>-->
                    <#--<a href="/jie/page/5/">5</a>-->
                        <span>…</span>
                        <a href="/topic?type=${type}&sort=${sort}&page=${lastPage}" class="laypage-last"
                           title="尾页">尾页</a>
                        <#if hasNext>
                        <a href="/topic?type=${type}&sort=${sort}&page=${page + 1}"
                           class="laypage-next">下一页</a>
                        </#if>
                    </div>
                </div>

            </div>
        </div>
        <div class="layui-col-md4">
            <dl class="fly-panel fly-list-one">
                <dt class="fly-panel-title">本周热议</dt>
                <dd>
                    <a href="">基于 layui 的极简社区页面模版</a>
                    <span><i class="iconfont icon-pinglun1"></i> 16</span>
                </dd>

                <!-- 无数据时 -->
                <!--
                <div class="fly-none">没有相关数据</div>
                -->
            </dl>

            <div class="fly-panel">
                <div class="fly-panel-title">
                    这里可作为广告区域
                </div>
                <div class="fly-panel-main">
                    <a href="" target="_blank" class="fly-zanzhu" style="background-color: #393D49;">虚席以待</a>
                </div>
            </div>

            <div class="fly-panel fly-link">
                <h3 class="fly-panel-title">友情链接</h3>
                <dl class="fly-panel-main">
                    <dd><a href="http://www.layui.com/" target="_blank">layui</a>
                    <dd>
                    <dd><a href="http://layim.layui.com/" target="_blank">WebIM</a>
                    <dd>
                    <dd><a href="http://layer.layui.com/" target="_blank">layer</a>
                    <dd>
                    <dd><a href="http://www.layui.com/laydate/" target="_blank">layDate</a>
                    <dd>
                    <dd>
                        <a href="mailto:xianxin@layui-inc.com?subject=%E7%94%B3%E8%AF%B7Fly%E7%A4%BE%E5%8C%BA%E5%8F%8B%E9%93%BE"
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