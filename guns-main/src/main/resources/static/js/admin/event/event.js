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
        tableId: "eventTable"   //表格id
    };

    /**
     * 初始化表格的列
     */
    Index.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'eventName', sort: true, title: '活动名称'},
            {field: 'eventType', align: "center", sort: true, title: '活动分类'},
            {field: 'eventStart', align: "center", sort: true, title: '活动开始时间'},
            {field: 'eventCode', align: "center", sort: true, title: '活动积分'},
            {field: 'eventStatus', align: "center", sort: true, title: '当前状态'},
            {align: 'center', toolbar: '#tableBarEvent', title: '操作', minWidth: 200}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Index.search = function () {
        var queryData = {};
        queryData['eventName'] = $("#eventName").val().trim();
        table.reload(Index.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加角色
     */
    Index.openAddEvent = function () {
        func.open({
            height: 600,
            title: '发布活动',
            content: Feng.ctxPath + '/admin/event/event_add',
            tableId: Index.tableId
        });
    };

    /**
     * 点击编辑角色
     *
     * @param data 点击按钮时候的行数据
     */
    Index.onEditEvent = function (data) {
        func.open({
            height: 600,
            title: '编辑活动',
            content: Feng.ctxPath + "/admin/event/event_edit?eventId=" + data.eventId,
            tableId: Index.tableId
        });
    };

    /**
     * 活动人员名单
     * @param data
     */
    Index.onJoinEvent = function (data) {
        func.open({
            height: 600,
            title: '活动人员名单',
            content: Feng.ctxPath + "/admin/event/event_join?eventId=" + data.eventId,
            tableId: Index.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    /*Index.exportExcel = function () {
        var checkRows = table.checkStatus(Index.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };*/

    /**
     * 点击删除角色
     *
     * @param data 点击按钮时候的行数据
     */
    /* Index.onDeleteArticle = function (data) {
         var operation = function () {
             var ajax = new $ax(Feng.ctxPath + "/admin/event/delete", function () {
                 Feng.success("删除成功!");
                 table.reload(Index.tableId);
             }, function (data) {
                 Feng.error("删除失败!" + data.responseJSON.message + "!");
             });
             ajax.set("arId", data.arId);
             ajax.start();
         };
         Feng.confirm("是否删除文章 " + data.arTitle + "?", operation);
     };*/


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Index.tableId,
        url: Feng.ctxPath + '/admin/event/list',
        page: true,
        height: "full-98",
        cellMinWidth: 100,
        cols: Index.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Index.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Index.openAddEvent();
    });


    // 工具条点击事件
    table.on('tool(' + Index.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Index.onEditEvent(data);
        } else if (layEvent === 'join') {
            Index.onJoinEvent(data);
        }
    });
});
