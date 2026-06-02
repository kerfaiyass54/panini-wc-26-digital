from  fastapi import FastAPI

from  database import SessionLocal

from fastapi.middleware.cors import CORSMiddleware


from  services.recommendation_service import (
    RecommendationService
)
app = FastAPI(
    title="Sticker Recommendation Engine"
)

app.add_middleware(
    CORSMiddleware,
    allow_origins=[
        "http://localhost:4200"
    ],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


@app.get("/recommendations")
def recommendations(
    user1: str,
    user2: str
):

    db = SessionLocal()

    try:

        service = RecommendationService(db)

        return service.recommend(
            user1,
            user2
        )

    finally:

        db.close()