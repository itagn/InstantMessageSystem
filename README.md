# InstantMessageSystem
:whale: 本科毕业设计：基于DES加密的即时通信聊天系统

    author: 蔡东
    desc: 本科毕业设计-基于DES加密的即时通信聊天系统（Windows系统运行）
    createdOn: 2017/3/25

## 项目详细介绍

[文档](http://itagn.xyz/#docs/InstantMessageSystem)

## 操作说明 
#### 配置环境，安装Java，配置数据库MySQL
    
    1.用Eclipse编辑器分别打开两个项目
    2.运行client.java文件和server.java文件
    3.MySQL配置忘记导出，只能靠记忆来完善

## MySQL表格

    1.MySQL表 user

|name  |password|status|ip  |port  |addpy |
|:----:|:----:|:----:|:----:|:----:|:----:|
|用户网名|用户密码|用户状态（登录/登出）|客户端ip地址|客户端端口号|用户未处理的好友请求|


    2.MySQL表 私人表 _username

|friends|msg  |readmsg|chating|file|
|:----:|:----:|:----:|:----:|:----:|
|用户的好友|未读信息|是否存在未读信息|与好友之间的聊天窗口是否开启|是否存在未处理的文件传输请求|

## 客户端代码

    client.java: 客户端主程序，负责载入的界面
    clientThread.java: 客户端辅助线程，负责处理服务器的响应
    alert.java: 用于提示用户操作信息
    repaint.java: 重绘达到实时效果，并解决界面最小化再打开后空白界面
    ddd.java: 调用音频线程
    des.java: des加密算法，进行消息的加密解密
    res.java: 负责处理添加好友权限请求

## 服务器代码

    server.java: 服务器主程序，负责载入界面
    serverThread.java: 服务器辅助线程，避免卡死主线程，负责客户端各种请求的处理和数据库操作
    alert.java: 用于提示用户操作信息
    repaint.java: 重绘达到实时效果，并解决界面最小化再打开后空白界面

## 客户端外部文件说明

    ddd.mid: 语音提示声音
    savePath.txt: 客户端接受传输文件的保存路径
    serverip.txt: 服务器的IP地址
    keyFile: DES加密解密的密钥
    img: 个性化商标
    
## 服务器外部文件说明

    savePath.txt: 服务器接手客户端离线发送的文件路径
    img: 个性化商标

## 关于打包成window可以运行的exe文件
这里推荐使用JavaLuanch和JAR2EXE两个软件，详细内容请看[JAVA2EXE.md](https://github.com/itagn/InstantMessageSystem/blob/master/JAVA2EXE.md)。

    步骤大概就是：
    1.先导出两个项目的jar包
    2.jar2exe软件实现jar包切换成exe文件（注：此时的exe并不能运行，因为没有环境）
    3.同级目录下复制轻量版本jre文件夹，JavaLuanch软件把jre环境和exe结合起来，就完成了exe
    
作者：微博 [@itagn][1] - Github [@itagn][2] 

[1]: https://weibo.com/p/1005053782707172
[2]: https://github.com/itagn
