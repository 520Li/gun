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
        tableId: "appointTable"   //表格id
    };

    /**
     * 初始化表格的列
     */
    Index.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'appointUser', sort: true, title: '预约人'},
            {field: 'appointIphone', align: "center", sort: true, title: '预约电话'},
            {field: 'serviceType', align: "center", sort: true, title: '服务类型'},
            {field: 'appointTitle', align: "center", sort: true, title: '主题'},
            {field: 'appointTime', align: "center", sort: true, title: '预约时间'},
            {field: 'applyStatus', align: "center", sort: true, title: '状态'},
            {align: 'center', toolbar: '#tableBarAppoint', title: '操作', minWidth: 200}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Index.search = function () {
        var queryData = {};
        queryData['appointUser'] = $("#appointUser").val().trim();
        queryData['serviceType'] = $("#serviceType").val().trim();
        table.reload(Index.tableId, {
            where: queryData, page: {curr: 1}
        });
    };


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Index.tableId,
        url: Feng.ctxPath + '/admin/appoint/list',
        page: true,
        height: "full-98",
        cellMinWidth: 100,
        cols: Index.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Index.search();
    });

    //详细按钮点击事件
    Index.display = function (data) {
        func.open({
            height: 600,
            title: '预约详细',
            content: Feng.ctxPath + "/admin/appoint/appoint_dis?appointId=" + data.appointId,
            tableId: Index.tableId
        });
    };

    // 工具条点击事件
    table.on('tool(' + Index.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'dis') {
            Index.display(data);
        }
    });


});
