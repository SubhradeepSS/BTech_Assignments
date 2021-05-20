import math
from collections import deque

ADDRESSES = [4, 8, 20, 24, 28, 36, 44, 20, 24, 28, 36, 40, 44, 68, 72, 92, 96, 100, 104, 108, 112, 100, 112, 116, 120, 128, 140]
ADDRESS_SIZE = 16
INSTRUCTION_SIZE = 32

BLOCK_SIZE = 4  # in bytes
NO_OF_ROWS = 8  # must be multiple of 2
WAYS = 1  # can be any number (n)
MAX_STORAGE_BITS = 800  # max number of bits all fields can take, tag, valid, LRU, data, etc.

# Set Associative requires LRU for each way ceil(log n)
# Fully Associative requires LRU for each row ceil(log r)

MISS_COST = 18 + (3 * BLOCK_SIZE)
HIT_COST = 1


def checkDirectMap():
    row_bits = math.ceil(math.log(NO_OF_ROWS, 2))
    index_bits = math.ceil(math.log(BLOCK_SIZE, 2))
    tag_bits = ADDRESS_SIZE - row_bits - index_bits
    valid_bits = 1
    row_size = tag_bits + (8 * BLOCK_SIZE) + valid_bits
    table_size = row_size * NO_OF_ROWS
    if table_size > MAX_STORAGE_BITS:
        print("Cache is too large, change your numbers: " + str(table_size) + "/" + str(MAX_STORAGE_BITS))
    else:
        print("Cache is within size constraints: " + str(table_size) + "/" + str(MAX_STORAGE_BITS))


def checkSetAssociative():
    row_bits = math.ceil(math.log(NO_OF_ROWS, 2))
    index_bits = math.ceil(math.log(BLOCK_SIZE, 2))
    tag_bits = ADDRESS_SIZE - row_bits - index_bits
    valid_bits = 1
    LRU_bits = math.ceil(math.log(WAYS, 2))
    row_size = (tag_bits + (8 * BLOCK_SIZE) + valid_bits + LRU_bits) * WAYS
    table_size = row_size * NO_OF_ROWS
    if table_size > MAX_STORAGE_BITS:
        print("Cache is too large, change your numbers: " + str(table_size))
    else:
        print("Cache is within size constraints: " + str(table_size) + "/" + str(MAX_STORAGE_BITS))


def checkFullyAssociative():
    index_bits = math.ceil(math.log(BLOCK_SIZE, 2))
    tag_bits = ADDRESS_SIZE - index_bits
    LRU_bits = math.ceil(math.log(NO_OF_ROWS, 2))
    valid_bits = 1
    row_size = tag_bits + (8 * BLOCK_SIZE) + valid_bits + LRU_bits
    table_size = row_size * NO_OF_ROWS
    if table_size > MAX_STORAGE_BITS:
        print("Cache is too large, change your numbers: " + str(table_size))
    else:
        print("Cache is within size constraints: " + str(table_size) + "/" + str(MAX_STORAGE_BITS))


def missTime():
    print("A cache miss will cost you: " + str(MISS_COST) + " cycles")


def simulateDirectMap():
    tags = [0] * NO_OF_ROWS
    valid = [0] * NO_OF_ROWS
    miss_count = 0
    total_instructions = 0
    for i in range(0,3):
        print("Address\tTag\tRow\tOffset\tCache Status")
        print("--------------------------------------------")
        for addr in ADDRESSES:
            #  addr = ADDRESSES[i]
            offset = addr % BLOCK_SIZE
            row = (addr // BLOCK_SIZE) % NO_OF_ROWS
            tag = addr // (BLOCK_SIZE * NO_OF_ROWS)
            print(str(addr) + "\t" + str(tag) + "\t" + str(row) + "\t" + str(offset), end="\t")
            if valid[int(row)] == 0:
                print("placing item")
                valid[int(row)] = 1
                tags[int(row)] = tag
            elif tag != tags[int(row)]:
                tags[int(row)] = tag
                print("Cache Miss - updating row " + str(row))
                if i > 0:
                    miss_count += 1
            else:
                print("Cache Hit on row " + str(row))
            if i > 0:
                total_instructions += 1

        print("END OF CYCLE " + str(i))
        print()
        print()

    print("row\tvalid\ttag")
    print("-------------------")
    for j in range(0, NO_OF_ROWS):
        print(str(j) + "\t" + str(valid[j]) + "\t" + str(tags[j]))
    cpi = ((MISS_COST * miss_count) + (total_instructions - miss_count)) / total_instructions
    print("\nCPI: " + str(cpi))
    print("SIMULATION COMPLETED!!")


def simulateSetAssociative():
    tags = [[-1 for i in range(0, WAYS)] for i in range(0, NO_OF_ROWS)]
    valid = [[0 for i in range(0, WAYS)] for i in range(0, NO_OF_ROWS)]
    LRU = [deque() for i in range(0,NO_OF_ROWS)]

    miss_count = 0
    total_instructions = 0

    for i in range(0, 3):
        print("Address\tTag\tRow\tOffset\tCache Status")
        print("--------------------------------------------")
        for addr in ADDRESSES:
            offset = addr % BLOCK_SIZE
            row = (addr // BLOCK_SIZE) % NO_OF_ROWS
            tag = addr // (BLOCK_SIZE * NO_OF_ROWS)
            print(str(addr) + "\t" + str(tag) + "\t" + str(row) + "\t" + str(offset), end="\t")
            flag = False
            if (tag in tags[row]) and (valid[row][tags[row].index(tag)]): # if our tag is in our row and its valid
                print("Cache Hit")
                if i > 0:
                    total_instructions += 1
                continue # go to the next address, we found this one

            for j in range(0,WAYS): # if we couldn't find it, see if there is an open spot
                if valid[row][j] == 0:
                    tags[row][j] = tag
                    if j in LRU[row]:
                        LRU[row].remove(j)
                    LRU[row].append(j)
                    valid[row][j] = 1
                    flag = True
                    print("added item for the first time")
                    break
            if flag == False: # The tag was wrong
                leastUsedWay = LRU[row].popleft()
                tags[row][leastUsedWay] = tag
                if i > 0:
                    miss_count += 1
                LRU[row].append(leastUsedWay)
                print("Cache Miss - updating entry")

            if i > 0:
                total_instructions += 1
        print("END OF CYCLE: " + str(i))
        print()
        print()

    print("row\tvalid\ttag\t")
    print("-------------------")
    for j in range(0, NO_OF_ROWS):
        print(str(j) + "\t", end="")
        for k in range(0, WAYS):
            print(str(valid[j][k]) + "\t" + str(tags[j][k]) ,end="")
        print("")


    cpi = ((MISS_COST * miss_count) + (total_instructions - miss_count)) / total_instructions
    print("\nCPI: " + str(cpi))
    print("SIMULATION COMPLETED!!")


def simulateFullyAssociative():
    tags = [-1] * NO_OF_ROWS
    valid = [0] * NO_OF_ROWS
    LRU = deque()

    miss_count = 0
    total_instructions = 0

    for i in range(0, 3):
        print("Address\tTag\tOffset\tCache status")
        print("------------------------------------")
        for addr in ADDRESSES:
            offset = addr % BLOCK_SIZE
            tag = addr // (BLOCK_SIZE)
            print(str(addr) +'\t' + str(tag) + '\t' + str(offset), end="\t")

            # see if tag is in table - hit
            if tag in tags:
                location = tags.index(tag)
                if location in LRU:
                    LRU.remove(location)
                LRU.append(location)
                print("Cache Hit")

            # see if there is an invalid row, - miss, add it
            elif 0 in valid:
                location = valid.index(0)
                tags[location] = tag
                valid[location] = 1
                if i > 0:
                    miss_count += 1
                if location in LRU:
                    LRU.remove(location)
                    print("THIS SHOULDNT HAPPEN!!!!!!!!!!!!")
                LRU.append(location)
                print("Cache Miss - adding to empty row")

            # else, find least recently used and update - miss
            else:
                leastUsedLoc = LRU.popleft()
                tags[leastUsedLoc] = tag
                if i > 0:
                    miss_count += 1
                if leastUsedLoc in LRU:
                    LRU.remove(leastUsedLoc)
                    print("THIS SHOULDNT HAPPEN!!!!!!!!!!!!")
                LRU.append(leastUsedLoc)
                print("Cache Miss - replacing row")

            if i > 0:
                total_instructions += 1
        print()
        print()

    print("row\tvalid\ttag")
    print("-------------------")
    for j in range(0, NO_OF_ROWS):
        print(str(j) + "\t" + str(valid[j]) + "\t" + str(tags[j]))
    cpi = ((MISS_COST * miss_count) + (total_instructions - miss_count)) / total_instructions
    print("\nCPI: " + str(cpi))
    print("SIMULATION COMPLETED!!")


def main():
    checkDirectMap()
    checkSetAssociative()
    checkFullyAssociative()

    missTime()

    print("\n\nDIRECT MAPPED SIMULATION\n\n")
    simulateDirectMap()

    print("\n\n------------------------------------------------------------------------------\n\n")
    print("\n\nSET ASSOCIATIVE SIMULATION\n\n")
    simulateSetAssociative()

    print("\n\n------------------------------------------------------------------------------\n\n")
    print("\n\nFULLY ASSOCIATIVE SIMULATION\n\n")
    simulateFullyAssociative()


if __name__ == "__main__":
    print()
    main()