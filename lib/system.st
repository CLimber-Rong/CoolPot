/*
* StamonVM SFN LIbrary
* @filename stdio.st
* @author CLimber-Rong
*/

import stddef;

def System = class {

    def version;

    def NullTypeID = 1;
	def IntegerTypeID = 2;
    def FloatTypeID = 3;
    def DoubleTypeID = 4;
    def StringTypeID = 5;
    def SequenceTypeID = 6;
    def ClassTypeID = 7;
    def MethodTypeID = 8;
    def ObjectTypeID = 9;

    func __init__(self) {
        self.version = "2.2.6";  //当前版本号
    }

    func Typeof(self, v) {
        //返回类型
        def port = ;
        def arg = v;
        sfn port, arg;
        return arg;
    }

    func Throw(self, s) {
        //抛出异常，同时让虚拟机退出
        def port = ;
        def arg = s;
        sfn port, arg;
    }

    func system(self, s) {
        def port = ;
        sfn port, s;
    }

    func exit(self, n) {
        def port = ;
        sfn port, n;
    }

}.new;