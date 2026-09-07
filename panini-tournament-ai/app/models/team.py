from pydantic import BaseModel
from typing import List
from .player import Player

class Team(BaseModel):
    name: str
    players: List[Player]