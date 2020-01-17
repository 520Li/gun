layui.use(['layer', 'form', 'table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 系统管理--角色管理
     */
    var Index = {
        tableId: "voerTable"   //表格id
    };

    /**
     * 初始化表格的列
     */
    Index.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'userName', sort: true, title: '报名人'},
            {field: 'userIphone', align: "center", sort: true, title: '联系电话'},
            {field: 'createTime', align: "center", sort: true, title: '报名时间'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Index.search = function () {
        var queryData = {};
        queryData['voer'] = $("#voer").val().trim();
        table.reload(Index.tableId, {
            where: queryData, page: {curr: 1}
        });
    };


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Index.tableId,
        url: Feng.ctxPath + '/admin/volunteer/vo_list?voId=' + Feng.getUrlParam("voId"),
        page: true,
        height: "full-98",
        cellMinWidth: 100,
        cols: Index.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnPerson').click(function () {
        Index.search();
    });

});
