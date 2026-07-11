from fastapi import FastAPI
from threading import Thread

from app.kafka.owned_players_consumer import (
    start_consumer
)

from app.routes.team_routes import (
    router as team_router
)

app = FastAPI()


@app.on_event("startup")
def startup():

    Thread(
        target=start_consumer,
        daemon=True
    ).start()


app.include_router(team_router)


@app.get("/")
def health():

    return {
        "status": "running"
    }