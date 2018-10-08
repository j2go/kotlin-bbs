<div class="fly-panel-title fly-filter">
    <a href="" class="layui-this">综合</a>
    <#--<span class="fly-mid"></span>-->
    <#--<a href="">未结</a>-->
    <#--<span class="fly-mid"></span>-->
    <#--<a href="">已结</a>-->
    <#--<span class="fly-mid"></span>-->
    <#--<a href="">精华</a>-->
    <span class="fly-filter-right layui-hide-xs">
    <a href="/?type=${type}" <#if sort == 0>class="layui-this"</#if>>按最新</a>
    <span class="fly-mid"></span>
    <a href="/?type=${type}&sort=1" <#if sort == 1>class="layui-this"</#if>>按热议</a>
  </span>
</div>