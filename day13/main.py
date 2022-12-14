#!/usr/bin/env python3
import functools
import json
import sys

LT = -1
EQ = 0
GT = 1


def part1(lines: list[str]) -> int:
    lines = [json.loads(line) for line in lines]

    groups = [lines[i * 2:(i + 1) * 2] for i in range((len(lines) + 2 - 1) // 2)]

    group_results = []
    for (group_id, group) in enumerate(groups):
        res = compare(group[0], group[1])

        group_results.append(res)

    print(f'results: {group_results}')

    result = 0
    for index, value in enumerate(group_results):
        if value == LT:
            result += index + 1

    return result


def part2(lines: list[str]) -> int:
    lines = [json.loads(line) for line in lines]

    sorted_lines = sorted(lines, key=functools.cmp_to_key(compare))

    key = sorted_lines.index([[2]]) + 1
    key *= (sorted_lines.index([[6]]) + 1)

    return key


def compare(left: list, right: list) -> int:
    if left == right:
        return EQ
    else:
        if isinstance(left, int) and isinstance(right, int):
            return LT if left < right else GT
        elif isinstance(left, list) and isinstance(right, int):
            return compare(left, [right])
        elif isinstance(left, int) and isinstance(right, list):
            return compare([left], right)
        elif left and right:
            result = compare(left[0], right[0])
            return compare(left[1:], right[1:]) if result == EQ else result

        return GT if left else LT


def get_data(path) -> list[str]:
    with open(path) as f:
        return [x.strip() for x in f.readlines() if x.strip()]


if __name__ == '__main__':
    if len(sys.argv) < 2:
        print('Usage: main.py <filename>')
        sys.exit(1)

    file_name = sys.argv[1]
    data = get_data(file_name)

    # print(f'part1: {part1(data)}')
    print(f'part2: {part2(data)}')
