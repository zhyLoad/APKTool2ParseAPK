*****************************************************

V 1.0  解析APK并获得信息的Demo程序

创作原因：
      目前流行的AXMLPrinter以及AXMLPrinter2第三方依赖来反解析出AndroidManifest文件的方法存在BUG和缺陷，而且Google已经很久不维护了。
	  最近发现Gradle高版本打包生成的APK包解析时，会有解析出乱码的问题，所以本文尝试并测试成功了APKTool这种工具包的可行性，以后将弃用AXMLPrinter。
	  APKTool在Git上有源码维护（github.com/iBotPeaches/Apktool.git），且功能强大，文档丰富，使用广泛，推荐使用该开源项目来解析APK。

*****************************************************


1.依赖APKTool，版本为2.3.4 ,jar包下载地址：https://bitbucket.org/iBotPeaches/apktool/downloads

2.Demo中的两种使用场景简要说明：

         tryToDecodeAll----------------使用时和使用apktool d xxx.apk 命令的效果一样，会在APK所在目录下生成文件夹，并将解析出的所有资源放在该目录下
		 
		 tryToDecodeJustManifestFile  ----------------- 精简了apktool的decode方法，只完成将源文件APK包的AndroidManifest.xml文件解析出来，生成可视化的XML文件。文件在指定的输出目录下，若该目录不存在，则会创建。