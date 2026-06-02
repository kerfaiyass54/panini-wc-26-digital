from  fastapi import FastAPI

from  database import SessionLocal

from  services.recommendation_service import (
    RecommendationService
)

app = FastAPI(
    title="Sticker Recommendation Engine"
)


@app.get("/recommendations")
def recommendations(
        user1: str,
        user2: str
):

    db = SessionLocal()

    try:

        service = RecommendationService(
            db
        )

        return service.recommend(
            user1,
            user2
        )

    finally:

        db.close()