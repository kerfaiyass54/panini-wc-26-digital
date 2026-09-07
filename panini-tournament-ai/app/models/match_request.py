from pydantic import BaseModel
from .team import Team

class MatchRequest(BaseModel):
    matchId: int
    homeTeam: Team
    awayTeam: Team