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
        tableId: "volunteerTable",   //表格id
    };

    /**
     * 初始化表格的列
     */
    Index.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'voName', sort: true, title: '活动名称'},
            {field: 'voTel', align: "center", sort: true, title: '咨询电话'},
            {field: 'voTime', align: "center", sort: true, title: '活动时间'},
            {field: 'voLocal', align: "center", sort: true, title: '活动地点'},
            {field: 'voDuration', align: "center", sort: true, title: '持续时间'},
            {align: 'center', toolbar: '#tableBarVo', title: '操作', minWidth: 200}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Index.search = function () {
        var queryData = {};
        queryData['voName'] = $("#voName").val().trim();
        table.reload(Index.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加角色
     */
    Index.openAddVolunteer = function () {
        func.open({
            height: 600,
            title: '发布志愿活动',
            content: Feng.ctxPath + '/admin/volunteer/volunteer_add',
            tableId: Index.tableId
        });
    };

    /**
     * 点击编辑角色
     *
     * @param data 点击按钮时候的行数据
     */
    Index.onEditVolunteer = function (data) {
        func.open({
            height: 600,
            title: '编辑志愿活动',
            content: Feng.ctxPath + "/admin/volunteer/volunteer_edit?voId=" + data.voId,
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
    Index.onDeleteVolunteer = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/admin/volunteer/delete", function () {
                Feng.success("删除成功!");
                table.reload(Index.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("voId", data.voId);
            ajax.start();
        };
        Feng.confirm("是否删除文章 " + data.voName + "?", operation);
    };


    /**
     * 查看报名人
     * @param data
     */
    Index.onOpenUser = function (data) {
        func.open({
            height: 600,
            title: '志愿者名单',
            content: Feng.ctxPath + "/admin/volunteer/volunteer_user?voId=" + data.voId,
            tableId: Index.tableId
        });
    }


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Index.tableId,
        url: Feng.ctxPath + '/admin/volunteer/list',
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
        Index.openAddVolunteer();
    });


    // 工具条点击事件
    table.on('tool(' + Index.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Index.onEditVolunteer(data);
        } else if (layEvent === 'delete') {
            Index.onDeleteVolunteer(data);
        } else if (layEvent === 'user') {
            Index.onOpenUser(data);
        }
        /* else if (layEvent === 'roleAssign') {
                    Index.roleAssign(data);
                }*/
    });
});
