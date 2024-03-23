# CoolPot Project Struct

CoolPot 项目源码结构

## 编译器包架构

### `org.coolpot.compiler`

> 一些基本的前端分析器等

#### LexicalAnalysis

* 基于 `OpenEXPro` 的词法分析器 (架构有改动)
* 因 `Stamon` 语言语法特殊性, 不会完全按照 OpenEX 方式解析

#### SourceFile

* 代表一个正在编译的文件

#### SymbolTable

* 代表一个符号表, 每个SourceFile有一个
* 负责存储该文件导入的所有库, 以及当前编译上下文等

> Scope : 代表一个编译上下文, 负责存储该上下文已定义的类和函数

#### Parser

* 代表一个根语法分析器, 负责识别出语句并将其传递至对应的子语法分析器

### `org.coolpot.compiler.tokens`

* CoolPot 词素定义

| 类名           | 描述                  |
|:-------------|:--------------------|
| Token        | 代表一个词素              |
| UnknownToken | 代表一个不符合Stamon语法内的符号 |
| UTF8Token    | 代表一个UTF-8符号         |

### `org.coolpot.compiler.parser`

* CoolPot 子语法分析器

| 类名               | 描述                 |
|:-----------------|:-------------------|
| SubParser        | 子语法分析器接口, 一个实现标准   |
| NullParser       | 语法分析终止占位符, 无任何解析功能 |
| ImportParser     | `import`语句分析       |
| DefParser        | `def`语句分析          |
| SugarClassParser | `class`语句语法糖       |

### `org.coolpot.compiler.node`

* CoolPot 抽象语法树定义

| 类名        | 描述               |
|:----------|:-----------------|
| ASTNode   | 语法树接口,一个实现标准     |
| EmptyNode | 空节点,作占位符         |
| GroupNode | 代表多个节点           |
| [irnode]  | 该包下的节点会被直接转译成字节码 |

### `org.coolpot.compiler.bytecode.ir`

* CoolPot 字节码指令定义

> 具体中间代码的定义以及其如何转义字节码详见 Stamon 文档

| 类名        | 描述                       |
|:----------|:-------------------------|
| STIR      | IR实现标准, 一个抽象类            |
| Nol_IR    | 空操作IR, 占位符 (不实际编译到字节码文件) |
| SFN_IR    | `sfn`指令                  |
| Member_IR | `member` 字节码             |

### `org.coolpot.compiler.bytecode.const`

* 代表Stamon字节码常量池

| 类名                   | 描述               |
|:---------------------|:-----------------|
| ConstTable           | 代表一个常量池表         |
| objects.ConstObject  | 常量池项类型基类, 一个定义标准 |
| objects.ConstInteger | 常量池标准整形          |
| objects.ConstString  | 常量池字符串型          |

## 编译流程

### 读取与源码格式化

1. 由Main解析命令行形参后将要编译的文件路径递交至 `CompilerManager 编译管理器`, 并加载各种编译配置到 `MetaConfig`
2. 编译管理器先查找标准库实现定义, 识别其中的预置接口 (该流程与编译用户脚本基本相同)
3. 然后开始分析用户编译脚本, 由 `org.coolpot.util.Util` 类读取文件内容至词法分析器
4. 之后通过 `SouceFile` 类自带的 `removeText` 方法剔除所有行注释与块注释

### 语法&语义分析

1. 将处理好的 `Token` 递交至 `Parser` 类做语法分析
2. `Parser` 查找语句头, 与子语法分析器进行匹配, 并进入对应子语法分析器进一步解析
3. 最后将编译好的中间代码递交至 `ParserX` 开始语义分析, 检查符号未定义, 重定义等冲突
4. 最后将编译好的中间代码递交至编译后端，开始生成字节码文件