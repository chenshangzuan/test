# 内部变量测试
import os

# 只有执行入口文件时的返回值才是__main__ ，如果入口文件被导入到别的文件里，此时入口文件的__name__返回值为模块名称
print('__name__:' + __name__)
# 获取导入文件的路径，多层目录以点分割，注意：对当前文件返回None
print('__file__:' + __file__)
# 获取文件的注释
print('__doc__:' + str(__doc__))
# 获取导入文件的路径，多层目录以点分割，注意：对当前文件返回None
print('__package__:' + str(__package__))
# 获取导入文件的缓存路径
print('__cached__:' + str(__cached__))
# 内置函数在这里面
print('__builtins__:' + str(__builtins__))