/**
 * 发布资源对话框
 */

layui.use(['layer', 'form', 'admin', 'ax', 'func', 'upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var func = layui.func;
    var upload = layui.upload;


    var E = window.wangEditor;
    var editor = new E('#editor');
    editor.customConfig.onchange = function (html) {
        $("#homeText").val(html);
    };
    editor.create();

    $("#showMap").click(function () {
        var local = $("#homeLocal").val().trim();
        var lng = $("#homeLng").val();
        var lat = $("#homeLat").val();


        func.open({
            height: 600,
            title: '选择地址',
            content: Feng.ctxPath + '/admin/home/map?local=' + local + "&lng=" + lng + "&lat=" + lat,
            mapId: 'home'
        });
    });

    //拖拽上传
    upload.render({
        elem: '#upload'
        , url: Feng.ctxPath + '/admin/file/file.do?path=home'
        , accept: 'file' //普通文件
        , exts: 'pdf|jpg|jepg|png'
        , size: 1000 * 1 //限制文件大小，单位 KB
        , done: function (res) {
            Feng.success("上传成功！");
            $('#uploadDemoView').removeClass('layui-hide').find('img').attr('src', res.data);
            $('#up').hide();
            $('#homePath').val(res.data);
        }
    });


    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/admin/home/add", function (data) {
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