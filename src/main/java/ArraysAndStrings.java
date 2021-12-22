public class ArraysAndStrings {
	public static void main(String[]args){
		System.out.println("*********** Unique Character Check ****************");
		System.out.println(checkDuplicateCharacters("Martin"));
		System.out.println(checkDuplicateCharacters("Brooks"));
		System.out.println(isUniqueCharacters("Martin"));
		System.out.println(isUniqueCharacters("Brooks"));
		System.out.println("***************************************************");

		System.out.println("*********** Permutation Check ****************");
		System.out.println(isPermutation("robe", "rober"));
		System.out.println(isPermutation("robe", "bore"));
		System.out.println(isPermutation("globe", "globs"));
		System.out.println("***************************************************");
		
		System.out.println("*********** Replace spaces ****************");
		System.out.println(replaceSpaces("Mr John Smith    ", 13));
		System.out.println("***************************************************");
		
		System.out.println("*********** Permutation of Palindrome ****************");
		System.out.println(isPermutationOfPalindrome("Tact Coa"));
		System.out.println("***************************************************");
		
		System.out.println("*********** One Edit Away ****************");
		System.out.println(isOneEditAway("pale", "ple"));
		System.out.println(isOneEditAway("pales", "pale"));
		System.out.println(isOneEditAway("spale", "pale"));
		System.out.println(isOneEditAway("pale", "bale"));
		System.out.println(isOneEditAway("pale", "bae"));
		System.out.println("***************************************************");
		
		
		System.out.println("*********** Compress consecutive string ****************");
		System.out.println(compress("aabcccccaaa"));
		System.out.println(compress("abcdefg"));
		System.out.println("***************************************************");
		
		
		System.out.println("*********** Roatate matrix ****************");
		int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		prettyPrint(matrix);
		System.out.println("_________________________");
		prettyPrint(rotate(matrix));
		System.out.println("***************************************************");
		
		System.out.println("*********** Set Zeros in matrix ****************");
		matrix = new int[][]{{1, 2, 3}, {4, 0, 6}, {7, 8, 0}};
		prettyPrint(matrix);
		setZeros(matrix);
		System.out.println("_________________________");
		prettyPrint(matrix);
		System.out.println("***************************************************");
		
		System.out.println("*********** Is rotation ****************");
		System.out.println(isRotation("waterbottle", "erbottlewat"));
		System.out.println("***************************************************");
	}

	/**
	* 1.1 Is Unique: Implement an algorithm to determine if a string has all unique characters.
	* What if you cannot use additional data structures?
	*/
	private static boolean checkDuplicateCharacters(String str) {
		if (str.length() > 128)
			return false;
		boolean[] chars = new boolean[128];
		for(int i = 0; i < str.length(); i++) {
			int val = str.charAt(i) - 'A';
			if (chars[val])
				return false;
			chars[val] = true;
		}
		return true;
	}

	/**
	 * 1.1 Is Unique: Implement an algorithm to determine if a string has all unique characters.
	 * What if you cannot use additional data structures?
	 */
	private static boolean isUniqueCharacters(String str) {
		int checker = 0;
		for(int i = 0; i < str.length(); i++) {
			int val = str.charAt(i) - 'A';
			if ((checker & (1 << val)) > 0)
				return false;
			checker |= (1 << val);
		}
		return true;
	}

	/**
	 * 1.2 Check Permutation: Given two strings, write a method to decide if one is a permutation of the other.
	 */
	private static boolean isPermutation(String a, String b) {
		if (a.length() != b.length())
			return false;
		
		int[] chars = new int[128];
		for (int i = 0; i < a.length(); i++)
			chars[a.charAt(i) - 'A']++;
		
		for (int i = 0; i < b.length(); i++) {
			if (chars[b.charAt(i) - 'A'] == 0)
				return false;
			chars[b.charAt(i) - 'A']--;
		}
		return true;
	}

	/**
	 * 1.3 URLify: Write a method to replace all spaces in a string with '%20'. You may assume that the string
	 *	has sufficient space at the end to hold the additional characters, and that you are given the "true"
	 *	length of the string. (Note: if implementing in Java, please use a character array so that you can
	 *	perform this operation in place.)
	 *	EXAMPLE
	 *		Input: "Mr John Smith ", 13
	 *		Output: "Mr%20John%20Smith"
	 */
	private static String replaceSpaces(String str, int trueLength) {
		char[] chars = new char[str.length()];
		int newLength = 0;
		
		for (int i = 0; i < trueLength; i++) {
			if (str.charAt(i) == ' ') {
				chars[newLength] = '%';
				chars[newLength + 1] = '2';
				chars[newLength + 2] = '0';
				newLength += 3;
			} else {
				chars[newLength] = str.charAt(i);
				newLength++;
			}
		}
		
		return new String(chars);
	}

	/**
	 * 1.4 Palindrome Permutation: Given a string, write a function to check if it is a permutation of
	 * a palindrome. A palindrome is a word or phrase that is the same forwards and backwards. A
	 * permutation is a rearrangement of letters. The palindrome does not need to be limited to just
	 * dictionary words.
	 * EXAMPLE
	 * 	Input: Tact Coa
	 * 	Output: True (permutations: "taco cat'; "atc o eta·; etc.)
	 * */
	private static boolean isPermutationOfPalindrome(String phrase) {
		/* Check that no more than one character has an odd count. */
		int oddCount = 0;
		int z = Character.getNumericValue('z');
		int a = Character.getNumericValue('a');
		int[] chars = new int[z - a + 1];
		for (int i = 0; i< phrase.length(); i++) {
			int c = Character.getNumericValue(phrase.charAt(i));
			if (a <=  c && z >= c) {
				chars[c - a]++;
				if (chars[c - a] % 2 == 1)
					oddCount++;
				else
					oddCount--;
			}
		}
		return oddCount <= 1;
	}

	/**
	 * 1.5 One Away: There are three types of edits that can be performed on strings: insert a character,
	 * remove a character, or replace a character. Given two strings, write a function to check if they are
	 * one edit (or zero edits) away.
	 * EXAMPLE
	 * 	pale, ple -> true
	 * 	pales, pale -> true
	 * 	spale, pale -> true
	 * 	pale, bale -> true
	 * 	pale, bae -> false
	 * */
	 private static boolean isOneEditAway(String a, String b) {
		 if (Math.abs(a.length() - b.length()) > 1)
			 return false;
		 
		 int count = 0;
		 if (a.length() == b.length()) {
			 for (int i = 0; i < a.length(); i++) {
				 if (a.charAt(i) != b.charAt(i))
					 count++;
				 
				 if (count > 1)
					 return false;
			 }
			 return true;
		 } else if (a.length() > b.length()) {
			 for (int i = 0, j = 0; i < b.length(); i++) {
				 if (a.charAt(j) != b.charAt(i)) {
					 count++;
					 j++;
					 if (a.charAt(j) != b.charAt(i)) {
						count++;
					}
				 }
				 
				 if (count > 1)
					 return false;
				 
				 j++;
			 }
			 return true;
		 } else {
			 for (int i = 0, j = 0; i < a.length(); i++) {
				 if (a.charAt(i) != b.charAt(j)) {
					 count++;
					 j++;
					 if (a.charAt(i) != b.charAt(j)) {
						count++;
					}
				 }
				 
				 if (count > 1)
					 return false;
				 
				 j++;
			 }
			 return true;
		 }
		 
	 }

	 /**
	  * 1.6 String Compression: Implement a method to perform basic string compression using the counts
	  * of repeated characters. For example, the string aabcccccaaa would become a2blc5a3. If the
	  * "compressed" string would not become smaller than the original string, your method should return
	  * the original string. You can assume the string has only uppercase and lowercase letters (a - z).
	  * */
	  private static String compress(String str) {
		  StringBuilder sb = new StringBuilder();
		  int countConsecutive = 0;
		  for (int i = 0; i < str.length(); i++) {
			  countConsecutive++;
			  if (i + 1 >= str.length() || str.charAt(i) != str.charAt(i + 1)) {
				  sb.append(str.charAt(i));
				  sb.append(countConsecutive);
				  countConsecutive = 0;
			  }
			  
			  if (sb.length() > str.length())
				  return str;
		  }
		  return sb.toString();
	  }

	  /**
	   * 1.7 Rotate Matrix: Given an image represented by an NxN matrix, where each pixel in the image is 4
	   * bytes, write a method to rotate the image by 90 degrees. Can you do this in place?
	   * 1, 2, 3             7, 4, 1
	   * 4, 5, 6             8, 5, 2
	   * 7, 8, 9             9, 6, 3
	   * */
	   private static int[][] rotate(int[][] matrix) {
		   int n = matrix.length;
		   for (int i = 0; i < n/2 ; i++){
			   int first = i;
			   int last = n - 1 - i;
			   for (int j = i; j < matrix[i].length; j++) {
				   int offset = j - first;
				   
				   int top = matrix[first][j];
				   
				   // left -> top
				   matrix[first][j] = matrix[last - offset][first];
				   
				   // bottom -> left
				   matrix[last - offset][first] = matrix[last][last - offset];
				   
				   // right -> bottom
				   matrix[last][last - offset] = matrix[i][last];
				   
				   // top -> right
				   matrix[i][last] = top;
			   }
		   }
		   return matrix;
	   }

	   /**
		* 1.8 Zero Matrix: Write an algorithm such that if an element in an MxN matrix is 0, its entire row and
		* column are set to 0.
		* */
		private static void setZeros(int[][] matrix) {
			boolean[] rows = new boolean[matrix.length];
			boolean[] columns = new boolean[matrix[0].length];
			
			for (int i = 0; i < matrix.length; i++){
				for (int j = 0; j < matrix[i].length; j++){
					if (matrix[i][j] == 0) {
						rows[i] = true;
						columns[j] = true;
					}
				}
			}
			
			for (int i = 0; i < matrix.length; i++){
				for (int j = 0; j < matrix[i].length; j++){
					if (rows[i] || columns[j])
						matrix[i][j] = 0;
				}
			}
		}

		/**
		 * 1.9 String Rotation: Assume you have a method isSubstring which checks if one word is a substring
		 * of another. Given two strings, s1 and s2, write code to check if s2 is a rotation of s1 using only one
		 * call to isSubstring (e.g., "waterbottle" is a rotation of" erbottlewat").
		 * */
		 private static boolean isRotation(String s1, String s2) {
			 return isSubstring(s1, s2 + s2) || isSubstring(s1 + s1, s2);
		 }
		 
		 private static boolean isSubstring(String s1, String s2) {
			 return s1.contains(s2) || s2.contains(s1);
		 }
		
		private static void prettyPrint(int[][] arr) {
			for (int row = 0; row < arr.length; row++) {//Cycles through rows
				for (int col = 0; col < arr[row].length; col++) {//Cycles through columns
					System.out.printf("%5d", arr[row][col]); //change the %5d to however much space you want
				}
				System.out.println(); //Makes a new row
			}
		}
}
