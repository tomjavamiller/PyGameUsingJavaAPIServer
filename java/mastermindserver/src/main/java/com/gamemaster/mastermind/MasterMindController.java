package com.gamemaster.mastermind;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class MasterMindController {

	// create a GET request
	@GetMapping("/mastermind/genboard")
	public MasterMindBoard newBoard(@RequestParam(value = "numpgs", defaultValue = "5") String numPgs,
			@RequestParam(value = "numcolors", defaultValue = "8") String numColors) {

		return new MasterMindBoard(Short.parseShort(numPgs), Short.parseShort(numColors));
	}

	class grade {
		final short numCorrect;
		final short numColors;

		public grade(short numCorrect, short numColors) {
			this.numCorrect = numCorrect;
			this.numColors = numColors;
		}
		public short getNumCorrect() {
			return numCorrect;
		}
		public short getNumColors() {
			return numColors;
		}
	}
	
	@RequestMapping(
		value = "/mastermind/guess", 
		method = RequestMethod.POST)
	public grade process(@RequestBody Map<String, Object> payload) 
			throws Exception {
		final short CONSUMED = -1;
		final Integer CONSUMED_INTEGER = Integer.valueOf(CONSUMED);

		//System.out.println(payload);
		if (payload.get("guess") instanceof ArrayList && payload.get("board") != null) {
			ArrayList<Integer> guessLst = (ArrayList<Integer>) payload.get("guess");

			Object boardObj = payload.get("board");
			MasterMindBoard theBoard = null;
			if (boardObj instanceof String) {
			    //System.out.println("got String");
			    theBoard = new MasterMindBoard((String)payload.get("board")); 
			} else if(boardObj instanceof Integer) {
			    //System.out.println("got Integer");
			    theBoard = new MasterMindBoard((Integer)payload.get("board"));
			}
			short[] testPuzzle = theBoard.getPuzzle(); 
			//System.out.println("the board I got was:");
			//System.out.println(theBoard);
			// System.out.println("the board numbers:");
			//         		System.out.println(testPuzzle);
			//         		System.out.printf("   this is the guess:  %s\n", payload.get("guess").getClass().getName());
			short numCorrect = 0;
			short numColor = 0;

			// 1. find all fully correct (right color in right position)
			if (guessLst.size() > testPuzzle.length) {
				// send user error here
				throw new Exception("User Error: guess has more possitions then puzzle given.");
			}
			int minLen = Math.min(testPuzzle.length, guessLst.size());
			for (int i = 0; i < minLen; i++) {
				//System.out.printf("   guess %d == %d\n", testPuzzle[i], guessLst.get(i).intValue()); 
				if (testPuzzle[i] == guessLst.get(i).intValue()) {
					numCorrect++;
					testPuzzle[i] = CONSUMED;
					guessLst.set(i,CONSUMED_INTEGER);
				}
			}
			// 2. check done
			if (numCorrect == testPuzzle.length) {
				return new grade(numCorrect,(short)0); 
			}
			// 3. check for correct colors
			for (int i = 0; i < minLen; i++) {
				if(testPuzzle[i] != CONSUMED) {
					//System.out.printf("   looking for color[%d] %d\n", i, testPuzzle[i]);
					for (int j = 0; j < guessLst.size(); j++) {
						//System.out.printf("   if %d == [%d] %d\n", testPuzzle[i], j, guessLst.get(j).intValue()); 
						if(guessLst.get(j) != CONSUMED_INTEGER && guessLst.get(j).intValue() == testPuzzle[i]) {
							//System.out.println("   yes   ");
							numColor++;
							testPuzzle[i] = CONSUMED;
							guessLst.set(j,CONSUMED_INTEGER);
							break;
						}
					}
				}
			}
			return new grade(numCorrect,numColor); 
		}
		return new grade((short)0,(short)0);
	}
}
