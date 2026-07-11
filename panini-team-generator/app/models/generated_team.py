from pydantic import BaseModel
from uuid import uuid4


class GeneratedPlayer(BaseModel):
    id: int
    name: str
    position: str
    ability: int
    nationality: str


class GeneratedTeam(BaseModel):
    team_id: str
    email: str
    team_name: str
    players: list[GeneratedPlayer]