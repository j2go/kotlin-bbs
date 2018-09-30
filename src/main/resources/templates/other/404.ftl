<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>404 - Fly社区</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <meta name="keywords" content="fly,layui,前端社区">
  <meta name="description" content="Fly社区是模块化前端UI框架Layui的官网社区，致力于为web开发提供强劲动力">
  <link rel="stylesheet" href="/layui/css/layui.css">
  <link rel="stylesheet" href="/css/global.css">
</head>
<body>

<#include "../common/header.ftl"/>

<div class="layui-container fly-marginTop">
	<div class="fly-panel">
	  <div class="fly-none">
	    <h2><i class="iconfont icon-404"></i></h2>
	    <p>这个地址不存在啊，你是不是在搞事情 ~~~~~</p>
	  </div>
	</div>
</div>

<div class="fly-footer">
  <p><a href="http://fly.layui.com/" target="_blank">Fly社区</a> 2017 &copy; <a href="http://www.layui.com/" target="_blank">layui.com 出品</a></p>
  <p>
    <a href="http://fly.layui.com/jie/3147/" target="_blank">付费计划</a>
    <a href="http://www.layui.com/template/fly/" target="_blank">获取Fly社区模版</a>
    <a href="http://fly.layui.com/jie/2461/" target="_blank">微信公众号</a>
  </p>
</div>

<script src="/layui/layui.js"></script>
<script>
layui.cache.page = '';
layui.cache.user = {
  username: '游客'
  ,uid: -1
  ,avatar: '/images/avatar/00.jpg'
  ,experience: 123
  ,sex: '男'
};
layui.config({
  version: "3.0.0"
  ,base: '/mods/'
}).extend({
  fly: 'index'
}).use('fly');
</script>

</body>
</html>