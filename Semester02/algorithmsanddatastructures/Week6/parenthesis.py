_ = input()
line = input()

stack = []
match = {}
count = {}

for i, char in enumerate(line):
    if char == '(':
        stack.append(i)

    if char == ')' and stack:
        match[i] = stack.pop()
        count[i] = 1
        j = match[i] - 1

        if j in match:
            count[i] += count[j]

print(sum(count.values()))