layui.use(['layer', 'element', 'form', 'table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var element = layui.element;

    var Benefit = {
        tableId: "btTable"   //表格id
    };
    var Log = {
        tableId: "logTable"
    };
    var Gift = {
        tableId: "giftTable"
    };

    Benefit.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'btUser', sort: true, title: '公益人'},
            {field: 'btName', align: "center", sort: true, title: '公益活动'},
            {field: 'btState', align: "center", sort: true, title: '状态'},
            {field: 'btTime', align: "center", sort: true, title: '公益时间'},
            {align: 'center', toolbar: '#tableBarBt', title: '操作', minWidth: 200}
        ]];
    };
    Log.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'zglBatchId', sort: true, title: '批次编号'},
            {field: 'zglGiftId', align: "center", sort: true, title: '兑换品'},
            {field: 'zglUserId', align: "center", sort: true, title: '兑换人'},
            {field: 'zglNum', align: "center", sort: true, title: '兑换个数'},
            {field: 'zglCore', align: "center", sort: true, title: '消耗积分'},
            {align: 'center', toolbar: '#tableBarLog', title: '操作', minWidth: 200}
        ]];
    };
    Gift.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'giftName', align: "center", sort: true, title: '兑换品'},
            {field: 'giftCore', align: "center", sort: true, title: '所需积分'},
            {field: 'giftText', align: "center", sort: true, title: '兑换品描述'},
            {field: 'giftNum', align: "center", sort: true, title: '兑换品库存'},
            {field: 'giftState', align: "center", sort: true, title: '状态'},
            {align: 'center', toolbar: '#tableBarGift', title: '操作', minWidth: 200}
        ]];
    };


    /**
     * 点击查询按钮
     */
    Benefit.search = function () {
        var queryData = {};
        queryData['btUser'] = $("#btUser").val().trim();
        queryData['btType'] = $("#btType").val().trim();
        table.reload(Benefit.tableId, {
            where: queryData, page: {curr: 1}
        });
    };
    Log.search = function () {
        var queryData = {};
        queryData['logGift'] = $("#logGift").val().trim();
        queryData['logName'] = $("#logName").val().trim();
        table.reload(Log.tableId, {
            where: queryData, page: {curr: 1}
        });
    };
    Gift.search = function () {
        var queryData = {};
        queryData['giftName'] = $("#giftName").val().trim();
        table.reload(Gift.tableId, {
            where: queryData, page: {curr: 1}
        });
    };


    Gift.openAdd = function () {
        func.open({
            height: 600,
            title: '新增兑换品',
            content: Feng.ctxPath + '/admin/benefit/gift_add',
            tableId: Gift.tableId
        });
    };
    Gift.onEdit = function (data) {
        func.open({
            height: 600,
            title: '编辑兑换品',
            content: Feng.ctxPath + "/admin/benefit/gift_edit?giftId=" + data.giftId,
            tableId: Gift.tableId
        });
    };
    Gift.onDelete = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/admin/benefit/gift_delete", function () {
                Feng.success("删除成功!");
                table.reload(Index.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("giftId", data.giftId);
            ajax.start();
        };
        Feng.confirm("是否删除文章 " + data.giftName + "?", operation);
    };


    Benefit.onOpenDis = function (data) {
        func.open({
            height: 600,
            title: '公益详情',
            content: Feng.ctxPath + '/admin/benefit/benefit_dis?btId=' + data.btId,
            tableId: Benefit.tableId
        });
    };
    Benefit.onApply = function (data, state) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/admin/benefit/benefit_apply", function () {
                Feng.success("操作成功!");
                table.reload(Benefit.tableId);
            }, function (data) {
                Feng.error("操作失败!" + data.responseJSON.message + "!");
            });
            ajax.set("btId", data.btId);
            ajax.set("btState", state);
            ajax.start();
        };
        Feng.confirm("确认操作?", operation);
    };


    Log.onOpenDis = function (data) {
        func.open({
            height: 600,
            title: '兑换详情',
            content: Feng.ctxPath + '/admin/benefit/log_dis?zglBatchId=' + data.zglBatchId,
            tableId: Log.tableId
        });
    };
    Log.onApply = function (data, state) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/admin/benefit/log_apply", function () {
                Feng.success("操作成功!");
                table.reload(Log.tableId);
            }, function (data) {
                Feng.error("操作失败!" + data.responseJSON.message + "!");
            });
            ajax.set("zglBatchId", data.zglBatchId);
            ajax.set("zglState", state);
            ajax.start();
        };
        Feng.confirm("确认操作?", operation);
    };


    // 渲染表格
    table.render({
        elem: '#' + Benefit.tableId,
        url: Feng.ctxPath + '/admin/benefit/benefit_list',
        page: true,
        height: "full-98",
        cellMinWidth: 100,
        cols: Benefit.initColumn()
    });
    table.render({
        elem: '#' + Log.tableId,
        url: Feng.ctxPath + '/admin/benefit/log_list',
        page: true,
        height: "full-98",
        cellMinWidth: 100,
        cols: Log.initColumn()
    });
    table.render({
        elem: '#' + Gift.tableId,
        url: Feng.ctxPath + '/admin/benefit/gift_list',
        page: true,
        height: "full-98",
        cellMinWidth: 100,
        cols: Gift.initColumn()
    });


    // 搜索按钮点击事件
    $('#btnSearch1').click(function () {
        Benefit.search();
    });
    $('#btnSearch2').click(function () {
        Log.search();
    });
    $('#btnSearch3').click(function () {
        Gift.search();
    });

    // 添加按钮点击事件
    $('#btnAdd3').click(function () {
        Gift.openAdd();
    });


    // 工具条点击事件
    table.on('tool(' + Benefit.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'dis') {
            Benefit.onOpenDis(data);
        } else if (layEvent === 'pass') {
            Benefit.onApply(data, "02");
        } else if (layEvent === 'back') {
            Benefit.onApply(data, "03");
        }
    });
    table.on('tool(' + Log.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'on') {
            Log.onOpenDis(data);
        } else if (layEvent === 'accept') {
            Log.onApply(data, '02');
        } else if (layEvent === 'off') {
            Log.onApply(data, '03');
        }
    });
    table.on('tool(' + Gift.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Gift.onEdit(data);
        } else if (layEvent === 'delete') {
            Gift.onDelete(data);
        }
    });
});
