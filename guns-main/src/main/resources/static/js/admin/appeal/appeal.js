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
        tableId: "appealTable"   //表格id
    };

    /**
     * 初始化表格的列
     */
    Index.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'appealUser', sort: true, title: '举报人'},
            {field: 'createTime', align: "center", sort: true, title: '举报时间'},
            {field: 'appealTel', align: "center", sort: true, title: '举报人电话'},
            {field: 'appealType', align: "center", sort: true, title: '举报类型'},
            {field: 'appealDegree', align: "center", sort: true, title: '紧急程度'},
            {field: 'appealStatus', align: "center", sort: true, title: '事件状态'},
            {align: 'center', toolbar: '#tableBarAppeal', title: '操作', minWidth: 200}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Index.search = function () {
        var queryData = {};
        queryData['appealUser'] = $("#appealUser").val().trim();
        queryData['appealType'] = $("#appealType").val().trim();
        queryData['appealStatus'] = $("#appealStatus").val().trim();
        table.reload(Index.tableId, {
            where: queryData, page: {curr: 1}
        });
    };


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Index.tableId,
        url: Feng.ctxPath + '/admin/appeal/list',
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
            title: '事件详细',
            content: Feng.ctxPath + "/admin/appeal/appeal_dis?appealId=" + data.appealId,
            tableId: Index.tableId
        });
    };

    Index.updateStatus = function (data, status) {
        /*var option = function () {
            var ajax = new $ax(Feng.ctxPath + "/admin/appeal/updateStatus", function (data) {
                Feng.success("操作成功!");
                table.reload(Index.tableId);
            }, function (data) {
                Feng.error("操作失败!");
            });
            ajax.set("appealId", data.appealId);
            ajax.set("appealStatus", status);
            ajax.start();
        }
        var str;
        if (status === '02') str = '<b style="color:red">受理</b>';
        else if (status === '03') str = '<b style="color:red">核实</b>';
        else if (status === '04') str = '<b style="color:red">结案</b>';
        Feng.confirm("确认进行 " + str + " 操作?", option);*/
        func.open({
            height: 600,
            width: 800,
            title: '事件处理',
            content: Feng.ctxPath + "/admin/appeal/toStatus?appealId=" + data.appealId + "&status=" + status,
            tableId: Index.tableId
        });
    }

    // 工具条点击事件
    table.on('tool(' + Index.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'dis') {
            Index.display(data);
        } else if (layEvent === 'accept') {
            Index.updateStatus(data, '02');
        } else if (layEvent === 'verify') {
            Index.updateStatus(data, '03');
        } else if (layEvent === 'final') {
            Index.updateStatus(data, '04');
        }
    });


});
