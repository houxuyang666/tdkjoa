/*
* 作者：柴工
* 2020/7/8
* */
var PublicFun = {
    /*
    * 引入Css文件
    * @param path
    * */
    ImportCss: function (path) {
        if (!path || path.length === 0) {
            throw new Error('参数"path"错误');
        }
        var head = document.getElementsByTagName('head')[0];
        var links = document.createElement("link");
        links.href = path;
        links.rel = "stylesheet";
        links.type = "text/css";
        head.appendChild(links);
    },

    /*
    * 引入Js文件
    * @param path
    * */
    ImportJs: function (path) {
        if (!path || path.length === 0) {
            throw new Error('参数"path"错误');
        }
        var head = document.getElementsByTagName("head")[0];
        var scripts = document.createElement("script");
        scripts.src = path;
        scripts.type = "text/javascript";
        head.appendChild(scripts);
    },

    /**
     * 成功
     * @param title
     * @returns {*}
     */
    LayerMsgSuccess: function (title) {
        return layer.msg(title, {
            icon: 1,
            shade: this.shade,
            scrollbar: false,
            time: 2000,
            shadeClose: true
        });
    },

    /**
     * 失败
     * @param title
     * @returns {*}
     */
    LayerMsgError: function (title) {
        return layer.msg(title, {
            icon: 2,
            shade: this.shade,
            scrollbar: false,
            time: 3000,
            shadeClose: true
        });
    },

    //绑定公司下拉框
    BindCompanyList: function () {
        $.ajax({
            url: "/company/selectallcompany",
            dataType: "json",
            type: "post",
            success: function (res) {
                var data = res.data;
                //layer.alert(JSON.stringify(data));
                var html = '';
                if (data != null && data != "") {
                    for (var i = 0; i < data.length; i++) {
                        html += '<option value="' + data[i].companyId + '">' + data[i].companyName + '</option>';
                    }
                } else {
                    html += "";
                }
                $('#companyList').append(html);
                layui.form.render("select");
            },
            error: function () {
                PublicFun.LayerMsgError("公司下拉框请求失败")
            }
        })
    },


    //ajax请求异步通用函数
    FunPostAjax:function (url,data,success,msgerror) {
        $.ajax({
            url:url,
            dataType:"json",
            type:"POST",
            async:false,
            data:data,
            success:success,
            error:function () {
                PublicFun.LayerMsgError(msgerror);
            }
        })
    },

    //请求上传图片的交互
    FunPostAjaxProcessData:function (url,data,success,msgerror) {
        $.ajax({
            url:url,
            dataType:"json",
            type:"POST",
            processData:false,
            contentType: false,
            data:data,
            success:success,
            error:function () {
                PublicFun.LayerMsgError(msgerror);
            }
        })
    },

    //格式日期控件 传入Id名 和 你想要的字符串
    layerDate:function ($id,formats) {
        var obj = new Date();
        layui.laydate.render({
            elem: $id,
            format: formats,
            value: obj,
            isInitValue: true,
            trigger: "click"
        });
    },
    //接收参数
    queryString:function (key) {
        var regex_str = "^.+\\?.*?\\b" + key + "=(.*?)(?:(?=&)|$|#)"
        var regex = new RegExp(regex_str, "i");
        var url = window.location.toString();
        if (regex.test(url)) return RegExp.$1;
        return undefined;
    },
    //格式化时间
    FormatDate:function (datetime,fmt) {
        if(parseInt(datetime)==datetime){
            if(datetime.length==10){
                datetime=parseInt(datetime)*1000;
            }else if(datetime.length==13){
                datetime=parseInt(datetime);
            }
        }
        datetime=new Date(datetime);
        var o={
            "M+" : datetime.getMonth()+1,                 //月份
            "d+" : datetime.getDate(),                    //日
            "h+" : datetime.getHours(),                   //小时
            "m+" : datetime.getMinutes(),                 //分
            "s+" : datetime.getSeconds(),                 //秒
            "q+" : Math.floor((datetime.getMonth()+3)/3), //季度
            "S"  : datetime.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (datetime.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
    },
    //重新渲染表单函数
    RenderForm:function () {
        layui.use('form',function () {
            var form=layui.form;
            form.render();
        })
    },

    //获取菜单数据/menu/getmenu
    GetMenuInfo:function () {
        var jsonMenu;
        PublicFun.FunPostAjax("/menu/getmenu","",function(res){
            if(res.responseCode==200){
                //console.log(res.data);
                jsonMenu=res.data;
            }
        },"菜单接口请求失败！！！");

        var jsonData=eval(jsonMenu);
        //绑定的字段
        var jsonDataTree=[];
        //将json串转换成树形结构
        for (var i in jsonData){  //parentId为0时表示为根节点
            if(jsonData[i].parentid=="0"){
                var tempObject={};
                tempObject.title=jsonData[i].title;
                tempObject.menuid=jsonData[i].menuid;
                tempObject.children=getChildren(tempObject.menuid);
                jsonDataTree.push(tempObject);
            }
        }
        //递归体  即对每条jsonData逐条递归找childs
        function getChildren(id){
            var  tempArray=[];
            for(var i in jsonData){
                if(jsonData[i].parentid==id){
                    var tempChild={};
                    tempChild.title=jsonData[i].title;
                    tempChild.menuid=jsonData[i].menuid;
                    if(selectChildren(jsonData[i].menuid)){   //若存在子节点，继续递归；否则为叶节点，停止递归
                        tempChild.children=getChildren(jsonData[i].menuid);
                    }
                    tempArray.push(tempChild);
                }
            }
            return tempArray;
        }
        function selectChildren(id){   // 是否存在子节点
            for(var i in jsonData){
                if(jsonData[i].parentid==id){
                    return true;
                }
            }
            return false;
        }
        return jsonDataTree;
    }



}





