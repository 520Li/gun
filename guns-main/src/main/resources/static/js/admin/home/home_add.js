/**
 * 发布文章对话框
 */
var data = {
    addr: "",
    lng: "",
    lat: ""
}


layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    var E = window.wangEditor;
    var editor = new E('#editor');
    editor.customConfig.onchange = function (html) {
        $("#homeText").val(html);
    };
    editor.create();

    $("#showMap").click(function () {
        /*$("#searchplace").val($("#homeLocal").val().trim());
        $("#s_p_search_btn").click();*/
        parent.layer.open({
            type: 2,
            title: '选择地址',
            area: ['1100px', '600px'], //宽高
            fix: false,
            maxmin: true,
            content: Feng.ctxPath + '/admin/home/map?local=' + $("#homeLocal").val().trim(),
            end: function () {
                /*$("input[name=homeLocal]").val(data.attr);//资源地址
                $("input[name=homeLng]").val(data.lng);//经度
                $("input[name=homeLat]").val(data.lat);//纬度*/
            }
        });
    });


    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/admin/index/add", function (data) {
            Feng.success("添加成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();

        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        //添加 return false 可成功跳转页面
        return false;
    });


});