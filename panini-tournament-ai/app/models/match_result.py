from pydantic import BaseModel
from typing import List


class GoalScorer(BaseModel):
    playerId: int
    minute: int


class MatchResult(BaseModel):
    matchId: int
    goalsHome: int
    goalsAway: int
    scorers: List[GoalScorer]