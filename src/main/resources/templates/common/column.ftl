<div class="fly-panel fly-column">
  <div class="layui-container">
    <ul class="layui-clear">
      <li class="layui-hide-xs layui-this"><a href="/">首页</a></li> 
      <li><a href="/topic?type=1&sort=${sort}">提问<#if type == 1><span class="layui-badge-dot"></span></#if></a></li>
      <li><a href="/topic?type=2&sort=${sort}">分享<#if type == 2><span class="layui-badge-dot"></span></#if></a></li>
      <li><a href="/topic?type=3&sort=${sort}">讨论<#if type == 3><span class="layui-badge-dot"></span></#if></a></li>
      <li><a href="/topic?type=4&sort=${sort}">建议<#if type == 4><span class="layui-badge-dot"></span></#if></a></li>
      <li><a href="/topic?type=5&sort=${sort}">公告<#if type == 5><span class="layui-badge-dot"></span></#if></a></li>
      <li><a href="/topic?type=6&sort=${sort}">动态<#if type == 6><span class="layui-badge-dot"></span></#if></a></li>
      <li class="layui-hide-xs layui-hide-sm layui-show-md-inline-block"><span class="fly-mid"></span></li> 
      
      <!-- 用户登入后显示 -->
      <li class="layui-hide-xs layui-hide-sm layui-show-md-inline-block"><a href="/user/index">我发表的贴</a></li>
      <li class="layui-hide-xs layui-hide-sm layui-show-md-inline-block"><a href="/user/index#collection">我收藏的贴</a></li>
    </ul> 
    
    <div class="fly-column-right layui-hide-xs"> 
      <span class="fly-search"><i class="layui-icon"></i></span> 
      <a href="/topic/add" class="layui-btn">发表新帖</a>
    </div> 
    <div class="layui-hide-sm layui-show-xs-block" style="margin-top: -10px; padding-bottom: 10px; text-align: center;"> 
      <a href="/topic/add" class="layui-btn">发表新帖</a>
    </div> 
  </div>
</div>