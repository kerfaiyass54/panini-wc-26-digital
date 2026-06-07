from pydantic import BaseModel


class TradeRequestPlayer(BaseModel):

    code: str
    name: str
    nationality: str
    rating: int
    score: float


class TradeRequestResponse(BaseModel):

    give: dict
    want: dict