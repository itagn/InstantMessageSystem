## Java项目转成exe文件
### 需要准备的软件

    1.jar2exe
    2.JavaLuanch

### 具体的详细步骤

    最开始在合成后期EXE文件的时候找了很多办法都不能解决，Java是1.8版本的，而目前使用最多的生成EXE文件的软件是exe4J，在Java1.8版本上exe4J只能支持Windows32位系统，对于系统是64位的，寻找到了两个软件，分别是jar2exe软件和Java launch软件，具体步骤如下：

    1.eclipse把Java项目导出成jar文件，项目右键-Export...-Java-JARfile-(JARfile为保存jar的路径，点击next)-继续next-点击Main class选主程序，选中主程序后点击OK，然后点击finish。
    
    2.打开j2ewiz.exe，然后选择之前保存的jar文件，运行时要求最低版本（随意，本文选择的是1.7）-选择 Windows 窗口程序（G）-继续next-随意选择功能（本文都没有选择）-点击“下一步”-上面的是exe生成的位置和exe的名字，下面是exe的图标和其他信息，图标自带的需要ico文件而不是png等图片，（生成的exe允不允许被修改，随意），然后点击“下一步”-生成没有Java运行环境的exe。
    
    3.用记事本或者Notepad++打开launcher.cfg，修改最后一行为“-jar.\”（生成的exe文件名）.exe，然后运行"软件名.exe"。
    
    注意：在合成EXE文件的时候也会存在一些问题，所以有以下地方需要注意：jre为java运行的环境，如果项目有其他导入的jar包，将jar包复制到在”jre/lib/ext/”文件夹内。如果项目有其他外部文件，把外部文件复制到同目录下。最终的文件夹只需：jre文件夹（可能含有项目依赖的jar文件）、launcher.cfg、软件名.exe、创建的exe文件你的外部文件。
