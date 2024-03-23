# CoolPot

A Stamon program language compiler implements.

Stamon编程语言的静态编译器实现

## Project struct

[简体中文](STRUCT.md)

[字节码规范](STVM字节码规范.md)

[指令集规范](STVM指令集规范.md)

## How to use

* In your console
  * `coolpot -h` 
  * `coolpot -?` 
  * `coolpot --help`

## Runtime

<p><strong>Warn</strong>: We can't guarantee that the built-in 
runtime will achieve the same features and execution 
efficiency as StamonVM, the runtime only tests the 
compiler for bugs, and we strongly recommend that
you use the native StamonVM to execute 
your bytecode.</p>

<p><strong>警告</strong>: 
我们无法保证内置的运行时可以达到与StamonVM相同的特性与执行效率, 该运行时仅测试编译器是否存在BUG，
我们强烈建议您使用原生StamonVM来执行您的字节码.</p>

### CommandLine

> -?, -h, --help             
  * Print helper message.
  * 打印命令行帮助信息

> -v, --version
  * Print compiler version.
  * 打印编译器版本

> -o, --out
  * Set output file name.
  * 设置输出文件名\
      Args: filename | file name

> --ns, --nosfn              
  * Disable sfn command.   
  * 禁用用户脚本SFN指令使用权限

> -r, --runtime  
  * Enable script runtime.
  * 启动内置脚本运行时