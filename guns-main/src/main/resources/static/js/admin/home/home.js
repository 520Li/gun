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
        tableId: "homeTable"  //表格id
    };

    /**
     * 初始化表格的列
     */
    Index.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'homeName', sort: true, title: '资源名称'},
            {field: 'homeType', align: "center", sort: true, title: '资源类别'},
            {field: 'homeLocal', align: "center", sort: true, title: '资源地址'},
            {field: 'homeDetail', align: "center", sort: true, title: '主营'},
            {field: 'homeTel', align: "center", sort: true, title: '电话'},
            {align: 'center', toolbar: '#tableBarHome', title: '操作', minWidth: 200}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Index.search = function () {
        var queryData = {};
        queryData['homeName'] = $("#homeName").val().trim();
        table.reload(Index.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加角色
     */
    Index.openAddHome = function () {
        func.open({
            height: 600,
            title: '新增资源',
            content: Feng.ctxPath + '/admin/home/home_add',
            tableId: Index.tableId
        });
    };

    /**
     * 点击编辑角色
     *
     * @param data 点击按钮时候的行数据
     */
    Index.onEditHome = function (data) {
        func.open({
            height: 600,
            title: '编辑资源',
            content: Feng.ctxPath + "/admin/home/home_edit?homeId=" + data.homeId,
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
    Index.onDeleteHome = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/admin/home/delete", function () {
                Feng.success("删除成功!");
                table.reload(Index.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("arId", data.arId);
            ajax.start();
        };
        Feng.confirm("是否删除资源 " + data.homeName + "?", operation);
    };


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Index.tableId,
        url: Feng.ctxPath + '/admin/home/list',
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
        Index.openAddHome();
    });


    // 工具条点击事件
    table.on('tool(' + Index.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Index.onEditHome(data);
        } else if (layEvent === 'delete') {
            Index.onDeleteHome(data);
        }
        /* else if (layEvent === 'roleAssign') {
                    Index.roleAssign(data);
                }*/
    });
});
