<h1>Article List</h1>
<a href="/article/new">New Article</a>
<ul>
    <#list data as article>
        <li>
            <a href="/article/${article.id}">${article.title}</a>
        </li>
    </#list>
</ul>