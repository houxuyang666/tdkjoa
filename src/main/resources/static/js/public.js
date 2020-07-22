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
            processData:false,
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



}





