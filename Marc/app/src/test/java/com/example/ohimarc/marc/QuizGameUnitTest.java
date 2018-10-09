package com.example.ohimarc.marc;

import com.example.ohimarc.marc.model.*;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


public class QuizGameUnitTest {

    @Test
    public void testRandomGen(){
        Deck d = new Deck("Test");
        d.addBasicNote("Front0","Back0");
        d.addBasicNote("Front1","Back1");
        d.addBasicNote("Front2","Back2");
        d.addBasicNote("Front3","Back3");
        QuizGame game = new QuizGame(d);
        //System.out.println(d.getCardCopies()[0][0]);
        String [] testArr = game.peekNextCard();
        for(int i = 0;i<testArr.length-1;i++){
            assertTrue(testArr[i] != testArr[i+1]);
        }
        System.out.println(Arrays.toString(game.peekNextCard()));
    }

    @Test
    public void testCheckers(){
        Deck d = new Deck("Test");
        d.addBasicNote("Front0","Back0");
        d.addBasicNote("Front1","Back1");
        d.addBasicNote("Front2","Back2");
        d.addBasicNote("Front3","Back3");
        QuizGame game = new QuizGame(d);
        //System.out.println(d.getCardCopies()[0][0]);
        String [] testArr = game.peekNextCard();
        int index = 0;
        for(int i = 0;i<testArr.length;i++){
            if(testArr[i].equals("Back0")){
                index = i;
                break;
            }
        }
        game.sendAnswer(index);
        assertTrue(game.isCorrect(index));
    }
}