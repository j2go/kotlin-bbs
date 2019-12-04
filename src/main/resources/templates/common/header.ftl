<div class="fly-header layui-bg-black">
    <div class="layui-container">
        <a class="fly-logo" href="/">
            <img src="/images/logo.png" alt="layui">
        </a>
        <ul class="layui-nav fly-nav layui-hide-xs">
            <li class="layui-nav-item layui-this">
                <a href="/"><i class="iconfont icon-jiaoliu"></i>交流</a>
            </li>
        </ul>

        <ul class="layui-nav fly-nav-user">
            <#if Session.user?exists>
                <li class="layui-nav-item">
                <a class="fly-nav-avatar" href="javascript:;">
            <#--${Session["username"]!"交流"}-->
                <cite class="layui-hide-xs">${Session.user.name}</cite>
            <i class="iconfont icon-renzheng layui-hide-xs" title="认证信息：layui 作者"></i>
                <i class="layui-badge fly-badge-vip layui-hide-xs">VIP${Session.user.level}</i>
            <img src="${Session.user.avatorUrl}">
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="/user/setting"><i class="layui-icon">&#xe620;</i>基本设置</a></dd>
                    <dd><a href="/user/message"><i class="iconfont icon-tongzhi" style="top: 4px;"></i>我的消息</a></dd>
                    <dd><a href="/user/home"><i class="layui-icon"
                                                style="margin-left: 2px; font-size: 22px;">&#xe68e;</i>我的主页</a></dd>
                    <hr style="margin: 5px 0;">
                    <dd><a href="/user/logout" style="text-align: center;">退出</a></dd>
                </dl>
                </li>
            <#else>
                <!-- 未登入的状态 -->
                <li class="layui-nav-item">
                    <a class="iconfont icon-touxiang layui-hide-xs" href="/user/login"></a>
                </li>
                <li class="layui-nav-item">
                    <a href="/user/login">登入</a>
                </li>
                <li class="layui-nav-item">
                    <a href="/user/reg">注册</a>
                </li>
                <li class="layui-nav-item layui-hide-xs">
                    <a href="/app/qq/" onclick="layer.msg('正在通过QQ登入', {icon:16, shade: 0.1, time:0})" title="QQ登入"
                       class="iconfont icon-qq"></a>
                </li>
                <li class="layui-nav-item layui-hide-xs">
                    <a href="/app/weibo/" onclick="layer.msg('正在通过微博登入', {icon:16, shade: 0.1, time:0})" title="微博登入"
                       class="iconfont icon-weibo"></a>
                </li>
            </#if>
        </ul>
    </div>
</div>