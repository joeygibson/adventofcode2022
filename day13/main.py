#!/usr/bin/env python3
import json
import sys


def part1(lines: list[str]) -> int:
    lines = [json.loads(line) for line in lines]

    groups = [lines[i * 2:(i + 1) * 2] for i in range((len(lines) + 2 - 1) // 2)]

    group_results = []
    for (group_id, group) in enumerate(groups):
        res = ordered_lists(group[0], group[1])

        # print(f'{group}: {res}')
        group_results.append(res)

    print(f'results: {group_results}')

    result = 0
    for index, value in enumerate(group_results):
        if value:
            result += index + 1

    return result


def ordered_lists(a: list, b: list, ordered: bool = False, recurse: bool = True) -> bool:
    if a == b:
        ordered = True
    else:
        for i in range(0, max(len(a), len(b))):
            if len(a) and not len(b):
                ordered = False
                break
            elif len(b) and not len(a):
                ordered = True
                break
            else:
                left = a[0]
                right = b[0]

                if isinstance(left, int) and isinstance(right, int):
                    ordered = ordered_ints(left, right)
                elif isinstance(left, list) and isinstance(right, int):
                    ordered = ordered_lists(left, [right], ordered, False)
                elif isinstance(left, int) and isinstance(right, list):
                    ordered = ordered_lists([left], right, ordered, False)
                else:
                    ordered = ordered_lists(left, right, ordered)

                if not ordered:
                    break

                if ordered and recurse:
                    return ordered_lists(a[1:], b[1:])

    return ordered


def ordered_ints(a: int, b: int) -> bool:
    return a <= b


def get_data(path) -> list[str]:
    with open(path) as f:
        return [x.strip() for x in f.readlines() if x.strip()]


if __name__ == '__main__':
    if len(sys.argv) < 2:
        print('Usage: main.py <filename>')
        sys.exit(1)

    file_name = sys.argv[1]
    data = get_data(file_name)

    print(f'part1: {part1(get_data(file_name))}')
