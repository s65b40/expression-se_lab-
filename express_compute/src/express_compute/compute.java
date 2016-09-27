package express_compute;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class compute {

	StringBuilder suffix;// �洢ת����ĺ�׺���ʽ
	StringBuilder com;// �洢������

	StringBuilder MiddleTolast(String text) {
		StringBuilder cal = new StringBuilder();
		cal.append(text);
		cal = TransplateExp(cal);
		Stack<String> sk = new Stack<String>();// ����ת����ջ
		StringBuilder result = new StringBuilder();// ������
		StringBuilder temp = new StringBuilder();// �������Ӷ�λ���ֺ���ĸ
		StringBuilder tran = new StringBuilder();
		int j = 0;
		for (int i = 0; i < cal.length(); i++) {
			tran.delete(0, tran.length());
			temp.delete(0, temp.length());
			if (cal.charAt(i) == '(') {
				sk.push("(");
			} else if (cal.charAt(i) == ')') {
				while (!sk.isEmpty()) {
					if (sk.peek().equals("(")) {
						sk.pop();
						break;
					} else {
						result.append(sk.pop());
					}
				}
			} else if (cal.charAt(i) == '+' || cal.charAt(i) == '-' || cal.charAt(i) == '*' || cal.charAt(i) == '/') {
				while (!sk.empty() && !sk.peek().equals("(")// ������Ƚ����ȼ������ջ�ڵ���������ȼ�������磬��ôһֱ��ջ������Ϊֹ
						&& this.compute_priority(sk.peek()) >= this.compute_priority(cal.charAt(i))) {
					result.append("#" + sk.pop() + "#");
				}
				sk.push(Character.toString(cal.charAt(i)));
			} else if (Character.isDigit(cal.charAt(i))) {
				j = i;
				while (j < cal.length() && Character.isDigit(cal.charAt(j))) {
					temp.append(cal.charAt(j));
					j++;
				}
				result.append("#" + temp.toString() + "#");
				if (j < cal.length() && Character.isLetter(cal.charAt(j))) {
					sk.push("*");
				}
				i = j - 1;
			} else if (Character.isLetter(cal.charAt(i))) {
				j = i;
				while (j < cal.length() && Character.isLetter(cal.charAt(j))) {
					temp.append(cal.charAt(j));
					j++;
				}
				result.append("#" + (temp.toString()) + "#");
				i = j - 1;
			}

		}
		while (!sk.isEmpty()) {
			result.append("#" + sk.pop() + "#");
		}
		this.suffix = result;
		return result;
	}

	StringBuilder TransplateExp(StringBuilder ch) {
		/*
		 * ����������������^���˷��Ĵ������в���chΪ�������ַ��� ����ѭ��ƥ��^�ķ�ʽ���в��ң������ҵ���^��ʱ��ʼ��ǰ��������ʼ
		 * ���ս�λ�ã�������γ˷�չ��Ϊ�˷���ʽ
		 */
		int findindex = 0;
		int startindex = 0;
		int endindex = 0;
		int times = 0;
		StringBuilder temp = new StringBuilder();
		while (ch.indexOf("^") != -1) {
			findindex = ch.indexOf("^");
			startindex = findindex;
			endindex = findindex;
			temp.delete(0, ch.length());
			while (startindex >= 0) {
				// �����λ��
				if (ch.charAt(startindex) == '+' || ch.charAt(startindex) == '-' || ch.charAt(startindex) == '*'
						|| ch.charAt(startindex) == '/' || ch.charAt(startindex) == '('
						|| Character.isDigit(ch.charAt(startindex))) {
					break;
				}
				startindex--;
			}
			if (startindex != 0) {
				startindex += 1;
			}
			while (endindex < ch.length()) {
				// ���յ�λ��
				if (ch.charAt(endindex) == '+' || ch.charAt(endindex) == '-' || ch.charAt(endindex) == '*'
						|| ch.charAt(endindex) == '/' || ch.charAt(endindex) == ')') {
					break;
				}
				endindex++;
			}
			times = Integer.parseInt(ch.substring(findindex + 1, endindex));// ���˷������洢
			for (int i = 0; i < times; i++) {
				temp.append(ch.substring(startindex, findindex));
				temp.append('*');
			}
			temp.setLength(temp.length() - 1);
			ch.replace(startindex, endindex, temp.toString());
		}
		return ch;
	}

	StringBuilder compute_suffix() {
		Stack<String> sk = new Stack<String>();
		StringBuilder temp = new StringBuilder();
		StringBuilder aBuilder = new StringBuilder();
		StringBuilder bBuilder = new StringBuilder();
		StringBuilder result = new StringBuilder();
		Pattern myPattern = Pattern.compile("#+");//������Ѿ��Ŷ�ȥ�������ҷָ�
		if (this.suffix.charAt(0) == '#') {
			this.suffix.delete(0, 1);
		}
		String[] stt = myPattern.split(this.suffix);
		for (int i = 0; i < stt.length; i++) {
			aBuilder.delete(0, aBuilder.length());
			bBuilder.delete(0, bBuilder.length());
			temp.delete(0, temp.length());
			temp.append(stt[i]);
			if (temp.toString().equals("+") && !sk.isEmpty()) {
				aBuilder.append(sk.pop());
				bBuilder.append(sk.pop());
				if (this.isNumeric(aBuilder.toString()) && this.isNumeric(bBuilder.toString())) {
					sk.push(Integer
							.toString(Integer.parseInt(aBuilder.toString()) + Integer.parseInt(bBuilder.toString())));

				} else {
					temp.delete(0, temp.length());
					temp.append(aBuilder);
					temp.append("+");
					temp.append(bBuilder);
					sk.push(temp.toString());
				}
			} else if (temp.toString().equals("-") && !sk.isEmpty()) {
				aBuilder.append(sk.pop());
				bBuilder.append(sk.pop());
				if (this.isNumeric(aBuilder.toString()) && this.isNumeric(bBuilder.toString())) {
					sk.push(Integer
							.toString(Integer.parseInt(bBuilder.toString()) - Integer.parseInt(aBuilder.toString())));

				} else {
					temp.delete(0, temp.length());
					temp.append(aBuilder);
					temp.insert(0, '-');
					temp.insert(0, bBuilder);
					sk.push(temp.toString());
				}
			} else if (temp.toString().equals("*") && !sk.isEmpty()) {
				aBuilder.append(sk.pop());
				bBuilder.append(sk.pop());
				if (this.isNumeric(aBuilder.toString()) && this.isNumeric(bBuilder.toString())) {
					sk.push(Integer
							.toString(Integer.parseInt(bBuilder.toString()) * Integer.parseInt(aBuilder.toString())));

				} else {
					temp.delete(0, temp.length());
					temp.append(aBuilder);
					temp.append('*');
					temp.append(bBuilder);
					sk.push(temp.toString());
				}

			} else if (temp.toString().equals("/") && !sk.isEmpty()) {
				aBuilder.append(sk.pop());
				bBuilder.append(sk.pop());
				if (this.isNumeric(aBuilder.toString()) && this.isNumeric(bBuilder.toString())) {
					sk.push(Integer
							.toString(Integer.parseInt(bBuilder.toString()) / Integer.parseInt(aBuilder.toString())));

				} else {
					temp.delete(0, temp.length());
					temp.append(bBuilder);
					temp.insert(0, '/');
					temp.insert(0, aBuilder);
					sk.push(temp.toString());
				}
			} else {
				sk.push(temp.toString());
			}
		}
		result.append(sk.pop());
		this.com = result;
		return result;
	}

	StringBuilder simple_conpute(String code) {
		StringBuilder temp = new StringBuilder();
		StringBuilder result = new StringBuilder();
		String[] arr;
		Pattern sourcePattern = Pattern.compile("\\w+=\\d+");
		Matcher sourceMatcher = sourcePattern.matcher(code);
		result.append(this.suffix);
		while (sourceMatcher.find()) {
			temp.delete(0, temp.length());
			arr = sourceMatcher.group().split("=");
			temp.append(result.toString().replaceAll(arr[0], arr[1]));
			result.delete(0, result.length());
			result.append(temp);

		}
		this.suffix = result;
		this.compute_suffix();
		return result;
	}

	boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) > '9' || str.charAt(i) < '0') {
				return false;
			}
		}
		return true;
	}

	int compute_priority(StringBuilder p) {
		if (p.equals("+") || p.equals("-")) {
			return 1;
		} else if (p.equals("*") || p.equals("/")) {
			return 2;
		} else if (p.equals("(")) {
			return 3;
		} else {
			return 100;
		}
	}

	int compute_priority(String p) {
		if (p.equals("+") || p.equals("-")) {
			return 1;
		} else if (p.equals("/") || p.equals("/")) {
			return 2;
		} else if (p.equals("(")) {
			return 3;
		} else {
			return 100;
		}
	}

	int compute_priority(Character p) {
		if (p.equals('+') || p.equals('-')) {
			return 1;
		} else if (p.equals('*') || p.equals('/')) {
			return 2;
		} else if (p.equals('(')) {
			return 3;
		} else {
			return 100;
		}
	}
}