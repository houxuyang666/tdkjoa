package com.tdkj.System.common;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/5/28 15:03
 */
public interface OAResultCode {
    //200
    // 请求成功，请求所需要的的数据都会随响应返回
    Integer HTTP_RNS_CODE_200 = 200;
    //请求已被实现，并且服务器创建了一个新的资源，其url将随Location头信息返回
    Integer HTTP_RNS_CODE_201 = 201;
    //服务器以接受此请求，但未处理，后续有可能不会执行此请求
    Integer HTTP_RNS_CODE_202 = 202;
    //服务器已响应此请求，但不需要返回任何实体内容，如果需要返回元信息，可能会包含在实体头部
    Integer HTTP_RNS_CODE_204 = 204;
    //服务器已成功处理部分GET请求，可以使用此响应实现多线程下载和断电续传功能。但请求头必须包含Range信息
    Integer HTTP_RNS_CODE_206 = 206;
    //代表返回的是xml信息
    Integer HTTP_RNS_CODE_207 = 207;

    //300
    //当前请求的资源在服务器有一系列的地址回馈，用户需要自行选择一个地址进行重定向
    Integer HTTP_RNS_CODE_300 = 300;
    //请求所需要的的资源已被永久移除，服务器在处理这个类型的资源时通常会重定向到新地址
    Integer HTTP_RNS_CODE_301 = 301;
    //资源临时从多个url响应请求，客户端应继续向原地址发送以后的请求
    Integer HTTP_RNS_CODE_302 = 302;
    //继上次请求后资源没有任何改变，所以此请求消息体不会返回任何东西
    Integer HTTP_RNS_CODE_304 = 304;
    //这个状态码代表请求需要使用指定的代理才能被访问
    Integer HTTP_RNS_CODE_305 = 305;

    //400
    //当前请求需要验证，客户端需要提交一个Authorization头信息来支持服务器验证证书
    Integer HTTP_RNS_CODE_401 = 401;
    //服务器已经理解请求，但是拒绝响应，即使提交了身份验证，通常服务器会在消息体返回拒绝原因
    Integer HTTP_RNS_CODE_403 = 403;
    //请求资源未在服务器上被发现
    Integer HTTP_RNS_CODE_404 = 404;
    //请求中指定的请求方法不能被用于请求响应资源
    Integer HTTP_RNS_CODE_405 = 405;
    //请求中的资源无法满足请求头中包含的条件，因此无法生成响应体
    Integer HTTP_RNS_CODE_406 = 406;
    //客户端需要在开启指定代理的情况下发送身份验证信息
    Integer HTTP_RNS_CODE_407 = 407;
    //请求超时
    Integer HTTP_RNS_CODE_408 = 408;
    //请求的资源已经永久消失
    Integer HTTP_RNS_CODE_410 = 410;
    //请求头中需要包含 Content-Length
    Integer HTTP_RNS_CODE_411 = 411;
    //请求头中的信息被服务器任务验证失败
    Integer HTTP_RNS_CODE_412 = 412;
    //服务器认为请求包含的数据太大，拒绝此类请求
    Integer HTTP_RNS_CODE_413 = 413;
    //请求的url长度超过服务器可以解释的最大长度
    Integer HTTP_RNS_CODE_414 = 414;
    //请求所需的资源和实体不是服务器可以处理的格式
    Integer HTTP_RNS_CODE_415 = 415;
    //请求中包含了Range信息，但指定数据范围超出资源可用范围
    Integer HTTP_RNS_CODE_416 = 416;
    //请求中含有语义错误
    Integer HTTP_RNS_CODE_422 = 422;
    //请求所需的资源被锁定
    Integer HTTP_RNS_CODE_423 = 423;
    //由于之前的请求失败导致这一次的失败
    Integer HTTP_RNS_CODE_424 = 424;
    //该请求因法律原因不可用
    Integer HTTP_RNS_CODE_451 = 451;

    //500
    // 服务器遇到了一个未曾预料到的情况，导致它无法完成这个请求
    Integer HTTP_RNS_CODE_500 = 500;
    //服务器不支持请求中所需要的的某一个功能
    Integer HTTP_RNS_CODE_501 = 501;
    //当前服务器作为代理或网关时，从上游接收到无效的响应
    Integer HTTP_RNS_CODE_502 = 502;
    //服务器临时维护或过载，服务器无法处理当前请求，这个状态是临时的，将在一段时间后恢复
    Integer HTTP_RNS_CODE_503 = 503;
    //服务器作为代理或网关时，未能在指定时间内接收到上游服务器的响应
    Integer HTTP_RNS_CODE_504 = 504;
    //服务器不支持当前HTTP版本的请求
    Integer HTTP_RNS_CODE_505 = 505;
    //代表服务器出现配置错误
    Integer HTTP_RNS_CODE_506 = 506;
    //服务器无法完成请求所需要储存的内容，这个状态被认为是临时的
    Integer HTTP_RNS_CODE_507 = 507;
    //这不是一个官方的状态码，它代表着服务器达到了带宽限制
    Integer HTTP_RNS_CODE_509 = 509;
    //获取资源所需要的的策略并没有被满足
    Integer HTTP_RNS_CODE_510 = 510;

}
