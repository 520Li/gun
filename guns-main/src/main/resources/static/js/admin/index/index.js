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
        tableId: "indexTable",    //表格id
        condition: {
            roleName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Index.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'arTitle', sort: true, title: '标题'},
            {field: 'arType', align: "center", sort: true, title: '文章分类'},
            {field: 'arIspath', align: "center", sort: true, title: '文章类型'},
            {field: 'arUser', align: "center", sort: true, title: '发布者'},
            {field: 'arOrg', align: "center", sort: true, title: '机构'},
            {field: 'createTime', align: "center", sort: true, title: '创建时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Index.search = function () {
        var queryData = {};
        queryData['arTitle'] = $("#arTitle").val();
        table.reload(Index.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加角色
     */
    Index.openAddArticle = function () {
        func.open({
            height: 600,
            title: '发布文章',
            content: Feng.ctxPath + '/admin/index/index_add',
            tableId: Index.tableId
        });
    };

    /**
     * 点击编辑角色
     *
     * @param data 点击按钮时候的行数据
     */
    Index.onEditArticle = function (data) {
        func.open({
            height: 600,
            title: '编辑文章',
            content: Feng.ctxPath + "/admin/index/index_edit?arId=" + data.arId,
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
    Index.onDeleteArticle = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/admin/index/delete", function () {
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
        url: Feng.ctxPath + '/admin/index/list?type=menu_01',
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
        Index.openAddArticle();
    });


    // 工具条点击事件
    table.on('tool(' + Index.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Index.onEditArticle(data);
        } else if (layEvent === 'delete') {
            Index.onDeleteArticle(data);
        }
        /* else if (layEvent === 'roleAssign') {
                    Index.roleAssign(data);
                }*/
    });
});
