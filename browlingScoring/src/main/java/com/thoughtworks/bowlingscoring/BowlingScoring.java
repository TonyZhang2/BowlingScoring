package com.thoughtworks.bowlingscoring;

import com.thoughtworks.bowlingscoring.exception.InvalidScoreCountException;
import com.thoughtworks.bowlingscoring.exception.InvalidScoreValueException;

import java.util.ArrayList;
import java.util.List;

public class BowlingScoring {

    public static final String STR_SPLITTER_SPACE = " ";
    public static final Integer MIN_SCORE_VALUE = 0;
    public static final Integer MAX_SCORE_VALUE = 10;
    public static final int MAX_SCORE_COUNT_VALUE = 21;

    private List<Integer> scores;

    public BowlingScoring(String scoreString) {
        this.initScoreList(scoreString);
    }

    private void initScoreList(String scoreString) {
        scores = new ArrayList<Integer>();
        if (scoreString != null && !scoreString.trim().isEmpty()) {
            String[] splitStrings = scoreString.split(STR_SPLITTER_SPACE);

            for (String splitString : splitStrings) {
                Integer score = Integer.valueOf(splitString);
                if (score < MIN_SCORE_VALUE || score > MAX_SCORE_VALUE) {
                    throw new InvalidScoreValueException("The value of the score for one ball is between 0 and 10.");
                }
                scores.add(score);
                if (scores.size() < MAX_SCORE_COUNT_VALUE - 3 && score == MAX_SCORE_VALUE) {
                    scores.add(MIN_SCORE_VALUE);
                }
            }

            if (scores.size() > MAX_SCORE_COUNT_VALUE) {
                throw new InvalidScoreCountException();
            }
        }
        for (int i = scores.size(); i < MAX_SCORE_COUNT_VALUE; i++) {
            scores.add(MIN_SCORE_VALUE);
        }
    }

    public Integer calculate() {
        Integer totalScore = 0;
        for (int i = 0; i < scores.size() && i < MAX_SCORE_COUNT_VALUE - 3; i += 2) {
            Integer firstBallScore = scores.get(i);
            Integer secondBallScore = scores.get(i + 1);
            Integer nextFrameFirstBallScore = scores.get(i + 2);
            Integer nextFrameSecondBallScore = scores.get(i + 3);
            Integer nextNextFrameFirstBallScore = scores.get(i + 4);

            Integer currentFrameScore = firstBallScore + secondBallScore;
            if (currentFrameScore > MAX_SCORE_VALUE) {
                throw new InvalidScoreValueException("The total score for one frame can not greater than 10.");
            }
            totalScore += currentFrameScore;
            if (firstBallScore == MAX_SCORE_VALUE) {
                if (nextFrameFirstBallScore == MAX_SCORE_VALUE) {
                    totalScore += nextFrameFirstBallScore + nextNextFrameFirstBallScore;
                } else {
                    totalScore += nextFrameFirstBallScore + nextFrameSecondBallScore;
                }
            } else if (currentFrameScore == MAX_SCORE_VALUE) {
                totalScore += nextFrameFirstBallScore;
            }
        }
        totalScore += scores.get(MAX_SCORE_COUNT_VALUE - 1) + scores.get(MAX_SCORE_COUNT_VALUE - 2) + scores.get
                (MAX_SCORE_COUNT_VALUE - 3);
        return totalScore;
    }
}
