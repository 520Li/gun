layui.use(['layer', 'form', 'table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     *
     */
    var Index = {
        tableId: "readTable"   //表格id
    };

    /**
     * 初始化表格的列
     */
    Index.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'arTitle', sort: true, title: '书名'},
            {field: 'arType', align: "center", sort: true, title: '菜单分类'},
            {field: 'arMajorType', align: "center", sort: true, title: '电子书类型'},
            {field: 'createTime', align: "center", sort: true, title: '创建时间'},
            {align: 'center', toolbar: '#tableBarRead', title: '操作', minWidth: 200}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Index.search = function () {
        var queryData = {};
        queryData['arTitle'] = $("#readName").val().trim();
        table.reload(Index.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加电子书
     */
    Index.openAddRead = function () {
        func.open({
            height: 600,
            title: '新增电子书',
            content: Feng.ctxPath + '/admin/read/read_add',
            tableId: Index.tableId
        });
    };

    /**
     * 点击编辑角色
     *
     * @param data 点击按钮时候的行数据
     */
    Index.onEditRead = function (data) {
        func.open({
            height: 600,
            title: '编辑电子书',
            content: Feng.ctxPath + "/admin/read/read_edit?arId=" + data.arId,
            tableId: Index.tableId
        });
    };


    /**
     * pdf预览
     * @param data
     */
    Index.onOpenRead = function (data) {
        window.open(data.arPath);
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
    Index.onDeleteRead = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/admin/read/delete", function () {
                Feng.success("删除成功!");
                table.reload(Index.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("arId", data.arId);
            ajax.start();
        };
        Feng.confirm("是否删除文章 " + data.arTitle + "?", operation);
    };


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Index.tableId,
        url: Feng.ctxPath + '/admin/read/list?type=menu_03',
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
        Index.openAddRead();
    });


    // 工具条点击事件
    table.on('tool(' + Index.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Index.onEditRead(data);
        } else if (layEvent === 'delete') {
            Index.onDeleteRead(data);
        } else if (layEvent === 'read') {
            Index.onOpenRead(data);
        }

    });
});
