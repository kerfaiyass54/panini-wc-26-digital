from pydantic import BaseModel


class PlayerInfo(BaseModel):
    id: int
    name: str
    position: str
    ability: int
    nationality: str


class OwnedPlayersResponse(BaseModel):
    email: str
    players: list[PlayerInfo]