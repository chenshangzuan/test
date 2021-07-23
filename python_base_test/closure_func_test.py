def move_one_step(start, next):
    print('move to ' + str(start + next))


def move(start):
    # 闭包函数：嵌套内部函数 + 外部函数的局部变量
    def move_next(next):
        print('move to ' + str(start + next) + '.')
    return move_next


def move_record(start):
    # 闭包函数
    pos_record = [start]

    def move_next(next):
        # 闭包函数内不能修改外部函数变量的引用，但能修改其内容
        pos_record.append(next)
        print('move record ' + str(pos_record) + '.')
    return move_next


if __name__ == '__main__':
    move_one_step(1, 2)
    # 柯里化Currying
    move(1)(2)
    move(1)(3)

    # 记忆功能
    move_next = move_record(1)
    move_next(2)
    move_next(3)
    move_next(4)