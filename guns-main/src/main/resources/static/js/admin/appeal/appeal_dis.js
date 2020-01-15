layui.use(['carousel', 'form'], function(){
    var carousel = layui.carousel;


    //图片轮播
    carousel.render({
        elem: '#image'
        ,width: '778px'
        ,height: '440px'
        ,interval: 5000
    });





});