layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var admin = layui.admin;

    initMap();//创建和初始化地图
    createSearch();
    createAutocomlete();
    $("#s_p_search_btn").click();


    function initMap() {
        createMap();//创建地图
        setMapEvent();//设置地图事件
    }

//创建地图函数：
    function createMap() {
        var map = new BMap.Map("allmap");
        var point = new BMap.Point(116.331398, 39.897445);
        map.centerAndZoom(point, 12);
        window.map = map;//将map变量存储在全局
    }

//地图事件设置函数：
    function setMapEvent() {
        map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
        map.enableScrollWheelZoom();//启用地图滚轮放大缩小
        map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
        map.enableKeyboard();//启用键盘上下左右键移动地图
    }

    function createSearch() {
        var map = window.map;
        var local = new BMap.LocalSearch(map,
            {
                renderOptions: {map: map, panel: "searchlist"}
            });
        window.local = local;
    }

//搜索
    function searchPlace(value) {
        window.local.search(value);
    }

    function createAutocomlete() {
        var map = window.map;
        var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
            {
                "input": "searchplace",
                "location": map
            });
        ac.addEventListener("onconfirm", function (e) {    //鼠标点击下拉列表后的事件
            var _value = e.item.value;
            var addr = _value.business + _value.province + _value.city + _value.district + _value.street + _value.streetNumber;
            searchPlace(addr);
        });
    }

    var geoc = new BMap.Geocoder();
    map.addEventListener("click", function (e) {
        /*map.clearOverlays();//移除所有覆盖物（标注）*/
        //通过点击百度地图，可以获取到对应的point, 由point的lng、lat属性就可以获取对应的经度纬度
        var pt = e.point;
        geoc.getLocation(pt, function (rs) {
            var address = rs.address;
            $("#address").val(address);
            $("#lng").val(pt.lng);
            $("#lat").val(pt.lat);
        });
    });


    $("#onlog").click(function () {
        /*parent.data = {
            addr: $("#address").val(),
            lng: $("#lng").val(),
            lat: $("#lat").val()
        };*/
        parent.$("input[name=homeLocal]").val($("#address").val());//资源地址
        parent.$("input[name=homeLng]").val($("#lng").val());//经度
        parent.$("input[name=homeLat]").val($("#lat").val());//纬度
        //关掉对话框
        admin.closeThisDialog();
    });


    $("#s_p_search_btn").click(function () {
        searchPlace($("#searchplace").val());
    });


})

