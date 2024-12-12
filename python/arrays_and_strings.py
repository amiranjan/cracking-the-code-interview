
def isUniqueCharacters(s: str) -> bool:
    """
    Is Unique: Implement an algorithm to determine if a string has all unique characters.
    :param s:
    :return:
    """
    characters = set()
    for c in s:
        if c in characters:
            return False
        characters.add(c)
    return True

def isUniqueCharactersBitwise(s: str) -> bool:
    """
    Is Unique: Implement an algorithm to determine if a string has all unique characters. What if you
    cannot use additional data structures?
    :param s:
    :return:
    """
    checker = 0
    for c in s:
        value = ord(c) - ord('A')
        if (checker & (1 << value)) > 0:
            return False
        checker |= (1 << value)
    return True

def isUniqueCharactersNoDS(s: str) -> bool:
    """
    Is Unique: Implement an algorithm to determine if a string has all unique characters. What if you
    cannot use additional data structures?
    :param s:
    :return:
    """
    for i in range(len(s) - 1):
        for j in range(i + 1, len(s)):
            if s[i] == s[j]:
                return False
    return True

def isUniqueCharactersAfterSorting(s: str) -> bool:
    """
    Is Unique: Implement an algorithm to determine if a string has all unique characters. What if you
    cannot use additional data structures?
    :param s:
    :return:
    """
    sorted_str = sorted(s)
    for i in range(len(sorted_str) - 1):
        if s[i] == s[i + 1]:
            return False
    return True

def isPermutation(s1: str, s2: str) -> bool:
    """
    1.2 Check Permutation: Given two strings, write a method to decide if one is a permutation of the other.
    :param s1:
    :param s2:
    :return:
    """
    if len(s1) != len(s2):
        return False
    char_count = {}
    for c in s1:
        if c not in char_count.keys():
            char_count[c] = 0
        char_count[c] += 1

    for c in s2:
        if c not in char_count.keys() or char_count[c] == 0:
            return False
        char_count[c] -= 1

    return True

def urlify(s: str, true_length: int) -> str:
    """
    1.3 URLify: Write a method to replace all spaces in a string with '%20'. You may assume that the string
    has sufficient space at the end to hold the additional characters, and that you are given the "true"
    length of the string. (Note: if implementing in Java, please use a character array so that you can
    perform this operation in place.)
    EXAMPLE
        Input: "Mr John Smith ", 13
        Output: "Mr%20John%20Smith"
    :param s:
    :param true_length:
    :return:
    """
    s = list(s)
    index = len(s) - 1
    for i in range(true_length - 1, 0, -1):
        if s[i] == ' ':
            s[index] = '0'
            index -= 1
            s[index] = '2'
            index -= 1
            s[index] = '%'
        else:
            s[index] = s[i]
        index -= 1
    return ''.join(s)

def isPalindromePermutation(s: str) -> bool:
    """
    1.4 Palindrome Permutation: Given a string, write a function to check if it is a permutation of
    a palindrome. A palindrome is a word or phrase that is the same forwards and backwards. A
    permutation is a rearrangement of letters. The palindrome does not need to be limited to just
    dictionary words.
    EXAMPLE
    Input: Tact Coa
    Output: True (permutations: "taco cat'; "atco eta·; etc.)
    :param s:
    :return:
    """
    a = ord('a')
    z = ord('z')
    chars = [0] * (z - a + 1)
    odd = 0
    for char in s:
        val = ord(char.lower())
        if a <= val <= z:
            chars[val - a] += 1
            if chars[val - a] % 2 == 1:
                odd += 1
            else:
                odd -= 1
        #print("char = {}, odd = {}".format(char, odd))

    return odd <= 1

def isOneEditAway(first: str, second: str) -> bool:
    """
    1.s One Away: There are three types of edits that can be performed on strings: insert a character,
    remove a character, or replace a character. Given two strings, write a function to check if they are
    one edit (or zero edits) away.
    EXAMPLE
    pale, ple -> true
    pales, pale -> true
    pale, bale -> true
    pale, bae -> false
    :param first:
    :param second:
    :return:
    """
    if abs(len(first) - len(second)) > 1:
        return False

    shorter = first if len(first) < len(second) else second
    longer = second if len(first) < len(second) else first
    index1, index2 = 0, 0
    diff = False
    while index1 < len(shorter) and index2 < len(longer):
        if shorter[index1] != longer[index2]:
            if diff:
                return False
            diff = True
            if len(shorter) == len(longer):
                index1 += 1
        else:
            index1 += 1
        index2 += 1
    return True

def compress(s: str) -> str:
    """
    1.6 String Compression: Implement a method to perform basic string compression using the counts
    of repeated characters. For example, the string aabcccccaaa would become a2blc5a3. If the
    "compressed" string would not become smaller than the original string, your method should return
    the original string. You can assume the string has only uppercase and lowercase letters (a - z).
    :param s:
    :return:
    """
    chars = []
    count = 0
    result = s
    for i in range(len(s)):
        count += 1
        if i >= len(s) - 1 or s[i] != s[i + 1]:
            chars.append(s[i] + str(count))
            count = 0
        result = ''.join(chars)
        if len(result) > len(s):
            return s
    return result

def rotate(matrix: list[list[int]]):
    """
    1.7 Rotate Matrix: Given an image represented by an NxN matrix, where each pixel in the image is 4
    bytes, write a method to rotate the image by 90 degrees. Can you do this in place?
    :param matrix:
    :return:
    """
    top = 0
    bottom = len(matrix) - 1

    left = 0
    right = len(matrix[0]) - 1

    while top < bottom and left < right:
        prev = matrix[top + 1][left]

        for i in range(left, right + 1):
            curr = matrix[top][i]
            matrix[top][i] = prev
            prev = curr
        top += 1

        for i in range(top, bottom + 1):
            curr = matrix[i][right]
            matrix[i][right] = prev
            prev = curr
        right -= 1

        for i in range(right, left - 1, -1):
            curr = matrix[bottom][i]
            matrix[bottom][i] = prev
            prev = curr
        bottom -= 1

        for i in range(bottom, top - 1, -1):
            curr = matrix[i][left]
            matrix[i][left] = prev
            prev = curr
        left += 1

def setZeros(matrix: list[list[int]]):
    """
    1.8 Zero Matrix: Write an algorithm such that if an element in an MxN matrix is 0, its entire row and
    column are set to 0.
    :param matrix:
    :return:
    """
    rows, columns = set(), set()
    for i in range(len(matrix)):
        for j in range(len(matrix[i])):
            if matrix[i][j] == 0:
                rows.add(i)
                columns.add(j)
    for i in range(len(matrix)):
        for j in range(len(matrix[i])):
            if i in rows or j in columns:
                matrix[i][j] = 0


# Utility Function
def printMatrix(mat: list[list[int]]):
    for row in mat:
        print(row)

def isSubstring(s1: str, s2: str) -> bool:
    return s1 in s2 or s2 in s1

def isRotation(s1: str, s2: str) -> bool:
    """
    1.9 String Rotation: Assume you have a method isSubstring which checks if one word is a substring
    of another. Given two strings, s1 and s2, write code to check if s2 is a rotation of s1 using only one
    call to isSubString (e.g., "waterbottle" is a rotation of "erbottlewat").
    :param s1:
    :param s2:
    :return:
    """
    return isSubstring(s1 + s1, s2)

if __name__ == "__main__":
    print("*********** Unique Character Check ****************")
    s = "Martin"
    print("isUniqueCharacters({}) = {}".format(s, isUniqueCharacters(s)))
    print("isUniqueCharactersBitwise({}) = {}".format(s, isUniqueCharactersBitwise(s)))
    print("isUniqueCharactersNoDS({}) = {}".format(s, isUniqueCharactersNoDS(s)))
    print("isUniqueCharactersAfterSorting({}) = {}".format(s, isUniqueCharactersAfterSorting(s)))
    s = "Brooks"
    print("isUniqueCharacters({}) = {}".format(s, isUniqueCharacters(s)))
    print("isUniqueCharactersBitwise({}) = {}".format(s, isUniqueCharactersBitwise(s)))
    print("isUniqueCharactersNoDS({}) = {}".format(s, isUniqueCharactersNoDS(s)))
    print("isUniqueCharactersAfterSorting({}) = {}".format(s, isUniqueCharactersAfterSorting(s)))
    print("***************************************************")

    print("*********** Permutation Check ****************")
    print("isPermutation(robe, rober) = {}".format(isPermutation("robe", "rober")))
    print("isPermutation(robe, bore) = {}".format(isPermutation("robe", "bore")))
    print("isPermutation(globe, globs) = {}".format(isPermutation("globe", "globs")))
    print("***************************************************")

    print("*********** Replace spaces ****************")
    print("urlify(Mr John Smith    , 13) = {}".format(urlify("Mr John Smith    ", 13)))
    print("***************************************************")

    print("*********** Permutation of Palindrome ****************")
    print("isPalindromePermutation(Tact Coa) = {}".format(isPalindromePermutation("Tact Coa")))
    print("***************************************************")

    print("*********** One Edit Away ****************")
    print("isOneEditAway(pale, ple) = {}".format(isOneEditAway("pale", "ple")))
    print("isOneEditAway(pales, pale) = {}".format(isOneEditAway("pales", "pale")))
    print("isOneEditAway(spale, pale) = {}".format(isOneEditAway("spale", "pale")))
    print("isOneEditAway(pale, bale) = {}".format(isOneEditAway("pale", "bale")))
    print("isOneEditAway(pale, pate) = {}".format(isOneEditAway("pale", "pate")))
    print("isOneEditAway(pale, bae) = {}".format(isOneEditAway("pale", "bae")))
    print("***************************************************")

    print("*********** Compress consecutive string ****************")
    print("compress(aabcccccaaa) = {}".format(compress("aabcccccaaa")))
    print("compress(abcdefg) = {}".format(compress("abcdefg")))
    print("***************************************************")

    print("*********** Rotate matrix ****************")
    # Test case 1
    matrix =[
        [1,  2,  3,  4 ],
        [5,  6,  7,  8 ],
        [9,  10, 11, 12 ],
        [13, 14, 15, 16 ]
    ]
    printMatrix(matrix)
    print("_________________________")
    rotate(matrix)
    printMatrix(matrix)
    print("_________________________")
    # Test case 2
    matrix =[
                [1, 2, 3],
                [4, 5, 6],
                [7, 8, 9]
            ]
    printMatrix(matrix)
    print("_________________________")
    rotate(matrix)
    # Print modified matrix
    printMatrix(matrix)
    print("***************************************************")


    print("*********** Set Zeros in matrix ****************")
    matrix = [
                [1, 2, 3],
                [4, 0, 6],
                [7, 8, 0]
            ]
    printMatrix(matrix)
    setZeros(matrix)
    print("_________________________")
    printMatrix(matrix)
    print("***************************************************")

    print("*********** Is rotation ****************")
    print(isRotation("waterbottle", "erbottlewat"))
    print(isRotation("erbottlewat", "waterbottle"))
    print("***************************************************")


