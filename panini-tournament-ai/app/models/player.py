from pydantic import BaseModel

class Player(BaseModel):
    id: int
    name: str
    ability: int
    position: str