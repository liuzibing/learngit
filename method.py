#!/usr/bin/env python3

# 请定义一个函数quadratic(a, b, c)，接收3个参数，返回一元二次方程：

# ax2 + bx + c = 0

# 的两个解。

# 提示：计算平方根可以调用math.sqrt()函数：

# x=(-b+math.sqrt(b*b - 4ac))/2a

import math

# define method 
def quadratic(a, b, c):
    if not isinstance(a,(int,float)):
        raise TypeError('bad operad value of a')
    if not isinstance(b,(int,float)):
        raise TypeError('bad operad value of b')
    if not isinstance(c,(int,float)):
        raise TypeError('bad operad value of c')

    de = b * b - 4 * a * c
    x1 = (-1 * b + math.sqrt(de)) / 2 * a
    x2 = (-1 * b - math.sqrt(de)) / 2 * a
    return x1,x2


a=input('please enter value of a:')
b=input('please enter value of b:')
c=input('please enter value od c:')

result = quadratic(int(a), int(b), int(c))
print(result)







    
    


