package com.thoughtworks.bowlingscoring;

import com.thoughtworks.bowlingscoring.exception.InvalidScoreCountException;
import com.thoughtworks.bowlingscoring.exception.InvalidScoreValueException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class BowlingScoringTest {

    @Test
    public void testCalculate() throws Exception {
        BowlingScoring bowlingScoring1 = new BowlingScoring("1 2 3 4");
        assertThat(bowlingScoring1.calculate(), equalTo(10));
        BowlingScoring bowlingScoring2 = new BowlingScoring("9 1 9 1");
        assertThat(bowlingScoring2.calculate(), equalTo(29));
        BowlingScoring bowlingScoring3 = new BowlingScoring("1 1 1 1 10 1 1");
        assertThat(bowlingScoring3.calculate(), equalTo(18));
        BowlingScoring bowlingScoring4 = new BowlingScoring("10 10 10 10 10 10 10 10 10 10 10 10");
        assertThat(bowlingScoring4.calculate(), equalTo(300));
        BowlingScoring bowlingScoring5 = new BowlingScoring("10 10 9 1 10 10 10 10 10 10 10 10 10");
        assertThat(bowlingScoring5.calculate(), equalTo(279));
    }

    @Test
    public void testCalculate_InvalidScoreValueException_singleValueGT10() {
        BowlingScoring bowlingScoring1 = new BowlingScoring("11 2 3 4");

        try {
            bowlingScoring1.calculate();
            fail("should throw InvalidScoreValueException here.");
        } catch (InvalidScoreValueException e) {
            assertThat(e.getMessage(), equalTo("The value of the score for one ball is between 0 and 10."));
        } catch (Throwable e) {
            fail("should catch InvalidScoreValueException.");
        }
    }

    @Test
    public void testCalculate_InvalidScoreValueException_frameCountGT10() {
        BowlingScoring bowlingScoring1 = new BowlingScoring("9 2 3 4");

        try {
            bowlingScoring1.calculate();
            fail("should throw InvalidScoreValueException here.");
        } catch (InvalidScoreValueException e) {
            assertThat(e.getMessage(), equalTo("The total score for one frame can not greater than 10."));

        } catch (Throwable e) {
            fail("should catch InvalidScoreValueException.");
        }
    }

    @Test
    public void testCalculate_InvalidScoreCountException() {
        BowlingScoring bowlingScoring1 = new BowlingScoring("11 2 3 4");

        try {
            bowlingScoring1.calculate();
            fail("should throw InvalidScoreCountException here.");
        } catch (InvalidScoreCountException e) {
        } catch (Throwable e) {
            fail("should catch InvalidScoreCountException.");
        }
    }
}