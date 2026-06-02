from  pydantic import BaseModel


class TradeSide(BaseModel):

    code: str

    name: str

    rating: int


class TradeProposal(BaseModel):

    score: float

    fairness: float

    user1_gives: TradeSide

    user2_gives: TradeSide


class RecommendationResponse(BaseModel):

    trades: list[TradeProposal]