package com.tuacy.common.utils;

public class ByteUtils {

	public static byte[] shortToByteArray(short s) {
		byte[] targets = new byte[2];
		for (int i = 0; i < 2; i++) {
			int offset = (targets.length - 1 - i) * 8;
			targets[i] = (byte) ((s >> offset) & 0xff);
		}
		return targets;
	}

	public static String dumpByte(byte[] data, int length) {
		if (null == data) {
			return null;
		}
		String print = "";
		for (int i = 0; i < data.length && i < length; i++) {
			byte temp = data[i];
			String hex = Integer.toHexString(temp & 0xFF);
			if (hex.length() == 1) {
				hex = "0" + hex;
			}
			print = print + "0x" + hex.toUpperCase() + ", ";
		}
		return print;
	}

	public static byte[] byteToBitArray(byte b) {
		byte[] bitArray = new byte[8];

		bitArray[0] = (byte) ((b >> 7) & 0x1);
		bitArray[1] = (byte) ((b >> 6) & 0x1);
		bitArray[2] = (byte) ((b >> 5) & 0x1);
		bitArray[3] = (byte) ((b >> 4) & 0x1);
		bitArray[4] = (byte) ((b >> 3) & 0x1);
		bitArray[5] = (byte) ((b >> 2) & 0x1);
		bitArray[6] = (byte) ((b >> 1) & 0x1);
		bitArray[7] = (byte) (b & 0x1);

		return bitArray;
	}

	public static byte bitArrayToByte(byte[] bit) {
		if (bit == null || bit.length != 8) {
			return 0xffffffff;
		}
		return (byte) ((((bit[0] & 0x1) << 7) | ((bit[1] & 0x1) << 6) | ((bit[2] & 0x1) << 5) | ((bit[3] & 0x1) << 4) |
						((bit[4] & 0x1) << 3) | ((bit[5] & 0x1) << 2) | ((bit[6] & 0x1) << 1) | ((bit[7] & 0x1))) & 0xff);
	}
}
