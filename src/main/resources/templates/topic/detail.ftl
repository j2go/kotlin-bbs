<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>你好大学 - ${topic.title}</title>
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
        <div class="layui-col-md8 content detail">
            <div class="fly-panel detail-box">
                <h1>${topic.title}</h1>
                <div class="fly-detail-info">
                    <#if topic.status == 1><span class="layui-badge">审核中</span></#if>

                    <#if topic.type == 6><span class="layui-badge layui-bg-green fly-detail-column">动态</span></#if>
                <#--<span class="layui-badge" style="background-color: #999;">未结</span>-->
                    <#if topic.status == 9>
                        <span class="layui-badge" style="background-color: #5FB878;">已结</span>
                    </#if>
          <#if topic.top>
              <span class="layui-badge layui-bg-black">置顶</span>
          </#if>
          <#if topic.nice>
              <span class="layui-badge layui-bg-red">精帖</span>
          </#if>
          <#if isAdmin>
            <div class="fly-admin-box" data-id="${topic.id}">
                <span class="layui-btn layui-btn-xs jie-admin" type="del">删除</span>
              <#if topic.top>
                <span class="layui-btn layui-btn-xs jie-admin" type="set" field="top" rank="false"
                      style="background-color:#ccc;">取消置顶</span>
              <#else>
                <span class="layui-btn layui-btn-xs jie-admin" type="set" field="top" rank="true">置顶</span>
              </#if>
              <#if topic.nice>
                <span class="layui-btn layui-btn-xs jie-admin" type="set" field="nice" rank="false"
                      style="background-color:#ccc;">取消加精</span>
              <#else>
                <span class="layui-btn layui-btn-xs jie-admin" type="set" field="nice" rank="true">加精</span>
              </#if>
            </div>
          </#if>
                    <span class="fly-list-nums">
            <a href="#comment"><i class="iconfont" title="回答">&#xe60c;</i> ${replyNum}</a>
            <i class="iconfont" title="人气">&#xe60b;</i> ${topic.readNum}
          </span>
                </div>
                <div class="detail-about">
                    <a class="fly-avatar" href="/user/home">
                        <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"
                             alt="贤心">
                    </a>
                    <div class="fly-detail-user">
                        <a href="../user/home.html" class="fly-link">
                            <cite>${author.name}</cite>
                            <i class="iconfont icon-renzheng" title="认证信息：{{ rows.user.approve }}"></i>
                            <i class="layui-badge fly-badge-vip">VIP3</i>
                        </a>
                        <span>${topic.createTime}</span>
                    </div>
                    <div class="detail-hits" id="LAY_jieAdmin" data-id="${topic.id}">
                        <span style="padding-right: 10px; color: #FF7200">悬赏：${topic.experience}飞吻</span>
                        <#if ofMine>
                        <span class="layui-btn layui-btn-xs jie-admin" type="edit"><a href="/topic/${topic.id}/edit">编辑此贴</a></span>
                        </#if>
                    </div>
                </div>
                <div class="detail-body photos">
                ${topic.content}
                </div>
            </div>

            <div class="fly-panel detail-box" id="flyReply">
                <fieldset class="layui-elem-field layui-field-title" style="text-align: center;">
                    <legend>回帖</legend>
                </fieldset>

                <ul class="jieda" id="jieda">
                  <#list replies as reply>
                    <li data-id="${reply.data.id}" class="jieda-daan">
                        <a name="item-${reply.data.id}"></a>
                        <div class="detail-about detail-about-reply">
                            <a class="fly-avatar" href="">
                                <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"
                                     alt=" ">
                            </a>
                            <div class="fly-detail-user">
                                <a href="" class="fly-link">
                                    <cite>${reply.user.name}</cite>
                                    <i class="iconfont icon-renzheng" title="认证信息：XXX"></i>
                                    <i class="layui-badge fly-badge-vip">VIP3</i>
                                </a>

                                <#if topic.userId == reply.data.userId><span>(楼主)</span></#if>
                                <!--
                                <span style="color:#5FB878">(管理员)</span>
                                <span style="color:#FF9E3F">（社区之光）</span>
                                <span style="color:#999">（该号已被封）</span>
                                -->
                            </div>

                            <div class="detail-hits">
                                <span>${reply.data.lastModifyTime}</span>
                            </div>

                            <#if reply.data.helpful><i class="iconfont icon-caina" title="最佳答案"></i></#if>
                        </div>
                        <div class="detail-body jieda-body photos">
                            ${reply.data.content}
                        </div>
                        <div class="jieda-reply">
                            <span class="jieda-zan <#if reply.liked>zanok</#if>" type="zan">
                            <i class="iconfont icon-zan"></i>
                            <em>${reply.data.likeNum}</em>
                            </span>
                            <span type="reply"><i class="iconfont icon-svgmoban53"></i>回复</span>
                            <div class="jieda-admin">

                                <#if reply.canEdit>
                                    <span type="edit">编辑</span>
                                    <span type="del">删除</span>
                                </#if>
                            <#if topic.userId == Session.user.id && topic.userId != reply.user.id && !reply.data.helpful>
                                <span class="jieda-accept" type="accept">采纳</span>
                            </#if>
                            </div>
                        </div>
                    </li>
                  </#list>
                    <!-- <li class="fly-none">消灭零回复</li> -->
                </ul>

                <div class="layui-form layui-form-pane">
                    <form action="/reply" method="post">
                    <#--<form action="/topic/${topic.id}/reply" method="post">-->
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="layui-form-item layui-form-text">
                            <a name="comment"></a>
                            <div class="layui-input-block">
                                <textarea id="L_content" name="content" required lay-verify="required"
                                          placeholder="请输入内容" class="layui-textarea fly-editor"
                                          style="height: 150px;"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <input type="hidden" name="id" value="${topic.id}">
                        <#--<input type="submit" value="提交回复" class="layui-btn" lay-filter="*">-->
                            <button class="layui-btn" lay-filter="*" lay-submit>提交回复</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    <#-- 全站讨论热点栏目 && 广告区域
    <div class="layui-col-md4">
      <dl class="fly-panel fly-list-one">
        <dt class="fly-panel-title">本周热议</dt>
        <dd>
          <a href="">基于 layui 的极简社区页面模版</a>
          <span><i class="iconfont icon-pinglun1"></i> 16</span>
        </dd>
        <dd>
          <a href="">基于 layui 的极简社区页面模版</a>
          <span><i class="iconfont icon-pinglun1"></i> 16</span>
        </dd>

        <!-- 无数据时 &ndash;&gt;
        <!--
        <div class="fly-none">没有相关数据</div>
        &ndash;&gt;
      </dl>

      <div class="fly-panel">
        <div class="fly-panel-title">
          这里可作为广告区域
        </div>
        <div class="fly-panel-main">
          <a href="http://layim.layui.com/?from=fly" target="_blank" class="fly-zanzhu" time-limit="2017.09.25-2099.01.01" style="background-color: #5FB878;">LayIM 3.0 - layui 旗舰之作</a>
        </div>
      </div>
    </div>
    -->
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
    }).use(['fly', 'face'], function () {
        var $ = layui.$
                , fly = layui.fly;
        //如果你是采用模版自带的编辑器，你需要开启以下语句来解析。

        $('.detail-body').each(function () {
            var othis = $(this), html = othis.html();
            othis.html(fly.content(html));
        });

    });
</script>

</body>
</html>