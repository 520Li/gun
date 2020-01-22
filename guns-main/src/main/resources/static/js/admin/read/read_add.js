/**
 * 发布文章对话框
 */

layui.use(['layer', 'form', 'admin', 'ax', 'upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var upload = layui.upload;
    form.render();

    form.on('radio(type)', function (obj) {
        if (obj.value === '01') {
            $("#editor").show();
            $("#upload").hide();
        } else if (obj.value === '02') {
            $("#editor").hide();
            $("#upload").show();
        }
    })
    var uploadInst = upload.render({
        elem: '#test1'
        , url: '/admin/file/file.do?path=read'
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#demo1').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res) {
            //如果上传失败
            if (res.flag) {
                $("#arFirstpath").val(res.data);
                return layer.msg('上传成功');
            } else {
                return layer.msg('上传失败');
            }
            //上传成功
        }
        , error: function () {
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function () {
                uploadInst.upload();
            });
        }
    });


    //拖拽上传
    upload.render({
        elem: '#upload'
        , url: Feng.ctxPath + '/admin/file/file.do?path=pdf'
        , accept: 'file' //普通文件
        , exts: 'pdf' //pdf
        , size: 1000 * 20 //限制文件大小，单位 KB
        , done: function (res) {
            if(res.flag){
                Feng.success("上传成功！");
                $('#uploadDemoView').removeClass('layui-hide').find('img').attr('src', res.data);
                $('#up').hide();
                $('#arPath').val(res.data);
            }else{
                Feng.error("上传失败！");
            }

        }
    });


    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var text = $("#arText").val().trim();
        if ($('input:radio:checked').val() === '01' && (text === '<p><br></p>' || text === '')) {
            layer.msg("文本内容不能为空");
            return false;
        }
        var ajax = new $ax(Feng.ctxPath + "/admin/read/add", function (data) {
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