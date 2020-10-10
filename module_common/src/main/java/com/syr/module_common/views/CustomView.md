
自定义View主要实现onMesure + onDraw
自定义ViewGroup主要实现onMesure + onLayout

LayoutParams是什么？
封装了Layout的位置，宽，高等信息的内部类(ViewGroup中)

MeasureSpec是什么？
MeasureSpec是View中的内部类，基本都是二进制运算。
int是32位(4字节)的，用高两位表示mode，低30位表示size，MODE_SHIFT = 30的作用是位移

MeasureSpec.UNSPECIFIED：不对View大小做限制
MeasureSpec.EXACTLY：确切的大小，如20dp
MeasureSpec.AT_MOST：大小不可超过某个值，如matchParent

怎么计算MeasureSpec？

getMeasuredWidth:在measure()之后
getWidht:在layout()之后

