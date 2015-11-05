package com.thoughtworks.bowlingscoring;

import com.thoughtworks.bowlingscoring.exception.InvalidScoreCountException;
import com.thoughtworks.bowlingscoring.model.GeneralFrame;

import static com.thoughtworks.bowlingscoring.Constants.FIRST_FRAME_INDEX;
import static com.thoughtworks.bowlingscoring.Constants.MIN_SCORE_VALUE;

public class BowlingScoring {

    private GeneralFrame frame;

    public BowlingScoring(String scoreString) {
        this.initFrame(scoreString);
    }

    private void initFrame(String scoreString) {

        GeneralFrame currentFrame = new GeneralFrame(FIRST_FRAME_INDEX);
        this.frame = currentFrame;
        if (scoreString != null && !scoreString.trim().isEmpty()) {
            String[] splitStrings = scoreString.split(Constants.STR_SPLITTER_SPACE);
            for (String splitString : splitStrings) {
                Integer score = Integer.valueOf(splitString);
                currentFrame = currentFrame.addScore(score);
            }
        }

        try {
            // fill in the rest score with 0 for the entire game.
            do {
                currentFrame.addScore(MIN_SCORE_VALUE);
            } while (1 == 1);
        } catch (InvalidScoreCountException e) {
        }
    }

    public Integer calculate() {
        Integer totalScore = 0;
        GeneralFrame currentFrame = this.frame;
        while (currentFrame != null) {
            totalScore += currentFrame.getFrameScore();
            currentFrame = currentFrame.getNextFrame();
        }
        return totalScore;
    }
}
