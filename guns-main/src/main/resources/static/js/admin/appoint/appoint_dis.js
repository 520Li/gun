/**
 * 预约详情对话框
 */

layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    // 表单提交事件
    form.on('submit(btnSubmit1)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/admin/appoint/apply?status=02", function (data) {
            Feng.success("处理成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();

        }, function (data) {
            Feng.error("处理失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        //添加 return false 可成功跳转页面
        return false;
    });
    // 表单提交事件
    form.on('submit(btnSubmit2)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/admin/appoint/apply?status=03", function (data) {
            Feng.success("处理成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();

        }, function (data) {
            Feng.error("处理失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        //添加 return false 可成功跳转页面
        return false;
    });
});