#!/usr/bin/env python3
import sys


def part1(lines: list[str]) -> int:
    lines = [eval(line) for line in lines]

    groups = [lines[i * 2:(i + 1) * 2] for i in range((len(lines) + 2 - 1) // 2)]

    group_results = {}
    for (group_id, group) in enumerate(groups):
        # for pairs in zip(group[0], group[1]):
        #     print(f'pairs: {pairs}')

        res = ordered_lists(group[0], group[1])

        print(f'res: {res}')
        group_results[group_id] = True


def ordered_lists(a: list, b: list) -> bool:
    ordered = False

    if a == b:
        ordered = True
    else:
        for i in range(0, max(len(a), len(b))):
            if i > len(a):
                ordered = True
                break
            elif i > len(b) is None:
                ordered = False
                break
            else:
                if isinstance(a[0], int) and isinstance(b[0], int):
                    if not ordered_ints(a[0], a[1]):
                        ordered = False
                        break
                elif isinstance(a[0], list) and isinstance(b[0], int):
                    if not ordered_lists(a[0], [b[0]]):
                        ordered = False
                        break
                elif isinstance(a[0], int) and isinstance(b[0], list):
                    if not ordered_lists([a[0]], b[0]):
                        ordered = False
                        break
                else:
                    if not ordered_lists(a[0], b[0]):
                        ordered = False
                        break

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
