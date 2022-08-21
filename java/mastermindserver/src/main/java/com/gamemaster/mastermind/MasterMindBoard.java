package com.gamemaster.mastermind;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class MasterMindBoard {

	private final static short NUM_SHIFT = 4;
	private final short numPlaces;
	private final short numColors;
	private final BigInteger board;
	private final String guessURL = "<a href=\"/mastermind/guess\">guess</a>";

	public MasterMindBoard(short numPlaces, short numColors) {
		this.numPlaces = numPlaces;
		this.numColors = numColors;
		Random random = new Random();
		BigInteger tgameNum = BigInteger.ZERO;
        //System.out.printf("----new---numPegs:%d---numColors:%d-\n", numPlaces, numColors);
		int aRnd;
		for (int i = 0; i < numPlaces; i++) {
            aRnd = random.nextInt(numColors);
            //System.out.printf("   ->%d<-\n", aRnd);
			//tgameNum = tgameNum.shiftLeft(4).add(BigInteger.valueOf(random.nextInt(numColors)));
			tgameNum = tgameNum.shiftLeft(NUM_SHIFT).add(BigInteger.valueOf(aRnd));
		}

		// now put the numColors
		tgameNum = tgameNum.shiftLeft(NUM_SHIFT).add(BigInteger.valueOf(numColors));
		// now put the numPlaces
		tgameNum = tgameNum.shiftLeft(NUM_SHIFT).add(BigInteger.valueOf(numPlaces));
		board = tgameNum;
	}

	private short getNumAtPossition(int index) {
		return (short) board.shiftRight(index * NUM_SHIFT).mod(BigInteger.TWO.pow(NUM_SHIFT)).intValue();
	}

	public MasterMindBoard(long board) {
		this.board = BigInteger.valueOf(board);
		numPlaces = getNumAtPossition(0);
		numColors = getNumAtPossition(1);
	}

	public MasterMindBoard(String board) {
		this.board = new BigInteger(board);
		numPlaces = getNumAtPossition(0);
		numColors = getNumAtPossition(1);
	}

	public short getColorAt(short pos) {
		//if(pos < 0 || pos > numPlaces) {
		//	throw new Exception(String.format("Error getColorAt pos: %d out-of-bounds",pos));
		//}
		return getNumAtPossition(pos+2);
	}

	public short getNumPlaces() {
		return numPlaces;
	}

	public short getNumColors() {
		return numColors;
	}

	public BigInteger getBoard() {
		return board;
	}

	/* package */ short[] getPuzzle() {
		short[] rtn = new short[numPlaces];
		for (short i = 0; i < numPlaces; i++) {
			rtn[i] = getColorAt(i);
		}
		return rtn;
	}

	@Override
	public String toString() {
		StringBuffer rtn = new StringBuffer("MM Board:");
		rtn.append(" numPlaces: ").append(numPlaces);
		rtn.append(" numColors: ").append(numColors).append(' ');
		short[] puzzle = getPuzzle();
		for (short s : puzzle) {
			rtn.append(s).append(", ");
		}
		return rtn.toString();
	}
}
